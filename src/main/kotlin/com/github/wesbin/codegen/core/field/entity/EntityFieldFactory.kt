package com.github.wesbin.codegen.core.field.entity

import com.github.wesbin.codegen.core.field.Field
import com.github.wesbin.codegen.core.field.TypeFieldFactory
import com.intellij.database.model.DasColumn

class EntityFieldFactory: TypeFieldFactory {

    override fun createTypeField(dasColumn: DasColumn, attributeType: String): Field =
        when (attributeType) {
            "BigDecimal" -> {
                EntityNumberField(dasColumn, attributeType)
            }
            else -> {
                EntitySimpleField(dasColumn, attributeType)
            }
        }
}