package com.github.wesbin.codegen.util

import com.intellij.database.util.Case
import com.intellij.psi.codeStyle.NameUtil

object StringUtil {

    fun toPascalCase(str: String?): String {
        if (str == null) {
            return ""
        }
        return NameUtil.splitNameIntoWords(str)
            .joinToString("") { s: String -> Case.LOWER.apply(s).replaceFirstChar { it.uppercase() } }
    }

    fun toCamelCase(str: String): String {
        return NameUtil.splitNameIntoWords(str).mapIndexed { index, s ->
            if (index != 0) {
                Case.LOWER.apply(s).replaceFirstChar { it.uppercase() }
            } else {
                Case.LOWER.apply(s)
            }
        }.joinToString("")
    }
}