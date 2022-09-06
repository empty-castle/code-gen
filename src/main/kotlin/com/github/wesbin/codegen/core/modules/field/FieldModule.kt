package com.github.wesbin.codegen.core.modules.field

import com.intellij.database.model.DasColumn

interface FieldModule {

    fun createTypeField(dasColumn: DasColumn, attributeType: String): Field
}