package com.github.wesbin.codegen.dialog.data

import com.intellij.database.model.DasColumn

class EntityFieldFactory {

    fun createEntityField(dasColumn: DasColumn, attributeType: String): EntityField =
        when (attributeType) {
            "BigDecimal" -> {
                EntityNumberField(dasColumn, attributeType)
            }
            else -> {
                EntitySimpleField(dasColumn, attributeType)
            }
        }
}