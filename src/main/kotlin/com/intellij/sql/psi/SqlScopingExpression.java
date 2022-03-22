package com.intellij.sql.psi;

import org.jetbrains.annotations.Nullable;

public interface SqlScopingExpression {
  @Nullable
  SqlTypeElement getTypeElement();
  
  @Nullable
  SqlExpression getInnerExpression();
}
