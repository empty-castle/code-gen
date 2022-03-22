/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.database.util;

import com.intellij.database.model.CasingProvider;
import com.intellij.database.model.ObjectKind;
import com.intellij.database.model.ObjectName;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.PairFunction;
import com.intellij.util.ThrowableConsumer;
import com.intellij.util.containers.HashingStrategy;
import com.intellij.util.containers.Interner;
import com.intellij.util.containers.WeakInterner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author gregsh
 */
public class ObjectPath extends ObjectPart {
  private static final Interner<ObjectPath> ourInterner = new WeakInterner<>(new HashingStrategy<>() {
    @Override
    public int hashCode(ObjectPath object) {
      int result = System.identityHashCode(object.parent);
      result = 31 * result + Boolean.hashCode(object.isStepUp());
      result = 31 * result + ObjectPart.hc(object);
      return result;
    }

    @Override
    public boolean equals(ObjectPath o1, ObjectPath o2) {
      if (o1 == o2 || o1 == null || o2 == null) return o1 == o2;
      return o1.parent == o2.parent && o1.isStepUp() == o2.isStepUp() && o1.matches(o2);
    }
  });
  public static final ObjectPath CURRENT = createInterned(".", ObjectKind.NONE, true, null, false, null);

  public final ObjectPath parent;

  private ObjectPath(@NotNull String name, @NotNull ObjectKind kind, @Nullable ObjectPath parent) {
    super(name, kind);
    this.parent = parent;
  }

  @NotNull
  public ObjectPath append(@NotNull String name, @NotNull ObjectKind kind) {
    return create(name, kind, true, null, this);
  }

  public ObjectPath append(@NotNull String name, @NotNull ObjectKind kind, boolean quoted, String identity) {
    return create(name, kind, quoted, identity, this);
  }

  public boolean isStepUp() {
    return false;
  }

  public int getSize() {
    int result = 0;
    for (ObjectPath p = this; p != null; p = p.parent) {
      ++result;
    }
    return result;
  }

  @Nullable
  public ObjectPath getParent(int steps) {
    ObjectPath result = this;
    for (int i = steps; i > 0 && result != null; i--) result = result.parent;
    return result;
  }

  @Nullable
  public ObjectPath findParent(ObjectKind kind, boolean strict) {
    for (ObjectPath p = strict ? parent : this; p != null; p = p.parent) {
      if (p.kind == kind) return p;
    }
    return null;
  }

  public boolean isAncestorOf(@Nullable ObjectPath child, boolean strict) {
    if (strict && this == child) return false;
    while (child != null && child != this) child = child.parent;
    return this == child;
  }

  @Nullable
  public ObjectPath getCommonAncestor(@Nullable ObjectPath other) {
    if (other == null) return null;
    ObjectPath self = this;
    int s = self.getSize();
    int os = other.getSize();
    if (s < os) {
      other = other.getParent(os - s);
    }
    else if (s > os) {
      self = self.getParent(s - os);
    }
    for (; self != other; self = self.parent, other = other.parent) {
      if (self == null || other == null) return null;
    }
    return self;
  }

  @NlsSafe
  @NotNull
  public String getDisplayName() {
    return reduce(
      new StringBuilder(), (sb, p) ->
        (sb.length() > 0 && p.name.length() > 0 ? sb.append(".") : sb).append(p.name))
      .toString();
  }

  @NlsSafe
  @NotNull
  public String getName() {
    return name;
  }

  public <T> T reduce(T t, @NotNull PairFunction<T, ObjectPath, T> reducer) {
    T result = t;
    if (parent != null) result = parent.reduce(t, reducer);
    return reducer.fun(result, this);
  }

  @NotNull
  public ObjectPath appendPart(@NotNull ObjectPart o) {
    return append(o.name, o.kind, o.isQuoted(), o.getIdentity());
  }

  @NotNull
  public static ObjectPath create(@NotNull String name, @NotNull ObjectKind kind) {
    return create(name, kind, true, null, null);
  }

  public static ObjectPath create(@NotNull String name,
                                  @NotNull ObjectKind kind,
                                  boolean quoted,
                                  @Nullable String identity,
                                  @Nullable ObjectPath parent) {
    return createInterned(name, kind, quoted, identity, false, parent);
  }

  public static ObjectPath createStepUp(@NotNull ObjectKind kind, @NotNull ObjectPath parent) {
    return createInterned("..", kind, true, null, true, parent);
  }

  public static ObjectPath copyUnder(@NotNull ObjectPath child, @Nullable ObjectPath parent) {
    return create(child.name, child.kind, child.isQuoted(), child.getIdentity(), parent);
  }

  @NotNull
  private static ObjectPath createInterned(@NotNull String name,
                                           @NotNull ObjectKind kind,
                                           boolean quoted,
                                           @Nullable String identity,
                                           boolean stepUp,
                                           @Nullable ObjectPath parent) {
    return ourInterner.intern(prepare(name, kind, quoted, identity, stepUp, parent));
  }
  @NotNull
  private static ObjectPath prepare(@NotNull String name,
                                    @NotNull ObjectKind kind,
                                    boolean quoted,
                                    @Nullable String identity,
                                    boolean stepUp,
                                    @Nullable ObjectPath parent) {
    if (stepUp) return new ObjectPath(name, kind, parent) {
      @Override public boolean isStepUp() { return true; }
    };
    if (quoted && identity == null) return new ObjectPath(name, kind, parent);
    if (identity == null) return new ObjectPath(name, kind, parent) {
      @Override public boolean isQuoted() { return false; }
    };
    if (quoted) return new ObjectPath(name, kind, parent) {
      @Override public String getIdentity() { return identity; }
    };
    return new ObjectPath(name, kind, parent) {
      @Override public boolean isQuoted() { return false; }
      @Override public String getIdentity() { return identity; }
    };
  }

  @NotNull
  public static ObjectPath create(@NotNull ObjectKind kind, @NotNull Iterable<String> names) {
    ObjectPath result = null;
    for (Iterator<String> it = names.iterator(); it.hasNext(); ) {
      result = create(it.next(), !it.hasNext() ? kind : ObjectKind.NONE, true, null, result);
    }
    return Objects.requireNonNull(result);
  }

  public <T extends Throwable> void forEach(@NotNull ThrowableConsumer<ObjectPath, T> r) throws T {
    forEachInner(this, r);
  }

  private static <T extends Throwable> void forEachInner(@NotNull ObjectPath p, @NotNull ThrowableConsumer<ObjectPath, T> r) throws T {
    if (p.parent != null) forEachInner(p.parent, r);
    r.consume(p);
  }

  public ObjectPath rebase(ObjectPath base) {
    return reduce(null, (r, o) ->
      o.kind == base.kind ? base : o.withParent(r));
  }

  public ObjectPath withParent(ObjectPath p) {
    return parent == p ? this : create(name, kind, isQuoted(), getIdentity(), p);
  }

  @Override
  public String toString() {
    String id = getIdentity();
    return (parent != null ? parent + "/" : "") +
           (kind == ObjectKind.NONE ? "" : kind.name() + ":") + name + (id == null ? "" : "@" + id);
  }

  @NotNull
  public String serialize() {
    return reduce(new StringBuilder(), (b, p) -> {
      if (b.length() != 0) b.append("/");
      if (p.isStepUp() || p == CURRENT) b.append("#");
      b.append(escape(p.kind.code()))
        .append("/");
      if (p.isQuoted()) b.append('"');
      b.append(escape(p.name));
      if (p.isQuoted()) b.append('"');
      if (p.getIdentity() != null) b.append(escape(p.getIdentity()));
      return b;
    }).toString();
  }

  @Nullable
  public static ObjectPath deserialize(@NotNull String s) {
    ObjectPath result = null;
    ObjectKind kind = null;
    boolean rel = false;
    for (String chunk : StringUtil.split(s, "/", true, false)) {
      if (kind == null) {
        rel = chunk.startsWith("#");
        kind = ObjectKind.getKind(unescape(rel ? chunk.substring(1) : chunk));
        if (kind == null) {
          kind = ObjectKind.NONE;
        }
      }
      else {
        int idIdx = chunk.indexOf(":");
        String id = idIdx == -1 ? null : unescape(chunk.substring(idIdx + 1));
        if (idIdx != -1) chunk = chunk.substring(0, idIdx);
        boolean quoted = chunk.startsWith("\"") && chunk.endsWith("\"");
        if (quoted) chunk = chunk.substring(1, chunk.length() - 1);
        String name = unescape(chunk);
        result = result == null && rel ? CURRENT : createInterned(name, kind, quoted, id, rel, result);
        kind = null;
        rel = false;
      }
    }
    if (kind != null) {
      result = create("", kind, true, null, result);
    }
    return result;
  }

  private static final List<String> TO_ESCAPE = Arrays.asList("/", "&", "\"", ":");
  private static final List<String> TO_UNESCAPE = Arrays.asList("&eslash;", "&amp;", "&quot;", "&colon;");
  private static String escape(String s) {
    return StringUtil.replace(s, TO_ESCAPE, TO_UNESCAPE);
  }

  private static String unescape(String s) {
    return StringUtil.replace(s, TO_UNESCAPE, TO_ESCAPE);
  }

  public static boolean namesEqual(@Nullable ObjectPath p1, @Nullable ObjectPath p2, @NotNull CasingProvider casingProvider) {
    return p1 == null || p2 == null ? p1 == p2 : ObjectName.namesEqual(
      p1.name, casingProvider.getCasing(p1.kind, null).choose(!p1.isQuoted()),
      p2.name, casingProvider.getCasing(p2.kind, null).choose(!p2.isQuoted()));
  }

  public static boolean namesEqual(@Nullable ObjectPath p1, @Nullable ObjectPath p2, @NotNull Casing casing) {
    return p1 == null || p2 == null ? p1 == p2 : ObjectName.namesEqual(p1.name, casing.choose(!p1.isQuoted()), p2.name, casing.choose(!p2.isQuoted()));
  }
}
