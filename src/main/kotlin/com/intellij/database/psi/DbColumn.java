package com.intellij.database.psi;

import com.intellij.database.model.DasColumn;
import com.intellij.database.model.PsiColumn;
import com.intellij.database.symbols.PsiColumnWithSymbol;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gregory.Shrago
 */
public interface DbColumn extends DbTableChild, PsiColumn, PsiColumnWithSymbol {
  @Override
  @NotNull
  default DasColumn getDasObject() {
    return this;
  }
  
  @Override
  DbTable getParent();
}
