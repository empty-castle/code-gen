package com.github.wesbin.intellijplugin.actions

import com.github.wesbin.intellijplugin.ui.Observer
import com.github.wesbin.intellijplugin.ui.sql.*
import com.intellij.database.model.DasModel
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.ui.JBSplitter
import java.awt.Dimension
import javax.swing.JComponent
import kotlin.properties.Delegates


lateinit var psiElement: PsiElement

class SqlCodeGenerator : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {

//        val dbDataSource = DbPsiFacade.getInstance(e.project as Project).dataSources[0]
//        val model = dbDataSource.model

//        model.traverser().expand { dasObject -> dasObject is DasNamespace }
//            .filter { dasObject -> DasUtil.getSchema(dasObject) == "DENALL" }.toJB()[1]


//        SCHEMA
//        model.traverser().expand { dasObject -> dasObject is DasNamespace }
//            .filter { dasObject -> dasObject.kind == ObjectKind.SCHEMA }.toList()



//        DbPsiFacade.getInstance(e.project as Project).dataSources
//        DbPsiFacade.getInstance(e.project as Project).dataSources[0].model.traverser()
//            .filter { dasObject -> dasObject.kind == ObjectKind.SCHEMA }
//            .forEach { dasObject: DasObject? -> dasObject.name }

//        psiElement = CommonDataKeys.PSI_ELEMENT.getData(e.dataContext) ?: throw Exception("psiElement is null")

        val project = e.project ?: throw Exception("아마도 인텔리제이가 정상적으로 실행되지 않았습니다.")
        UiDialog(project, templatePresentation.text).show()
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

        val observerList = mutableListOf<Observer>()
        val bindingProperties = BindingProperties(observerList)

        val controlPanel = ControlPanel(bindingProperties = bindingProperties, dasModel = model)
        observerList.add(controlPanel)
        jbSplitter.firstComponent = controlPanel.generatePanel()

        val resultPanel = ResultPanel(bindingProperties = bindingProperties)
        observerList.add(resultPanel)
        jbSplitter.secondComponent = resultPanel.generatePanel()

        return jbSplitter;
    }
}

class BindingProperties(observerList: List<Observer>) {
    var text: String by Delegates.observable("") { property, oldValue, newValue ->
        observerList.forEach(Observer::update)
    }
}
