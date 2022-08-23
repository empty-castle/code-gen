package com.github.wesbin.codegen.core.field

import com.intellij.database.model.DasColumn

interface TypeFieldFactory {

    fun createTypeField(dasColumn: DasColumn, attributeType: String): Field
}