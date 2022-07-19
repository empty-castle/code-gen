package com.github.wesbin.intellijplugin.backup.sql

class QueryGen(private val schema: String, private val table: String, private val columns: MutableList<String>) {
    private val len = columns.size

    fun create() {

    }

    fun select(): String {
        var selectQuery = """
            | -- select
            |SELECT 
        """.trimMargin()
        for (i in 0 until len) {
            val column = columns[i]
            selectQuery +=
            if (i == len - 1) {
                """ |$column
                    |
                """.trimMargin()
            } else {
//                탭 두번 포함되어 있음
                """ |$column
                    |       
                """.trimMargin()
            }
        }
        selectQuery += """FROM   ${schema}.${table}""".trimMargin()
        return selectQuery
    }

    fun update() {

    }

    fun delete() {

    }
}