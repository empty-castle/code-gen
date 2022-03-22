package com.intellij.database.types;

import com.intellij.database.model.DasUserDefinedType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DasDefinedTypeClass extends DasTypeClass {
  @Nullable
  String getSchemaName();

  @Nullable
  String getPackageName();

  @NotNull
  DasUserDefinedType getDefinition();
}
