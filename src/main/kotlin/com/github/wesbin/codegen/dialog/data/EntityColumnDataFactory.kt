package com.github.wesbin.codegen.dialog.data

import com.github.wesbin.codegen.dialog.util.StringUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.util.DasUtil

class EntityColumnDataFactory {

    fun createEntityColumnData(dasColumn: DasColumn, attributeType: String): EntityColumnData =
        when (attributeType) {
            "BigDecimal" -> {
                NumberColumn(
                    DasUtil.isPrimary(dasColumn),
                    dasColumn.name,
                    attributeType,
                    StringUtil.toCamelCase(dasColumn.name),
                    dasColumn.dataType.size,
                    dasColumn.dataType.scale,
                )
            }
            else -> {
                SimpleColumn(
                    DasUtil.isPrimary(dasColumn),
                    dasColumn.name,
                    attributeType,
                    StringUtil.toCamelCase(dasColumn.name)
                )
            }
        }
}