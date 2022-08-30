package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.EntityCodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.field.entity.EntityFieldModule
import com.github.wesbin.codegen.core.type.TypeModule
import com.github.wesbin.codegen.core.type.entity.EntityTypeMappingData

object EntityCodeGen: CodeGenFactory {

    override fun code(): CodeModule = EntityCodeModule()
    override fun field(): FieldModule = EntityFieldModule()
    override val type: Array<TypeModule> = EntityTypeMappingData.values().map { it as TypeModule }.toTypedArray()
}