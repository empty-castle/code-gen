package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class IntType private constructor(): DataType(){

    companion object {
        val INSTANCE = IntType()
    }

    override val name: String = "INT"
    override val entityMappingTypes: List<String> = listOf(
        "Integer"
    )
}