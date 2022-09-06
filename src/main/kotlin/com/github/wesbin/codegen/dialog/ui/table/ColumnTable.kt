package com.github.wesbin.codegen.dialog.ui.table

import javax.swing.table.AbstractTableModel

class ColumnTable : AbstractTableModel() {

    private val rows = mutableListOf<ColumnTableRecordData>()

    override fun getRowCount(): Int {
        return rows.size
    }

    override fun getColumnCount(): Int {
        return 4
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val columnRecordRaw = rows[rowIndex]
        return when (columnIndex) {
            0 -> columnRecordRaw.columnName
            1 -> columnRecordRaw.columnType
            2 -> columnRecordRaw.attributeName
            3 -> columnRecordRaw.attributeType
            else -> ""
        }
    }

    override fun getColumnName(column: Int): String {
        return when (column) {
            0 -> "ColumnName"
            1 -> "ColumnType"
            2 -> "AttributeName"
            3 -> "AttributeType"
            else -> ""
        }
    }

    fun add(item: ColumnTableRecordData) {
        val lastRow: Int = rows.size
        rows.add(item)
        fireTableRowsInserted(lastRow, lastRow)
    }

    fun remove(rowIndex: Int) {
        rows.removeAt(rowIndex)
        fireTableRowsDeleted(rowIndex, rowIndex)
    }

    fun clear() {
        rows.clear()
        fireTableDataChanged()
    }
}