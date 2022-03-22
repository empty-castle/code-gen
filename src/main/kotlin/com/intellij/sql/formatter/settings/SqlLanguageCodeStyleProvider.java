package com.intellij.sql.formatter.settings;

import com.intellij.psi.codeStyle.LanguageCodeStyleProvider;
import org.jetbrains.annotations.NotNull;

public interface SqlLanguageCodeStyleProvider extends LanguageCodeStyleProvider {
  @NotNull
  Class<? extends SqlCodeStyleSettings> getSettingsClass();
}
