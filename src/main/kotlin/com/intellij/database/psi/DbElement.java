/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.database.psi;

import com.intellij.database.model.DasScopeProcessor;
import com.intellij.database.model.ObjectKind;
import com.intellij.database.model.PsiObject;
import com.intellij.database.symbols.DasPsiSymbol;
import com.intellij.database.symbols.DasSymbolObject;
import com.intellij.ide.util.treeView.WeighedItem;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.ResolveState;
import com.intellij.psi.meta.PsiPresentableMetaData;
import com.intellij.util.Processor;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface DbElement extends PsiObject, 
                                   PsiFileSystemItem, 
                                   PsiPresentableMetaData,
                                   WeighedItem,
                                   DasPsiSymbol,
                                   DasSymbolObject {

  @NotNull
  Object getDelegate();

  @Nullable
  @Override
  DbElement getParent();

  @NlsSafe
  @NotNull
  CharSequence getDocumentation(boolean hover);

  //todo: compatibility, move to targets when no V1 docs
  @NlsSafe
  @NotNull
  CharSequence getDocumentationBase();

  @Override
  @Nullable
  String getComment();

  @Override
  @Nullable
  default DbElement getDasParent() {
    return getParent();
  }

  @NotNull
  @Override
  default JBIterable<? extends DbElement> getDasChildren(@Nullable ObjectKind kind) {
    return JBIterable.empty();
  }

  @NotNull
  DbDataSource getDataSource();

  boolean isCaseSensitive();

  @Override
  @NotNull
  String getTypeName();

  @NotNull
  Processor<DbElement> createDeclarationProcessor(@NotNull final DasScopeProcessor processor,
                                                  @NotNull final ResolveState state,
                                                  @Nullable final PsiElement lastParent,
                                                  @NotNull final PsiElement place);
}
