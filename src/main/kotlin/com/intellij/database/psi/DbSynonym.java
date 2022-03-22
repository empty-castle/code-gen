package com.intellij.database.psi;

import com.intellij.database.model.DasSynonym;
import org.jetbrains.annotations.Nullable;

public interface DbSynonym extends DbElement, DasSynonym {
  @Nullable
  @Override
  DbElement resolveTarget();
}
