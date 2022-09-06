package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class NumberType private constructor(): CodeGenDataType() {

    companion object {
        val INSTANCE = NumberType()
    }

    override val name: String = "NUMBER"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.BigDecimal
    )
}
