package com.github.wesbin.codegen.core.type

abstract class MappingDataType {

    abstract val name: String
    abstract val mappingTypes: List<MappingTypeCollection>

    fun getType(index: Int = 0): MappingTypeCollection = mappingTypes[index]
}