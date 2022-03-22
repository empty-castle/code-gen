package com.intellij.sql.psi;

import com.intellij.model.psi.PsiSymbolReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiQualifiedReference;
import com.intellij.psi.ResolveResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SqlReference extends PsiPolyVariantReference, PsiQualifiedReference, PsiSymbolReference {
  SqlReferenceElementType getReferenceElementType();

  @Override
  @NotNull
  String getReferenceName();

  boolean isQuoted();

  ResolveResult resolveSingle();

  @Nullable
  PsiElement resolveImmediate();

  @NotNull
  @Override
  default TextRange getAbsoluteRange() {
    return PsiPolyVariantReference.super.getAbsoluteRange();
  }
}