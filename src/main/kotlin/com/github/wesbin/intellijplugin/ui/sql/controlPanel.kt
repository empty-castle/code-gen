package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.intellij.database.model.DasModel
import com.intellij.database.model.DasNamespace
import com.intellij.database.model.DasObject
import com.intellij.database.model.ObjectKind
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.Constraints
import com.intellij.ui.dsl.gridLayout.GridLayout
import com.intellij.util.containers.toArray
import com.jetbrains.rd.util.first
import java.awt.Component
import java.awt.event.ItemEvent
import javax.swing.*

@Suppress("UnstableApiUsage")
class ControlPanel(private val bindingProperties: BindingProperties, private val dasModel: DasModel) {
    private lateinit var panel: DialogPanel

//    Combobox
    private lateinit var schemaCombobox: ComboBox<String>
    private lateinit var tableCombobox: ComboBox<DasObject>

//    Row Label
    private val schemaRowLabel: String = "SCHEMA:"
    private val tableRowLabel: String = "TABLE:"
    private val columnRowLabel: String = "COLUMNS:"

//    선택된 스키마에 포함된 테이블
    private val groupedTable: MutableMap<String, MutableList<DasObject>> = mutableMapOf()

//    private val tableColumns: MutableList<String> = mutableListOf()

    private fun update(updatedLabel: String) {
        when (updatedLabel) {
            schemaRowLabel -> schemaUpdate()
            tableRowLabel -> tableUpdate()
        }
    }

    private fun schemaUpdate() {
        val comboBoxModel = tableCombobox.model
        if (comboBoxModel.size > 0) {
            (comboBoxModel as DefaultComboBoxModel).removeAllElements()
        }
        val tableList = groupedTable[schemaCombobox.item]
        if (!tableList.isNullOrEmpty()) {
            tableList
                .forEach { dasObject -> (comboBoxModel as MutableComboBoxModel).addElement(dasObject) }
        }
    }

    private fun tableUpdate() {
//        fixme 현재 뿌려져있는 Column Checkbox 삭제 이후에 원하는 Checkbox 를 생성하는 로직 필요
        val lastComponent = panel.components[panel.componentCount - 1]
        val constraints = (panel.layout as GridLayout).getConstraints(lastComponent as JComponent)
        if (constraints != null) {
            Constraints(constraints.grid, constraints.x, constraints.y + 1)
            panel.add(JBCheckBox("Test", true),
                Constraints(constraints.grid, constraints.x, constraints.y + 1))
            panel.revalidate()
            panel.repaint()
        }
    }

    fun generatePanel(): DialogPanel {

        val expandedDasModel = dasModel
            .traverser()
            .expand { dasObject -> dasObject is DasNamespace }

        val allTable = expandedDasModel
            .filter { dasObject -> dasObject.kind == ObjectKind.TABLE }

        val linkedSchemaList = mutableSetOf<String>()
        allTable
            .forEach { dasObject ->
                val parentSchema = dasObject.dasParent
                if (parentSchema != null) {
                    if (parentSchema.name == "SYS" || parentSchema.name == "SYSTEM") {
                        return@forEach
                    } else {
                        linkedSchemaList.add(parentSchema.name)
                        groupedTable
                            .getOrPut(parentSchema.name) { mutableListOf() }
                            .add(dasObject)
                    }
                }
            }

        panel = panel {
//            fixme 로딩하면서 선택된 값 binding 되어 있지 않는다 but panel.Apply() 실행하면 정상적으로 된다.
            row(schemaRowLabel) {
                schemaCombobox = comboBox(linkedSchemaList.toArray(emptyArray()))
                    .bindItem(bindingProperties::schema)
                    .component
                schemaCombobox.addItemListener {
                    if (it.stateChange == ItemEvent.SELECTED) {
                        update(schemaRowLabel)

                        panel.apply()
                    }
                }
//                    .onApply { println("onApply") } // panel.Apply 로 모습이 바뀔 때
            }

            row(tableRowLabel) {
                tableCombobox =
                    comboBox(groupedTable.first().value.toArray(emptyArray()), DasObjectCellRenderer())
                        .bindItem({ null }, { dasObject ->
                            if (dasObject != null) {
                                bindingProperties.table = dasObject.name
                            }
                        })
                        .component
                tableCombobox.selectedIndex = 0
                tableCombobox.addItemListener {
                    if (it.stateChange == ItemEvent.SELECTED) {
                        update(tableRowLabel)
                        panel.apply()
                    }
                }
            }

            row(columnRowLabel) {
//                val tableColumns = tableCombobox.item.getDasChildren(ObjectKind.COLUMN)
//                if (tableColumns.isNotEmpty) {
//                    tableColumns.forEach { dasObject ->
//                        cell(JBCheckBox(dasObject.name, true))
//                    }
//                }
            }
        }

        return panel
    }
}

class DasObjectCellRenderer: JLabel(), ListCellRenderer<DasObject?> {
    override fun getListCellRendererComponent(
        list: JList<out DasObject>?,
        value: DasObject?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (value != null) {
            text = value.name
        }
        return this
    }
}