package com.intellij.sql.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;

/**
 * @author Gregory.Shrago
 */
public interface SqlImplicitDeclarationsProvider extends SqlElement {
  boolean processImplicitContextDeclarations(final SqlScopeProcessor processor, final ResolveState state, final PsiElement lastParent, final PsiElement place);

  boolean stopProcessingIfFoundInImplicitContext();
}
