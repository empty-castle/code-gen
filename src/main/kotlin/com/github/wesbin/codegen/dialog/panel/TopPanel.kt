package com.github.wesbin.codegen.dialog.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
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
        lateinit var sourceRootComboBox: ComboBox<Pair<Module, String>>

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
                sourceRootComboBox = comboBox(
                    findSourceRootUrl(),
                    object : JLabel(), ListCellRenderer<Pair<Module, String>?> {
                        override fun getListCellRendererComponent(
                            list: JList<out Pair<Module, String>?>?,
                            value: Pair<Module, String>?,
                            index: Int,
                            isSelected: Boolean,
                            cellHasFocus: Boolean
                        ): Component {
                            if (value != null) {
                                text = "[ ${value.first.name} ] ${value.second}"
                            }
                            return this
                        }
                    }
                )
                    .component
                    .apply {
                        addItemListener {
                            if (it.stateChange == ItemEvent.SELECTED) {
                                observableProperties.selectedSourceRoot = it.item as Pair<Module, String>
                                observableProperties.selectedPackage?.text = (it.item as Pair<Module, String>).second
                            }
                        }
                    }
            }

            // todo PackageChooserDialog
            row("Entity package") {
                observableProperties.selectedPackage =
                    textFieldWithBrowseButton(fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor())
                        .columns(COLUMNS_LARGE)
                        .component
                        .apply {
                            text = sourceRootComboBox.item.second
                        }
            }
        }
    }

    // 프로젝트 소스 루트 URL 찾기
    private fun findSourceRootUrl(): Array<Pair<Module, String>> {
        val moduleManager: ModuleManager = ModuleManager.getInstance(project)
        val sourceRoots = mutableListOf<Pair<Module, String>>()

        moduleManager.modules.forEach { module: Module ->
            module.rootManager.sourceRoots.forEach { virtualFile: VirtualFile ->
                sourceRoots.add(Pair(module, virtualFile.presentableUrl))
            }
        }
        return sourceRoots.toTypedArray()
    }
}