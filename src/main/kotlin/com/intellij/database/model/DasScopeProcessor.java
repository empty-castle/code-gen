package com.intellij.database.model;

import com.intellij.database.symbols.DasSymbol;
import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.NotNull;

public interface DasScopeProcessor {
  boolean execute(@NotNull DasSymbol symbol, @NotNull ResolveState state);
}
