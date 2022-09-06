package com.github.wesbin.codegen.core.code

import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.field.entity.EntityFieldModule
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType
import com.github.wesbin.codegen.dialog.observer.ObservableProperties
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

class EntityCodeGen(): CodeGen() {

    override val fieldModule: FieldModule = EntityFieldModule.INSTANCE

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