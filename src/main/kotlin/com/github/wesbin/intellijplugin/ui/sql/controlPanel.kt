package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import java.awt.event.ActionEvent
import javax.swing.JLabel

@Suppress("UnstableApiUsage")
class ControlPanel(private val bindingProperties: BindingProperties): Observer {
    private lateinit var label: JLabel

    override fun update() {
        label.text = bindingProperties.text
    }

    fun generatePanel(): DialogPanel {
        lateinit var panel: DialogPanel

        panel = panel {
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