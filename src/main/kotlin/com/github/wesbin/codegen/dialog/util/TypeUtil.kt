package com.github.wesbin.codegen.dialog.util

import com.github.wesbin.codegen.dialog.enum.ColumnType
import com.intellij.database.model.DataType

object TypeUtil {

    fun toAttributeType(type: DataType): String {
        val columnType: ColumnType? = ColumnType.values().find { it.name == type.typeName.uppercase() }
        return columnType?.attributeType ?: "[Error]TypeUtil.toAttributeType: Not founded ${type.typeName} from ColumnType Enum Class"
    }
}