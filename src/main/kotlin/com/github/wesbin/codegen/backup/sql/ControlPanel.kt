package com.github.wesbin.codegen.backup.sql

import com.github.wesbin.codegen.actions.BindingProperties
import com.intellij.database.model.DasModel
import com.intellij.database.model.DasObject
import com.intellij.database.model.ObjectKind
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.GridLayout
import com.intellij.util.containers.toArray
import com.jetbrains.rd.util.first
import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ItemEvent
import javax.swing.*

@Suppress("UnstableApiUsage")
class ControlPanel(
    private val bindingProperties: BindingProperties,
    private val dasModel: DasModel,
    private val project: Project
) {
    private lateinit var panel: DialogPanel

//    Combobox
    private lateinit var schemaCombobox: ComboBox<String>
    private lateinit var tableCombobox: ComboBox<DasObject>
    private lateinit var textFieldWithBrowseButton: Cell<TextFieldWithBrowseButton>

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
        val lastComponent = panel.components[panel.componentCount - 1]
        val constraints = (panel.layout as GridLayout).getConstraints(lastComponent as JComponent)
        if (constraints != null) {
            var y = constraints.y
            val tableColumns = tableCombobox.item.getDasChildren(ObjectKind.COLUMN)
            if (tableColumns.isNotEmpty) {
                bindingProperties.columns.clear()
                tableColumns.forEach { dasObject ->
                    bindingProperties.columns.add(dasObject.name)
                }
            }
        }
    }

//    무조건 마지막 row 에 있는 경우에만 동작할 수 있다.
    private fun clearColumns() {
        val componentIterator = panel.components.iterator()
        var isDelete = false
        while (componentIterator.hasNext()) {
            val component = componentIterator.next()
            if (component is JLabel && component.text == columnRowLabel) {
                isDelete = true
                continue
            }
            if (isDelete) {
                panel.remove(component)
            }
        }
        panel.revalidate()
        panel.repaint()
    }

    private fun createJBCheckBox(title: String, isSelected: Boolean, init: ActionEvent.() -> Unit): JBCheckBox {
        val jbCheckBox = JBCheckBox(title, isSelected)
        jbCheckBox.addActionListener(init)
        return jbCheckBox
    }

    fun generatePanel(): DialogPanel {

        val expandedDasModel = dasModel
            .traverser()
//            .expand { dasObject -> dasObject is DasNamespace }

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
//            SCHEMA panel
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
            }

//            TABLE panel
            row(tableRowLabel) {
//                [groupedTable.first()] 화면이 로딩될 때 combobox 의 특성으로 첫 번쨰 schema 가 선택된다.
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
                        clearColumns()
                        update(tableRowLabel)
                        panel.apply()
                    }
                }
            }

            row {
                textFieldWithBrowseButton = textFieldWithBrowseButton(fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor())
//                ControlPanel 실행과 동시에 파일 선택기가 동작한다.
//                FileChooser.chooseFiles(
//                    FileChooserDescriptorFactory.createSingleFolderDescriptor(),
//                    project,
//                    null,
//
//                )
            }

//            component1 이 열려있는 첫 번째 프로젝트를 말하는데 내가 원하는 프로젝트 찾는건 어떻게?
//            ProjectManager.getInstance().openProjects.component1().basePath
//            ProjectManager.getInstance().openProjects.component1().locationHash

            row {
                button("TEST") { event: ActionEvent ->
                    textFieldWithBrowseButton()
                    println("TEST Button Click")
                }
            }
        }


//        화면 로딩 조회를 위해 실행
        tableUpdate()
        panel.apply()

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