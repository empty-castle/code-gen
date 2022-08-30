package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.type.TypeModule

interface CodeGenFactory {

    fun code(): CodeModule
    fun field(): FieldModule
    fun type(): TypeModule
}