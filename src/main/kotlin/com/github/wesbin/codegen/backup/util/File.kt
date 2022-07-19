package com.github.wesbin.codegen.backup.util

import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager

class File(val project: Project) {

    fun create() {
        // file create
        ApplicationManager.getApplication().runWriteAction {
            /*
            * Language.findLanguageByID("kotlin")
            * Language.findLanguageByID("JAVA")
            * */

            val psiFile = Language.findLanguageByID("kotlin")
                ?.let {
                    PsiFileFactory.getInstance(project)
                        .createFileFromText("TEST.kt", it, "data class test (val str: String)")
                }

            val directory = project.guessProjectDir()?.let { PsiManager.getInstance(project).findDirectory(it) }

            if (directory != null) {
                psiFile?.let { directory.add(it) }
            }
        }
    }
}