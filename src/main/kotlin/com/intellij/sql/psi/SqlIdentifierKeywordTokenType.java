package com.intellij.sql.psi;

import org.jetbrains.annotations.NotNull;

/**
 * @author Gregory.Shrago
 */
public class SqlIdentifierKeywordTokenType extends SqlTokenType {
  private final SqlKeywordTokenType myKeyword;

  public SqlIdentifierKeywordTokenType(@NotNull SqlKeywordTokenType keyword) {
    super(keyword.getDebugName() + "_IDENT", SqlLanguage.INSTANCE, false);
    myKeyword = keyword;
  }

  public SqlIdentifierKeywordTokenType(@NotNull SqlKeywordTokenType keyword, boolean register) {
    super(keyword.getDebugName() + "_IDENT", SqlLanguage.INSTANCE, register);
    myKeyword = keyword;
  }

  public SqlKeywordTokenType getKeyword() {
    return myKeyword;
  }
}