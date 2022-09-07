package com.github.wesbin.codegen.core.modules.type

import com.github.wesbin.codegen.core.modules.type.kind.*
import com.intellij.database.model.DataType

/*
* https://documentation.softwareag.com/webmethods/adapters_estandards/Adapters/JDBC/JDBC_10-3/10-3_Adapter_for_JDBC_webhelp/index.html#page/jdbc-webhelp/co-jdbc_data_type_to_java_data_type.html
* */
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