package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.ModelCodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.field.model.ModelFieldModule

class ModelCodeGenModules: CodeGenModules() {

    override fun code(): CodeModule = ModelCodeModule.INSTANCE
    override fun field(): FieldModule = ModelFieldModule.INSTANCE
}
