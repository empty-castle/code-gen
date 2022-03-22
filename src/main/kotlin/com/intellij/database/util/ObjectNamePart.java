package com.intellij.database.util;

import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class ObjectNamePart {
  @NlsSafe
  public final String name;

  protected ObjectNamePart(@NotNull String name) {
    this.name = name;
  }

  public boolean isQuoted() {
    return true;
  }

  @Nullable
  public String getIdentity() {
    return null;
  }

  public static boolean matches(@Nullable ObjectNamePart o1, @Nullable ObjectNamePart o2) {
    if (o1 == o2) return true;
    if (o1 == null || o2 == null) return false;
    if (!o1.name.equals(o2.name)) return false;
    if (!Comparing.equal(o1.isQuoted(), o1.isQuoted())) return false;
    if (!Objects.equals(o2.getIdentity(), o2.getIdentity())) return false;
    return true;
  }

  public static int hc(@NotNull ObjectNamePart object) {
    int result = object.name.hashCode();
    result = 31 * result + Boolean.hashCode(object.isQuoted());
    result = 31 * result + Comparing.hashcode(object.getIdentity());
    return result;
  }
}
