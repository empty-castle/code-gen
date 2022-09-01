package com.github.wesbin.codegen.core.type

abstract class DataType {

    abstract val name: String
    abstract val entityMappingTypes: List<String>
}