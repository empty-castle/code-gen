package com.github.wesbin.codegen.dialog.data

data class NumberColumn(
    override val isPrimary: Boolean,
    override val columnName: String,
    override val attributeType: String,
    override val attributeName: String,
    val precision: Int,
    val scale: Int
): EntityColumnData() {

    override fun getColumn(): String {
        var result = """@Column(name = "$columnName""""
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