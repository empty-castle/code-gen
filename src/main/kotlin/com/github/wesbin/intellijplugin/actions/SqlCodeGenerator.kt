package com.github.wesbin.intellijplugin.actions

import com.github.wesbin.intellijplugin.ui.Tab
import com.github.wesbin.intellijplugin.ui.sql.*
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import java.awt.Dimension
import javax.swing.JComponent
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

val TABS = arrayOf(
    ::tabCreate,
    ::tabRead,
    ::tabUpdate,
    ::tabDelete
)

lateinit var psiElement: PsiElement

class SqlCodeGenerator : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        psiElement = CommonDataKeys.PSI_ELEMENT.getData(e.dataContext) ?: throw Exception("psiElement is null")
        UiDialog(e.project, templatePresentation.text).show()
    }
}

private class UiDialog(val project: Project?, dialogTitle: String) :
    DialogWrapper(project, null, true, IdeModalityType.MODELESS, false) {

    init {
        title = dialogTitle
        init()
    }

    override fun createCenterPanel(): JComponent {
        val result = JBTabbedPane()
        result.minimumSize = Dimension(800, 600)
        result.preferredSize = Dimension(1000, 800)

        for (tab in TABS) {
            addTab(tab, result)
        }

        return result
    }

    private fun addTab(tab: KFunction<DialogPanel>, tabbedPane: JBTabbedPane) {
        val annotation = tab.findAnnotation<Tab>() ?: throw Exception("Tab annotation is missed for ${tab.name}")

        val content = panel {

            val args = tab.parameters.associateBy(
                { it },
                {
                    when (it.name) {
                        "psiElement" -> psiElement
                        else -> null
                    }
                }
            )

            val dialogPanel = tab.callBy(args)
            row {
                cell(dialogPanel)
                    .horizontalAlign(HorizontalAlign.FILL)
                    .resizableColumn()
            }
        }

        tabbedPane.add(annotation.title, content)
    }
}