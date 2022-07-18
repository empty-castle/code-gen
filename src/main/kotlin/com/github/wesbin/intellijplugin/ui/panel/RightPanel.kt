package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableModel

@Suppress("UnstableApiUsage")
class RightPanel(val observableProperties: ObservableProperties): Panel, Observer {

    private val tableModel: TableModel = object : AbstractTableModel() {

        private val rows = mutableListOf<ColumnItem>(
            ColumnItem("A", "A"),
            ColumnItem("B", "B"),
            ColumnItem("C", "C"),
            ColumnItem("D", "D"),
            ColumnItem("E", "E"),
            ColumnItem("F", "F"),
        )

        override fun getRowCount(): Int {
            return rows.size
        }

        override fun getColumnCount(): Int {
            return 2
        }

        override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
            return rows[rowIndex]
        }

        override fun getColumnName(column: Int): String {
            return if (column == 0) "Name" else "Type"
        }
    }

    override fun createPanel(): DialogPanel {

        val table = JBTable(tableModel)
            .apply {
                setShowGrid(false)
                intercellSpacing = JBUI.emptySize()
                dragEnabled = false
                showVerticalLines = false
                tableHeader.reorderingAllowed = false
                selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION
            }

        return panel {
            row("Class name") {
                textField()
                    .horizontalAlign(HorizontalAlign.FILL)
            }
            row {
                cell(
                    JPanel(BorderLayout())
                        .apply {
                            add(JBScrollPane(table), BorderLayout.CENTER)
                        }
                )
                    .apply {
                        horizontalAlign(HorizontalAlign.FILL)
                        verticalAlign(VerticalAlign.FILL)
                    }
            }
                .resizableRow()
        }
    }

    override fun update() {
        print("RightPanel update")
    }
}

// 컬럼 크기 조절
//                            val width: Int = this.getFontMetrics(this.font).stringWidth("DIRECTORY") + 10
//                            columnModel.getColumn(0)
//                                .apply {
//                                    this.preferredWidth = width
//                                    this.maxWidth = width
//                                    this.minWidth = width
//                                }