package com.github.wesbin.codegen.dialog.util

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

object FileUtil {

    fun create(project: Project, title: String, text: String, path: String) {

        ApplicationManager.getApplication().runWriteAction {

            val psiFile: PsiFile? = Language.findLanguageByID("kotlin")
                ?.let {
                    PsiFileFactory.getInstance(project)
                        .createFileFromText("$title.kt", it, text)
                }

            val psiDirectory: PsiDirectory? = LocalFileSystem.getInstance().findFileByPath(path)?.let {
                PsiManager.getInstance(project).findDirectory(it)
            }

//            if (psiDirectory != null && psiFile != null) {
//                psiDirectory.children
//                    .find { (it as PsiFileBase).name == psiFile.name }
//                    ?: psiDirectory.add(psiFile)
//            }
        }
    }
}