package com.intellij.sql.psi;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Alexey Chmutov
 */
public interface SqlDmlInstruction extends SqlElement {
  @Nullable
  SqlExpression getTargetExpression();

  @NotNull
  List<SqlReferenceExpression> getTargetColumnReferences();

  SqlTableType getTargetType();

}
