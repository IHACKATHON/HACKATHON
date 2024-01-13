import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.allforyou.app.retrofit.ApiRespond
import com.bumptech.glide.Glide
import com.gdsc.hackerton.AccessTokenManager
import com.gdsc.hackerton.ApiService
import com.gdsc.hackerton.R
import com.gdsc.hackerton.RetrofitClient
import com.gdsc.hackerton.databinding.ActivityStoreDetailBinding
import com.gdsc.hackerton.databinding.ItemMenuBinding
import com.gdsc.hackerton.retrofit.StoreDetail
import com.gdsc.hackerton.retrofit.menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

import java.util.LinkedList

class StoreDetailActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var binding: ActivityStoreDetailBinding
    private lateinit var basket: MutableList<menu>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = RetrofitClient.getClient()


        val storeId = intent.getLongExtra("storeId", -1)

        if (storeId != -1L) {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response: Response<ApiRespond<StoreDetail>> = apiService.getStoreDetail(
                        AccessTokenManager.getBearer(),
                        storeId
                    )

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        Log.d("Store Detail", apiResponse.toString())

                        // Update the UI with the store detail data
                        val storeDetail = apiResponse?.data
                        storeDetail?.let {
                            updateUI(storeDetail)
                        }
                    } else {
                        Toast.makeText(
                            this@StoreDetailActivity,
                            "네트워크 오류",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            // Handle the case where storeId is not provided
            finish()
        }
    }


    private fun updateUI(storeDetail: StoreDetail) {
        // Dynamically add menu items to the LinearLayout
        val menuContainer = binding.menuContainer
        menuContainer.removeAllViews() // Clear existing views

        for (menu in storeDetail.menus) {
            // Inflate the item_menu layout using data binding
            val menuItemBinding: ItemMenuBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_menu,
                null,
                false
            ) as ItemMenuBinding


            // Set menu details to the inflated view
            menuItemBinding.menuName.text = menu.name
            menuItemBinding.menuPrice.text = menu.price.toString()


            Glide.with(this).load(menu.menuImage).into(menuItemBinding.menuImage);

            // Handle button click
            menuItemBinding.menuAdd.setOnClickListener {
                basket.add(menu)
                Toast.makeText(
                    this@StoreDetailActivity,
                    "${menu.name} added to basket",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Add the inflated view to the menu container
            menuContainer.addView(menuItemBinding.root)
        }
    }

}
