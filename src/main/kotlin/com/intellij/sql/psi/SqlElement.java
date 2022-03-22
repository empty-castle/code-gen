package com.intellij.sql.psi;

import com.intellij.database.model.DasScopeAware;
import com.intellij.database.model.DasScopeProcessor;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface SqlElement extends NavigatablePsiElement, DasScopeAware {
  void accept(final SqlVisitor visitor);

  void acceptChildren(final SqlVisitor visitor);

  default boolean processDeclarations(@NotNull final SqlScopeProcessor processor,
                                      @NotNull final ResolveState state,
                                      @Nullable final PsiElement lastParent,
                                      @NotNull final PsiElement place) {
    return true;
  }

  @Override
  default boolean processDeclarations(@NotNull DasScopeProcessor processor,
                                      @NotNull ResolveState state,
                                      @Nullable PsiElement lastParent,
                                      @NotNull PsiElement place) {
    return !(processor instanceof SqlScopeProcessor) ||
           processDeclarations((SqlScopeProcessor)processor, state, lastParent, place);
  }
}
