package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.type.DataTypeMap

abstract class CodeGenModules {

    abstract fun code(): CodeModule
    abstract fun field(): FieldModule
    val type: DataTypeMap = DataTypeMap()
}