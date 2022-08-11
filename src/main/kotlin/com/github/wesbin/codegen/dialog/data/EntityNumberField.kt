package com.github.wesbin.codegen.dialog.data

import com.intellij.database.model.DasColumn

data class EntityNumberField(val dasColumn: DasColumn, val attributeType: String): EntityField(dasColumn, attributeType) {

    private val precision: Int = dasColumn.dataType.size
    private val scale: Int = dasColumn.dataType.scale

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