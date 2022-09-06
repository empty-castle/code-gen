package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.MappingDataType
import com.github.wesbin.codegen.core.modules.type.MappingTypeCollection

class VarcharType private constructor() : MappingDataType() {

    companion object {
        val INSTANCE = VarcharType()
    }

    override val name: String = "VARCHAR2"
    override val mappingTypes: List<MappingTypeCollection> = listOf(
        MappingTypeCollection.String
    )
}