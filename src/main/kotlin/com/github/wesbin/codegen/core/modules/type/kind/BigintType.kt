package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class BigintType private constructor(): CodeGenDataType(){

    companion object {
        val INSTANCE = BigintType()
    }

    override val name: String = "BIGINT"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.LocalDate
    )
}