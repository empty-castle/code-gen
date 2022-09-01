package com.github.wesbin.codegen.core.type

import com.github.wesbin.codegen.core.type.kind.*
import com.intellij.database.model.DataType

class DataTypeMap {

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