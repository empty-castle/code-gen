package com.github.wesbin.codegen.core.modules.field.model

import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.util.StringUtil
import com.intellij.database.model.DasColumn

class ModelField(private val dasColumn: DasColumn, private val attributeType: String):
    Field(dasColumn, attributeType) {

    override fun getField(): String {
        val attributeName: String = StringUtil.toCamelCase(dasColumn.name)
        return "    private $attributeType $attributeName;\n"
    }
}