package com.github.wesbin.codegen.dialog.data

abstract class EntityColumnData() {

    open val isPrimary: Boolean = false
    open val columnName: String = ""
    open val attributeType: String = ""
    open val attributeName: String = ""

    fun getId(): String? {
        return if (isPrimary) "@Id" else null
    }

    open fun getColumn(): String {
        return """@Column(name = "$columnName")"""
    }

    fun getAnnotations(): String {
        var result = ""
        getId()?.let {
            result += "    $it\n"
        }
        result += "    ${getColumn()}"
        return result
    }

    fun getField(): String {
        var result = "${getAnnotations()}\n"
        result += "    open var $attributeName: $attributeType? = null\n"
        return result
    }
}