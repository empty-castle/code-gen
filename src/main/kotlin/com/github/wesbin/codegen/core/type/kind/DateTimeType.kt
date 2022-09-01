package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.MappingDataType
import com.github.wesbin.codegen.core.type.MappingTypeCollection

class DateTimeType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = DateTimeType()
    }

    override val name: String = "DATETIME"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.LocalDate
    )
}