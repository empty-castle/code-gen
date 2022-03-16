package com.github.wesbin.intellijplugin.services

import com.intellij.openapi.project.Project
import com.github.wesbin.intellijplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
