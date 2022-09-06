package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.mapping.MappingType

class DateType private constructor() : MappingDataType() {

    companion object {
        val INSTANCE = DateType()
    }

    override val name: String = "DATE"
    override val mappingTypes: List<MappingType> = listOf(
        MappingType.LocalDate
    )
}