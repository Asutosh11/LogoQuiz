package com.asutosh.phonepe.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.asutosh.phonepe.R

class JumbledLetersRvAdapter(var context: Context) : RecyclerView.Adapter<JumbledLetersViewHolder>() {
    
    lateinit var tvLetterBox: TextView
    var letterClickedLiveData : MutableLiveData<String>? = null
    var correctCompanyName : String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JumbledLetersViewHolder {
        var viewItem = LayoutInflater.from(context).inflate(R.layout.rv_item_jumbled_letters, parent, false)
        return JumbledLetersViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: JumbledLetersViewHolder, position: Int) {
        tvLetterBox = holder.itemView.findViewById(R.id.tv_letter)
        tvLetterBox.text = correctCompanyName.get(holder.adapterPosition).toString()

        tvLetterBox.setOnClickListener {
            letterClickedLiveData?.postValue(tvLetterBox.text.toString())
        }
    }

    override fun getItemCount(): Int {
        if(correctCompanyName != null){
            return correctCompanyName?.length!!
        }
        else{
            return 0
        }
    }

    fun setData(correctCompanyName: String){
        this.correctCompanyName = correctCompanyName
    }
}

class JumbledLetersViewHolder(view: View) : RecyclerView.ViewHolder(view)