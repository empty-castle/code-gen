package com.github.wesbin.intellijplugin.actions

import com.intellij.database.psi.DbTable
import com.intellij.database.util.DasUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiElement

class BaseCodeGeneratorTemp: DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(PlatformDataKeys.PROJECT)

        val psiElement : PsiElement? = CommonDataKeys.PSI_ELEMENT.getData(e.dataContext)
        for (dasColumn in DasUtil.getColumns(psiElement as DbTable)) {
            println(dasColumn.name)
        }



//        while (dasColumns.hasNext()) {
//            val dasColumn = dasColumns.next()
//            println(dasColumn.name)
//        }

//        DasUtil.getColumns(psiElement as DbTable).get(1).name`
        //psiElement 는 우클릭한 테이블을 바라보네

//        DbTableImpl
//        (psiElement as DbTableImpl)

//        if (project != null) {
//            DbPsiFacade.getInstance(project).dataSources
//        }

        Messages.showMessageDialog(project, "Hello from Kotlin!", "Greeting", Messages.getInformationIcon())
    }
}