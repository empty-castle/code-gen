package com.github.wesbin.codegen.core.modules.field.model

import com.github.wesbin.codegen.core.modules.field.Field
import com.github.wesbin.codegen.core.modules.field.FieldModule
import com.intellij.database.model.DasColumn

class ModelFieldModule private constructor(): FieldModule {

    companion object {
        val INSTANCE = ModelFieldModule()
    }

    override fun createTypeField(dasColumn: DasColumn, attributeType: String): Field {
        return ModelField(dasColumn, attributeType)
    }
}