package com.intellij.database.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.ExtensionPoint;
import com.intellij.openapi.extensions.ExtensionsArea;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.Tag;
import com.intellij.util.xmlb.annotations.XCollection;
import org.intellij.lang.annotations.RegExp;

import java.util.*;

@Tag("text-mode")
public class UserPatterns {
  @Attribute("in-scripts")
  public boolean inScripts = true;
  @Attribute("in-literals")
  public boolean inLiterals = true;
  @Attribute("process-strings")
  public boolean processStrings = false;
  @Tag("parameter-patterns")
  @XCollection
  public List<ParameterPattern> patterns = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserPatterns mode = (UserPatterns)o;

    if (inLiterals != mode.inLiterals) return false;
    if (inScripts != mode.inScripts) return false;
    if (!patterns.equals(mode.patterns)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = patterns.hashCode();
    result = 31 * result + (inLiterals ? 1 : 0);
    result = 31 * result + (inScripts ? 1 : 0);
    return result;
  }

  public void migrate(int version) {
    if (version < 3) { //assign ids
      Map<String, ParameterPattern> byPattern = ContainerUtil.newMapFromValues(getPredefined().iterator(), p -> p.pattern);
      ContainerUtil.fillMapWithValues(byPattern, getPredefined().iterator(), p -> p.pattern.replaceAll("[()]", ""));
      for (ParameterPattern pattern : patterns) {
        boolean isPredef = "true".equals(pattern.id) || pattern.id == null;
        ParameterPattern predef = isPredef ? byPattern.get(pattern.pattern) : null;
        if (predef != null) pattern.id = predef.id;
      }
    }
    Set<String> existing = new HashSet<>();
    for (ParameterPattern pattern : patterns) {
      if (pattern.id == null) continue;
      ParameterPattern predef = getPredefinedById().get(pattern.id);
      if (predef == null) {
        pattern.id = null;
        continue;
      }
      existing.add(pattern.id);
      pattern.pattern = predef.pattern;
      if (!pattern.isSetByUserFlag) {
        pattern.scope = predef.scope;
      }
    }
    for (ParameterPattern predef : getPredefined()) {
      if (existing.contains(predef.id)) continue;
      ParameterPattern pattern = predef.clone();
      patterns.add(pattern);
    }
  }

  public static class Predef {
    public static final List<ParameterPattern> HARDCODED_PATTERNS = ContainerUtil.immutableList(
      new ParameterPattern("\\$\\{([^{}]*)}", "*,-SQL", "${name} - hardcoded").enabled(false, true),
      new ParameterPattern("#\\{([^{}]*)}", "*,-SQL", "#{name} - hardcoded").enabled(false, true)
    );
    private static final Map<String, ParameterPattern> ourPredefined = new LinkedHashMap<>();

    static {
      Application application = ApplicationManager.getApplication();
      ExtensionsArea area = application == null ? null : application.getExtensionArea();
      ExtensionPoint<?> point = area == null ? null : area.getExtensionPointIfRegistered(DatabaseParameterPatternProvider.EP.getName());
      if (point != null) point.addChangeListener(Predef::clear, application);
    }

    private synchronized static void clear() {
      ourPredefined.clear();
    }

    private synchronized static Map<String, ParameterPattern> getPredefined() {
      if (ourPredefined.isEmpty()) {
        for (DatabaseParameterPatternProvider provider : DatabaseParameterPatternProvider.EP.getExtensionsIfPointIsRegistered()) {
          add(provider.getPatterns());
        }
        add(
          new ParameterPattern("\\$\\{([^\\{\\}]*)\\}", "", "${name}"),
          new ParameterPattern("\\$\\(([^\\)]+)\\)", "", "$(name)")  // ms sql
        );
      }
      return ourPredefined;
    }

    private static void add(ParameterPattern... params) {
      for (ParameterPattern param : params) {
        ourPredefined.put(param.id, param);
      }
    }
  }

  public static Collection<ParameterPattern> getPredefined() {
    return getPredefinedById().values();
  }

  public static Map<String, ParameterPattern> getPredefinedById() {
    return Predef.getPredefined();
  }

  @Tag("parameter-pattern")
  public static class ParameterPattern implements Cloneable {
    @Attribute("value")
    public String pattern = "";
    @Attribute("in-scripts")
    public boolean inScripts = false;
    @Attribute("in-literals")
    public boolean inLiterals = true;
    @Attribute("scope")
    public String scope = "";
    @Attribute(value = "predefined")
    public String id = null;
    private transient boolean isSetByUserFlag = false;

    @SuppressWarnings("unused")
    @Attribute("set-by-user")
    public void setSetByUser(boolean s) {
      isSetByUserFlag = s;
    }

    @SuppressWarnings("unused")
    @Attribute("set-by-user")
    public boolean isSetByUser() {
      if (id == null) return false;
      ParameterPattern predef = getPredefinedById().get(id);
      if (predef == null) return false;
      return !Objects.equals(predef.scope, scope);
    }


    public ParameterPattern() {
    }

    public ParameterPattern(@RegExp String pattern) {
      this.pattern = pattern;
      this.inScripts = true;
    }

    public ParameterPattern(@RegExp String pattern, String scope, String id) {
      this.pattern = pattern;
      this.scope = scope;
      this.id = id;
      this.inScripts = true;
    }

    public ParameterPattern enabled(boolean inScripts, boolean inLiterals) {
      this.inScripts = inScripts;
      this.inLiterals = inLiterals;
      return this;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ParameterPattern pattern1 = (ParameterPattern)o;

      if (id != pattern1.id) return false;
      if (inScripts != pattern1.inScripts) return false;
      if (inLiterals != pattern1.inLiterals) return false;
      if (pattern != null ? !pattern.equals(pattern1.pattern) : pattern1.pattern != null) return false;
      if (scope != null ? !scope.equals(pattern1.scope) : pattern1.scope != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = Objects.hashCode(pattern);
      result = 31 * result + Objects.hashCode(id);
      result = 31 * result + (inScripts ? 1 : 0);
      result = 31 * result + (inLiterals ? 1 : 0);
      result = 31 * result + Objects.hashCode(scope);
      return result;
    }

    @Override
    public ParameterPattern clone() {
      try {
        return (ParameterPattern)super.clone();
      }
      catch (CloneNotSupportedException e) {
        throw new AssertionError();
      }
    }
  }
}
