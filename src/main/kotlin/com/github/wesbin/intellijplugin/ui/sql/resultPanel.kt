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

            var text = """"""
            bindingProperties.columns.forEach { column ->
                text += """|$column"""
            }

            application.runWriteAction {
                editor.document.setText(
                    """ |SELECT $text
                        |FROM   ${bindingProperties.schema}.${bindingProperties.table}
                    """
                        .trimMargin()
                )
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
//                textArea = textArea()
//                    .rows(50)
//                    .horizontalAlign(HorizontalAlign.FILL)
//                    .text(bindingProperties.schema + bindingProperties.table)
//                    .component
            }
        }
    }
}