package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class NumberType private constructor(): DataType() {

    companion object {
        val INSTANCE = NumberType()
    }

    override val name: String = "NUMBER"
    override val entityMappingTypes: List<String> = listOf(
        "java.math.BigDecimal"
    )
}