package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasClassedType extends DasType {
  @NotNull
  DasTypeClass getTypeClass();
}