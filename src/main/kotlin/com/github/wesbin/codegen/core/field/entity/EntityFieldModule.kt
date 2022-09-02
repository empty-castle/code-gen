package com.github.wesbin.codegen.core.field.entity

import com.github.wesbin.codegen.core.field.Field
import com.github.wesbin.codegen.core.field.FieldModule
import com.intellij.database.model.DasColumn

class EntityFieldModule private constructor(): FieldModule {

    companion object {
        val INSTANCE = EntityFieldModule()
    }

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