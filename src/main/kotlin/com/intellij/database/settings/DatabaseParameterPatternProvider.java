package com.intellij.database.settings;

import com.intellij.openapi.extensions.ExtensionPointName;

public interface DatabaseParameterPatternProvider {
  ExtensionPointName<DatabaseParameterPatternProvider> EP = ExtensionPointName.create("com.intellij.database.parameterPatternProvider");

  UserPatterns.ParameterPattern[] getPatterns();
}
