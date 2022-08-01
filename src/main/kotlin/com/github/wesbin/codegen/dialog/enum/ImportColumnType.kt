package com.github.wesbin.codegen.dialog.enum

enum class ImportColumnType(val importCode: String) {

    BIGDECIMAL("java.math.BigDecimal"),
    LOCALDATE("java.time.LocalDate")
}