package com.albedo.testproject1.ui.filter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albedo.testproject1.R
import com.albedo.testproject1.data.models.ServiceUIState
import com.albedo.testproject1.databinding.ServiceItemListDefaultBinding
import com.albedo.testproject1.databinding.ServiceItemListSelectedBinding
import javax.inject.Inject

class ServiceAdapter @Inject constructor(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TAG = "ServiceAdapter"

    private var data : List<ServiceUIState> = listOf()
    private var listSelectedItems : MutableList<ServiceUIState> = mutableListOf()

    lateinit var onClickListener: OnClickListener



    inner class ItemDefaultViewHolder(private var binding: ServiceItemListDefaultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemModel: ServiceUIState) {

            binding.containerMainServiceItemListDefault.setOnClickListener {
                onClickListener.onClick(itemModel)
                if (listSelectedItems.contains(itemModel)){
                    listSelectedItems.remove(itemModel)
                }else{
                    listSelectedItems.add(itemModel)
                }
                notifyDataSetChanged()
            }

            if(itemModel in listSelectedItems){
                binding.containerMain3ServiceItemListDefault.setCardBackgroundColor(context.getColor(R.color.blue30))
                binding.containerMain2ServiceItemListDefault.setCardBackgroundColor(context.getColor(R.color.blue70))
                binding.textView2MainServiceItemListDefault.setBackgroundColor(context.getColor(R.color.blue80))

                binding.textView1MainServiceItemListDefault.text = "${itemModel.name} Service"
                binding.textView2MainServiceItemListDefault.text = "${itemModel.number}"
                binding.textView3MainServiceItemListDefault.text = "Количество пинов: ${itemModel.quantityPins}"
            }else{
                binding.containerMain3ServiceItemListDefault.setCardBackgroundColor(context.getColor(R.color.gray30))
                binding.containerMain2ServiceItemListDefault.setCardBackgroundColor(context.getColor(R.color.gray70))
                binding.textView2MainServiceItemListDefault.setBackgroundColor(context.getColor(R.color.gray80))

                binding.textView1MainServiceItemListDefault.text = "${itemModel.name} Service"
                binding.textView2MainServiceItemListDefault.text = "${itemModel.number}"
                binding.textView3MainServiceItemListDefault.text = "Количество пинов: ${itemModel.quantityPins}"
            }
        }
    }

//    inner class ItemSelectedViewHolder(private var binding: ServiceItemListSelectedBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(itemModel: ServiceUIState) {
//            binding.containerMainServiceItemListSelected.setOnClickListener {
//                onClickListener.onClick(itemModel)
//                if (listSelectedItems.contains(itemModel)){
//                    listSelectedItems.remove(itemModel)
//                }else{
//                    listSelectedItems.add(itemModel)
//                }
//            }
//            binding.textView1MainServiceItemListSelected.text = "${itemModel.name} Service"
//            binding.textView2MainServiceItemListSelected.text = "${itemModel.number}"
//            binding.textView3MainServiceItemListSelected.text = "Количество пинов: ${itemModel.quantityPins}"
//
//        }
//    }

//    override fun getItemViewType(position: Int): Int {
////        return when {
////            (data[position] in listSelectedItems) -> 0
////            (data[position] !in listSelectedItems) -> 1
////            else -> -1
////        }
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)

        return ItemDefaultViewHolder(ServiceItemListDefaultBinding.inflate(itemView, parent, false))
//        return when (viewType) {
//            0 -> ItemDefaultViewHolder(
//                ServiceItemListDefaultBinding.inflate(itemView, parent, false)
//            )
//            1 -> ItemSelectedViewHolder(
//                ServiceItemListSelectedBinding.inflate(itemView, parent, false)
//            )
//            else -> throw IllegalArgumentException("Invalid view type")
//        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            (holder as ItemDefaultViewHolder).bind(data[position])
//        when  {
//            (data[position] in listSelectedItems) -> (holder as ItemSelectedViewHolder).bind(data[position])
//            (data[position] !in listSelectedItems) -> (holder as ItemDefaultViewHolder).bind(data[position])
//        }
    }

    override fun getItemId(position: Int) = data[position].id.toLong()

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList : List<ServiceUIState>){
        Log.d(TAG, "newList $newList")
        data = newList
        listSelectedItems = mutableListOf()
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(itemModel: ServiceUIState)
    }
}