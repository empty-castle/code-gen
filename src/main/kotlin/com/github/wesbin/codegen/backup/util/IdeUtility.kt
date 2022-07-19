package com.github.wesbin.codegen.utility

import com.intellij.application.options.colors.ColorAndFontOptions
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.impl.EditorImpl

fun createEditor(text: String): EditorImpl {

    val scheme = EditorColorsManager.getInstance().globalScheme
    val options = ColorAndFontOptions()
    options.reset()
    options.selectScheme(scheme.name)

    val editorFactory = EditorFactory.getInstance()
    val document = editorFactory.createDocument(text)
    val editor = editorFactory.createEditor(document) as EditorEx
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

    return editor as EditorImpl
}