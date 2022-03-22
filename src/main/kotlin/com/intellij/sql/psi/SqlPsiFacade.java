/*
 * Copyright 2000-2016 JetBrains s.r.o.
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
package com.intellij.sql.psi;

import com.intellij.database.model.DasModel;
import com.intellij.database.model.DasObject;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.psi.DbElement;
import com.intellij.database.script.ScriptModel;
import com.intellij.database.util.SearchPath;
import com.intellij.lang.Language;
import com.intellij.model.Pointer;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiCodeFragment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.sql.database.SqlNotebookManager;
import com.intellij.sql.dialects.SqlLanguageDialect;
import com.intellij.util.PairProcessor;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author Gregory.Shrago
 */
public abstract class SqlPsiFacade {

  @NotNull
  public static SqlPsiFacade getInstance(Project project) {
    return project.getService(SqlPsiFacade.class);
  }

  @NotNull
  public abstract SqlNotebookManager getNotebookManager();

  @NotNull
  public abstract ScriptModel<?> createScriptModel(@NotNull PsiFile file);

  @NotNull
  public abstract ScriptModel<?> createScriptModel(@NotNull VirtualFile file, @Nullable Language language);

  @NotNull
  public SqlFile createROFile(@NotNull SqlLanguageDialect dialect, @NotNull CharSequence text) {
    return createROFile(dialect, text, null);
  }

  @NotNull
  public abstract SqlFile createROFile(@NotNull SqlLanguageDialect dialect, @NotNull CharSequence text, @Nullable Language hostLanguage);

  @NotNull
  public PsiFile createROFile(@NotNull Language language, @NotNull CharSequence text) {
    return createROFile(language, text, null);
  }

  @NotNull
  public abstract PsiFile createROFile(@NotNull Language language, @NotNull CharSequence text, @Nullable Language hostLanguage);

  @NotNull
  public abstract JBIterable<DbElement> findRelatedDbElements(@Nullable PsiElement element, boolean strict);

  public abstract void format(@NotNull Project project, @NotNull SqlLanguageDialect dialect,
                              @NotNull Document document, @Nullable CodeStyleSettings settings);

  @NotNull
  public abstract PsiCodeFragment createTableReferenceFragment(@NotNull Language dialect,
                                                               @Nullable DbDataSource dataSourceElement,
                                                               @Nullable DbElement schemaElement,
                                                               @NotNull String text);

  @NotNull
  public abstract SqlCodeFragment createTypeElementFragment(@NotNull Language dialect,
                                                            @Nullable DbDataSource dataSourceElement,
                                                            @Nullable SearchPath searchPath,
                                                            @NotNull String text);

  @NotNull
  public abstract PsiCodeFragment createEvaluableExpressionFragment(@NotNull Language dialect,
                                                                    @Nullable DbDataSource dataSourceElement,
                                                                    @Nullable SearchPath searchPath,
                                                                    @NotNull String text);

  @NotNull
  public abstract SqlCodeFragment createExpressionFragment(@NotNull Language dialect,
                                                           @Nullable DbDataSource dataSourceElement,
                                                           @Nullable SearchPath searchPath,
                                                           @NotNull String text);

  @NotNull
  public abstract SqlCodeFragment createExpressionFragment(@NotNull Language dialect,
                                                           @Nullable DbDataSource dataSourceElement,
                                                           @Nullable SearchPath searchPath,
                                                           @NotNull String text,
                                                           boolean isPhysical);

  @NotNull
  public abstract SqlCodeFragment createExpressionFragment(@NotNull Language dialect,
                                                           @Nullable DbDataSource dataSourceElement,
                                                           @Nullable SearchPath searchPath,
                                                           @NotNull String text,
                                                           @NotNull String context);

  @NotNull
  public abstract SqlCodeFragment createOrderByFragment(@NotNull Language dialect,
                                                        @Nullable DbDataSource dataSourceElement,
                                                        @Nullable SearchPath searchPath,
                                                        @NotNull String text,
                                                        @NotNull String context);

  @Nullable
  public abstract SqlLanguageDialect getConfiguredSqlLanguageDialect(@NotNull VirtualFile file);

  public abstract void openDialectsConfigurable(@NotNull List<VirtualFile> files);

  @Nullable
  public abstract SqlLanguageDialect getConfiguredDefaultDialect();

  public abstract void setDialectMapping(@Nullable VirtualFile file, @NotNull SqlLanguageDialect dialect);

  @NotNull
  public abstract SqlLanguageDialect getDialectMapping(@Nullable VirtualFile file);

  @Nullable
  public abstract ExecutionFlowAnalyzer<PsiElement> getExecutionFlowAnalyzer(@NotNull Language language);

  public abstract Map<? extends DasObject, Pointer<DasObject>> buildModel(DasModel model, List<SqlFile> files, PairProcessor<DasObject, DasObject> newHandler);

  @NotNull
  public abstract List<DbDataSource> getDataSources(@NotNull PsiElement element);
}