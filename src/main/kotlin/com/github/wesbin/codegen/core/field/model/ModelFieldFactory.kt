package com.github.wesbin.codegen.core.field.model

import com.github.wesbin.codegen.core.field.Field
import com.github.wesbin.codegen.core.field.TypeFieldFactory
import com.intellij.database.model.DasColumn

class ModelFieldFactory: TypeFieldFactory {

    override fun createTypeField(dasColumn: DasColumn, attributeType: String): Field {
        TODO("Not yet implemented")
    }
}