package com.github.wesbin.codegen.dialog.panel

import com.github.wesbin.codegen.dialog.util.StringUtil
import com.github.wesbin.codegen.dialog.util.TypeUtil
import com.intellij.database.model.DasColumn
import com.intellij.database.model.DasObject
import com.intellij.database.util.DasUtil
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel
import kotlin.reflect.KProperty

@Suppress("UnstableApiUsage")
class RightPanel(private val observableProperties: ObservableProperties): Panel, Observer {

    private val tableModel: ColumnTable = ColumnTable()

    private lateinit var classNameField: JBTextField
    private lateinit var panel: DialogPanel

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
                classNameField = textField()
                    .horizontalAlign(HorizontalAlign.FILL)
//                    .bindText(observableProperties.codeData::className)
                    .bindText(observableProperties::className)
                    .component
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
            .apply {
                panel = this
            }
    }

    fun apply() {
        panel.apply()
    }

    override fun update(property: KProperty<*>, newValue: Any?) {
        if (property.name == "selectedTable") {
            val selectedTable = newValue as DasObject?
            tableModel.clear()
            classNameField.text = StringUtil.toPascalCase(selectedTable?.name)
            if (selectedTable != null) {
                DasUtil.getColumns(selectedTable).forEach { dasColumn: DasColumn? ->
                    if (dasColumn != null) {
                        tableModel.add(
                            ColumnRecordRaw(
                                dasColumn.name,
                                dasColumn.dataType.specification,
                                StringUtil.toCamelCase(dasColumn.name),
                                TypeUtil.toAttributeType(dasColumn.dataType)
                            )
                        )
                    }
                }
            }
        }
    }
}