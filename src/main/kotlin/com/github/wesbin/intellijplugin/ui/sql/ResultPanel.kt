package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.github.wesbin.intellijplugin.ui.utility.createEditor
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import java.awt.Dimension

@Suppress("UnstableApiUsage")
class ResultPanel(private val bindingProperties: BindingProperties): Observer {
    private lateinit var editor: EditorImpl

    override fun update() {
        if (bindingProperties.columns.size > 0) {
            val application = ApplicationManager.getApplication()

            val queryGen = QueryGen(
                bindingProperties.schema,
                bindingProperties.table,
                bindingProperties.columns
            )

            application.runWriteAction {
                editor.document.setText(queryGen.select())
            }
        }
    }

    fun generatePanel(): DialogPanel {

        editor = createEditor("")
        val editorPanel = editor.component
        editorPanel.preferredSize = Dimension(500, 800)

        return panel {
            row {
                cell(editorPanel)
                    .horizontalAlign(HorizontalAlign.FILL)
                    .verticalAlign(VerticalAlign.FILL)
            }
        }
    }
}