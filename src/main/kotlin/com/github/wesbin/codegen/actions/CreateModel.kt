package com.github.wesbin.codegen.actions

import com.github.wesbin.codegen.dialog.Dialog
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl
import com.intellij.openapi.project.DumbAwareAction

class CreateModel : DumbAwareAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // fixme throw Exception 대신 CustomException 을 만들어서 활용하자
        val project = e.project ?: throw Exception("인텔리제이가 정상적으로 실행되지 않았습니다.")
        Dialog(
            project,
            templatePresentation.text,
            (e.actionManager as ActionManagerImpl).lastPreformedActionId
        ).show()
    }
}