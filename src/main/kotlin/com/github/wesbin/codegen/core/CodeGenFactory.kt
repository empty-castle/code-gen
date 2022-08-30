package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.type.TypeModule
import com.github.wesbin.codegen.core.type.entity.EntityTypeMappingData

interface CodeGenFactory {

    fun code(): CodeModule
    fun field(): FieldModule
    val type: Array<TypeModule>
}