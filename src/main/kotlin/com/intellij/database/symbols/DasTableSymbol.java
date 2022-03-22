package com.intellij.database.symbols;

import com.intellij.database.model.DasTable;
import org.jetbrains.annotations.Nullable;

public interface DasTableSymbol extends DasSymbol {
  @Override
  @Nullable
  DasTable getDasObject();
}
