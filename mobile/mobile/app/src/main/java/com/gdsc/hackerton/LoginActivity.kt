package com.gdsc.hackerton

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gdsc.hackerton.databinding.ActivityLoginBinding
import com.gdsc.hackerton.retrofit.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener({
//            val email:String=binding.memberEmail.text.toString()
//            val password:String=binding.memberPassword.text.toString()
//            performLogin(email, password)

            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

    }


    fun performLogin(email : String, password : String) {

        var retrofitAPI = RetrofitClient.getClient()
        retrofitAPI.login(LoginRequest(email,password)).enqueue(object :
            Callback<AccessTokenResponse> {
            override fun onResponse(call: Call<AccessTokenResponse>, response: Response<AccessTokenResponse>) {
                if (response.isSuccessful) {
                    val accessToken : AccessTokenResponse.AccessToken = response.body()?.data!!
                    Log.d("my_tag", "ResponseBody $response");
                    if (accessToken != null) {
                        AccessTokenManager.init(accessToken);
                        retrofitAPI.login(LoginRequest(email,password))
                        Log.d("my_tag",accessToken.toString())
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("enroll",false);
                        intent.putExtra("destination", "com.gdsc.hackerton.MainActivity");
                        startActivity(intent)
                        finish()
                    } else {
                        nullLoginAlertDialog()
                    }
                } else {
                    loginFailedAlertDialog(response.toString())
                }
            }

            override fun onFailure(call: Call<AccessTokenResponse>, t: Throwable) {
                showNetworkLoginAlertDialog()
            }

        })


    }

    private fun loginFailedAlertDialog(message : String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage(message)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun nullLoginAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage("이메일 또는 비밀번호를 입력하세요")


        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun showNetworkLoginAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("로그인 오류")
        alertDialogBuilder.setMessage("네트워크 오류 발생")

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}