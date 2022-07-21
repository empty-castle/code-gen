package com.github.wesbin.codegen.dialog.util

import com.intellij.database.util.Case
import com.intellij.psi.codeStyle.NameUtil

object StringUtil {

    fun toPascalCase(str: String): String {
        return NameUtil.splitNameIntoWords(str)
            .joinToString("") { s: String -> Case.LOWER.apply(s).replaceFirstChar { it.uppercase() } }
    }
}