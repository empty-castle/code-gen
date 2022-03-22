package com.intellij.sql.formatter.settings;

import com.intellij.application.options.CodeStyle;
import com.intellij.database.Dbms;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleScheme;
import com.intellij.psi.codeStyle.CodeStyleSchemes;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.sql.dialects.SqlLanguageDialect;
import com.intellij.sql.formatter.SqlDialectCodeStyleProvider;
import com.intellij.sql.psi.SqlLanguage;
import com.intellij.util.ReflectionUtil;
import com.intellij.util.containers.JBIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static com.intellij.util.ObjectUtils.chooseNotNull;
import static com.intellij.util.ObjectUtils.tryCast;
import static java.util.Collections.emptyList;


/**
 * Static functions for obtaining correct code style settings stuff. 
 * 
 * @author Leonid Bushuev
 */
public abstract class SqlCodeStyles {

  @NotNull
  private static JBIterable<? extends SqlLanguageCodeStyleProvider> getProviders() {
    return SqlCodeStyleProviderService.getInstance().getProviders();
  }

  public static JBIterable<SqlLanguageDialect> listSqlDialects() {
    return getProviders().map(p -> p.getLanguage()).filter(SqlLanguageDialect.class);
  }
  
  @Nullable
  private static SqlLanguageCodeStyleProvider findProvider(@NotNull Language language) {
    return getProviders().filter(p -> p.getLanguage().equals(language)).first();
  }

  @NotNull
  private static Language getDefaultDialect() {
    return SqlLanguage.INSTANCE;
  }

  public static SqlLanguageDialect getGenericDialect() {
    return SqlLanguageDialect.getGenericDialect();
  }

  public static SqlLanguageDialect getIsoDialect() {
    return SqlLanguageDialect.getIsoDialect();
  }

  public static SqlLanguageDialect getPreviewDialectFor(@Nullable Language language) {
    if (language == null || language == SqlLanguage.INSTANCE || language == getGenericDialect()) return getDefaultPreviewDialect();
    if (language instanceof SqlLanguageDialect) return (SqlLanguageDialect)language;
    return getDefaultPreviewDialect();
  }

  @NotNull
  private static SqlLanguageDialect getDefaultPreviewDialect() {
    return getIsoDialect();
  }

  /**
   * For the given original dialect it returns the corresponded dialect for which code style settings exist.
   * Warning! — this function doesn't consider the {@link SqlCodeStyleSettings#USE_GENERAL_STYLE} option.
   * @param dialect original dialect.
   * @return the corresponded settings dialect.
   * @see #getSettingsClass
   */
  @NotNull
  public static Language getSettingsLanguage(@NotNull Language dialect) {
    if (findProvider(dialect) != null) return dialect;
    
    if (dialect instanceof SqlLanguageDialect) {
      SqlLanguageDialect d = (SqlLanguageDialect) dialect;
      Dbms dbms = d.getDbms();
      if (dbms == Dbms.UNKNOWN) return SqlLanguageDialect.getIsoDialect();
      if (dbms.isPostgres())  return SqlLanguageDialect.EP.forDbms(Dbms.POSTGRES);
      if (dbms.isOracle())  return SqlLanguageDialect.EP.forDbms(Dbms.ORACLE);
      if (dbms.isTransactSql()) return SqlLanguageDialect.EP.forDbms(Dbms.MSSQL);
      if (dbms.isMysql()) return SqlLanguageDialect.EP.forDbms(Dbms.MYSQL);
    }
    
    return getDefaultDialect();
  }

  /**
   * Returns the settings class for the given dialect.
   * Warning! — this function doesn't consider the {@link SqlCodeStyleSettings#USE_GENERAL_STYLE} option.
   * @param dialect the original (or settings) dialect.
   * @return the settings class.
   */
  @NotNull
  public static Class<? extends SqlCodeStyleSettings> getSettingsClass(@NotNull Language dialect) {
    Language settingLanguage = getSettingsLanguage(dialect);
    Class<? extends SqlCodeStyleSettings> settingsClass = findSettingsClass(settingLanguage);
    assert settingsClass != null : "The settings class for the dialect " + dialect.getID() + " is not registered";
    return settingsClass;
  }

  @Nullable
  private static Class<? extends SqlCodeStyleSettings> findSettingsClass(Language settingLanguage) {
    SqlLanguageCodeStyleProvider provider = tryCast(findProvider(settingLanguage), SqlLanguageCodeStyleProvider.class);
    return provider == null ? null : provider.getSettingsClass();
  }

  /**
   * Looks for the appropriate SQL settings for the given file,
   * considering dialect and the inheritance option.
   */
  @NotNull
  public static SqlCodeStyleSettings getSqlSettings(@NotNull PsiFile file) {
    Language dialect = file.getLanguage();
    CodeStyleSettings settingsContainer = CodeStyle.getSettings(file);
    return getSqlSettings(settingsContainer, dialect);
  }

  /**
   * Looks for the appropriate SQL settings for the given project and dialect.
   * The inheritance option is also considered.
   */
  @NotNull
  public static SqlCodeStyleSettings getSqlSettings(@Nullable Project project, @NotNull Language dialect) {
    return getSqlSettings(getSettings(project), dialect);
  }

  /**
   * Returns the SQL settings from the given settings container for the specified dialect,
   * or the generic settings (from this container) if the dialect's option {@link SqlCodeStyleSettings#USE_GENERAL_STYLE} is checked.
   */
  @NotNull
  public static SqlCodeStyleSettings getSqlSettings(@NotNull CodeStyleSettings settingsContainer, @NotNull Language dialect) {
    SqlCodeStyleSettings settings = pickSqlSettings(settingsContainer, dialect);
    if(settings.USE_GENERAL_STYLE) settings = getGeneralSqlSettings(settingsContainer);
    return settings;
  }

  /**
   * Returns the SQL settings from the given settings container for the specified dialect.
   * The option {@link SqlCodeStyleSettings#USE_GENERAL_STYLE} is NOT considered.
   */
  @NotNull
  public static SqlCodeStyleSettings pickSqlSettings(@NotNull CodeStyleSettings settingsContainer, @NotNull Language dialect) {
    if (!(dialect instanceof SqlLanguageDialect || dialect instanceof SqlLanguage))
      return getGeneralSqlSettings(settingsContainer); // fix EA-142449: sometimes this method is called with PlainTextLanguage
    Language settingLanguage = getSettingsLanguage(dialect);
    Class<? extends SqlCodeStyleSettings> settingsClass = findSettingsClass(settingLanguage);
    if (settingsClass == null)
      throw new IllegalArgumentException("Dialect " + dialect.getID() + " and settings language " + settingLanguage.getID() + " have no corresponding settings class");

    try {
      return settingsContainer.getCustomSettings(settingsClass);
    }
    catch (RuntimeException rte) {
      throw new RuntimeException("Failed to get setting " + settingsClass.getSimpleName() + " (dialect: " + dialect.getID() + ")", rte);
    }
  }

  /**
   * Returns the SQL settings from the given settings container for the specified dialect, if exists.
   * Or null if not.
   * The option {@link SqlCodeStyleSettings#USE_GENERAL_STYLE} is NOT considered.
   */
  @Nullable
  public static SqlCodeStyleSettings pickSqlSettingsIfExists(@NotNull CodeStyleSettings settingsContainer, @NotNull SqlLanguageDialect dialect) {
    Language settingLanguage = getSettingsLanguage(dialect);
    Class<? extends SqlCodeStyleSettings> settingsClass = findSettingsClass(settingLanguage);
    if (settingsClass == null) return null;

    final SqlCodeStyleSettings result;
    try {
      result = settingsContainer.getCustomSettingsIfCreated(settingsClass);
    }
    catch (RuntimeException rte) {
      return null;
    }

    return result != null && result.getCorrespondedDialect() == settingLanguage ? result : null;
  }

  @NotNull
  public static SqlCodeStyleSettings getGeneralSqlSettings(@Nullable Project project) {
    CodeStyleSettings settingsContainer = getSettings(project);
    return getGeneralSqlSettings(settingsContainer);
  }

  @NotNull
  public static SqlCodeStyleSettings getGeneralSqlSettings(@NotNull CodeStyleSettings settingsContainer) {
    return settingsContainer.getCustomSettings(SqlCodeStyleSettings.class);
  }

  /**
   * Looks for a code style scheme with the specified name.
   *
   * This method looks for the scheme in both lists:
   * <ul>
   *   <li>in the regular scheme list (that is shown in the Code Style configuration dialog),</li>
   *   <li>in the list of dialect-specific schemes provided by dialect modules.</li>
   * </ul>
   *
   * When both lists have schemes with the specified name the scheme from the regular list is used.
   *
   * @param schemeName name of the scheme.
   * @return found scheme, or null when not found.
   */
  @Nullable
  public static CodeStyleScheme findScheme(@NotNull String schemeName) {
    CodeStyleScheme scheme = CodeStyleSchemes.getInstance().findSchemeByName(schemeName);
    if (scheme == null) {
      scheme = listSpecificSchemes().find(s -> s.getName().equalsIgnoreCase(schemeName));
    }
    return scheme;
  }

  /**
   * Lists dialect-specific schemes provided by dialect modules.
   *
   * @see #findScheme(String)
   */
  @NotNull
  public static JBIterable<SqlDialectSpecificCodeStyleScheme> listSpecificSchemes() {
    SqlDialectCodeStyleProvider[] extensions = SqlDialectCodeStyleProvider.EP_NAME.getExtensions();
    JBIterable<SqlDialectSpecificCodeStyleScheme> schemes =
      JBIterable.of(extensions)
        .flatten(ex -> chooseNotNull(ex.getDialectSpecificPredefinedStyleSchemes(), emptyList()));
    return schemes;
  }


  @NotNull
  public static CodeStyleSettings getSettings(@Nullable Project project) {
    return project != null ? CodeStyle.getSettings(project) : CodeStyle.getDefaultSettings();
  }

  @NotNull
  public static CodeStyleSettings getSettings(@Nullable Project project, @Nullable String schemeName) {
    CodeStyleScheme scheme = schemeName == null ? null : findScheme(schemeName);
    return scheme == null ? getSettings(project) : scheme.getCodeStyleSettings();
  }

  @NotNull
  public static SqlCodeStyleSettings getSqlSettings(@Nullable Project project, @Nullable String codeStyleName, @NotNull SqlLanguageDialect dialect) {
    return getSqlSettings(getSettings(project, codeStyleName), dialect);
  }

  /**
   * Returns all registered settings dialects, when the default dialect is first.
   * @return
   */
  public static JBIterable<? extends Language> getSettingsDialects() {
    return JBIterable
      .of(getDefaultDialect())
      .append(listSqlDialectsWithoutDefault());
  }

  @NotNull
  static JBIterable<SqlLanguageDialect> listSqlDialectsWithoutDefault() {
    return listSqlDialects().filter(d -> d != getDefaultDialect());
  }


  /**
   * Copies settings. This function is subject for deleting when the EP on read/write settings done;
   * please use {@link SqlCodeStyleSettingsUtil#copyCodeStyleSettings} instead.
   */
  static void copySettings(@NotNull SqlCodeStyleSettings source, @NotNull SqlCodeStyleSettings target) {
    if (source == target) return;
    
    // common part
    CommonCodeStyleSettings sourceCommonSettings = source.getCorrespondedCommonSettings();
    CommonCodeStyleSettings targetCommonSettings = target.getCorrespondedCommonSettings();
    if (sourceCommonSettings != targetCommonSettings) {
      targetCommonSettings.copyFrom(sourceCommonSettings);
    }

    // custom part
    Field[] fieldsToCopy =
      JBIterable.of(SqlCodeStyleSettings.class.getDeclaredFields())
      .filter(f -> Modifier.isPublic(f.getModifiers()) 
                   && !Modifier.isStatic(f.getModifiers())
                   && Character.isUpperCase(f.getName().charAt(0)) 
                   && !f.isAnnotationPresent(Deprecated.class)
                   && !target.isSettingHidden(f.getName()))
      .toArray(new Field[0]);
    ReflectionUtil.copyFields(fieldsToCopy, source, target);
    
    // the version
    target.myVersion = source.myVersion;
    target.myInitialized = source.myInitialized;
  } 
  
}
