package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.MappingTypeCollection

class BigintType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = BigintType()
    }

    override val name: String = "BIGINT"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.LocalDate
    )
}