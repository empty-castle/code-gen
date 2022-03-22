package com.intellij.database.model;

import com.intellij.database.types.DasType;
import org.jetbrains.annotations.NotNull;

public interface DasTypeAwareObject {
  @NotNull
  DasType getDasType();
}