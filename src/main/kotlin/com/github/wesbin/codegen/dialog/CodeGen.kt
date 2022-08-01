package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.enum.ColumnType
import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.dialog.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

object CodeGen {

    // todo Entity 생성
    fun genEntity(observableProperties: ObservableProperties): String {

        val packageName: String = observableProperties.selectedSourceRoot?.text ?: "com.wesbin.codegen"
        val imports: List<String> = emptyList<String>()
        val fields: List<String> = emptyList<String>()

        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            if (dasColumn != null) {
                val attributeType = TypeUtil.toAttributeType(dasColumn.dataType)
                val importAttributeType = TypeUtil.toImportAttributeType(attributeType)
                println("$attributeType [][] $importAttributeType")
            }
        }

        return """
            package $packageName

            import com.github.wesbin.codegen.dialog.panel.ObservableProperties

            object CodeGen {

                // todo Entity 생성
                fun genEntity(observableProperties: ObservableProperties): String {

                    return ""
                }
            }
        """.trimIndent()
    }
}