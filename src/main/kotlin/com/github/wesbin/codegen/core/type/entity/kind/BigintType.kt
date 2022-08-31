package com.github.wesbin.codegen.core.type.entity.kind

import com.github.wesbin.codegen.core.type.DataType

class BigintType private constructor(): DataType(){

    companion object {
        val INSTANCE = BigintType()
    }

    override val name: String = "BIGINT"
    override val mappingTypeList: List<String> = listOf(
        "java.math.BigDecimal"
    )
}