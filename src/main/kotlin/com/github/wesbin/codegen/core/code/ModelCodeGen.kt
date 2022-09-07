package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.field.model.ModelFieldModule
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType
import com.github.wesbin.codegen.dialog.observer.ObservableProperties
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

class ModelCodeGen(): CodeGen() {

    override val fieldModule: FieldModule = ModelFieldModule.INSTANCE

    override fun generate(observableProperties: ObservableProperties): String {
        val packageName: String = observableProperties.packageComboBox!!.text

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<Field> = mutableListOf()

        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            if (dasColumn != null) {
                val codeGenMappingType: CodeGenMappingType =
                    typeModule.getMappingDataType(dasColumn.dataType)?.getType() ?: throw Exception()
                codeGenMappingType.importPath?.let(imports::add)
                fields.add(fieldModule.createTypeField(dasColumn, codeGenMappingType.name))
            }
        }

        imports.add("lombok.Getter")
        imports.add("lombok.Setter")

        var result = """
            |package $packageName
            |
        """.trimMargin()

        imports.forEach {
            result += """
                |
                |import $it;
            """.trimMargin()
        }

        result += """
            |
            |
            |@Getter
            |@Setter
            |public class ${observableProperties.className} {
            |
            |
        """.trimMargin()

        fields.forEach { result += """
            |${it.getField()}
            |
        """.trimMargin() }

        result += """
            |}
            |
        """.trimMargin()

        return result
    }
}