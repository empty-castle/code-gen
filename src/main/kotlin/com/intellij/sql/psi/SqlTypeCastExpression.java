package com.intellij.sql.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author ignatov
 */
public interface SqlTypeCastExpression extends SqlOperatorExpression {
  @NotNull
  SqlExpression getExpression();

  @Nullable
  SqlTypeElement getTypeElement();
}
