package com.github.wesbin.intellijplugin.ui.sql

class QueryGen(private val schema: String, private val table: String, private val columns: MutableList<String>) {
    private val len = columns.size

    fun create() {

    }

    fun select(): String {
        var selectQuery = """"""
        for (i in 0 until len) {
            val column = columns[i]
            selectQuery +=
                when(i) {
                    1, len - 1 -> """   |$column"""
                    else -> """ |$column
                                |
                            """
                }.trimMargin()
        }
        return selectQuery
    }

    fun update() {

    }

    fun delete() {

    }
}