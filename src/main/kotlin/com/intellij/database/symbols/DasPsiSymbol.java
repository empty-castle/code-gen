package com.intellij.database.symbols;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DasPsiSymbol extends PsiNamedElement, DasSymbol {
  @Override
  @NotNull
  default JBIterable<PsiElement> getPsiDeclarations() {
    return JBIterable.of(this);
  }

  @Override
  @Nullable
  default PsiElement getContextElement() {
    return this;
  }
}