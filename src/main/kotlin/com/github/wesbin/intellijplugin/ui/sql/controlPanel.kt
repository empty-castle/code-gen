package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.intellij.database.model.DasModel
import com.intellij.database.model.DasNamespace
import com.intellij.database.model.DasObject
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.toJB
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.containers.toArray
import java.awt.Component
import java.awt.event.ActionEvent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

@Suppress("UnstableApiUsage")
class ControlPanel(private val bindingProperties: BindingProperties, private val dasModel: DasModel): Observer {
    private lateinit var label: JLabel

    override fun update() {
        label.text = bindingProperties.text
    }

    fun generatePanel(): DialogPanel {
        lateinit var panel: DialogPanel

        val expandedDasModel = dasModel
            .traverser()
            .expand { dasObject -> dasObject is DasNamespace }

        val allTable = expandedDasModel
            .filter { dasObject -> dasObject.kind == ObjectKind.TABLE }

        val linkedSchemaList = mutableSetOf<DasObject>()
        allTable
            .forEach { dasObject ->
                val parentSchema = dasObject.dasParent
                if (parentSchema != null) {
                    println(parentSchema.name)
                    if (parentSchema.name == "SYSTEM") {
                        return@forEach
                    } else {
                        linkedSchemaList.add(parentSchema)
                    }
                }
            }

//        // 스키마 리스트
//        val schemaList = expandedDasModel
//            .filter { dasObject -> dasObject.kind == ObjectKind.SCHEMA }
//            .toJB()
//            .toArray(emptyArray())

        panel = panel {
            row("SCHEMA") {
                comboBox(linkedSchemaList.toArray(emptyArray()), CellRenderer())
            }

            row {
                textField()
                    .label("Please")
                    .bindText(bindingProperties::text)
                label = label(bindingProperties.text).component
            }

            row {
                button("Apply") { event: ActionEvent ->
                    panel.apply()
                }
            }
        }

        return panel
    }
}

class CellRenderer: JLabel(), ListCellRenderer<DasObject?> {
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