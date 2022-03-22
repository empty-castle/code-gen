package com.intellij.database.symbols;

import com.intellij.model.Pointer;
import com.intellij.model.Symbol;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DasPredefinedSymbol extends UserDataHolderBase implements DasSymbolObject, Symbol {
  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  @NotNull
  public Pointer<? extends DasPredefinedSymbol> createPointer() {
    return Pointer.hardPointer(this);
  }

  @Override
  @NotNull
  public JBIterable<PsiElement> getPsiDeclarations() {
    return JBIterable.empty();
  }

  @Override
  @Nullable
  public PsiElement getNavigationElement() {
    return null;
  }

  @Override
  @Nullable
  public PsiElement getContextElement() {
    return null;
  }
}
