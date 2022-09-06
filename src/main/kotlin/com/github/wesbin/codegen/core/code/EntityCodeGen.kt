package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.CodeGenModules
import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.core.modules.field.entity.EntityFieldModule
import com.github.wesbin.codegen.core.modules.type.mapping.MappingType
import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

class EntityCodeGen private constructor(): CodeGen {

    companion object {
        val INSTANCE = EntityCodeGen()
    }

    override fun generate(codeGenModules: CodeGenModules, observableProperties: ObservableProperties): String {
        val packageName: String = observableProperties.packageComboBox!!.text

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<Field> = mutableListOf()

        val entityFieldFactory: EntityFieldModule = EntityFieldModule.INSTANCE
        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            if (dasColumn != null) {
                val mappingType: MappingType =
                    codeGenModules.type.getMappingDataType(dasColumn.dataType)?.getType() ?: throw Exception()
                mappingType.importPath?.let(imports::add)
                fields.add(entityFieldFactory.createTypeField(dasColumn, mappingType.name))
            }
        }

        // use lombok
        imports.add("lombok.Getter")
        imports.add("lombok.Setter")
        imports.add("javax.persistence.*")

        // start entity
        var result = """
            |package $packageName
            |
        """.trimMargin()

        // set import
        imports.forEach { s: String ->
            result += """
            |
            |import $s
        """.trimMargin()
        }

        // start class
        result += """
            |
            |
            |@Entity
            |@Table(name = "${observableProperties.selectedTable!!.name}")
            |@Getter
            |@Setter
            |open class ${observableProperties.className} { 
            |
            |
        """.trimMargin()

        // set field
        fields.forEach { columnData -> result += "${columnData.getField()}\n" }

        // end class
        result += """
            |} 
            |
        """.trimMargin()


        return result
    }
}