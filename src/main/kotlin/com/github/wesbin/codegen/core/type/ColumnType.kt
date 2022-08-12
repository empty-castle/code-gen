package com.github.wesbin.codegen.core.type

enum class ColumnType(val attributeType: String) {

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