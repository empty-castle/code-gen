package com.github.wesbin.codegen.core.field.entity

import com.intellij.database.model.DasColumn

data class EntitySimpleField(val dasColumn: DasColumn, val attributeType: String)
    : EntityField(dasColumn, attributeType)