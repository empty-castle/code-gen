package com.github.wesbin.intellijplugin.ui

import com.github.wesbin.intellijplugin.ui.panel.LeftPanel
import com.github.wesbin.intellijplugin.ui.panel.RightPanel
import com.github.wesbin.intellijplugin.ui.panel.TopPanel
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
        // 하단 좌우 분리
        val horizontalSplitter = JBSplitter(false, 0.2f).apply {
            // 좌 panel 장착
            firstComponent = LeftPanel().createPanel()
            // 우 panel 장착
            secondComponent = RightPanel().createPanel()
        }
        // 상단 하단 분리
        val verticalSplitter = JBSplitter(true, 0.1f).apply {
            minimumSize = Dimension(1000, 800)
            preferredSize = Dimension(1200, 800)
            // 상단 장착
            firstComponent = TopPanel(dataSources = DbPsiFacade.getInstance(project).dataSources).createPanel()
            // 하단 장착
            secondComponent = horizontalSplitter
        }
        return verticalSplitter
    }
}