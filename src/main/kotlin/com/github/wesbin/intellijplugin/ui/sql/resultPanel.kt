package com.github.wesbin.intellijplugin.ui.sql

import com.github.wesbin.intellijplugin.actions.BindingProperties
import com.github.wesbin.intellijplugin.ui.Observer
import com.intellij.application.options.colors.ColorAndFontOptions
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.util.Disposer
import com.intellij.ui.components.JBTextArea
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import java.awt.Dimension
import javax.swing.JPanel

@Suppress("UnstableApiUsage")
class ResultPanel(private val bindingProperties: BindingProperties): Observer {
    private lateinit var textArea: JBTextArea

    override fun update() {

        // panel.Apply() 실행하면 여기로 들어온다
        textArea.text = "SELECT * FROM ${bindingProperties.schema}.${bindingProperties.table}"
    }

    fun generatePanel(): DialogPanel {

        val scheme = EditorColorsManager.getInstance().globalScheme
        val options = ColorAndFontOptions()
        options.reset()
        options.selectScheme(scheme.name)

        val editorFactory = EditorFactory.getInstance()
        val editorDocument: Document = editorFactory.createDocument("GOOD?")
        val editor = editorFactory.createEditor(editorDocument) as EditorEx
        editor.colorsScheme = scheme
        val settings = editor.settings
        settings.isLineNumbersShown = true
        settings.isWhitespacesShown = false
        settings.isLineMarkerAreaShown = false
        settings.isIndentGuidesShown = false
        settings.isFoldingOutlineShown = false
        settings.additionalColumnsCount = 0
        settings.additionalLinesCount = 0
        settings.isRightMarginShown = false

        val editorPanel = editor.component
        editorPanel.preferredSize = Dimension(500, 500)

//        Disposer.register(
//            disposable
//        ) {
//            EditorFactory.getInstance().releaseEditor(editor)
//        }

        return panel {
            row {
                cell(editorPanel)
                    .horizontalAlign(HorizontalAlign.FILL)
                    .verticalAlign(VerticalAlign.FILL)
//                EditorTextField("EditorTextField")
//                    .editor
//                textArea = textArea()
//                    .rows(50)
//                    .horizontalAlign(HorizontalAlign.FILL)
//                    .text(bindingProperties.schema + bindingProperties.table)
//                    .component
            }
        }
    }
}