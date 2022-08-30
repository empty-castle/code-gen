package com.github.wesbin.codegen.core.type

import com.intellij.database.model.DataType

abstract class DataTypeMap {

    abstract val mappingData: Map<String, String>

    fun getMappingData(type: DataType)
}