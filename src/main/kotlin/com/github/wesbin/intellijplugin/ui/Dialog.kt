package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.JBSplitter
import com.intellij.ui.dsl.builder.panel
import java.awt.Color
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

    override fun createCenterPanel(): JComponent? {

        val jbSplitter = JBSplitter(false, 0.5f)

        val firstPanel = panel {
            row {

            }
        }
        firstPanel.background = Color.BLUE
        jbSplitter.firstComponent = firstPanel

        val secondPanel = panel {
            row {

            }
        }
        secondPanel.background = Color.RED
        jbSplitter.secondComponent = secondPanel


        return jbSplitter
    }
}