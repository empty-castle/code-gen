package com.github.wesbin.codegen.core.type

import com.intellij.database.model.DataType

interface TypeModule {
    val attributeType: String
    fun getMappingData(type: DataType): String
}