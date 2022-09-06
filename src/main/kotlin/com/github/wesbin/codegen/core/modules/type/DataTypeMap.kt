package com.github.wesbin.codegen.core.modules.type

import com.github.wesbin.codegen.core.modules.type.kind.*
import com.intellij.database.model.DataType

class DataTypeMap private constructor() {

    companion object {
        val INSTANCE = DataTypeMap()
    }

    private val mappingData: List<MappingDataType> = listOf(
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

    fun getMappingDataType(type: DataType): MappingDataType? = mappingData.find { it.name == type.typeName.uppercase() }
}