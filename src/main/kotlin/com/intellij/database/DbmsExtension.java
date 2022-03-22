package com.intellij.database;

import com.intellij.diagnostic.PluginException;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.PluginAware;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.util.KeyedExtensionCollector;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.KeyedLazyInstance;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ReflectionUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author gregsh
 */
public class DbmsExtension<T> extends KeyedExtensionCollector<T, Dbms> {
  private static final KeyedExtensionCollector<Dbms, Dbms> FALLBACK =
    new KeyedExtensionCollector<>("com.intellij.database.extensionFallback");

  public DbmsExtension(@NonNls @NotNull String epName) {
    super(epName);
  }

  @NotNull
  @Override
  protected String keyToString(@NotNull Dbms key) {
    return key.getName();
  }

  @NotNull
  public List<T> allForDbms(@NotNull Dbms dbms) {
    return forKey(dbms);
  }

  public T forDbms(@NotNull Dbms dbms) {
    return findSingle(dbms);
  }

  @NotNull
  public List<Bean<T>> allExtensions() {
    ExtensionPoint<Bean<T>> point = ApplicationManager.getApplication().getExtensionArea().getExtensionPointIfRegistered(getName());
    return point == null ? Collections.emptyList() : point.getExtensionList();
  }

  @NotNull
  @Override
  protected List<T> buildExtensions(@NotNull String stringKey, @NotNull Dbms key) {
    List<T> result = super.buildExtensions(stringKey, key);
    if (result.isEmpty()) {
      result = copyFromFallback(key);
    }
    return result;
  }

  @Nullable
  private static Dbms getFallback(@NotNull Dbms key) {
    Dbms fallback = ObjectUtils.notNull(FALLBACK.findSingle(key), Dbms.UNKNOWN);
    return key == fallback ? null : fallback;
  }

  private List<T> copyFromFallback(@NotNull Dbms key) {
    ExtensionPoint<Bean<T>> point = getOurPoint();
    if (point == null) {
      return Collections.emptyList();
    }
    List<T> result = new ArrayList<>();
    for(Dbms fallback = getFallback(key); fallback != null && result.isEmpty(); fallback = getFallback(fallback)) {
      for (Bean<T> bean : point.getExtensions()) {
        if (!StringUtil.equals(bean.dbmsStr, fallback.getName())) {
          continue;
        }

        try {
          result.add(bean.doGetInstance(ApplicationManager.getApplication().loadClass(bean.implementationClass, bean.pluginDescriptor), key));
        }
        catch (ReflectiveOperationException e) {
          throw new PluginException(e, bean.pluginDescriptor.getPluginId());
        }
      }
    }
    return result;
  }

  @Nullable
  @SuppressWarnings({"unchecked", "rawtypes"})
  private ExtensionPoint<Bean<T>> getOurPoint() {
    return (ExtensionPoint)getPoint();
  }

  public static class Bean<T> implements KeyedLazyInstance<T>, PluginAware {
    @Attribute("dbms")
    public String dbmsStr;

    @Attribute("implementationClass")
    public String implementationClass;

    private PluginDescriptor pluginDescriptor;

    private final NotNullLazyValue<T> myHandler = NotNullLazyValue.createValue(() -> {
      try {
        Application app = ApplicationManager.getApplication();
        @SuppressWarnings("unchecked")
        Class<T> aClass = app == null ? (Class<T>)Class.forName(implementationClass) : app.loadClass(implementationClass, pluginDescriptor);
        return doGetInstance(aClass, getDbms());
      }
      catch (ReflectiveOperationException e) {
        throw new PluginException(e, pluginDescriptor.getPluginId());
      }
    });

    @Override
    public void setPluginDescriptor(@NotNull PluginDescriptor pluginDescriptor) {
      this.pluginDescriptor = pluginDescriptor;
    }

    @NotNull T doGetInstance(@NotNull Class<T> tClass, @NotNull Dbms dbms) throws ReflectiveOperationException {
      try {
        return instantiateByDbms(tClass, dbms);
      }
      catch (NoSuchMethodException ignored) {
        return ReflectionUtil.newInstance(tClass);
      }
    }

    @NotNull
    T instantiateByDbms(@NotNull Class<T> tClass, @NotNull Dbms dbms) throws ReflectiveOperationException {
      Constructor<T> constructor = tClass.getConstructor(Dbms.class);
      constructor.setAccessible(true);
      return constructor.newInstance(dbms);
    }

    @NotNull
    public Dbms getDbms() {
      return DbmsExtension.getDbms(dbmsStr);
    }

    @NotNull
    @Override
    public T getInstance() {
      return myHandler.getValue();
    }

    @Override
    public String getKey() {
      return dbmsStr;
    }

    @Override
    public String toString() {
      return dbmsStr;
    }
  }

  public static class InstanceBean<T> extends Bean<T> {
    @NotNull
    @Override
    T doGetInstance(@NotNull Class<T> aClass, @NotNull Dbms dbms) throws ReflectiveOperationException {
      //noinspection unchecked
      return (T)aClass.getField("INSTANCE").get(null);
    }
  }

  public static class FallbackBean implements KeyedLazyInstance<Dbms> {
    @Attribute("dbms")
    public String dbmsStr;
    @Attribute("fallbackDbms")
    public String fallbackDbmsStr;

    @NotNull
    public Dbms getDbms() {
      return DbmsExtension.getDbms(dbmsStr);
    }

    @NotNull
    public Dbms getFallbackDbms() {
      return DbmsExtension.getDbms(fallbackDbmsStr);
    }

    @Override
    public String getKey() {
      return dbmsStr;
    }

    @NotNull
    @Override
    public Dbms getInstance() {
      return getFallbackDbms();
    }
  }

  @NotNull
  private static Dbms getDbms(String dbmsStr) {
    Dbms dbms = Dbms.byName(dbmsStr);
    if (dbms == null) {
      throw new IllegalArgumentException("Dbms not found: " + dbmsStr);
    }
    return dbms;
  }
}