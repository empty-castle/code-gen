package com.github.wesbin.codegen.core.modules.field.model

import com.github.wesbin.codegen.core.modules.field.Field
import com.intellij.database.model.DasColumn

class ModelField(private val dasColumn: DasColumn, private val attributeType: String):
    Field(dasColumn, attributeType) {

    override fun getField(): String {
        return "getField"
    }
}