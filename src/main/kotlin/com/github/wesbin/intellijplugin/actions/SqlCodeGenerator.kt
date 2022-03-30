package com.github.wesbin.intellijplugin.actions

import com.github.wesbin.intellijplugin.ui.sql.controlPanel
import com.github.wesbin.intellijplugin.ui.sql.resultPanel
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.psi.PsiElement
import com.intellij.ui.JBSplitter
import java.awt.Dimension
import javax.swing.JComponent


lateinit var psiElement: PsiElement

class SqlCodeGenerator : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val dbDataSource = DbPsiFacade.getInstance(e.project as Project).dataSources[0]

        val model = dbDataSource.model

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
        UiDialog(e.project, templatePresentation.text).show()
    }
}

private class UiDialog(val project: Project?, dialogTitle: String) :
    DialogWrapper(project, null, true, IdeModalityType.MODELESS, false) {

    init {
        title = dialogTitle
        init()
    }

    override fun createCenterPanel(): JComponent {

        val jbSplitter = JBSplitter(false, 0.5f)

        jbSplitter.minimumSize = Dimension(800, 600)
        jbSplitter.preferredSize = Dimension(1000, 800)

        val bindingProperties = BindingProperties()

        jbSplitter.firstComponent = controlPanel(bindingProperties)
        jbSplitter.secondComponent = resultPanel(bindingProperties)

        return jbSplitter;
    }
}

data class BindingProperties (
    var text: String = "Test"
)