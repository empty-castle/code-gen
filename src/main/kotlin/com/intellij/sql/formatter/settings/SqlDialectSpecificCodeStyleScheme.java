package com.intellij.sql.formatter.settings;

import com.intellij.psi.codeStyle.CodeStyleScheme;
import com.intellij.sql.dialects.SqlLanguageDialect;
import org.jetbrains.annotations.NotNull;


public interface SqlDialectSpecificCodeStyleScheme extends CodeStyleScheme {

  @NotNull
  SqlLanguageDialect getApplicableDialect();

}
