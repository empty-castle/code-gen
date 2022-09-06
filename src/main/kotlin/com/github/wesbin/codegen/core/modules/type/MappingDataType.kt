package com.github.wesbin.codegen.core.modules.type

import com.github.wesbin.codegen.core.modules.type.mapping.MappingType

abstract class MappingDataType {

    abstract val name: String
    abstract val mappingTypes: List<MappingType>

    fun getType(index: Int = 0): MappingType = mappingTypes[index]
}