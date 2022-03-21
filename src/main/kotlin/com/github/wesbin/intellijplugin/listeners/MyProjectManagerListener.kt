package com.github.wesbin.intellijplugin.listeners

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.github.wesbin.intellijplugin.services.MyProjectService
import com.intellij.icons.AllIcons
import com.intellij.openapi.ui.Messages

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.service<MyProjectService>()
    }
}
