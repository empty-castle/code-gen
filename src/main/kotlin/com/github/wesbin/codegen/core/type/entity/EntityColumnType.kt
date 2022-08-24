package com.github.wesbin.codegen.core.type.entity

import com.github.wesbin.codegen.core.type.ColumnType

enum class EntityColumnType(override val attributeType: String): ColumnType {

    NUMBER("BigDecimal"),
    DECIMAL("BigDecimal"),
    BIGINT("BigDecimal"),

    INT("Integer"),

    DATE("LocalDate"),
    DATETIME("LocalDate"),

    VARCHAR("String"),
    VARCHAR2("String"),
    TEXT("String"),

}