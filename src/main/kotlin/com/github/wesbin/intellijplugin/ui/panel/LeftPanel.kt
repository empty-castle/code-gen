package com.github.wesbin.intellijplugin.ui.panel

import com.intellij.database.model.DasObject
import com.intellij.database.model.ObjectKind
import com.intellij.database.psi.DbDataSource
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.CheckBoxList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.gridLayout.HorizontalAlign
import com.intellij.ui.dsl.gridLayout.VerticalAlign
import javax.swing.ListSelectionModel

@Suppress("UnstableApiUsage")
class LeftPanel(private val observableProperties: ObservableProperties): Panel, Observer {

    private lateinit var panel: DialogPanel
    private val items: MutableList<String> = mutableListOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K")

    override fun createPanel(): DialogPanel {

        panel = panel {
            row {
                cell(
                    JBScrollPane(
                        CheckBoxList<String>()
                            .apply {
                                setItems(items, null)
                                selectionMode = ListSelectionModel.SINGLE_SELECTION
                                setCheckBoxListListener { index, value ->
                                    print("$index : $value")
                                }
                            }
                    )
                )
                    .apply {
                        horizontalAlign(HorizontalAlign.FILL)
                        verticalAlign(VerticalAlign.FILL)
                    }
            }
                .resizableRow()
        }

        return panel
    }

    override fun update() {
        // todo dasObjects 를 바로 setItems 할 수 있는 방법
        val dasObjects: MutableList<DasObject> = analyzeDbDataSource()
        dasObjects.forEach { dasObject: DasObject -> items.add(dasObject.name) }
        panel.apply()
        panel.updateUI()
    }

    private fun analyzeDbDataSource(): MutableList<DasObject> {
        val result: MutableList<DasObject> = mutableListOf()
        val selectedDbDataSource: DbDataSource? = observableProperties.selectedDbDataSource
        if (selectedDbDataSource != null) {
            val dasObjects = selectedDbDataSource.model.traverser()
            dasObjects
                .filter { dasObject: DasObject? -> dasObject?.kind == ObjectKind.TABLE }
                .forEach { dasObject: DasObject? ->
                    if (dasObject == null) return@forEach
                    val dasParent: DasObject = dasObject.dasParent ?: return@forEach
                    if (dasParent.name == "SYS" || dasParent.name == "SYSTEM") {
                        return@forEach
                    } else {
                        result.add(dasObject)
                    }
                }
        }
        return result
    }
}