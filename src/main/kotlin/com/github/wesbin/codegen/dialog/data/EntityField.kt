package com.github.wesbin.codegen.dialog.data

import com.github.wesbin.codegen.dialog.util.StringUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

abstract class EntityField(private val dasColumn: DasColumn, private val attributeType: String) {

    private val attributeName: String = StringUtil.toCamelCase(dasColumn.name)

    private fun getId(): String? {
        return if (DasUtil.isPrimary(dasColumn)) "@Id" else null
    }

    open fun getColumn(): String {
        return """@Column(name = "${dasColumn.name}")"""
    }

    private fun getAnnotations(): String {
        var result = ""
        getId()?.let {
            result += "    $it\n"
        }
        result += "    ${getColumn()}"
        return result
    }

    fun getField(): String {
        var result = "${getAnnotations()}\n"
        result += "    open var $attributeName: $attributeType? = null\n"
        return result
    }
}