package com.intellij.database.util;

import com.intellij.database.model.ObjectKind;
import org.jetbrains.annotations.NotNull;

public abstract class ObjectPart extends ObjectNamePart {
  public final ObjectKind kind;

  protected ObjectPart(@NotNull String name, @NotNull ObjectKind kind) {
    super(name);
    this.kind = kind;
  }

  public boolean matches(@NotNull ObjectPart o) {
    if (this == o) return true;
    if (!kind.equals(o.kind)) return false;
    return matches(this, o);
  }

  public static int hc(@NotNull ObjectPart object) {
    return 31 * object.kind.hashCode() + hc((ObjectNamePart)object);
  }
}
