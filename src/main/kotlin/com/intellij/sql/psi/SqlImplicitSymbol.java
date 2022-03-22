package com.intellij.sql.psi;

import com.intellij.database.model.CasingProvider;
import com.intellij.database.util.Casing;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SqlImplicitSymbol extends SqlImplicitTarget {
  @Nullable
  PsiElement getTargetDefinition();
  
  @NotNull
  Casing getCasing(CasingProvider casingProvider);
}