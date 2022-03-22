package com.intellij.database.types;

import com.intellij.database.model.DasNamed;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface DasScope<T extends DasNamed> {
  @NotNull 
  Collection<T> getAllElements();

  @NotNull
  Collection<T> getElementsByName(@NotNull String name);
}
