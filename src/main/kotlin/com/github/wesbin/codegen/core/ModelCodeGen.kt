package com.github.wesbin.codegen.core

import com.github.wesbin.codegen.core.codeModule.CodeModule
import com.github.wesbin.codegen.core.codeModule.ModelCodeModule
import com.github.wesbin.codegen.core.field.FieldModule
import com.github.wesbin.codegen.core.field.entity.EntityFieldModule
import com.github.wesbin.codegen.core.type.entity.EntityTypeMappingData

object ModelCodeGen: CodeGenFactory {

    override fun code(): CodeModule = ModelCodeModule()
    override fun field(): FieldModule = EntityFieldModule()
    override val type: Array<EntityTypeMappingData> = EntityTypeMappingData.values()
}
