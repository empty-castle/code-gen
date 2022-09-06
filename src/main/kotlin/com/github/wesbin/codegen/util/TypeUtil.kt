package com.github.wesbin.codegen.util

import com.github.wesbin.codegen.core.modules.type.entity.EntityTypeMappingData
import com.github.wesbin.codegen.core.modules.type.entity.ImportEntityColumnType
import com.intellij.database.model.DataType

object TypeUtil {

    fun toAttributeType(type: DataType): String {
        val entityColumnType: EntityTypeMappingData? = EntityTypeMappingData.values().find { it.name == type.typeName.uppercase() }
        return entityColumnType?.attributeType ?: "[Error]TypeUtil.toAttributeType: Not founded ${type.typeName} from ColumnType Enum Class"
    }

    fun getAssociatedImport(attributeType: String): String? {
        return ImportEntityColumnType.values().find { it.name == attributeType.uppercase() }?.importCode
    }
}