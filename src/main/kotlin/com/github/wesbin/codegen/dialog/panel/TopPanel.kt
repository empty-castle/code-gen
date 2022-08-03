package com.github.wesbin.codegen.dialog.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessModuleDir
import com.intellij.openapi.project.rootManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.dsl.builder.*
import java.awt.Component
import java.awt.event.ItemEvent
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

@Suppress("UnstableApiUsage")
class TopPanel(
    private val project: Project,
    private val dataSources: List<DbDataSource>,
    private var observableProperties: ObservableProperties
    ): Panel {

    override fun createPanel(): DialogPanel {

        lateinit var tableCombobox: ComboBox<DbDataSource>

        return panel {
            row("DB connection:") {
                tableCombobox = comboBox(
                    dataSources.toTypedArray(),
                    object: JLabel(), ListCellRenderer<DbDataSource?> {
                        override fun getListCellRendererComponent(
                            list: JList<out DbDataSource>?,
                            value: DbDataSource?,
                            index: Int,
                            isSelected: Boolean,
                            cellHasFocus: Boolean
                        ): Component {
                            if (value != null) {
                                text = value.name
                            }
                            return this
                        }
                    }
                )
                    .apply {
                        columns(COLUMNS_LARGE)
                        // todo 의미가 있는가?
                        bindItem(
                            { this.component.item },
                            { dbDataSource -> observableProperties.selectedDbDataSource = dbDataSource }
                        )
                    }
                    .component
                    .apply {
                        addItemListener {
                            if (it.stateChange == ItemEvent.SELECTED) {
                                observableProperties.selectedDbDataSource = it.item as DbDataSource
                            }
                        }
                    }
            }

            // tableCombobox 초기 선택 observableProperties 반영
            if (tableCombobox.itemCount > 0) {
                observableProperties.selectedDbDataSource = tableCombobox.item
            }

            row("Source root:") {
                comboBox(
                    findSourceRootUrl(),
                    object : JLabel(), ListCellRenderer<Pair<String, String>?> {
                        override fun getListCellRendererComponent(
                            list: JList<out Pair<String, String>?>?,
                            value: Pair<String, String>?,
                            index: Int,
                            isSelected: Boolean,
                            cellHasFocus: Boolean
                        ): Component {
                            if (value != null) {
                                text = "[ ${value.first} ] ${value.second}"
                            }
                            return this
                        }
                    }
                )
            }

            row("Entity package") {
                observableProperties.selectedPackage =
                    textFieldWithBrowseButton(fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor())
                        .columns(COLUMNS_LARGE)
//                        .text(findSourceRootUrl())
                        .component
            }
        }
    }

    // 프로젝트 소스 루트 URL 찾기
    private fun findSourceRootUrl(): Array<Pair<String, String>> {
        val moduleManager: ModuleManager = ModuleManager.getInstance(project)
        val sourceRoots = mutableListOf<Pair<String, String>>()

        var currentParentModule: Module? = null
        moduleManager.modules.forEach { module: Module ->
            if (module.rootManager.sourceRoots.isEmpty()) {
                currentParentModule = module
                return@forEach
            }
            module.rootManager.sourceRoots.forEach { virtualFile: VirtualFile ->
                val url = currentParentModule?.let { module: Module ->
                    module.guessModuleDir()?.let { parentModuleVFile: VirtualFile ->
                        virtualFile.presentableUrl.replace(parentModuleVFile.presentableUrl, "")
                    }
                } ?: virtualFile.presentableUrl

                sourceRoots.add(Pair(module.name, url))
            }
        }
        return sourceRoots.toTypedArray()
    }
}