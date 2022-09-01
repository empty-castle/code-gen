package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.MappingDataType
import com.github.wesbin.codegen.core.type.MappingTypeCollection

class NumberType private constructor(): MappingDataType() {

    companion object {
        val INSTANCE = NumberType()
    }

    override val name: String = "NUMBER"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.BigDecimal
    )
}