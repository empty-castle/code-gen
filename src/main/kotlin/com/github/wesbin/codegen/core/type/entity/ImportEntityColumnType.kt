package com.github.wesbin.codegen.core.type.entity

import com.github.wesbin.codegen.core.type.ImportColumnType

enum class ImportEntityColumnType(override val importCode: String): ImportColumnType {

    BIGDECIMAL("java.math.BigDecimal"),
    LOCALDATE("java.time.LocalDate")
}