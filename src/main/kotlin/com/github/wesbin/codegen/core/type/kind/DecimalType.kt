package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.MappingDataType
import com.github.wesbin.codegen.core.type.MappingTypeCollection

class DecimalType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = DecimalType()
    }

    override val name: String = "DECIMAL"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.BigDecimal
    )
}