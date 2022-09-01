package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.MappingDataType
import com.github.wesbin.codegen.core.type.MappingTypeCollection

class IntType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = IntType()
    }

    override val name: String = "INT"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.Integer
    )
}