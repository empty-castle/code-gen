package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class DateTimeType private constructor(): CodeGenDataType(){

    companion object {
        val INSTANCE = DateTimeType()
    }

    override val name: String = "DATETIME"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.LocalDate
    )
}