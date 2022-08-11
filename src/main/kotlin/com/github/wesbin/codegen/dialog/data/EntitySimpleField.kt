package com.github.wesbin.codegen.dialog.data

import com.intellij.database.model.DasColumn

data class EntitySimpleField(val dasColumn: DasColumn, val attributeType: String): EntityField(dasColumn, attributeType)