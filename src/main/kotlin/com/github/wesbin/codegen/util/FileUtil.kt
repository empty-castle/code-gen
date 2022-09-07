package com.github.wesbin.codegen.util

import  com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory

object FileUtil {

    fun create(project: Project, title: String, text: String, psiDirectory: PsiDirectory) {

        ApplicationManager.getApplication().runWriteAction {

            val psiFile: PsiFile? = Language.findLanguageByID("kotlin")
                ?.let {
                    PsiFileFactory.getInstance(project)
                        .createFileFromText(title, it, text)
                }

            if (psiFile != null) {
                psiDirectory.children
                    .find { (it as PsiFileBase).name == psiFile.name }
                    ?: psiDirectory.add(psiFile)
            }
        }
    }
}