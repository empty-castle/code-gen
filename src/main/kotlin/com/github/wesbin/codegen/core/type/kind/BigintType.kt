package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class BigintType private constructor(): DataType(){

    companion object {
        val INSTANCE = BigintType()
    }

    override val name: String = "BIGINT"
    override val entityMappingTypes: List<String> = listOf(
        "java.math.BigDecimal"
    )
}