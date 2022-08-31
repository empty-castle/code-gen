package com.github.wesbin.codegen.core.type.entity.kind

import com.github.wesbin.codegen.core.type.DataType

class IntType private constructor(): DataType(){

    companion object {
        val INSTANCE = IntType()
    }

    override val name: String = "INT"
    override val mappingTypeList: List<String> = listOf(
        "Integer"
    )
}