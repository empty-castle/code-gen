package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class VarcharType private constructor() : CodeGenDataType() {

    companion object {
        val INSTANCE = VarcharType()
    }

    override val name: String = "VARCHAR2"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.String
    )
}