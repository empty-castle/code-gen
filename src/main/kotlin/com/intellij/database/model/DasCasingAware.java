package com.intellij.database.model;

import com.intellij.database.util.Casing;
import org.jetbrains.annotations.NotNull;

public interface DasCasingAware {
  @NotNull
  Casing getCustomCasing(CasingProvider casingProvider);
}
