package com.intellij.sql.psi;

import org.jetbrains.annotations.NotNull;

/**
 * @author Gregory.Shrago
 */
public interface SqlExpression extends SqlDasTypeAwareElement {
  @NotNull
  SqlType getSqlType();
}
