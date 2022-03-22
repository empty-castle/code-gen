package com.intellij.sql.psi;

import com.intellij.database.model.DataType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gregory.Shrago
 */
public interface SqlTypeElement extends SqlDasTypeAwareElement {

  @NotNull
  DataType getDataType();

  @NotNull
  SqlType findSqlType();

  String getDisplayName();
}
