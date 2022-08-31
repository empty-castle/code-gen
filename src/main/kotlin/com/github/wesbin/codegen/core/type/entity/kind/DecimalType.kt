package com.github.wesbin.codegen.core.type.entity.kind

import com.github.wesbin.codegen.core.type.DataType

class DecimalType private constructor(): DataType(){

    companion object {
        val INSTANCE = DecimalType()
    }

    override val name: String = "DECIMAL"
    override val mappingTypeList: List<String> = listOf(
        "java.math.BigDecimal"
    )
}