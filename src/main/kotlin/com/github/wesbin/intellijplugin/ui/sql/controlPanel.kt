package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.intellij.database.model.DasModel
import com.intellij.database.model.DasNamespace
import com.intellij.database.model.DasObject
import com.intellij.database.model.ObjectKind
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.containers.toArray
import java.awt.Component
import java.awt.event.ActionEvent
import javax.swing.*

@Suppress("UnstableApiUsage")
class ControlPanel(private val bindingProperties: BindingProperties, private val dasModel: DasModel): Observer {
    private lateinit var schemaLabel: JLabel
    private lateinit var tableLabel: JLabel
    private lateinit var tableCombobox: ComboBox<DasObject>
    private val groupedTable: MutableMap<String, MutableList<DasObject>> = mutableMapOf()

    override fun update() {
        schemaLabel.text = bindingProperties.schema
        tableLabel.text = bindingProperties.table

        val comboBoxModel = tableCombobox.model
        if (comboBoxModel.size > 0) {
            (comboBoxModel as DefaultComboBoxModel).removeAllElements()
        }
        val tableList = groupedTable[bindingProperties.schema]
        if (!tableList.isNullOrEmpty()) {
            tableList
                .forEach { dasObject -> (comboBoxModel as MutableComboBoxModel).addElement(dasObject) }
        }
    }

    fun generatePanel(): DialogPanel {
        lateinit var panel: DialogPanel

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

//        // 스키마 리스트
//        val schemaList = expandedDasModel
//            .filter { dasObject -> dasObject.kind == ObjectKind.SCHEMA }
//            .toJB()
//            .toArray(emptyArray())

//        val filteredTable = allTable
//            .filter { dasObject ->
//                val dasParent = dasObject.dasParent
//                if (dasParent != null)
//                    dasObject.name == bindingProperties.schema
//                else
//                    false
//            }
//            .filter { dasObject -> dasObject.dasParent.name == bindingProperties.schema }

        panel = panel {
            row("SCHEMA") {
                comboBox(linkedSchemaList.toArray(emptyArray()))
                    .bindItem(bindingProperties::schema)
            }

            row("TABLE") {
                tableCombobox = comboBox(emptyArray<DasObject>(), DasObjectCellRenderer())
                    .bindItem({ null }, { dasObject ->
                        if (dasObject != null) {
                            bindingProperties.table = dasObject.name
                        }
                    })
                    .component
            }

            row("COLUMNS") {
                
            }

            row {
                button("Apply") { event: ActionEvent ->
                    panel.apply()
                }
            }

            separator("TESTING")

            row {
//                textField()
//                    .label("Please")
//                    .bindText(bindingProperties::schema)
                schemaLabel = label(bindingProperties.schema).component
                tableLabel = label(bindingProperties.table).component
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