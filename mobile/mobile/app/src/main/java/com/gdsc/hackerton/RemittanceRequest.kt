package com.gdsc.hackerton

data class RemittanceRequest(
    var email:String
)
object RemittanceRequestManager {
    private var data: RemittanceRequest? = null
    var email: String = ""


    fun initData(request: RemittanceRequest) {
        data = request
    }

    fun getInstance(): RemittanceRequest {
        if (data == null) {
            data = RemittanceRequest("")
        }
        return data!!
    }
}