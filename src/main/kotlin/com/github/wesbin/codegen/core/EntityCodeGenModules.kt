package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.EntityCodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.field.entity.EntityFieldModule

class EntityCodeGenModules: CodeGenModules() {

    override fun code(): CodeModule = EntityCodeModule()
    override fun field(): FieldModule = EntityFieldModule()
}