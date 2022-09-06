package com.github.wesbin.codegen.core.modules.type.mapping

enum class CodeGenMappingType(val importPath: kotlin.String?) {

    LocalDate("java.time.LocalDate"),
    BigDecimal("java.math.BigDecimal"),
    Integer(null),
    String(null),
}