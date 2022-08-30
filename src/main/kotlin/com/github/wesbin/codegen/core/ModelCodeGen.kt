package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.ModelCodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.type.ColumnType

object ModelCodeGen: CodeGenFactory {

    override fun code(): CodeModule = ModelCodeModule()

    override fun type(): ColumnType {
        TODO("Not yet implemented")
    }

    override fun field(): FieldModule {
        TODO("Not yet implemented")
    }
}