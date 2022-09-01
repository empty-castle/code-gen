package com.github.wesbin.codegen.core.type.kind

import com.github.wesbin.codegen.core.type.DataType

class VarcharType private constructor() : DataType() {

    override val name: String = "VARCHAR2"
    override val entityMappingTypes: List<String> = listOf(
        "String"
    )
}