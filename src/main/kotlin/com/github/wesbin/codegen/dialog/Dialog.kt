package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.dialog.panel.LeftPanel
import com.github.wesbin.codegen.dialog.panel.ObservableProperties
import com.github.wesbin.codegen.dialog.panel.RightPanel
import com.github.wesbin.codegen.dialog.panel.TopPanel
import com.github.wesbin.codegen.dialog.util.FileUtil
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
        false
    ) {

    private val observableProperties = ObservableProperties()

    init {
        title = dialogTitle
        setOKButtonText("OK")
        setCancelButtonText("Cancel")
        init()
    }


    // todo JBSplitter 대신 createNorthPanel 사용해서 Dialog 구성
    override fun createCenterPanel(): JComponent {
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
                    project,
                    DbPsiFacade.getInstance(project).dataSources,
                    observableProperties
                ).createPanel()
                // 하단
                secondComponent = horizontalSplitter
                setHonorComponentsMinimumSize(false)
            }
        return verticalSplitter
    }

    override fun doOKAction() {

        FileUtil.create(
            project,
            title = "Title",
            text = CodeGen.genEntity(observableProperties),
            path = observableProperties.selectedSourceRoot?.text ?: ""
        )
        super.doOKAction()
    }

    //    companion object {
//        val observableProperties = ObservableProperties()
//    }
//
//    override fun createNorthPanel(): JComponent = TopPanel(
//        project,
//        DbPsiFacade.getInstance(project).dataSources,
//        observableProperties
//    )
//        .createPanel()
//
//    override fun createCenterPanel(): JComponent = JBSplitter(false, 0.2f)
//        .apply {
//            preferredSize = Dimension(1200, 800)
//            // 좌 panel
//            firstComponent = LeftPanel(observableProperties)
//                .apply {
//                    observableProperties.leftPanel = this
//                }
//                .createPanel()
//            // 우 panel
//            secondComponent = RightPanel(observableProperties)
//                .apply {
//                    observableProperties.rightPanel = this
//                }
//                .createPanel()
//        }

}