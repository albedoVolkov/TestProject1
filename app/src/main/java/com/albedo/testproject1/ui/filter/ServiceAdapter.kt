package com.albedo.testproject1.ui.filter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.databinding.ServiceItemListBinding
import javax.inject.Inject

class ServiceAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<ServiceAdapter.ItemViewHolder>() {

    val TAG = "ServiceAdapter"

    private var data : List<ServiceUIState> = listOf()


    lateinit var onClickListener: OnClickListener

    inner class ItemViewHolder(private var binding: ServiceItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ServiceUIState) {
            binding.containerMainServiceItemList.setOnClickListener {
                onClickListener.onClick(itemModel)
            }
            binding.textView1MainServiceItemList.text = "${itemModel.name} Service"
            binding.textView2MainServiceItemList.text = "${itemModel.number}"
            binding.textView3MainServiceItemList.text = "Количество сервисов: ${itemModel.quantityPins}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        return ItemViewHolder(ServiceItemListBinding.inflate(itemView, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemId(position: Int) = data[position].id.toLong()

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList : List<ServiceUIState>){
        Log.d(TAG, "newList $newList")
        data = newList
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(itemModel: ServiceUIState)
    }
}