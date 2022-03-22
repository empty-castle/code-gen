package com.intellij.database.symbols;

import com.intellij.database.Dbms;
import com.intellij.database.model.DasNamed;
import com.intellij.database.model.DasObject;
import com.intellij.database.model.ObjectKind;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DasSymbol extends DasNamed, UserDataHolder {
  @NotNull Dbms getDbms();
  
  boolean isValid();

  @Nullable DasObject getDasObject();
  
  @Override
  @NotNull ObjectKind getKind();

  @NotNull JBIterable<? extends PsiElement> getPsiDeclarations();
  
  @Nullable PsiElement getNavigationElement();
  
  @Nullable PsiElement getContextElement();
}