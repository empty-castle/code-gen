package com.intellij.sql.dialects;

import com.intellij.database.model.DataType;
import com.intellij.sql.psi.SqlType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SqlTypeSystem {
  protected SqlTypeSystem() {
  }

  @NotNull
  public abstract String getNormalizedTypeName(@NotNull String name);

  @NotNull
  public abstract DataType normalizeType(@NotNull DataType type);

  @Nullable
  public abstract String getDefaultTypeName(@NotNull SqlType.Category cat);

  @NotNull
  public abstract SqlType.Category getTypeCategory(@NotNull DataType dataType);
}
