package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.mapping.MappingType

class IntType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = IntType()
    }

    override val name: String = "INT"
    override val mappingTypes: List<MappingType> = listOf(
        MappingType.Integer
    )
}