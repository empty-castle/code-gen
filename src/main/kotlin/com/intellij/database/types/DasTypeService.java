package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasTypeService {
  @NotNull
  DasType getIntType();

  @NotNull
  DasType getRealType();
  
  @NotNull
  DasType getBooleanType();
  
  @NotNull
  DasType getTimeType();

  @NotNull
  DasType getDateType();

  @NotNull
  DasType getDateTimeType();

  @NotNull
  DasType getTimestampType();

  @NotNull
  DasType getStringType();
}
