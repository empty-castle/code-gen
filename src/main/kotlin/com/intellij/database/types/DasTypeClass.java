package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasTypeClass {
  @NotNull
  String getName();

  @NotNull
  default String getCanonicalName() {
    return getName();
  }
}