package com.intellij.database.psi;

import com.intellij.database.model.DasObject;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ModelNameIndex {
  void clear();

  @NotNull Collection<String> getAllNames();

  @NotNull Collection<DasObject> getObjectsByName(@NotNull String name);

  @NotNull JBIterable<DasObject> getObjectsByNameInsensitive(@NotNull String name);
}
