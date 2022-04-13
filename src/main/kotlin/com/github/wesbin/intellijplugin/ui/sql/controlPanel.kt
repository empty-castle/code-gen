package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.intellij.database.model.DasModel
import com.intellij.database.model.DasNamespace
import com.intellij.database.model.DasObject
import com.intellij.database.model.ObjectKind
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.sql.change
import com.intellij.ui.dsl.builder.bindItem
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.containers.toArray
import java.awt.Component
import java.awt.event.ActionEvent
import javax.swing.*

@Suppress("UnstableApiUsage")
class ControlPanel(private val bindingProperties: BindingProperties, private val dasModel: DasModel) {
    private lateinit var panel: DialogPanel
    private lateinit var schemaLabel: JLabel
    private lateinit var tableLabel: JLabel
    private lateinit var tableCombobox: ComboBox<DasObject>
    private val groupedTable: MutableMap<String, MutableList<DasObject>> = mutableMapOf()


    private fun apply() {
        panel.apply()

        schemaLabel.text = bindingProperties.schema
        tableLabel.text = bindingProperties.table

    //        tableCombobox.item.getDasChildren(ObjectKind.COLUMN)
    //        tableCombobox.item.getDasChildren(ObjectKind.COLUMN)[0].name

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
                    .component
//                    .addActionListener {
//                        println(it.actionCommand)
//                    }
//                    .addActionListener { println("addActionListener") } // 값을 선택할 때 호출된다. 하지만 같은 값이라도 호출
//                    .onApply { println("onApply") } // panel.Apply 로 모습이 바뀔 때
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
                    apply()
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