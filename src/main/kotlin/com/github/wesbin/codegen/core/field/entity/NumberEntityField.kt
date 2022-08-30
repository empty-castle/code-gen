package com.github.wesbin.codegen.core.field.entity

import com.intellij.database.model.DasColumn

data class NumberEntityField(val dasColumn: DasColumn, val attributeType: String)
    : EntityField(dasColumn, attributeType) {

    private val precision: Int = dasColumn.dataType.size
    private val scale: Int = dasColumn.dataType.scale

    // todo Column 생성용 MutableMap class 를 하나 생성해서
    override fun getColumn(): String {
        var result = """@Column(name = "${dasColumn.name}""""
        if (precision < Int.MAX_VALUE - 1) {
            result += ", precision = $precision"
        }
        if (scale > 0) {
            result += ", scale = $scale"
        }
        result += ")"
        return result
    }
}