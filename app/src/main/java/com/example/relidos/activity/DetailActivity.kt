package com.example.relidos.activity

import ManagmentCart
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.relidos.Adapter.ColorAdapter
import com.example.relidos.Adapter.SizeAdapter
import com.example.relidos.Adapter.SliderAdapter
import com.example.relidos.Model.ItemsModel
import com.example.relidos.Model.SliderModel
import com.example.relidos.R
import com.example.relidos.databinding.ActivityDetailBinding
import com.example.relidos.databinding.ViewholderSizeBinding
import java.util.ArrayList
import java.util.ResourceBundle
import java.util.ResourceBundle.getBundle

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item:ItemsModel
    private var number0rder=1
    private lateinit var managmentCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart=ManagmentCart(this)

        getBundle()
        banners()
        initLists()
    }

    private fun initLists() {
        val sizeList=ArrayList<String>()
        for(size in item.size){
            sizeList.add(size.toString())
        }

        binding.sizeList.adapter=SizeAdapter(sizeList)
        binding.sizeList.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList=ArrayList<String>()
        for (imagemUrl in item.picUrl){
            colorList.add(imagemUrl)
        }

        binding.colorList.adapter=ColorAdapter(colorList)
        binding.colorList.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun banners() {
        val sliderItems=ArrayList<SliderModel>()
        for (imageUrl in item.picUrl){
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.adapter=SliderAdapter(sliderItems, binding.slider)
        binding.slider.clipToPadding=true
        binding.slider.clipChildren=true
        binding.slider.offscreenPageLimit=1
        binding.slider.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER

        if (sliderItems.size>1){
            binding.dotIndicator.visibility= View.VISIBLE
            binding.dotIndicator.attachTo(binding.slider)
        }
    }

    private fun getBundle(){
        item=intent.getParcelableExtra("object")!!

        binding.titleText.text=item.title
        binding.descriptionText.text=item.description
        binding.priceText.text="R$"+item.price
        binding.ratingText.text="${item.rating} Rating"
        binding.addToCartBtn.setOnClickListener {
            item.numberInCart=number0rder
            managmentCart.insertFood(item)
        }
        binding.backBtn.setOnClickListener{
            finish()
        }
        binding.cartBtn.setOnClickListener{
            startActivity(Intent(this@DetailActivity,CartActivity::class.java))

        }
    }

}