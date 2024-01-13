package com.gdsc.hackerton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.retrofit.ApiRespond
import com.gdsc.hackerton.databinding.ActivityMainBinding
import com.gdsc.hackerton.databinding.ActivityPayBinding
import com.gdsc.hackerton.retrofit.GetAccountResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class PayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayBinding
    private lateinit var apiService:ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService=RetrofitClient.getClient()

        //잔액조회
        GlobalScope.launch(Dispatchers.Main){

            try{
                val price:Int=binding.price.text.toString().toInt();
                val response: Response<ApiRespond<GetAccountResponse>> = apiService.getAccountBalance(AccessTokenManager.getBearer())

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("잔액조회 결과",apiResponse.toString()+'원')

                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Success, handle the list of access applications
                            val balance = apiResponse.data.balance

                            binding.balance.text=balance.toString()
                        }
                        // Handle other response codes if needed
                        else -> {
                            Toast.makeText(this@PayActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                        }
                    }
                } else {
                    Toast.makeText(this@PayActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                }
            }
            catch(e:Exception){
                e.printStackTrace()
            }

        }


        binding.btnPay.setOnClickListener{
            //결제 진행
            //아래 코드 수정 필요

//            GlobalScope.launch(Dispatchers.Main){
//
//                try{
//                    val price:Int=binding.price.text.toString().toInt();
//                    val response: Response<ApiRespond<GetAccountResponse>> = apiService.getAccountBalance(AccessTokenManager.getBearer())
//
//                    if (response.isSuccessful) {
//                        val apiResponse = response.body()
//                        Log.d("잔액조회 결과",apiResponse.toString()+'원')
//
//                        // Check the code and handle the response accordingly
//                        when (apiResponse?.code) {
//                            200 -> {
//                                // Success, handle the list of access applications
//                                val balance = apiResponse.data.balance
//
//                                binding.balance.text=balance.toString()
//                            }
//                            // Handle other response codes if needed
//                            else -> {
//                                Toast.makeText(this@PayActivity, "네트워크 오류", Toast.LENGTH_SHORT)
//                            }
//                        }
//                    } else {
//                        Toast.makeText(this@PayActivity, "네트워크 오류", Toast.LENGTH_SHORT)
//                    }
//                }
//                catch(e:Exception){
//                    e.printStackTrace()
//                }
//
//            }

        }
    }
}