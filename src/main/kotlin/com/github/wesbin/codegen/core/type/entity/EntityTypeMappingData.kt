package com.github.wesbin.codegen.core.type.entity

import com.github.wesbin.codegen.core.type.TypeModule
import com.intellij.database.model.DataType

enum class EntityTypeMappingData(override val attributeType: String) : TypeModule {

    NUMBER("java.math.BigDecimal"),
    DECIMAL("java.math.BigDecimal"),
    BIGINT("java.math.BigDecimal"),

    INT("Integer"),

    DATE("java.time.LocalDate"),
    DATETIME("java.time.LocalDate"),

    VARCHAR("String"),
    VARCHAR2("String"),
    TEXT("String"),;

    override fun getMappingData(type: DataType): String {
        return "TEST"
    }
}
