package com.github.wesbin.codegen.dialog.panel

import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.rootManager
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogPanel
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
                                // why? value.localDataSource?.url ?: ""
                                text = value.name
                            }
                            return this
                        }
                    }
                )
                    .apply {
                        columns(COLUMNS_LARGE)
                        // fixme 의미가 있는가?
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
                observableProperties.selectedSourceRoot =
                    textFieldWithBrowseButton(fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor(),)
                        .columns(COLUMNS_LARGE)
                        .text(findSourceRootUrl())
                        .component
            }
        }
    }

    // 프로젝트 소스 루트 URL 찾기
    private fun findSourceRootUrl(): String {
        var result: String = project.basePath ?: ""
        val moduleManager: ModuleManager = ModuleManager.getInstance(project)
        moduleManager.modules.forEach {
            if (it.name.endsWith("main") && it.rootManager.sourceRoots.isNotEmpty()) {
                result = it.rootManager.sourceRoots[0].presentableUrl
            }
        }
        return result
    }
}