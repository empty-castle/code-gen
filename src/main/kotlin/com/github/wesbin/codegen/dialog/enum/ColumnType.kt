package com.github.wesbin.codegen.dialog.enum

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