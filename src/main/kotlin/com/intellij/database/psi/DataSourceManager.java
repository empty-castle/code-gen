package com.intellij.database.psi;

import com.intellij.database.model.DasDataSource;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.ProjectExtensionPointName;
import com.intellij.openapi.project.Project;
import com.intellij.util.Consumer;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EventListener;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public abstract class DataSourceManager<T extends DasDataSource> {
  public static final ProjectExtensionPointName<DataSourceManager<?>> EP_NAME = new ProjectExtensionPointName<>("com.intellij.database.dataSourceManager");
  public static final Topic<Listener> TOPIC = new Topic<>("DATASOURCE_TOPIC", Listener.class);

  public static @NotNull List<DataSourceManager<?>> getManagers(@NotNull Project project) {
    return EP_NAME.getExtensions(project);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public static <T extends DasDataSource> DataSourceManager<T> byDataSource(@NotNull Project project, @NotNull Class<? extends T> clazz) {
    ExtensionPoint<DataSourceManager<?>> ep = project.getExtensionArea().getExtensionPointIfRegistered(EP_NAME.getName());
    return ep == null ? null : (DataSourceManager<T>)ContainerUtil.find(ep.getExtensionList(), m -> m.isMyDataSource(clazz));
  }

  public abstract @NotNull List<T> getDataSources();

  public abstract boolean containsDataSource(@NotNull T element);

  public abstract void addDataSource(@NotNull T element);

  public abstract void removeDataSource(@NotNull T element);

  public abstract @Nullable AnAction getCreateDataSourceAction(@NotNull Consumer<? super T> consumer);

  public abstract @NotNull T createEmpty();

  public abstract @NotNull T copyDataSource(@NotNull String newName, @NotNull T copyFrom);

  public abstract void renameDataSource(@NotNull T element, @NotNull String name);

  public abstract boolean isMyDataSource(@NotNull Class<? extends DasDataSource> clazz);

  public interface Listener extends EventListener {
    default <T extends DasDataSource> void dataSourceAdded(@NotNull DataSourceManager<T> manager, @NotNull T dataSource) { }

    default <T extends DasDataSource> void dataSourceRemoved(@NotNull DataSourceManager<T> manager, @NotNull T dataSource) { }

    default <T extends DasDataSource> void dataSourceChanged(@Nullable DataSourceManager<T> manager, @Nullable T dataSource) { }
  }
}
