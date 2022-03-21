package com.github.wesbin.intellijplugin

import com.github.wesbin.intellijplugin.ui.*
import com.github.wesbin.intellijplugin.ui.Demo
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.rootManager
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import com.intellij.ui.dsl.builder.BottomGap
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import com.intellij.util.ui.JBEmptyBorder
import java.awt.Dimension
import javax.swing.JComponent
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaMethod

const val BASE_URL = "https://github.com/JetBrains/intellij-community/blob/master/platform/platform-impl/"
val DEMOS = arrayOf(
    ::demoBasics,
    ::demoRowLayout,
    ::demoComponentLabels,
    ::demoComments,
    ::demoComponents,
    ::demoGaps,
    ::demoGroups,
    ::demoAvailability,
    ::demoTips
)

class UiTest : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        UiTestDialog(e.project, templatePresentation.text).show()
    }
}

private class UiTestDialog(val project: Project?, dialogTitle: String) :
        DialogWrapper(project, null, true, IdeModalityType.MODELESS, false) {

    init {
        title = dialogTitle
        init()
    }

    override fun createCenterPanel(): JComponent? {
        var result = JBTabbedPane()
        result.minimumSize = Dimension(400, 300)
        result.preferredSize = Dimension(800, 600)

        for (demo in DEMOS) {
            addDemo(demo, result)
        }

        return result
    }

    private fun addDemo(demo: KFunction<DialogPanel>, tabbedPane: JBTabbedPane) {
        val annotation = demo.findAnnotation<Demo>() ?: throw Exception("Demo annotation is missed for ${demo.name}")

        val content = panel {

            row {
                label("<html>Description: ${annotation.description}")
            }

            val simpleName = "src/${demo.javaMethod!!.declaringClass.name}".replace('.', '/')
            val fileName = (simpleName.substring(0..simpleName.length - 3) + ".kt")
            row {
                link("View source") {
                    if (!openInIdeProject(fileName)) {
                        BrowserUtil.browse(BASE_URL + fileName)
                    }
                }
            }.bottomGap(BottomGap.MEDIUM)

            val args = demo.parameters.associateBy(
                { it },
                {
                    when (it.name) {
                        "parentDisposable" -> myDisposable
                        else -> null
                    }
                }
            )

            val dialogPanel = demo.callBy(args)
            if (annotation.scrollbar) {
                row {
                    dialogPanel.border = JBEmptyBorder(10)
                    cell(dialogPanel, JBScrollPane(dialogPanel))
                        .horizontalAlign(HorizontalAlign.FILL)
                        .verticalAlign(VerticalAlign.FILL)
                        .resizableColumn()
                }.resizableRow()
            } else {
                row {
                    cell(dialogPanel)
                        .horizontalAlign(HorizontalAlign.FILL)
                        .resizableColumn()
                }
            }
        }

        tabbedPane.add(annotation.title, content)
    }

    private fun openInIdeProject(fileName: String): Boolean {

        if (project == null) {
            return false
        }
        val moduleManager = ModuleManager.getInstance(project)
        val module = moduleManager.findModuleByName("intellij.platform.ide.impl") ?: return false

        for (contentRoot in module.rootManager.contentRoots) {
            val file = contentRoot.findFileByRelativePath(fileName)
            if (file?.isValid == true) {
                OpenFileDescriptor(project, file).navigate(true)
                return true
            }
        }
        return false
    }
}