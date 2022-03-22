package com.intellij.sql.psi;

import com.intellij.database.model.DasTrigger;
import com.intellij.database.model.TrigEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author Gregory.Shrago
 */
public interface SqlCreateTriggerStatement extends SqlCreateStatement, SqlTargetContextProvider, DasTrigger {
  @Override
  @NotNull
  Set<TrigEvent> getEvents();
  @Nullable
  SqlExpression getTriggerProcedure();
}