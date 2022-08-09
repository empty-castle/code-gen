package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.data.EntityColumnData
import com.github.wesbin.codegen.dialog.data.EntityColumnDataFactory
import com.github.wesbin.codegen.dialog.data.SimpleColumn
import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.dialog.util.StringUtil
import com.github.wesbin.codegen.dialog.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

object CodeGen {

    // todo result 를 import, fields 부분으로 나눠서 작업
    // todo id, notnull 등 annotation 붙이는 작업 해야 함
    fun genEntity(observableProperties: ObservableProperties): String {

        val packageName: String = observableProperties.selectedPackage!!.text
            .replace(observableProperties.selectedSourceRoot!!.second, "")
            .replace("\\", ".")
            .substring(1)

        val imports: MutableSet<String> = mutableSetOf()
        val fields: MutableList<EntityColumnData> = mutableListOf()

        val entityColumnDataFactory = EntityColumnDataFactory()
        DasUtil.getColumns(observableProperties.selectedTable).forEach { dasColumn: DasColumn? ->
            println("isPrimary? ${DasUtil.isPrimary(dasColumn)}")
            if (dasColumn != null) {
                val attributeType = TypeUtil.toAttributeType(dasColumn.dataType)
                val importAttributeType = TypeUtil.toImportAttributeType(attributeType)
                importAttributeType?.let(imports::add)
                fields.add(entityColumnDataFactory.createEntityColumnData(dasColumn, attributeType))
            }
        }

        /*
        *
        * type size
        * DasUtil.getColumns(observableProperties.selectedTable)[6].dataType.size
        *
        * type scale
        * DasUtil.getColumns(observableProperties.selectedTable)[6].dataType.scale
        *
        * */

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