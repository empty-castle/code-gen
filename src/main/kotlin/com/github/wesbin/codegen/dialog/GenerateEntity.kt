package com.github.wesbin.codegen.dialog

import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

class GenerateEntity(val project: Project) {

    fun create() {
        ApplicationManager.getApplication().runWriteAction {
            val psiFile: PsiFile? = Language.findLanguageByID("kotlin")?.let {
                 PsiFileFactory.getInstance(project)
                    .createFileFromText("TEST.kt", it, "entity create test")
            }

            val findDirectory: PsiDirectory? = project.guessProjectDir()?.let {
                PsiManager.getInstance(project).findDirectory(it)
            }

            if (findDirectory != null) {
                psiFile?.let {
                    findDirectory.add(it)
                }
            }
        }
    }
}