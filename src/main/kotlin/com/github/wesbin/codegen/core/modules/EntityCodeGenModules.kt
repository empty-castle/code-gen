package com.github.wesbin.codegen.core.modules

import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.field.entity.EntityFieldModule

class EntityCodeGenModules: CodeGenModules() {

    override fun field(): FieldModule = EntityFieldModule.INSTANCE
}