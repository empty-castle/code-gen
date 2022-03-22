package com.intellij.sql.psi;

import com.intellij.lang.LanguageExtension;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface ExecutionFlowAnalyzerProvider {

  LanguageExtension<ExecutionFlowAnalyzerProvider> EP = new LanguageExtension<>("com.intellij.sql.executionFlowAnalyzerProvider");

  @NotNull
  ExecutionFlowAnalyzer getAnalyzer(@NotNull Project project);
}
