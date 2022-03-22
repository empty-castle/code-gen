package com.intellij.sql.dialects;

import com.intellij.database.Dbms;
import com.intellij.database.DbmsExtension;
import com.intellij.database.dialects.DatabaseDialect;
import com.intellij.database.model.DasColumn;
import com.intellij.database.model.DasObject;
import com.intellij.database.model.ObjectKind;
import com.intellij.database.symbols.DasSymbol;
import com.intellij.lang.Language;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.sql.psi.*;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Gregory.Shrago
 */
public abstract class SqlLanguageDialect extends Language {
  public static final DbmsExtension<SqlLanguageDialect> EP = new DbmsExtension<>("com.intellij.sql.dialect");
  public static final String ISO_SQL_ID = "SQL92";
  public static final String GENERIC_SQL_ID = "GenericSQL";

  public SqlLanguageDialect(@NonNls @NotNull String id) {
    super(SqlLanguage.INSTANCE, id);
  }

  protected SqlLanguageDialect(@NotNull SqlLanguageDialect base, @NonNls @NotNull String id) {
    super(base, id);
  }

  @NotNull
  public abstract DatabaseDialect getDatabaseDialect();

  @NotNull
  public abstract Icon getIcon();

  @NotNull
  public abstract Dbms getDbms();

  public abstract boolean isReservedKeyword(IElementType tokenType);

  public boolean isReservedKeywordPL(IElementType tokenType) {
    return isReservedKeyword(tokenType);
  }

  public abstract boolean isOperatorSupported(IElementType tokenType);

  @NotNull
  public abstract Set<String> getKeywords();

  @NotNull
  public abstract Set<String> getReservedKeywords();

  @NotNull
  public abstract Set<String> getSystemVariables();

  @NotNull
  public Set<String> getExceptionNames() {
    return Collections.emptySet();
  }

  public boolean isResolveTargetAccepted(@Nullable DasSymbol symbol,
                                         ObjectKind type,
                                         @NotNull Set<ObjectKind> expectedTypes,
                                         @Nullable PsiElement place, boolean strict, boolean isCompletion) {
    return false;
  }

  public boolean isResolveTargetNotAccepted(@Nullable DasSymbol symbol,
                                            ObjectKind type,
                                            @NotNull Set<ObjectKind> expectedTypes,
                                            @Nullable PsiElement place, boolean strict, boolean isCompletion) {
    return false;
  }

  @NotNull
  public ObjectKind unaliased(@NotNull ObjectKind kind) {
    return kind;
  }

  public boolean allowGlobalTableResolve(@NotNull PsiElement place) {
    return false;
  }

  public boolean shouldHide(@Nullable DasSymbol symbol, @Nullable PsiElement place) {
    return false;
  }

  public abstract TokenSet getStatementSeparators();

  public String getDelimiterAt(@NotNull SqlFile file, int offset) {
    return ";";
  }

  public abstract Collection<ObjectKind> getMajorChildKinds();

  public final boolean isMajorChild(@NotNull ObjectKind kind) {
    return getMajorChildKinds().contains(kind);
  }

  public abstract boolean shouldSkipFromQualification(@NotNull ObjectKind kind);

  public abstract <T extends Collection<ObjectKind>> T getParentDbTypes(final T result, final ObjectKind type);

  public boolean processReservedEntitiesWithType(@Nullable String name,
                                                 @NotNull PsiElement place,
                                                 boolean resolve,
                                                 @NotNull SqlScopeProcessor processor) {
    return true;
  }

  public boolean processUnqualifiedResolve(@NotNull SqlScopeProcessor processor, @NotNull ResolveState state, @NotNull PsiReference ref) {
    return true;
  }

  public boolean processQualifiedResolve(@NotNull SqlScopeProcessor processor, 
                                         @NotNull ResolveState state, 
                                         @NotNull SqlReference ref, 
                                         @NotNull SqlExpression qualifier) {
    return true;
  }

  @Nullable
  public Condition<DasObject> getAdditionalExpandCondition(@NotNull SqlScopeProcessor processor, @NotNull PsiElement place) {
    return null;
  }

  @Nullable
  public Condition<DasObject> getAdditionalExpandFilter(@NotNull SqlScopeProcessor processor, @NotNull PsiElement place) {
    return null;
  }

  public boolean isValidRawToken(@NotNull IElementType type, @NotNull CharSequence text) {
    return false;
  }

  public boolean isAutoIncrementColumn(@NotNull DasColumn column) {
    return false;
  }

  @NotNull
  public abstract SqlTypeSystem getTypeSystem();

  @NotNull
  public static SqlLanguageDialect getGenericDialect() {
    List<SqlLanguageDialect> dialects = EP.allForDbms(Dbms.UNKNOWN);
    assert !dialects.isEmpty() : "Generic dialect must exist (but found no dialects related to 'UNKNOWN' dbms)";
    SqlLanguageDialect dialect = ContainerUtil.find(dialects, d -> d.getID().equals(GENERIC_SQL_ID));
    assert dialect != null : "No Generic dialect found but found " + dialects.size() + " other ones";
    return dialect;
  }
  
  @NotNull
  public static SqlLanguageDialect getIsoDialect() {
    List<SqlLanguageDialect> dialects = EP.allForDbms(Dbms.UNKNOWN);
    assert !dialects.isEmpty() : "Iso dialect must exist (but found no dialects related to 'UNKNOWN' dbms)";
    SqlLanguageDialect dialect = ContainerUtil.find(dialects, d -> d.getID().equals(ISO_SQL_ID));
    assert dialect != null : "No Iso dialect found but found " + dialects.size() + " other ones";
    return dialect;
  }
}
