package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class DecimalType private constructor(): DataType(){

    companion object {
        val INSTANCE = DecimalType()
    }

    override val name: String = "DECIMAL"
    override val entityMappingTypes: List<String> = listOf(
        "java.math.BigDecimal"
    )
}