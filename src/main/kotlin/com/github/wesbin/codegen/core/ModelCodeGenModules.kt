package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.ModelCodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.field.entity.EntityFieldModule

class ModelCodeGenModules: CodeGenModules() {

    override fun code(): CodeModule = ModelCodeModule.INSTANCE
    override fun field(): FieldModule = EntityFieldModule.INSTANCE
}
