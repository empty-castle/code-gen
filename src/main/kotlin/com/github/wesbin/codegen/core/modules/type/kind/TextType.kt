package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.mapping.MappingType

class TextType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = TextType()
    }

    override val name: String = "TEXT"
    override val mappingTypes: List<MappingType> = listOf(
        MappingType.String
    )
}