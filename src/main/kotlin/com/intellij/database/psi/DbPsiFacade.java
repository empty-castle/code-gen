/*
 * Copyright 2000-2014 JetBrains s.r.o.
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

import com.intellij.database.model.DasDataSource;
import com.intellij.database.model.DasObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SimpleModificationTracker;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EventListener;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public abstract class DbPsiFacade extends SimpleModificationTracker {
  public static final Topic<Listener> TOPIC = new Topic<>("DB_PSI_TOPIC", Listener.class);

  @NotNull
  public static DbPsiFacade getInstance(@NotNull Project project) {
    return project.getService(DbPsiFacade.class);
  }

  public abstract void clearCaches();

  @NotNull
  public abstract Project getProject();

  @NotNull
  public abstract DataSourceManager<DasDataSource> getDataSourceManager(@NotNull DbDataSource dataSource);

  @NotNull
  public abstract List<DbDataSource> getDataSources();

  @Nullable
  public abstract DbDataSource findDataSource(@Nullable String id);

  /**
   * @deprecated Use com.intellij.database.psi.DbDataSource#findElement(com.intellij.database.model.DasObject)
   */
  @Deprecated
  @ApiStatus.ScheduledForRemoval(inVersion = "2021.3")
  @Nullable
  public abstract DbElement findElement(@Nullable DasObject o);

  public interface Listener extends EventListener {
    void onChanged(@Nullable DbDataSource source);
  }
}
