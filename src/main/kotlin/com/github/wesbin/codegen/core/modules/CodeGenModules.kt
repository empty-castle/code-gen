package com.github.wesbin.codegen.core.modules

import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.type.DataTypeMap

abstract class CodeGenModules {

    abstract fun field(): FieldModule
    val type: DataTypeMap = DataTypeMap.INSTANCE
}