package com.github.wesbin.codegen.core.type

abstract class DataType {

    abstract val name: String
    abstract val mappingTypeList: List<String>
}