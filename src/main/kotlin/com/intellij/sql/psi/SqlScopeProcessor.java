package com.intellij.sql.psi;

import com.intellij.database.model.DasScopeProcessor;
import com.intellij.database.model.ObjectKind;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.symbols.DasSymbol;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.sql.dialects.SqlLanguageDialect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface SqlScopeProcessor extends DasScopeProcessor {
  SqlLanguageDialect getDialect();

  String getReferenceName();
  
  List<DbDataSource> getDataSources();
  
  PsiElement getPlace();

  Set<ObjectKind> getInitialExpectedTypes();

  Set<ObjectKind> getExpectedTypes();

  boolean isExpected(@NotNull ObjectKind kind);

  boolean mayAccept(@NotNull ObjectKind t);

  boolean mayAcceptParent(@NotNull ObjectKind t);

  default void addIgnoredKinds(@NotNull Set<ObjectKind> kinds) {}

  default void setExpectedKinds(@NotNull Set<ObjectKind> kinds) {}

  default void setResolveContext(@Nullable PsiElement context) {}

  default void setStrict(boolean isStrict) {}

  boolean isResultEmpty();

  default boolean executeTarget(@NotNull DasSymbol symbol,
                                @Nullable SqlType sqlType,
                                @Nullable Boolean forcedCaseSens,
                                @NotNull ResolveState state) { return true; }
}