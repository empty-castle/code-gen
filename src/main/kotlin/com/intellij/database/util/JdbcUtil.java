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
package com.intellij.database.util;

import com.intellij.database.model.DataType;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Types;

public final class JdbcUtil {


  private JdbcUtil() {
  }

  public static boolean hasScaleAndPrecision(int jdbcType) {
    switch(jdbcType) {
      case Types.DECIMAL:
      case Types.NUMERIC:
      case Types.FLOAT:
      case Types.REAL:
      case Types.DOUBLE:
        return true;
      default: return false;
    }
  }

  public static boolean hasLength(int jdbcType) {
    switch(jdbcType) {
      case Types.CHAR:
      case Types.VARCHAR:
      case Types.LONGVARCHAR:
        return true;
      default:return false;
    }
  }

  @NotNull
  public static String getJdbcTypeName(@NotNull DataType dataType) {
    return getJdbcTypeName(dataType, true);
  }

  @NotNull
  public static String getJdbcTypeName(@NotNull final DataType dataType, boolean addLength) {
    final String sqlType = dataType.typeName;
    if (StringUtil.isNotEmpty(sqlType) && sqlType.indexOf('(') > -1) return sqlType;
    final int jdbcType = dataType.jdbcType;
    final String typeName = StringUtil.isNotEmpty(sqlType) ? sqlType : getJdbcTypeName(jdbcType);
    if (addLength) {
      boolean hasLength = hasLength(jdbcType);
      boolean hasScaleAndPrecision = !hasLength && hasScaleAndPrecision(jdbcType);
      int length = hasLength || hasScaleAndPrecision ? dataType.getLength() : -1;
      int scale = hasScaleAndPrecision ? dataType.getScale() : -1;
      if (hasLength && length > 0) {
        return typeName + "(" + length + ")";
      }
      else if (hasScaleAndPrecision && length > 0 && scale >= 0) {
        return typeName + "(" + length + ", " + scale + ")";
      }
    }
    return typeName;
  }

  /**
   * Returns JDBC type name string, i.e. "LONGVARCHAR" for Types.LONGVARCHAR.
   * @param jdbcType one of java.sql.Types.* constants
   * @return type name
   */
  @NotNull
  public static String getJdbcTypeName(final int jdbcType) {
    @NonNls final String result;
    switch (jdbcType) {
      case Types.BIT:
        result = "BIT";
        break;
      case Types.TINYINT:
        result = "TINYINT";
        break;
      case Types.SMALLINT:
        result = "SMALLINT";
        break;
      case Types.INTEGER:
        result = "INTEGER";
        break;
      case Types.BIGINT:
        result = "BIGINT";
        break;
      case Types.FLOAT:
        result = "FLOAT";
        break;
      case Types.REAL:
        result = "REAL";
        break;
      case Types.DOUBLE:
        result = "DOUBLE";
        break;
      case Types.NUMERIC:
        result = "NUMERIC";
        break;
      case Types.DECIMAL:
        result = "DECIMAL";
        break;
      case Types.CHAR:
        result = "CHAR";
        break;
      case Types.VARCHAR:
        result = "VARCHAR";
        break;
      case Types.LONGVARCHAR:
        result = "LONGVARCHAR";
        break;
      case Types.DATE:
        result = "DATE";
        break;
      case Types.TIME:
        result = "TIME";
        break;
      case Types.TIMESTAMP:
        result = "TIMESTAMP";
        break;
      case Types.BINARY:
        result = "BINARY";
        break;
      case Types.VARBINARY:
        result = "VARBINARY";
        break;
      case Types.LONGVARBINARY:
        result = "LONGVARBINARY";
        break;
      case Types.NULL:
        result = "NULL";
        break;
      case Types.OTHER:
        result = "OTHER";
        break;
      case Types.JAVA_OBJECT:
        result = "JAVA_OBJECT";
        break;
      case Types.DISTINCT:
        result = "DISTINCT";
        break;
      case Types.STRUCT:
        result = "STRUCT";
        break;
      case Types.ARRAY:
        result = "ARRAY";
        break;
      case Types.BLOB:
        result = "BLOB";
        break;
      case Types.CLOB:
        result = "CLOB";
        break;
      case Types.REF:
        result = "REF";
        break;
      case Types.DATALINK:
        result = "DATALINK";
        break;
      case Types.BOOLEAN:
        result = "BOOLEAN";
        break;
      default:
        result = "UNKNOWN";
    }
    return result;
  }

  public static int guessJdbcTypeByName(final String name) {
    if (StringUtil.isEmpty(name)) return Types.OTHER;
    @NonNls final String fixed = StringUtil.toUpperCase(name);
    if (fixed.contains("BINARY_FLOAT")) return Types.FLOAT;
    else if (fixed.contains("BINARY")) return Types.VARBINARY;
    else if (fixed.contains("BIT")) return Types.BIT;
    else if (fixed.contains("BOOL")) return Types.BOOLEAN;
    else if (fixed.contains("DATERANGE")) return Types.OTHER;
    else if (fixed.contains("DATE")) return Types.DATE;
    else if (fixed.contains("TIMESTAMP")) return Types.TIMESTAMP;
    else if (fixed.contains("TIME")) return Types.TIME;
    else if (fixed.contains("REAL") || fixed.contains("NUMBER")) return Types.REAL;
    else if (fixed.contains("FLOAT")) return Types.FLOAT;
    else if (fixed.contains("DOUBLE")) return Types.DOUBLE;
    else if (fixed.equals("CHAR") && !fixed.contains("VAR")) return Types.CHAR;
    else if (fixed.contains("INT") && !fixed.contains("INTERVAL") && !fixed.contains("POINT")) return Types.INTEGER;
    else if (fixed.contains("DECIMAL")) return Types.DECIMAL;
    else if (fixed.contains("NUMERIC")) return Types.NUMERIC;
    else if (fixed.contains("CHAR") || fixed.contains("TEXT")) return Types.VARCHAR;
    else if (fixed.contains("BLOB")) return Types.BLOB;
    else if (fixed.contains("CLOB")) return Types.CLOB;
    else if (fixed.contains("REFERENCE")) return Types.REF;
    return Types.OTHER;
  }

  public static boolean isDouble(@Nullable String clazz) {
    return Double.class.getName().equals(clazz) ||
           "oracle.sql.BINARY_DOUBLE".equals(clazz);
  }

  public static boolean isFloat(@Nullable String clazz) {
    return Float.class.getName().equals(clazz) ||
           "oracle.sql.BINARY_FLOAT".equals(clazz);
  }

  public static boolean isBigDecimal(@Nullable String clazz) {
    return BigDecimal.class.getName().equals(clazz);
  }

  public static boolean isNumberType(int jdbcType) {
    boolean result;
    switch (jdbcType) {
      case Types.BIGINT:
      case Types.DECIMAL:
      case Types.DOUBLE:
      case Types.FLOAT:
      case Types.INTEGER:
      case Types.NUMERIC:
        result = true;
        break;
      default:
        result = false;
    }
    return result;
  }

  public static boolean isStringType(int jdbcType) {
    switch (jdbcType) {
      case Types.VARCHAR:
      case Types.CHAR:
      case Types.CLOB:
      case Types.LONGVARCHAR:
      case Types.LONGNVARCHAR:
      case Types.NCHAR:
      case Types.NVARCHAR:
      case Types.NCLOB:
        return true;
      default:
        return false;
    }
  }

  public static boolean isDateTimeType(int jdbcType) {
    switch (jdbcType) {
      case Types.DATE:
      case Types.TIME:
      case Types.TIMESTAMP:
        return true;
      default:
        return false;
    }
  }

  @NlsSafe
  @NotNull
  public static String getLongMessage(@NotNull Throwable e) {
    return getMessagePrefix(e) + getMessage(e);
  }

  @NlsSafe
  @NotNull
  public static String getMessage(@NotNull Throwable t) {
    String m = t.getMessage();
    return StringUtil.isNotEmpty(m) ? m.trim() : t.getClass().getName();
  }

  @NotNull
  public static String getMessagePrefix(@NotNull Throwable t) {
    if (isUserOutput(t)) return "";
    if (!(t instanceof SQLException)) return "";
    SQLException e = (SQLException)t;
    String state = e.getSQLState();
    int code = e.getErrorCode();
    if (StringUtil.isEmpty(state)) {
      return code != 0 ? "[" + code + "] " : "";
    }
    else if (code != 0) {
      return "[" + state + "][" + code + "] ";
    }
    else if (!isZeroState(state)) {
      return "[" + state + "] ";
    }
    return "";
  }

  private static boolean isZeroState(@Nullable String state) {
    return "0".equals(state) || "00000".equals(state) || "S0000".equals(state);
  }

  public static boolean isUserOutput(@NotNull Throwable t) {
    return t instanceof SQLWarning && ((SQLWarning)t).getErrorCode() == 0 && isUserOutputState(((SQLWarning)t).getSQLState());
  }

  private static boolean isUserOutputState(@Nullable String state) {
    return StringUtil.isEmpty(state) || isZeroState(state) || /* MSSQL: PRINT */ "S0001".equals(state);
  }
}
