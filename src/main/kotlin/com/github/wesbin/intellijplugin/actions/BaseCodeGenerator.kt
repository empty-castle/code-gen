package com.github.wesbin.intellijplugin.actions

import com.intellij.database.psi.DbTable
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElement

class BaseCodeGenerator: DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)

        var psiElement : PsiElement? = CommonDataKeys.PSI_ELEMENT.getData(e.dataContext)

//        DasUtil.getColumns(psiElement as DbTable).get(1).name

//        if (psiElement != null) {
//            psiElement
//        }

        //psiElement 는 우클릭한 테이블을 바라보네

//        DbTableImpl
//        (psiElement as DbTableImpl)

//        if (project != null) {
//            DbPsiFacade.getInstance(project).dataSources
//        }

        Messages.showMessageDialog(project, "Hello from Kotlin!", "Greeting", Messages.getInformationIcon())
    }
}