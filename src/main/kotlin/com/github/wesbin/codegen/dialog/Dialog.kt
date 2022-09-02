package com.github.wesbin.codegen.dialog

import com.github.wesbin.codegen.core.CodeGenModules
import com.github.wesbin.codegen.core.CodeGenerator
import com.github.wesbin.codegen.core.EntityCodeGenModules
import com.github.wesbin.codegen.core.ModelCodeGenModules
import com.github.wesbin.codegen.dialog.panel.*
import com.github.wesbin.codegen.util.FileUtil
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.rootManager
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiPackageImpl
import com.intellij.ui.JBSplitter
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel

@Suppress("UnstableApiUsage")
class Dialog(val project: Project, dialogTitle: String, val actionId: String) :
    DialogWrapper(
        project,
        false
    ) {

    val observableProperties = ObservableProperties(actionId)
    var codeGenModules: CodeGenModules? = when (actionId) {
        "entity" -> EntityCodeGenModules()
        "model" -> ModelCodeGenModules()
        else -> null
    }

    init {
        if (codeGenModules == null) {
            // todo custom Exception
            throw Exception()
        }
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
                    secondComponent = RightPanel(observableProperties, codeGenModules!!)
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
        // 우측 패널 내용 적용
        observableProperties.rightPanel.apply()

        if (observableProperties.checkValues()) {
            // 선택한 모듈 URL
            val selectedModuleUrl =
                observableProperties.modulesComboBox!!.selectedModule!!.rootManager.contentRoots[0].presentableUrl
            // 선택한 폴더
            val psiDirectory: PsiDirectory? =
                PsiPackageImpl(PsiManager.getInstance(project), observableProperties.packageComboBox!!.text)
                    .directories.find { it.virtualFile.presentableUrl.startsWith(selectedModuleUrl) }

            if (psiDirectory != null) {
                // todo CodeGenerator module 사용으로 전환
                FileUtil.create(
                    project,
                    title = observableProperties.className,
                    text = CodeGenerator(actionId).generate(observableProperties),
                    psiDirectory
                )
            } else {
                // todo action
            }
        } else {
            // todo action
        }

        super.doOKAction()
    }
}