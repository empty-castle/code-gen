package com.github.wesbin.codegen.core.type.entity

import com.github.wesbin.codegen.core.type.DataTypeMap

class EntityDataTypeMap: DataTypeMap() {

    override val mappingData: Map<String, String> =
        mapOf(
            "NUMBER" to "java.math.BigDecimal",
            "DECIMAL" to "java.math.BigDecimal",
            "BIGINT" to "java.math.BigDecimal",
            "INT" to "Integer",
            "DATE" to "java.time.LocalDate",
            "DATETIME" to "java.time.LocalDate",
            "VARCHAR" to "String",
            "VARCHAR2" to "String",
            "TEXT" to "String",
        )
}