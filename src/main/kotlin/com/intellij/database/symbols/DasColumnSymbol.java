package com.intellij.database.symbols;

import com.intellij.database.model.DasColumn;
import org.jetbrains.annotations.Nullable;

public interface DasColumnSymbol extends DasSymbol {
  @Override
  @Nullable 
  DasColumn getDasObject();
}
