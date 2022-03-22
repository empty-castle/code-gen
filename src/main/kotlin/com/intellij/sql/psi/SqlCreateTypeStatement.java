package com.intellij.sql.psi;

import org.jetbrains.annotations.Nullable;

/**
 * @author ignatov
 */
public interface SqlCreateTypeStatement extends SqlCreateStatement, SqlUserTypeDefinition {
  @Nullable
  SqlType getSqlType();
}
