package com.intellij.sql.formatter

import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.util.NlsSafe
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.sql.dialects.SqlLanguageDialect
import com.intellij.sql.formatter.settings.SqlCodeStyleSettings
import com.intellij.sql.formatter.settings.SqlDialectSpecificCodeStyleScheme
import kotlin.reflect.KClass

/**
 * SQL code style settings and Formatter extension point.
 */
interface SqlDialectCodeStyleProvider {

  companion object {
    @JvmField
    val EP_NAME = ExtensionPointName.create<SqlDialectCodeStyleProvider>("com.intellij.sql.dialectCodeStyleProvider")
  }

  val dialect: SqlLanguageDialect

  val settingsClass: KClass<out SqlCodeStyleSettings>

  /**
   * Language display name, if it differs from the default display name of the dialect.
   */
  val dialectDisplayName: @NlsSafe String?
    get() = null

  fun createCustomSettings(settingsContainer: CodeStyleSettings): SqlCodeStyleSettings

  /**
   * The importance weight, defaults to 0. Bigger importance weight makes a configurable be displayed prior
   * to ones with lower importance weight.
   */
  fun getWeight(): Int = 0

  /**
   * Code style schemas that can be specified as data source code style.
   * Null if no ones.
   */
  val dialectSpecificPredefinedStyleSchemes: List<SqlDialectSpecificCodeStyleScheme>?
    get() = null

}