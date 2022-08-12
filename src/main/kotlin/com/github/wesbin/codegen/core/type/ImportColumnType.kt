package com.github.wesbin.codegen.core.type

enum class ImportColumnType(val importCode: String) {

    BIGDECIMAL("java.math.BigDecimal"),
    LOCALDATE("java.time.LocalDate")
}