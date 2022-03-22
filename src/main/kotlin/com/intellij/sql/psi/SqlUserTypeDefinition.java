package com.intellij.sql.psi;

import com.intellij.database.model.DasUserDefinedTypeEx;
import org.jetbrains.annotations.Nullable;

public interface SqlUserTypeDefinition extends SqlDefinition, SqlDasTypeAwareElement, DasUserDefinedTypeEx {
  @Nullable
  SqlReferenceExpression getSuperTypeReference();

  @Nullable
  SqlTypeElement getAliasedTypeElement();
}