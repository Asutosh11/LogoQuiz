package com.asutosh.phonepe.ui

import android.content.res.AssetManager
import com.asutosh.phonepe.base.BaseViewModel
import com.asutosh.phonepe.data.LogoAndName
import org.json.JSONArray

class MainVM : BaseViewModel() {

    // TODO: Need to write unit test cases for every function if time permits

    fun getRandomLogoDataFromJson(logosJson: String, position: Int) : LogoAndName? {

        if(logosJson != null) {
            val logosJsonArray = JSONArray(logosJson)

            if(logosJsonArray != null && logosJsonArray.length() > 0){
                val logoAndName = LogoAndName(
                    logosJsonArray.getJSONObject(position).optString("imgUrl"),
                    logosJsonArray.getJSONObject(position).optString("name")
                )
                return logoAndName
            }
            return null
        }
        return null
    }

    fun loadJsonFromAssets(fileName: String, assetManager: AssetManager): String{
        val jsonfile: String = assetManager.open(fileName).bufferedReader().use {it.readText()}
        return jsonfile
    }

    fun getLengthOfLogosJson(logosJson: String) : Int{

        if(logosJson != null) {
            val logosJsonArray = JSONArray(logosJson)

            if(logosJsonArray != null && logosJsonArray.length() > 0){
                return logosJsonArray.length()
            }
            else{
                return 0
            }
        }
        else{
            return 0
        }
    }

}