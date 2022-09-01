package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.MappingDataType
import com.github.wesbin.codegen.core.type.MappingTypeCollection

class DateType private constructor() : MappingDataType() {

    companion object {
        val INSTANCE = DateType()
    }

    override val name: String = "DATE"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.LocalDate
    )
}