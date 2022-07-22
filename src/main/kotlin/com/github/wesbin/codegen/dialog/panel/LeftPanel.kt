package com.github.wesbin.codegen.dialog.panel

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
import kotlin.reflect.KProperty

@Suppress("UnstableApiUsage")
class LeftPanel(private val observableProperties: ObservableProperties): Panel, Observer {

    private val items: MutableList<DasObject> = mutableListOf()

    private lateinit var panel: DialogPanel
    private lateinit var checkBoxList: CheckBoxList<DasObject>

    override fun createPanel(): DialogPanel {

        checkBoxList = CheckBoxList<DasObject>()
            .apply {
                setItems(items) { param: DasObject -> param.name }
                selectionMode = ListSelectionModel.SINGLE_SELECTION
                setCheckBoxListListener { index, value ->
                    // fixme too high
                    if (value) {
                        // 기존 선택 초기화
                        for (i in 0 until itemsCount) {
                            if (i == index) {
                                continue
                            }
                            if (isItemSelected(i)) {
                                setItemSelected(getItemAt(i), false)
                                break
                            }
                        }
                        observableProperties.selectedTable = this.getItemAt(index)
                    } else {
                        observableProperties.selectedTable = null
                    }
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

    override fun update(property: KProperty<*>, newValue: Any?) {
        // fixme println remove
        println("LeftPanel update")
        if (property.name == "selectedDbDataSource") {
            val dasObjects: MutableList<DasObject> = analyzeDbDataSource(newValue as DbDataSource?)
            checkBoxList.clear()
            checkBoxList.setItems(dasObjects) { param: DasObject -> param.name }
        }
    }

    // todo oracle, mariadb 는 특성이 조금 다르다. SYS, SYSTEM 을 제외하는건 oracle 기준
    private fun analyzeDbDataSource(dbDataSource: DbDataSource?): MutableList<DasObject> {
        val result: MutableList<DasObject> = mutableListOf()
        if (dbDataSource != null) {
            val dasObjects = dbDataSource.model.traverser()
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