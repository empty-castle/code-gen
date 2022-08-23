package com.github.wesbin.codegen.core.field.entity

import com.github.wesbin.codegen.core.field.Field
import com.github.wesbin.codegen.util.StringUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

abstract class EntityField(private val dasColumn: DasColumn, private val attributeType: String):
    Field(dasColumn, attributeType) {

    private val attributeName: String = StringUtil.toCamelCase(dasColumn.name)

    override fun getId(): String? {
        return if (DasUtil.isPrimary(dasColumn)) "@Id" else null
    }

    override fun getColumn(): String {
        var result = "@Column("
        val params: MutableMap<String, Any> = mutableMapOf()
        params["name"] = dasColumn.name
        params["nullable"] = !dasColumn.isNotNull
        result += params.entries.joinToString(separator = ", ") {
            if (it.value is String) """${it.key} = "${it.value}""""
            else """${it.key} = ${it.value}"""
        }
        result += ")"
        return result
    }

    override fun getAnnotations(): String {
        var result = ""
        getId()?.let {
            result += "    $it\n"
        }
        result += "    ${getColumn()}"
        return result
    }

    override fun getField(): String {
        var result = "${getAnnotations()}\n"
        result += "    open var $attributeName: $attributeType? = null\n"
        return result
    }
}