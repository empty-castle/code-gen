package com.intellij.sql.psi;

import com.intellij.database.model.DasRoutine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SqlRoutineDefinition extends SqlDefinition, SqlControlFlowHolder, DasRoutine {
  @Nullable
  SqlParameterList getParameterList();
  
  @NotNull
  @Override
  Iterable<SqlParameterDefinition> getArguments();
}
