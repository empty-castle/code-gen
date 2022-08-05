package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.dialog.util.StringUtil
import com.github.wesbin.codegen.dialog.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

object CodeGen {

    // todo Entity 생성
    fun genEntity(observableProperties: ObservableProperties): String {

        // todo is best?
        val packageName: String =
            observableProperties.selectedPackage?.let { selectedPackage ->
                observableProperties.selectedSourceRoot?.let { selectedSourceRoot ->
                    selectedPackage.text.replace(selectedSourceRoot.second, "")
                }
            } ?: "com.wesbin.code-gen"

        val imports: MutableList<String> = mutableListOf<String>()
        val fields: MutableList<Pair<String, String>> = mutableListOf<Pair<String, String>>()

        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            if (dasColumn != null) {
                val attributeType = TypeUtil.toAttributeType(dasColumn.dataType)
                val importAttributeType = TypeUtil.toImportAttributeType(attributeType)
                importAttributeType?.let(imports::add)
                fields.add(Pair(attributeType, StringUtil.toCamelCase(dasColumn.name)))
            }
        }

        // todo packageName to module path
        // start entity
        var result = """
            |package $packageName
            |
        """.trimMargin()

        // set import
        imports.forEach { s: String -> result += """
            |
            |import $s
        """.trimMargin() }

        // start class
        result += """
            |
            |
            |open class ${observableProperties.className} { 
            |
        """.trimMargin()

        // set field
        fields.forEach { (attributeType, attributeName) ->
            result += """
                |
                |   open var $attributeName: $attributeType? = null
                |
            """.trimMargin()
        }

        // end class
        result += """
            |
            |}
            |
        """.trimMargin()

        return result
    }
}