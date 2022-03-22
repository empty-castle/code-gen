package com.intellij.database.types;

import org.jetbrains.annotations.NotNull;

public interface DasBuiltinTypeClass extends DasTypeClass {
  enum Kind {
    NUMBER,
    BOOLEAN,
    DATE,
    TIME,
    DATE_TIME,
    TIMESTAMP,
    INTERVAL,
    STRING,
    BINARY,
    NULL,
    VOID,
    MISC,
    UNKNOWN
  }

  @NotNull
  Kind getKind();
}
