package com.intellij.database.util;

import com.intellij.database.DbmsExtension;
import com.intellij.openapi.extensions.*;
import com.intellij.openapi.util.JDOMUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.containers.JBIterable;
import com.intellij.util.xmlb.JDOMXIncluder;
import com.intellij.util.xmlb.XmlSerializer;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DbTestUtils {
  private static final PluginDescriptor ourDescriptor =
    new DefaultPluginDescriptor(PluginId.getId("database.tests"), DbTestUtils.class.getClassLoader());

  @TestOnly
  @NotNull
  public static <T> JBIterable<T> loadBeans(String epName, Class<? extends T> clazz, boolean isSql) {
    String shortName = StringUtil.getShortName(epName);
    String descriptorPath = isSql ? "META-INF/SqlDialectsCore.xml" : "META-INF/DatabaseDialectsCore.xml";
    URL xml = DbmsExtension.class.getClassLoader().getResource(descriptorPath);
    if (xml == null) {
      throw new AssertionError("descriptor not found: " + descriptorPath);
    }
    class MyOrderable implements LoadingOrder.Orderable {
      final T bean;
      final String orderId;
      final LoadingOrder order;

      MyOrderable(Element e) {
        bean = XmlSerializer.deserialize(e, clazz);
        if (bean instanceof PluginAware) ((PluginAware)bean).setPluginDescriptor(ourDescriptor);
        orderId = e.getAttributeValue("id");
        order = LoadingOrder.readOrder(e.getAttributeValue("order"));
      }

      @Nullable
      @Override
      public String getOrderId() { return orderId; }

      @Override
      public LoadingOrder getOrder() { return order; }
    }
    try (InputStream stream = xml.openStream()) {
      List<MyOrderable> list = JBIterable.from(JDOMXIncluder.resolve(JDOMUtil.load(stream), xml))
        .flatten(e -> e.getChildren("extensions"))
        .flatten(e -> e.getChildren())
        .filter(e -> e.getName().endsWith(shortName))
        .filter(e -> epName.equals(StringUtil.getQualifiedName(
          e.getParentElement().getAttributeValue("defaultExtensionNs"), e.getName())))
        .map(MyOrderable::new)
        .addAllTo(new ArrayList<>());
      LoadingOrder.sort(list);
      JBIterable<T> result = JBIterable.from(list).map(o -> o.bean).collect();
      if (result.isEmpty()) throw new AssertionError(epName + " is empty in " + descriptorPath);
      return result;
    }
    catch (Exception e) {
      throw new AssertionError("Failed to load '" + epName + "' list", e);
    }
  }
}
