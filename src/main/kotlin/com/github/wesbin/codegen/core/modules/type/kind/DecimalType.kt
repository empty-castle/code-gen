package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.MappingTypeCollection

class DecimalType private constructor(): MappingDataType(){

    companion object {
        val INSTANCE = DecimalType()
    }

    override val name: String = "DECIMAL"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.BigDecimal
    )
}