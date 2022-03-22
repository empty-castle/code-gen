/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.database.psi;

import com.intellij.database.dialects.DatabaseDialect;
import com.intellij.database.model.DasDataSource;
import com.intellij.database.model.DasModel;
import com.intellij.database.model.DasObject;
import com.intellij.database.model.DatabaseSystem;
import com.intellij.database.util.ObjectPath;
import com.intellij.lang.Language;
import com.intellij.util.Function;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface DbDataSource extends DbElement, DasDataSource {

  /**
   * The upcoming breaking change: return type will be changed to DasDataSource.
   * for connection config use {@link DbDataSource#getConnectionConfig()}
   * for LocalDataSource use {@link com.intellij.database.util.DbImplUtil#getMaybeLocalDataSource(DasDataSource)}
   */
  @ApiStatus.Internal
  @NotNull
  @Override
  DatabaseSystem getDelegate();

  @Override
  @NotNull
  DasModel getModel();

  @NotNull DatabaseDialect getDatabaseDialect();

  @NotNull Language getQueryLanguage();

  <S> Function<DasObject, S> mapper();

  @Contract("null->null;!null->!null")
  DbElement findElement(@Nullable DasObject o);
  @Nullable
  DbElement findElement(@NotNull ObjectPath path);

  @NotNull JBIterable<DasObject> findObjects(@NotNull ObjectPath path);

  @NotNull ModelNameIndex getNameIndex();
}
