package com.github.wesbin.codegen.dialog.data

data class SimpleColumn(
    override val isPrimary: Boolean,
    override val columnName: String,
    override val attributeType: String,
    override val attributeName: String,
): EntityColumnData()