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

    // todo 초기 값 설정
    private val items: MutableList<DasObject> = mutableListOf()

    private lateinit var panel: DialogPanel
    private lateinit var checkBoxList: CheckBoxList<DasObject>

    override fun createPanel(): DialogPanel {

        checkBoxList = CheckBoxList<DasObject>()
            .apply {
                setItems(items) { param: DasObject -> param.name }
                selectionMode = ListSelectionModel.SINGLE_SELECTION
                setCheckBoxListListener { index, value ->
                    print("$index : $value")
                }
            }

        panel = panel {
            row {
                cell(JBScrollPane(checkBoxList))
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
        val dasObjects: MutableList<DasObject> = analyzeDbDataSource()
        checkBoxList.clear()
        checkBoxList.setItems(dasObjects) { param: DasObject -> param.name }
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