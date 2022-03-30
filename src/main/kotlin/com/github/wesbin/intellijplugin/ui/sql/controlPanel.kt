package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.intellij.openapi.Disposable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.Alarm
import java.awt.event.ActionEvent
import javax.swing.JLabel
import javax.swing.SwingUtilities

@Suppress("UnstableApiUsage")
fun controlPanel(bindingProperties: BindingProperties): DialogPanel {
    lateinit var panel: DialogPanel
    lateinit var label: JLabel

    panel = panel {
        row {
            textField()
                .label("Please")
                .bindText(bindingProperties::text)
            label = label(bindingProperties.text).component
        }
        row {
            button("Print") { event: ActionEvent ->
                panel.apply()
                label.text = bindingProperties.text
                println(bindingProperties.text)
            }
        }
    }

    return panel
}