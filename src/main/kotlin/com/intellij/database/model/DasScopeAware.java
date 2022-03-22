package com.intellij.database.model;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DasScopeAware {
  default boolean processDeclarations(@NotNull DasScopeProcessor processor,
                                      @NotNull ResolveState state,
                                      @Nullable PsiElement lastParent,
                                      @NotNull PsiElement place) { return true; }
}
