package com.intellij.database.model;

import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NotNull;

public interface DasNamed {
  /**
   * Returns name or DasUtil.NO_NAME
   */
  @NotNull @NlsSafe
  String getName();

  @NotNull
  ObjectKind getKind();

  boolean isQuoted();  // collateQuoted ???
}
