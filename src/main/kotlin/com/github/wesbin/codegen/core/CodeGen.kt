package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.entity.EntityField
import com.github.wesbin.codegen.core.entity.EntityFieldFactory
import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

object CodeGen {

    // todo notnull
    fun genEntity(observableProperties: ObservableProperties): String {

        val packageName: String = observableProperties.packageComboBox!!.text

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<EntityField> = mutableListOf()

        val entityFieldFactory = EntityFieldFactory()
        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            if (dasColumn != null) {
                val attributeType = TypeUtil.toAttributeType(dasColumn.dataType)
                val associatedImport = TypeUtil.getAssociatedImport(attributeType)
                associatedImport?.let(imports::add)
                fields.add(entityFieldFactory.createEntityField(dasColumn, attributeType))
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
        imports.forEach { s: String -> result += """
            |
            |import $s
        """.trimMargin() }

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
        fields.forEach { columnData -> result += "${columnData.getField()}\n"}

        // end class
        result += """
            |} 
            |
        """.trimMargin()


        return result
    }
}