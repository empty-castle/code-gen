package com.intellij.database.symbols;

import com.intellij.database.model.DasObject;
import org.jetbrains.annotations.NotNull;

public interface DasSymbolObject extends DasSymbol, DasObject {
  @NotNull
  @Override
  default DasObject getDasObject() {
    return this;
  }
}
