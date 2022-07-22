package com.github.wesbin.codegen.dialog.util

import com.github.wesbin.codegen.dialog.enum.ColumnType
import com.intellij.database.model.DataType
import java.util.*

object TypeUtil {

    fun toAttributeType(type: DataType): String {
        return try {
            enumValueOf<ColumnType>(type.typeName.uppercase(Locale.getDefault())).attributeType
        } catch (e: Exception) {
            "[Error]TypeUtil.toAttributeType: Not founded ${type.typeName} from ColumnType Enum Class"
        }
    }
}