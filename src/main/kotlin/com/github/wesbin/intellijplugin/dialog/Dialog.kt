package com.github.wesbin.intellijplugin.dialog

import com.github.wesbin.intellijplugin.dialog.panel.LeftPanel
import com.github.wesbin.intellijplugin.dialog.panel.ObservableProperties
import com.github.wesbin.intellijplugin.dialog.panel.RightPanel
import com.github.wesbin.intellijplugin.dialog.panel.TopPanel
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.JBSplitter
import java.awt.Dimension
import javax.swing.JComponent

@Suppress("UnstableApiUsage")
class Dialog(val project: Project, dialogTitle: String):
    DialogWrapper(
        project,
        null,
        true,
        IdeModalityType.MODELESS,
        false
    ) {

    init {
        title = dialogTitle
        init()
    }

    override fun createCenterPanel(): JComponent {

        val observableProperties = ObservableProperties()
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
        // 상하단 분리
        val verticalSplitter = JBSplitter(true, 0.1f)
            .apply {
                minimumSize = Dimension(1000, 800)
                preferredSize = Dimension(1200, 800)
                // 상단
                firstComponent = TopPanel(
                    DbPsiFacade.getInstance(project).dataSources,
                    observableProperties
                ).createPanel()
                // 하단
                secondComponent = horizontalSplitter
                setHonorComponentsMinimumSize(false)
            }
        return verticalSplitter
    }
}