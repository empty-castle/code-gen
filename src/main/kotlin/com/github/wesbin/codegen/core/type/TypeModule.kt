package com.github.wesbin.codegen.core.type

interface TypeModule {

    /*
    *
    * java.math.BigDecimal => import + 마지막 String
    * String
    *
    * */

    fun mappingTypeData(): ColumnType
}