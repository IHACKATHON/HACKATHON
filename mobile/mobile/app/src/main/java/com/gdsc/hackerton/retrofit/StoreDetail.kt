package com.gdsc.hackerton.retrofit

data class StoreDetail(
    val storeId:Long,
    val name:String,
    val address :String,
    val category:String,
    val opening:String,
    val storeImage:String,
    val menus:List<menu>

)
