package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasBuiltinType<C extends DasBuiltinTypeClass> extends DasClassedType {
  @NotNull
  @Override
  C getTypeClass();

  @NotNull
  DasBuiltinType<C> withTypeClass(@NotNull C newTypeClass);
}