package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.dialog.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

object CodeGen {

    // todo Entity 생성
    fun genEntity(observableProperties: ObservableProperties): String {

        val packageName: String = observableProperties.selectedSourceRoot?.text ?: "com.wesbin.codegen"
        var result = """
            |package $packageName
            |
        """.trimMargin()

        val imports: MutableList<String> = mutableListOf<String>()
        val fields: MutableList<String> = mutableListOf<String>()

        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            if (dasColumn != null) {
                val attributeType = TypeUtil.toAttributeType(dasColumn.dataType)
                val importAttributeType = TypeUtil.toImportAttributeType(attributeType)
                importAttributeType?.let(imports::add)
                fields.add(attributeType)
            }
        }

        imports.forEach { s: String -> result += """
            |
            |import $s
        """.trimMargin() }

        result += """
            |
            |
            |public class Test { 
            |
        """.trimMargin()

        fields.forEach { s: String ->
            result += """
                |
                |   private $s
                |
            """.trimMargin()
        }

        result += """
            |
            |}
            |
        """.trimMargin()


        return result
    }
}