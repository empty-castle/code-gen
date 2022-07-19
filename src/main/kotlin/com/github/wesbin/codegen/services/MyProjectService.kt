package com.github.wesbin.codegen.services

import com.intellij.openapi.project.Project
import com.github.wesbin.codegen.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
