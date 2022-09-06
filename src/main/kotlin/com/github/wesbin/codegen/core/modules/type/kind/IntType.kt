package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class IntType private constructor(): CodeGenDataType(){

    companion object {
        val INSTANCE = IntType()
    }

    override val name: String = "INT"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.Integer
    )
}