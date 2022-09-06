package com.github.wesbin.codegen.core.modules.type

import com.github.wesbin.codegen.core.modules.type.mapping.CodeGenMappingType

abstract class CodeGenDataType {

    abstract val name: String
    abstract val codeGenMappingTypes: List<CodeGenMappingType>

    fun getType(index: Int = 0): CodeGenMappingType = codeGenMappingTypes[index]
}