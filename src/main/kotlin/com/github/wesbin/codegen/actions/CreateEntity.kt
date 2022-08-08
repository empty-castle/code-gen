package com.github.wesbin.codegen.actions

import com.github.wesbin.codegen.dialog.Dialog
import com.github.wesbin.codegen.backup.Old_Observer
import com.github.wesbin.codegen.backup.sql.ControlPanel
import com.github.wesbin.codegen.backup.sql.ResultPanel
import com.intellij.database.model.DasModel
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.ui.JBSplitter
import java.awt.Dimension
import javax.swing.JComponent
import kotlin.properties.Delegates


lateinit var psiElement: PsiElement

class CreateEntity : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // fixme throw Exception 대신 CustomException 을 만들어서 활용하자
        val project = e.project ?: throw Exception("인텔리제이가 정상적으로 실행되지 않았습니다.")
        Dialog(project, templatePresentation.text).show()
//        UiDialog(project, templatePresentation.text).show()
    }
}

private class UiDialog(val project: Project, dialogTitle: String) :
    DialogWrapper(project, null, true, IdeModalityType.MODELESS, false) {

    val model: DasModel

    init {
        title = dialogTitle
        model = DbPsiFacade.getInstance(project).dataSources[0].model
        init()
    }

    override fun createCenterPanel(): JComponent {

        val jbSplitter = JBSplitter(false, 0.5f)
        jbSplitter.minimumSize = Dimension(800, 600)
        jbSplitter.preferredSize = Dimension(1000, 800)

        val observerList = mutableListOf<Old_Observer>()
        val bindingProperties = BindingProperties(observerList)

        val resultPanel = ResultPanel(bindingProperties = bindingProperties)
        observerList.add(resultPanel)
        jbSplitter.secondComponent = resultPanel.generatePanel()

        val controlPanel = ControlPanel(
            bindingProperties = bindingProperties,
            dasModel = model,
            project = project
        )
//        observerList.add(controlPanel)
        jbSplitter.firstComponent = controlPanel.generatePanel()


        return jbSplitter;
    }
}

class BindingProperties(private val observerList: List<Old_Observer>) {
    // 스키마
    var schema: String by Delegates.observable("") { property, oldValue, newValue ->
        if (oldValue != newValue) {
            observerList.forEach(Old_Observer::update)
        }
    }

    // 테이블
    var table: String by Delegates.observable("") { property, oldValue, newValue ->
        if (oldValue != newValue) {
            observerList.forEach(Old_Observer::update)
        }
    }

    // 컬럼
    var columns: MutableList<String> = mutableListOf()

    fun changeTrigger() {
        observerList.forEach(Old_Observer::update)
    }
}
