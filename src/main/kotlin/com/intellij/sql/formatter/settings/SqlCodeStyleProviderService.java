package com.intellij.sql.formatter.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.util.containers.JBIterable;

public abstract class SqlCodeStyleProviderService {
  public abstract JBIterable<? extends SqlLanguageCodeStyleProvider> getProviders();
  
  public static SqlCodeStyleProviderService getInstance() {
    return ApplicationManager.getApplication().getService(SqlCodeStyleProviderService.class);
  }
}
