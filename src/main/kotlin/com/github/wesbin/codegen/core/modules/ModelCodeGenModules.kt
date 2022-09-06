package com.github.wesbin.codegen.core.modules

import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.github.wesbin.codegen.core.modules.field.model.ModelFieldModule

class ModelCodeGenModules: CodeGenModules() {

    override fun field(): FieldModule = ModelFieldModule.INSTANCE
}
