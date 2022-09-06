package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.mapping.MappingType

class BigintType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = BigintType()
    }

    override val name: String = "BIGINT"
    override val mappingTypes: List<MappingType> = listOf(
        MappingType.LocalDate
    )
}