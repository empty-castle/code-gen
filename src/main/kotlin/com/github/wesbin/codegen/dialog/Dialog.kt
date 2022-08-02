package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.panel.*
import com.github.wesbin.codegen.dialog.util.FileUtil
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.JBSplitter
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTabbedPane
import com.intellij.util.ui.components.JBComponent
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.LayoutManager
import java.awt.LayoutManager2
import javax.swing.JComponent
import javax.swing.JPanel

@Suppress("UnstableApiUsage")
class Dialog(val project: Project, dialogTitle: String):
    DialogWrapper(
        project,
        false
    ) {

    private val observableProperties = ObservableProperties()

    init {
        title = dialogTitle
        setOKButtonText("OK")
        setCancelButtonText("Cancel")
        init()
    }

    override fun createCenterPanel() = JPanel(BorderLayout())
        .apply {
            minimumSize = Dimension(1000, 800)
            preferredSize = Dimension(1200, 800)
            // 하단 좌우 분리
            val horizontalSplitter = JBSplitter(false, 0.2f)
                .apply {
                    // 좌 panel
                    firstComponent = LeftPanel(observableProperties)
                        .apply {
                            observableProperties.leftPanel = this
                        }
                        .createPanel()
                    // 우 panel
                    secondComponent = RightPanel(observableProperties)
                        .apply {
                            observableProperties.rightPanel = this
                        }
                        .createPanel()
                }
            add(
                TopPanel(
                    project,
                    DbPsiFacade.getInstance(project).dataSources,
                    observableProperties
                ).createPanel(),
                BorderLayout.NORTH
            )
            add(horizontalSplitter)
        }

    override fun doOKAction() {

        observableProperties.rightPanel.apply()

        FileUtil.create(
            project,
            title = "Title",
            text = CodeGen.genEntity(observableProperties),
            path = observableProperties.selectedSourceRoot?.text ?: ""
        )
        super.doOKAction()
    }

}