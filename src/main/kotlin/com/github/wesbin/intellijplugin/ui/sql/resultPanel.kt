package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign

@Suppress("UnstableApiUsage")
class ResultPanel(private val bindingProperties: BindingProperties): Observer {
    private lateinit var textArea: JBTextArea

    override fun update() {

        // panel.Apply() 실행하면 여기로 들어온다
        textArea.text = "SELECT * FROM ${bindingProperties.schema}.${bindingProperties.table}"
    }

    fun generatePanel(): DialogPanel {

        return panel {
            row {
//                EditorTextField("EditorTextField")
//                    .editor
                textArea = textArea()
                    .rows(50)
                    .horizontalAlign(HorizontalAlign.FILL)
                    .text(bindingProperties.schema + bindingProperties.table)
                    .component
            }
        }
    }
}