package com.intellij.sql.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.util.NotNullFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SqlHierarchicalElementType extends SqlCompositeElementType {
  public static NotNullFunction<String, SqlHierarchicalElementType> factory(@Nullable SqlHierarchicalElementType parentType) {
    return name -> new SqlHierarchicalElementType(name, parentType);
  }
  
  @Nullable
  private final SqlHierarchicalElementType parentType;
  
  public SqlHierarchicalElementType(@NotNull @NonNls String debugName) {
    this(debugName, null);
  }

  public SqlHierarchicalElementType(@NotNull @NonNls String debugName, @Nullable SqlHierarchicalElementType parentType) {
    super(debugName);
    this.parentType = parentType;
  }

  @Nullable
  public final SqlHierarchicalElementType getParentType() {
    return parentType;
  }
  
  @Contract("null -> false")
  public boolean contains(@Nullable IElementType elementType) {
    if (elementType == null) return false;
    if (elementType == this) return true;
    if (!(elementType instanceof SqlHierarchicalElementType)) return false;
    SqlHierarchicalElementType parentType = ((SqlHierarchicalElementType)elementType).getParentType();
    return parentType != null && contains(parentType);
  }
}
