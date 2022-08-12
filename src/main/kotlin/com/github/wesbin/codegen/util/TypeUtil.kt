package com.github.wesbin.codegen.util

import com.github.wesbin.codegen.core.type.ColumnType
import com.github.wesbin.codegen.core.type.ImportColumnType
import com.intellij.database.model.DataType

object TypeUtil {

    fun toAttributeType(type: DataType): String {
        val columnType: ColumnType? = ColumnType.values().find { it.name == type.typeName.uppercase() }
        return columnType?.attributeType ?: "[Error]TypeUtil.toAttributeType: Not founded ${type.typeName} from ColumnType Enum Class"
    }

    fun getAssociatedImport(attributeType: String): String? {
        return ImportColumnType.values().find { it.name == attributeType.uppercase() }?.importCode
    }
}