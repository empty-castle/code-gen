package com.intellij.database;

import com.intellij.database.util.DbTestUtils;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.ExtensionPointListener;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.*;

public abstract class HSet {
  @NotNull
  public static HSet create(@NotNull String name) {
    return LazyData.createImpl(name);
  }

  @NotNull
  public static HSet create(HSet @NotNull ... sets) {
    return new HSetSet(Arrays.asList(sets));
  }

  @NotNull
  public HSet and(HSet @NotNull ... sets) {
    List<HSet> res = new ArrayList<>(sets.length + 1);
    res.add(this);
    Collections.addAll(res, sets);
    return new HSetSet(res);
  }

  @NotNull
  public abstract String getName();

  public abstract boolean contains(@NotNull HSet set);

  private static class HSetSet extends HSet {
    private final List<HSet> myChildren;

    private HSetSet(@NotNull List<HSet> children) {
      myChildren = children;
    }

    @Override
    public @NotNull String getName() {
      return "";
    }

    @Override
    public boolean contains(@NotNull HSet set) {
      if (set == this) return true;
      for (HSet child : myChildren) {
        if (child.contains(set)) return true;
      }
      return false;
    }
  }

  private static class HSetImpl extends HSet {
    private final String myName;
    private volatile List<HSetImpl> myParents;

    private HSetImpl(@NotNull String name) {
      myName = name;
    }

    @Override
    @NotNull
    public String getName() {
      return myName;
    }

    public boolean isIn(@NotNull String name) {
      if (name.equals(myName)) return true;
      for (HSetImpl set : getParentSets()) {
        if (set.isIn(name)) return true;
      }
      return false;
    }

    @Override
    public boolean contains(@NotNull HSet set) {
      if (set == this) return true;
      if (set instanceof HSetImpl) {
        return ((HSetImpl)set).isIn(myName);
      }
      return false;
    }


    @NotNull
    public Iterable<HSetImpl> getParentSets() {
      if (myParents != null) return myParents;
      List<HSetImpl> parents = LazyData.loadParentSets(myName);
      if (parents == null) return Collections.emptyList();
      return myParents = parents;
    }

    private void clearParents() {
      myParents = null;
    }
  }

  @TestOnly
  public static List<HSet> getAllSets() {
    return ContainerUtil.map(LazyData.SET_EP.getExtensionsIfPointIsRegistered(), b -> create(b.item));
  }
  @TestOnly
  public static Iterable<? extends HSet> getParentSets(@NotNull HSet set) {
    if (set instanceof HSetImpl) return ((HSetImpl)set).getParentSets();
    return Collections.emptyList();
  }

  public static final class HSetBean {
    @Attribute("item")
    public String item;
    @Attribute("set")
    public String set;
  }

  private static class LazyData {
    private static final Map<String, HSetImpl> ourInstances = ContainerUtil.createConcurrentWeakValueMap();
    private static final ExtensionPointName<HSetBean> SET_EP = ExtensionPointName.create("com.intellij.database.addToHSet");
    private static boolean ourListenerAdded = addListener(getPoint());
    private static volatile List<HSetBean> ourTestBeans = null;

    @NotNull
    private static HSetImpl createImpl(@NotNull String name) {
      return ourInstances.computeIfAbsent(name, HSetImpl::new);
    }

    private static boolean addListener(ExtensionPoint<HSetBean> ep) {
      if (ep == null) {
        return false;
      }
      ep.addExtensionPointListener(new ExtensionPointListener<>() {
        @Override
        public void extensionAdded(@NotNull HSetBean extension, @NotNull PluginDescriptor pluginDescriptor) {
          HSetImpl item = extension.item == null ? null : ourInstances.get(extension.item);
          if (item != null) item.clearParents();
        }

        @Override
        public void extensionRemoved(@NotNull HSetBean extension, @NotNull PluginDescriptor pluginDescriptor) {
          HSetImpl item = extension.item == null ? null : ourInstances.get(extension.item);
          if (item != null) item.clearParents();
        }
      }, false, null);
      return true;
    }

    @Nullable
    private static ExtensionPoint<HSetBean> getPoint() {
      Application application = ApplicationManager.getApplication();
      return application == null ? null : application.getExtensionArea()
        .getExtensionPointIfRegistered(SET_EP.getName());
    }

    @Nullable
    private static List<HSetImpl> loadParentSets(@NotNull String name) {
      List<HSetBean> beans = getBeans();
      if (beans == null) return null;
      List<HSetImpl> res = new ArrayList<>();
      for (HSetBean bean : beans) {
        if (bean.set != null && name.equals(bean.item)) {
          res.add(createImpl(bean.set));
        }
      }
      return res;
    }

    @Nullable
    private static List<HSetBean> getBeans() {
      ExtensionPoint<HSetBean> point = getPoint();
      if (point == null) {
        Application application = ApplicationManager.getApplication();
        if (application == null || application.isUnitTestMode()) {
          //noinspection TestOnlyProblems
          return getTestBeans();
        }
        return null;
      }
      if (!ourListenerAdded) {
        ourListenerAdded = addListener(point);
      }
      return point.getExtensionList();
    }

    @TestOnly
    private static List<HSetBean> getTestBeans() {
      if (ourTestBeans == null) {
        ourTestBeans = DbTestUtils.loadBeans(SET_EP.getName(), HSetBean.class, false).toList();
      }
      return ourTestBeans;
    }
  }
}
