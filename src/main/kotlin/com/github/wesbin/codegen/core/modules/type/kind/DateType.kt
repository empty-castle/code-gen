package com.github.wesbin.codegen.core.modules.type.kind

import com.github.wesbin.codegen.core.modules.type.CodeGenDataType
import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

class DateType private constructor() : CodeGenDataType() {

    companion object {
        val INSTANCE = DateType()
    }

    override val name: String = "DATE"
    override val codeGenMappingTypes: List<CodeGenMappingType> = listOf(
        CodeGenMappingType.LocalDate
    )
}