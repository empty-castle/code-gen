package com.github.wesbin.codegen.core.field.model

import com.github.wesbin.codegen.core.field.Field
import com.intellij.database.model.DasColumn

class ModelField(private val dasColumn: DasColumn, private val attributeType: String):
    Field(dasColumn, attributeType) {

    override fun getField(): String {
        return "getField"
    }
}