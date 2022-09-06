package com.github.wesbin.codegen.core.modules.type

import com.github.wesbin.codegen.core.modules.type.kind.*
import com.intellij.database.model.DataType

class TypeModule private constructor() {

    companion object {
        val INSTANCE = TypeModule()
    }

    private val mappingData: List<CodeGenDataType> = listOf(
        BigintType.INSTANCE,
        DateTimeType.INSTANCE,
        DateType.INSTANCE,
        DecimalType.INSTANCE,
        IntType.INSTANCE,
        NumberType.INSTANCE,
        TextType.INSTANCE,
        Varchar2Type.INSTANCE,
        VarcharType.INSTANCE,
    )

    fun getMappingDataType(type: DataType): CodeGenDataType? = mappingData.find { it.name == type.typeName.uppercase() }
}