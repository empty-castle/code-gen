package com.intellij.sql.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;

/**
 * @author Gregory.Shrago
 */
public interface SqlExtraDeclarationsProvider extends SqlElement {
  boolean processExtraDeclarations(final SqlScopeProcessor processor, final ResolveState state, final PsiElement lastParent, final PsiElement place);
}
