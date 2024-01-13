package com.gdsc.hackerton

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.allforyou.app.retrofit.ApiRespond
import com.gdsc.hackerton.databinding.ActivityPayBinding
import com.gdsc.hackerton.databinding.ActivitySearchBinding
import com.gdsc.hackerton.databinding.ItemStoreBinding
import com.gdsc.hackerton.retrofit.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var apiService:ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService=RetrofitClient.getClient()

        //기본 가게 리스트 조회
        GlobalScope.launch(Dispatchers.Main){

            try{
                val response: Response<ApiRespond<List<Store>>> = apiService.getStores(AccessTokenManager.getBearer(),"")

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("전체 가게 리스트 조회",apiResponse.toString())

                    // Check the code and handle the response accordingly
                    when (apiResponse?.code) {
                        200 -> {
                            // Access the LinearLayout
                            val storesLayout = binding.stores

                            // Iterate through the response data and create/store views for each store
                            for (store in apiResponse.data) {
                                val storeItemBinding = ItemStoreBinding.inflate(layoutInflater)
                                val storeLayout = storeItemBinding.root

                                // Access the TextViews and set their text based on store data
                                storeItemBinding.name.text = store.name
                                storeItemBinding.category.text = store.category
                                storeItemBinding.address.text = store.address
                                storeItemBinding.opening.text = store.opening

                                // Add the store layout to the storesLayout
                                storesLayout.addView(storeLayout)
                            }
                        }
                        // Handle other response codes if needed
                        else -> {
                            Toast.makeText(this@SearchActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                        }
                    }
                } else {
                    Toast.makeText(this@SearchActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                }
            }
            catch(e:Exception){
                e.printStackTrace()
            }

        }



        binding.btnSearch.setOnClickListener{
            val content=binding.content.text.toString()

            //검색 결과 반환
            GlobalScope.launch(Dispatchers.Main){

                try{
                    val response: Response<ApiRespond<List<Store>>> = apiService.getStores(AccessTokenManager.getBearer(),content)

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("전체 가게 리스트 조회",apiResponse.toString())

                        // Check the code and handle the response accordingly
                        when (apiResponse?.code) {
                            200 -> {
                                // Access the LinearLayout
                                val storesLayout = binding.stores

                                // Iterate through the response data and create/store views for each store
                                for (store in apiResponse.data) {
                                    val storeItemBinding = ItemStoreBinding.inflate(layoutInflater)
                                    val storeLayout = storeItemBinding.root

                                    // Access the TextViews and set their text based on store data
                                    storeItemBinding.storeId.text=store.storeId.toString()
                                    storeItemBinding.name.text = store.name
                                    storeItemBinding.category.text = store.category
                                    storeItemBinding.address.text = store.address
                                    storeItemBinding.opening.text = store.opening

                                    storeItemBinding.seeDetail.setOnClickListener {
                                        // Handle the click event, navigate to StoreDetailActivity with the storeId
                                        val storeId = store.storeId // Assuming your Store class has a property "id"
                                        navigateToStoreDetailActivity(storeId)
                                    }

                                    // Add the store layout to the storesLayout
                                    storesLayout.addView(storeLayout)
                                }
                            }
                            // Handle other response codes if needed
                            else -> {
                                Toast.makeText(this@SearchActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                            }
                        }
                    } else {
                        Toast.makeText(this@SearchActivity, "네트워크 오류", Toast.LENGTH_SHORT)
                    }
                }
                catch(e:Exception){
                    e.printStackTrace()
                }

            }


        }
    }

    private fun navigateToStoreDetailActivity(storeId: Long) {
        val intent = Intent(this, StoreDetailActivity::class.java)
        intent.putExtra("storeId", storeId)
        startActivity(intent)
    }
}