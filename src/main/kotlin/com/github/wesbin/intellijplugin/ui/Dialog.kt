package com.github.wesbin.intellijplugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.JBSplitter
import com.intellij.ui.dsl.builder.panel
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

        // 상단 하단 분리
        val verticalSplitter = JBSplitter(true, 0.15f)
        verticalSplitter.minimumSize = Dimension(1000, 800)
        verticalSplitter.preferredSize = Dimension(1200, 1000)

        // 상단
        val topPanel = panel {
            row("Top") {
                button("QWER") { _ ->
                    println("QWER Button click")
                }
            }
        }
//        topPanel.background = Color.BLUE
        verticalSplitter.firstComponent = topPanel

        // 하단 좌우 분리
        val horizontalSplitter = JBSplitter(false, 0.2f)
        // 좌 panel
        val leftPanel = panel {
            row("Left") {

            }
        }
//        leftPanel.background = Color.RED
        horizontalSplitter.firstComponent = leftPanel
        // 우 panel
        val rightPanel = panel {
            row("Right") {

            }
        }
//        rightPanel.background = Color.GREEN
        horizontalSplitter.secondComponent = rightPanel

        verticalSplitter.secondComponent = horizontalSplitter

        return verticalSplitter
    }
}