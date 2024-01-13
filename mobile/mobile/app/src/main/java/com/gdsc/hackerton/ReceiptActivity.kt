package com.gdsc.hackerton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdsc.hackerton.databinding.ActivityPayBinding
import com.gdsc.hackerton.databinding.ActivityReceiptBinding

class ReceiptActivity : AppCompatActivity() {

    private lateinit var binding:ActivityReceiptBinding
    private lateinit var apiService:ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService=RetrofitClient.getClient()


        //결제내역을 불러오는 api추가 필요



        binding.btnOkay.setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}