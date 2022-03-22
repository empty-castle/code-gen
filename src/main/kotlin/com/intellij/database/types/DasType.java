package com.intellij.database.types;

import com.intellij.database.model.DataType;
import org.jetbrains.annotations.NotNull;

public interface DasType {
  @NotNull
  default DataType toDataType() {
    return DataType.UNKNOWN;
  }

  @NotNull
  String render();
}