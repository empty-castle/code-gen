package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class TextType private constructor(): CodeGenDataType(){

    companion object {
        val INSTANCE = TextType()
    }

    override val name: String = "TEXT"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.String
    )
}