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

        val schemaList = dasModel
            .traverser()
            .expand { dasObject -> dasObject is DasNamespace }
            .filter { dasObject -> dasObject.kind == ObjectKind.SCHEMA }
            .toJB()
            .toArray(emptyArray())

        // 현재 테이블
//        dasModel.traverser()
//            .filter { dasObject -> dasObject.kind == ObjectKind.TABLE && dasObject.dasParent.name == "DENALL" }.toList()

        panel = panel {
            row("SCHEMA") {
                comboBox(schemaList, CellRenderer())
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