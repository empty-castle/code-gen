package com.intellij.sql.psi;

import com.intellij.database.symbols.DasPsiSymbol;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface SqlImplicitTarget extends DasPsiSymbol {
  @Override
  @NotNull
  PsiElement getContextElement();
}
