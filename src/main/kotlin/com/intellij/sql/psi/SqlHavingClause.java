package com.intellij.sql.psi;

import org.jetbrains.annotations.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface SqlHavingClause extends SqlQueryClause {
  @Nullable
  SqlExpression getExpression();
}