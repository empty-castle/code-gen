package com.github.wesbin.codegen.core.type

enum class MappingTypeCollection(val importPath: kotlin.String?) {

    LocalDate("java.time.LocalDate"),
    BigDecimal("java.math.BigDecimal"),
    Integer(null),
    String(null),
}