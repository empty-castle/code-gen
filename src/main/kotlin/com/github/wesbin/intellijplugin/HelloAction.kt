package com.github.wesbin.intellijplugin

import com.intellij.database.psi.DataSourceManager
import com.intellij.database.psi.DbPsiFacade
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.Messages

class HelloAction : DumbAwareAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT)
        Messages.showMessageDialog(project, "Hello from Kotlin!", "Greeting", Messages.getInformationIcon())

//        val project = event.project
//        if (project != null) {
//
//        }
    }
}