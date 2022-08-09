package com.github.wesbin.codegen.dialog.data

data class EntityColumnData(
    val isPrimary: Boolean,
    val columnName: String,
    val attributeType: String,
    val attributeName: String,
) {
    fun getId(): String? {
        return if (isPrimary) "@Id" else null
    }

    fun getColumn(): String {
        return """| @Column(name = "$columnName")""".trimMargin()
    }

    fun getAllAnnotation(): String {
        var result = """"""
        getId()?.let {
            result += it
        }
        result += getColumn()
        return result
    }
}