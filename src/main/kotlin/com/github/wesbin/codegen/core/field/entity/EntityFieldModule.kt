package com.github.wesbin.codegen.core.field.entity

import com.github.wesbin.codegen.core.field.Field
import com.github.wesbin.codegen.core.field.FieldModule
import com.intellij.database.model.DasColumn

class EntityFieldModule: FieldModule {

    override fun createTypeField(dasColumn: DasColumn, attributeType: String): Field =
        when (attributeType) {
            "BigDecimal" -> {
                NumberEntityField(dasColumn, attributeType)
            }
            else -> {
                SimpleEntityField(dasColumn, attributeType)
            }
        }
}