package com.gdsc.hackerton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.allforyou.app.retrofit.ApiRespond
import com.gdsc.hackerton.databinding.ActivityLoginBinding
import com.gdsc.hackerton.databinding.ActivitySignupBinding
import com.gdsc.hackerton.retrofit.JoinResponse
import com.gdsc.hackerton.retrofit.SignupRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var apiService:ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService=RetrofitClient.getClient()

        binding.btnSignup.setOnClickListener({
            val memberName:String=binding.memberName.text.toString()
            val bank:String=binding.memberBank.text.toString()
            val accountNumber:String=binding.memberAccountNumber.text.toString()
            val email:String=binding.memberEmail.text.toString()
            val password:String=binding.memberPassword.text.toString()

            lifecycleScope.launch {
                performSignup(email, password, memberName, bank, accountNumber)
            }
        })


    }




    suspend fun performSignup(email: String, password: String, memberName: String, bank: String, accountNumber: String) {
        try {
            // Create a SignupRequest object
            val signupRequest = SignupRequest(email, password, memberName, bank, accountNumber)

            // Call the signup method in ApiService with the SignupRequest object using coroutines
            val response: Response<ApiRespond<JoinResponse>> = apiService.signup(signupRequest)

            // Check if the response is successful
            if (response.isSuccessful) {
                val apiRespond: ApiRespond<JoinResponse>? = response.body()
                Toast.makeText(this@SignupActivity, "회원가입이 성공적으로 완료되었습니다", Toast.LENGTH_SHORT).show()

                val intent =Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val errorBody = response.errorBody()?.string()
                Toast.makeText(this@SignupActivity, "회원가입에 실패했습니다. 다시 시도하세요", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            // Handle exceptions
            println("오류 발생: ${e.message}")
        }
    }
}