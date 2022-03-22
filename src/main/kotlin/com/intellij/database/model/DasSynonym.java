package com.intellij.database.model;

import com.intellij.database.util.DasUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @see DasUtil#resolveFinalTarget(DasSynonym) for synonyms of synonyms
 *
 * @author Leonid Bushuev from JetBrains
 **/
public interface DasSynonym extends DasObject  {
  @NotNull 
  ObjectKind getTargetObjectKind();
  
  /**
   * Returns the origin object of this synonym (in other words,
   * the object that this synonym for.
   * @return the origin object, or null if not exists or is nor resolved.
   */
  @Nullable
  DasObject resolveTarget();

  /**
   * Returns the namespace containing the synonym's target object
   * @return the origin object schema, or null if it can't be resolved
   */
  @Nullable
  DasNamespace getTargetNamespace();

  /**
   * Returns the origin object path.
   * @return the path.
   */
  @NotNull
  Iterable<String> getTargetPath();
}
