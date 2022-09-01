package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class TextType private constructor(): DataType(){

    override val name: String = "TEXT"
    override val entityMappingTypes: List<String> = listOf(
        "String"
    )
}