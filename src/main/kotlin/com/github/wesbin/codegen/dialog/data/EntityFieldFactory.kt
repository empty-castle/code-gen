package com.github.wesbin.codegen.dialog.data

import com.github.wesbin.codegen.dialog.util.StringUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

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