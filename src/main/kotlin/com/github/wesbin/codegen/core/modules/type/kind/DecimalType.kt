package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class DecimalType private constructor(): CodeGenDataType(){

    companion object {
        val INSTANCE = DecimalType()
    }

    override val name: String = "DECIMAL"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.BigDecimal
    )
}