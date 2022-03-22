package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasCallableType {
  @NotNull
  DasType getReturnType();
}
