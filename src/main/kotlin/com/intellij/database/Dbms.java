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
package com.intellij.database;

import com.intellij.database.model.RawConnectionConfig;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ConcurrencyUtil;
import com.intellij.util.ReflectionUtil;
import com.intellij.util.containers.JBIterable;
import com.intellij.util.containers.MultiMap;
import com.intellij.util.xmlb.annotations.Attribute;
import icons.DatabaseIcons;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * @author gregsh
 */
public final class Dbms implements Comparable<Dbms> {
  private static final Logger LOG = Logger.getInstance(Dbms.class);
  private static final ConcurrentMap<String, Dbms> ourValues = new ConcurrentHashMap<>();
  private static final List<Pair<Pattern, Dbms>> ourDetectors = new CopyOnWriteArrayList<>();

  public static @NotNull Dbms create(@NotNull String displayName, @NotNull Supplier<Icon> icon) {
    return create(displayName, displayName, icon);
  }

  public static @NotNull Dbms create(@NotNull String name, @NotNull String displayName, @NotNull Supplier<Icon> icon) {
    return create(name, displayName, icon, null);
  }

  public static @NotNull Dbms create(@NotNull String name, @NotNull String displayName, @NotNull Supplier<Icon> icon, @RegExp @Nullable String detectPattern) {
    name = StringUtil.toUpperCase(name); // force uppercase
    Dbms existing = ourValues.get(name);
    if (existing != null) return existing;
    Dbms proposed = new Dbms(name, displayName, icon);
    Dbms result = ConcurrencyUtil.cacheOrGet(ourValues, name, proposed);
    if (proposed == result) {
      String pattern = detectPattern == null ? defaultPattern(name) : detectPattern;
      ourDetectors.add(Pair.create(Pattern.compile(pattern), result));
    }
    return result;
  }

  private static String defaultPattern(@NotNull String name) {
    return String.format("(?i).*\\b(?:%s).*", name.replace("_", ".*"));
  }


  public static final Dbms SYNAPSE = create("SYNAPSE", "Azure Synapse Analytics", () -> AllIcons.Providers.Azure, defaultPattern("azure sql data warehouse"));

  // supported dialects
  public static final Dbms UNKNOWN = create("UNKNOWN", () -> DatabaseIcons.Dbms);
  public static final Dbms ORACLE = create("Oracle", () -> AllIcons.Providers.Oracle);
  public static final Dbms MEMSQL = create("MEMSQL", "SingleStore",() ->  AllIcons.Providers.Singlestore);
  public static final Dbms MARIA = create("MariaDB", () -> AllIcons.Providers.Mariadb);
  public static final Dbms MYSQL_AURORA = create("MYSQL_AURORA", "Amazon Aurora MySQL", () -> AllIcons.Providers.Mysql, defaultPattern("mysql_aurora") + "|software\\.aws\\.rds\\.jdbc\\.Driver");
  public static final Dbms MYSQL = create("MySQL", () -> AllIcons.Providers.Mysql);
  public static final Dbms GITBASE = create("gitbase", () -> DatabaseIcons.DriverRed4);
  public static final Dbms POSTGRES = create("POSTGRES", "PostgreSQL", () -> AllIcons.Providers.Postgresql);
  public static final Dbms REDSHIFT = create("REDSHIFT", "Amazon Redshift", () -> AllIcons.Providers.Redshift);
  public static final Dbms GREENPLUM = create("GREENPLUM", "Greenplum", () -> AllIcons.Providers.Greenplum);
  public static final Dbms SYBASE = create("SYBASE", "Sybase ASE", () -> AllIcons.Providers.Sybase, defaultPattern("sybase|adaptive server") + "|ase.*");
  public static final Dbms AZURE = create("AZURE", "Azure SQL Database", () -> AllIcons.Providers.Azure);
  public static final Dbms MSSQL_LOCALDB = create("MSSQL_LOCALDB", "Microsoft SQL Server LocalDB", () -> AllIcons.Providers.SqlServer, defaultPattern("(mssql|sqlserver).localdb"));
  public static final Dbms MSSQL = create("MSSQL", "Microsoft SQL Server", () -> AllIcons.Providers.SqlServer, defaultPattern("microsoft|sqlserver|jtds"));
  public static final Dbms DB2_LUW = create("DB2_LUW", "IBM Db2 LUW", () -> AllIcons.Providers.DB2);
  public static final Dbms DB2_IS = create("DB2_IS", "IBM Db2 iSeries", () -> AllIcons.Providers.DB2);
  public static final Dbms DB2_ZOS = create("DB2_ZOS", "IBM Db2 z/OS", () -> AllIcons.Providers.DB2);
  public static final Dbms DB2 = create("DB2", "IBM Db2", () -> AllIcons.Providers.DB2, "(?i).*(?:\\bDB2|[:.]as400[:.]).*");
  public static final Dbms SQLITE = create("SQLite", () -> AllIcons.Providers.Sqlite);
  public static final Dbms HSQL = create("HSQLDB", "HSQLDB", () -> AllIcons.Providers.Hsqldb, defaultPattern("hsql"));
  public static final Dbms H2 = create("H2", () -> AllIcons.Providers.H2);
  public static final Dbms DERBY = create("DERBY", "Apache Derby", () -> AllIcons.Providers.ApacheDerby);
  public static final Dbms EXASOL = create("EXASOL", "Exasol", () -> AllIcons.Providers.Exasol, defaultPattern("exasol|exa"));
  public static final Dbms CLICKHOUSE = create("ClickHouse", () -> AllIcons.Providers.ClickHouse);
  public static final Dbms CASSANDRA = create("CASSANDRA", "Apache Cassandra", () -> AllIcons.Providers.Cassandra);
  public static final Dbms VERTICA = create("Vertica", () -> AllIcons.Providers.Vertica);
  public static final Dbms HIVE = create("HIVE", "Apache Hive", () -> AllIcons.Providers.Hive);
  public static final Dbms SPARK = create("SPARK", "Apache Spark", () -> AllIcons.Providers.Spark);
  public static final Dbms SNOWFLAKE = create("Snowflake", () -> AllIcons.Providers.Snowflake);
  public static final Dbms MONGO = create("MONGO", "MongoDB", () -> AllIcons.Providers.MongoDB);
  public static final Dbms COCKROACH = create("COCKROACH", "CockroachDB", () -> AllIcons.Providers.CockroachDB);
  public static final Dbms BIGQUERY = create("BIGQUERY", "BigQuery", () -> AllIcons.Providers.BigQuery);
  public static final Dbms COUCHBASE_QUERY = create("COUCHBASE", "Couchbase Query", () -> AllIcons.Providers.Couchbase);

  // unsupported dialects
  public static final Dbms HANA = create("HANA", "SAP HANA", () -> AllIcons.Providers.HANA, defaultPattern("sap|hana|hdb"));
  public static final Dbms FIREBIRD = create("Firebird", () -> AllIcons.Providers.Firebird);
  public static final Dbms ATHENA = create("ATHENA", "AWS Athena", () -> AllIcons.Providers.Athena);
  public static final Dbms PRESTO = create("PRESTO", "Presto", () -> AllIcons.Providers.Presto, defaultPattern("presto"));
  public static final Dbms TRINO = create("TRINO", "Trino", () -> AllIcons.Providers.Trino, defaultPattern("trino"));
  public static final Dbms INFORMIX = create("INFORMIX", "Informix", () -> AllIcons.Providers.Informix, defaultPattern("informix") + "|ids.*");
  public static final Dbms IMPALA = create("Impala", () -> AllIcons.Providers.Impala);
  public static final Dbms NETEZZA = create("Netezza", () -> AllIcons.Providers.Netezza);
  public static final Dbms PHOENIX = create("Phoenix", () -> AllIcons.Providers.ApachePhoenix);
  public static final Dbms INGRES = create("Ingres", () -> DatabaseIcons.DriverRed4);
  public static final Dbms TERADATA = create("Teradata", () -> AllIcons.Providers.Teradata);
  public static final Dbms OPENEDGE = create("OpenEdge", () -> AllIcons.Providers.Openedge);
  public static final Dbms TIBERO = create("Tibero", () -> AllIcons.Providers.Tibero);
  public static final Dbms FILEMAKER = create("FileMaker", () -> DatabaseIcons.DriverGreen3);
  public static final Dbms FRONTBASE = create("FrontBase", () -> DatabaseIcons.DriverPink4);
  public static final Dbms IGNITE = create("IGNITE", "Apache Ignite", () -> DatabaseIcons.DriverRed4);
  public static final Dbms MONET = create("MONET", "MonetDB", () -> DatabaseIcons.DriverBlue4);
  public static final Dbms CLOUD_SPANNER = create("CLOUD_SPANNER", "Google Cloud Spanner", () -> AllIcons.Providers.GoogleCloudSpanner, defaultPattern("cloud ?spanner"));

  // classes
  public static final HSet POSTGRES_LIKE = HSet.create("POSTGRES_LIKE");
  public static final HSet MYSQL_LIKE = HSet.create("MYSQL_LIKE");
  public static final HSet DB2_LIKE = HSet.create("DB2_LIKE");
  public static final HSet AZURE_LIKE = HSet.create("AZURE_LIKE");
  public static final HSet MSSQL_LIKE = HSet.create("MSSQL_LIKE");
  public static final HSet PRESTO_LIKE = HSet.create("PRESTO_LIKE");
  public static final HSet TRANSACT_SQL = HSet.create("TRANSACT_SQL");
  public static final HSet DOCUMENT_ORIENTED = HSet.create("DOCUMENT_ORIENTED");
  public static final HSet PSEUDO_SUPPORTED = HSet.create("PSEUDO_SUPPORTED");

  private final String myName;
  private final String myDisplayName;
  private final NotNullLazyValue<Icon> myIcon;
  private final HSet mySet;

  private Dbms(@NotNull String name, @NotNull String displayName, @NotNull Supplier<Icon> icon) {
    myName = name;
    myDisplayName = displayName;
    myIcon = NotNullLazyValue.lazy(icon);
    mySet = HSet.create(name);
  }

  public @NotNull String getName() {
    return myName;
  }

  @NlsSafe
  public @NotNull String getDisplayName() {
    return myDisplayName;
  }

  public @NotNull Icon getIcon() {
    return myIcon.getValue();
  }

  @Override
  public String toString() {
    return getName();
  }

  @NotNull
  public HSet getHSet() {
    return mySet;
  }

  public boolean isOracle() { return is(ORACLE); }
  public boolean isMysql() { return in(MYSQL_LIKE); }
  public boolean isPostgres() { return in(POSTGRES_LIKE); }
  public boolean isBigQuery() { return is(BIGQUERY); }
  public boolean isRedshift() { return is(REDSHIFT); }
  public boolean isGreenplum() { return is(GREENPLUM); }
  public boolean isVertica() {return is(VERTICA); }
  public boolean isMicrosoft() { return in(MSSQL_LIKE); }
  public boolean isSybase() { return is(SYBASE); }
  public boolean isDb2() { return in(DB2_LIKE); }
  public boolean isHsqldb() { return is(HSQL); }
  public boolean isH2() { return is(H2); }
  public boolean isDerby() { return is(DERBY); }
  public boolean isSqlite() { return is(SQLITE); }
  public boolean isExasol() { return is(EXASOL); }
  public boolean isClickHouse() { return is(CLICKHOUSE); }
  public boolean isCassandra() { return is(CASSANDRA); }
  public boolean isHive() { return is(HIVE); }
  public boolean isSpark() { return is(SPARK); }
  public boolean isSnowflake() { return is(SNOWFLAKE); }
  public boolean isMongo() { return is(MONGO); }
  public boolean isCouchbase() { return is(COUCHBASE_QUERY); }
  public boolean isCockroach() { return is(COCKROACH); }

  public boolean isTransactSql() { return in(TRANSACT_SQL); }
  public boolean isDocumentOriented() { return in(DOCUMENT_ORIENTED); }

  public boolean eq(@Nullable Dbms dbms) { return this == dbms; }
  public boolean is(@Nullable Dbms dbms) { return eq(dbms); }
  public boolean in(@NotNull HSet set) {
    return set.contains(mySet);
  }

  public static @NotNull Dbms forConnection(@Nullable RawConnectionConfig o) {
    if (o == null) return UNKNOWN;
    Dbms result = fromString(o.getUrl());
    if (result != UNKNOWN) return result;
    return fromString(o.getDriverClass());
  }

  public static @NotNull Dbms fromString(@Nullable String text) {
    if (text == null) return UNKNOWN;
    for (Pair<Pattern, Dbms> detector : ourDetectors) {
      if (detector.first.matcher(text).matches()) {
        return detector.second;
      }
    }

    @RegExp
    String pattern = "(?i).*\\b(?:%s).*";
    for (Dbms dbms : allValues()) {
      if (text.matches(String.format(pattern, dbms.getName()))) return dbms;
    }
    return UNKNOWN;
  }

  public static @Nullable Dbms byName(@Nullable String name) {
    return name == null ? null : LazyData.allDbms().get(name);
  }

  public static @NotNull Collection<Dbms> allValues() {
    return LazyData.allDbms().values();
  }

  public static @NotNull HSet asSet(Dbms @NotNull ... dbms) {
    HSet[] sets = new HSet[dbms.length];
    for (int i = 0; i < dbms.length; i++) {
      sets[i] = dbms[i].getHSet();
    }
    return HSet.create(sets);
  }

  @Override
  public int compareTo(@NotNull Dbms o) {
    return Comparing.compare(getName(), o.getName());
  }

  public static final class DbmsBean {
    @Attribute("id")
    public String id;
    @Attribute("instance")
    public String instance;
  }

  private static final ExtensionPointName<DbmsBean> DBMS_EP = ExtensionPointName.create("com.intellij.database.dbms");
  @NotNull
  public static Collection<PluginDescriptor> getPluginDescriptors(@NotNull Dbms dbms) {
    return LazyData.ourDescriptors.get(dbms);
  }
  public static boolean isPredefined(@NotNull Dbms dbms) {
    return LazyData.ourPredefined.contains(dbms);
  }

  private static class LazyData {
    private static final MultiMap<Dbms, PluginDescriptor> ourDescriptors = MultiMap.create();
    private static final Set<Dbms> ourPredefined = new HashSet<>();
    static {
      Application app = ApplicationManager.getApplication();
      if (app != null && app.getExtensionArea().hasExtensionPoint(DBMS_EP)) {
        DBMS_EP.processWithPluginDescriptor((bean, pluginDescriptor) -> {
          try {
            addDbms(bean, pluginDescriptor);
          }
          catch (Throwable ignore) { }
        });
      }
      getPredefinedDbms().addAllTo(ourPredefined);
    }

    private static JBIterable<Dbms> getPredefinedDbms() {
      return JBIterable.of(Dbms.class.getDeclaredFields()).filter(f -> {
        if (!ReflectionUtil.isAssignable(Dbms.class, f.getType())) return false;
        int modifiers = f.getModifiers();
        int req = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
        return (modifiers & req) == req;
      }).filterMap(f -> {
        try {
          return (Dbms)f.get(null);
        }
        catch (IllegalAccessException e) {
          LOG.warn(e);
          return null;
        }
      });
    }

    private static void addDbms(DbmsBean bean, PluginDescriptor pluginDescriptor) {
      Dbms dbms = getDbms(bean, pluginDescriptor);
      if (dbms == null) return;
      String id = bean.id;
      String name = dbms.getName();
      if (id == null) {
        LOG.error(name + " should define `id=\"" + name + "\"` in xml");
      }
      else if (!id.equals(name)) {
        LOG.error(name + " has defined id=" + id + ", but they should match");
      }
      else {
        ourDescriptors.putValue(dbms, pluginDescriptor);
        if (ourValues.get(name) != dbms) {
          LOG.error(bean.instance + " does not match existing dbms");
        }
      }
    }

    private static Dbms getDbms(DbmsBean bean, PluginDescriptor pluginDescriptor) {
      int dotIdx = bean.instance.lastIndexOf('.');
      if (dotIdx < 0) {
        LOG.error("Class field reference should contain `.` in: " + bean.instance + "[" + pluginDescriptor.getPluginId() + "]");
        return null;
      }
      try {
        Class<?> holder = Class.forName(bean.instance.substring(0, dotIdx), true, pluginDescriptor.getPluginClassLoader());
        Dbms dbms = ReflectionUtil.getStaticFieldValue(holder, Dbms.class, bean.instance.substring(dotIdx + 1));
        if (dbms == null) {
          LOG.error("Static field not found: " + bean.instance + "[" + pluginDescriptor.getPluginId() + "]");
        }
        return dbms;
      }
      catch (Exception e) {
        LOG.error("Unable to find dbms: " + bean.instance + "[" + pluginDescriptor.getPluginId() + "]", e);
        return null;
      }
    }

    private static Map<String, Dbms> allDbms() {
      return ourValues;
    }
  }
}
