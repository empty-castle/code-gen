package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class DateTimeType private constructor(): DataType(){

    companion object {
        val INSTANCE = DateTimeType()
    }

    override val name: String = "DATETIME"
    override val entityMappingTypes: List<String> = listOf(
        "java.time.LocalDate"
    )
}