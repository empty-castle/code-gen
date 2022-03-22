package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasDefinedType extends DasClassedType {
  @NotNull
  @Override
  DasDefinedTypeClass getTypeClass();
}