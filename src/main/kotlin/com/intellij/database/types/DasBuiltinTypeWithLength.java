package com.intellij.database.types;

import com.intellij.database.model.SizeConstant;
import org.jetbrains.annotations.NotNull;

public interface DasBuiltinTypeWithLength<C extends DasBuiltinTypeClass> extends DasBuiltinType<C> {
  @SizeConstant
  int getLength();

  @NotNull
  DasBuiltinTypeWithLength<C> withLength(@SizeConstant int length);
}