package com.intellij.sql.psi;

import org.jetbrains.annotations.Nullable;

public interface SqlWithOrdinalityClause extends SqlClause {

  @Nullable
  SqlTypedDefinition getOrdinalityDefinition();
}
