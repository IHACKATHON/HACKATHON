package com.gdsc.hackerton

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.allforyou.app.retrofit.ApiRespond
import com.gdsc.hackerton.databinding.ActivityLoginBinding
import com.gdsc.hackerton.databinding.ActivityMainBinding
import com.gdsc.hackerton.retrofit.GetAccountResponse
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService:ApiService

    private val CAMERA_PERMISSION_REQUEST_CODE = 1001

    override fun onResume() {
        super.onResume()
        // 여기에 IntentIntegrator 초기화 코드를 추가
    }

    override fun onPause() {
        super.onPause()
        // 여기에 IntentIntegrator 해제 코드를 추가
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService=RetrofitClient.getClient()


        //잔액 조회
        GlobalScope.launch(Dispatchers.Main){

            try{
                val response:Response<ApiRespond<GetAccountResponse>> = apiService.getAccountBalance(AccessTokenManager.getBearer())

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
                            Toast.makeText(this@MainActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                }
            }
            catch(e:Exception){
                e.printStackTrace()
            }

        }

        binding.btnFind.setOnClickListener{
            val intent= Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        binding.btnReservation.setOnClickListener{
            val intent= Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.btnPay.setOnClickListener{


            val integrator=IntentIntegrator(this)

            Log.d("앍","버튼눌림!!")
            with(integrator){
                setBeepEnabled(false)
                //스캔화면으로 이동할 activity 설정
                captureActivity=ResultActivity::class.java

                // 스캔 화면으로 이동할 activity 설정
                captureActivity = ResultActivity::class.java

                //초기화
                initiateScan()
            }


//            val intent= Intent(this, PayActivity::class.java)
//            startActivity(intent)
        }

    }

    //바코드를 인식하고 난 후의 로직
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val scanRes = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
           //인식한 url을 가져옴
            val content = scanRes.contents

            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()

            val intent=Intent(this@MainActivity, PayActivity::class.java)
            startActivity(intent)


        } else Toast.makeText(this, "인식 실패", Toast.LENGTH_SHORT).show()
    }
}