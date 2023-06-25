package com.app.medoxnetwork.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.medoxnetwork.databinding.ListWithdrawHistoryBinding
import com.app.medoxnetwork.model.ModelWithdrawHistory

class WithdrawHistoryAdapter(
    val context: Context, val list: ArrayList<ModelWithdrawHistory.Result?>
) :
    RecyclerView.Adapter<WithdrawHistoryAdapter.ItemView>() {
    lateinit var onClickItem: OnClickItem

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemView {
        val v = ListWithdrawHistoryBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ItemView(v)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {

        holder.binding.date.text=list[position]?.date
        holder.binding.amount.text=list[position]?.amount.toString()
        holder.binding.remark.text=list[position]?.remarks
    }


    override fun getItemCount(): Int {
       return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ItemView(itemView: ListWithdrawHistoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding = itemView
    }

    fun setOnClickListener(onClickItem: OnClickItem) {
        this.onClickItem = onClickItem
    }

    interface OnClickItem {
        fun clickItem(id: Int)
    }


}