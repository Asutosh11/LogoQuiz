package com.asutosh.phonepe.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asutosh.phonepe.R
import com.asutosh.phonepe.base.BaseActivity
import com.asutosh.phonepe.data.LogoAndName
import com.bumptech.glide.Glide

class MainActivity : BaseActivity() {

    private val viewModel: MainVM by viewModels()
    private lateinit var jumbledLettersRv: RecyclerView
    private lateinit var logoImv: ImageView
    private var currentRandomLogoPosition : Int = 0
    private lateinit var jumbledLetersRvAdapter: JumbledLetersRvAdapter
    private var wordGuesedByUser: String = ""
    private var currentLogoName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jumbledLettersRv = findViewById(R.id.rv_jumbled_letters) as RecyclerView
        logoImv = findViewById(R.id.imv_logo) as ImageView
        jumbledLetersRvAdapter = JumbledLetersRvAdapter(this)
        jumbledLettersRv.layoutManager = GridLayoutManager(this, 2)
        jumbledLettersRv.adapter = jumbledLetersRvAdapter

        initUI()
    }

    private fun initUI() {
        val logosJsonInString = viewModel.loadJsonFromAssets("logos.json", applicationContext.assets)
        val logosJsonLength = viewModel.getLengthOfLogosJson(logosJsonInString)
        selectRandomLogoToShow(logosJsonLength, logosJsonInString, jumbledLetersRvAdapter)
    }

    private fun selectRandomLogoToShow(jsonLength: Int, logosJsonInString: String, jumbledLetersRvAdapter: JumbledLetersRvAdapter) {
        wordGuesedByUser = ""
        val randomIndex = (0 until jsonLength).random()
        currentRandomLogoPosition = randomIndex
        val randomLogoData = viewModel.getRandomLogoDataFromJson(logosJsonInString, randomIndex)

        if(randomLogoData != null){
            currentLogoName = randomLogoData.name
            drawUI(randomLogoData, jumbledLetersRvAdapter)
        }
    }

    private fun drawUI(randomLogoData: LogoAndName, jumbledLetersRvAdapter: JumbledLetersRvAdapter) {
        jumbledLetersRvAdapter.setData(randomLogoData.name)
        Glide.with(this).load(randomLogoData.logo).into(logoImv)
        observeLiveDataForLetterSelection(jumbledLetersRvAdapter)
        jumbledLetersRvAdapter.notifyDataSetChanged()
    }

    private fun observeLiveDataForLetterSelection(jumbledLetersRvAdapter: JumbledLetersRvAdapter) {
        jumbledLetersRvAdapter.letterClickedLiveData?.observe(this, Observer {
            wordGuesedByUser = wordGuesedByUser + it
            if(wordGuesedByUser.equals(currentLogoName)){
                Toast.makeText(applicationContext, getString(R.string.logo_guessed_success_message), LENGTH_LONG).show()
                initUI()
            }
        })
    }

}