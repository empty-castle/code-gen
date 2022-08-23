package com.github.wesbin.codegen.core.field

import com.intellij.database.model.DasColumn

abstract class Field(dasColumn: DasColumn, attributeType: String) {

    open fun getId(): String? {
        return null
    }

    open fun getColumn(): String? {
        return null
    }

    open fun getAnnotations(): String? {
        return null
    }

    abstract fun getField(): String
}