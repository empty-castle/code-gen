package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import com.intellij.ui.layout.PropertyBinding

fun resultPanel(bindingProperties: BindingProperties): DialogPanel {

    return panel {
        row {
            textArea()
                .rows(50)
                .horizontalAlign(HorizontalAlign.FILL)
                .text(bindingProperties.text)
        }
    }
}