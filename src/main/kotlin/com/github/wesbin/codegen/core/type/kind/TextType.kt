package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.MappingDataType
import com.github.wesbin.codegen.core.type.MappingTypeCollection

class TextType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = TextType()
    }

    override val name: String = "TEXT"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.String
    )
}