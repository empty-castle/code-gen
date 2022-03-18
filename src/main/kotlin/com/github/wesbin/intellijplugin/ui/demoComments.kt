package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.text

@Demo(title = "Comments", description = "Comments")
fun demoComments(): DialogPanel {
    return panel {
        row("Row1:") {
            textField()
                .comment("Comment to textField1")
                .text("textField1")
        }
    }
}