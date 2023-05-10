package com.app.medoxnetwork.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.medoxnetwork.R
import com.app.medoxnetwork.databinding.ListTeamNameBinding
import com.app.medoxnetwork.databinding.ListTotalTeamBinding
import com.app.medoxnetwork.model.ModelTotalTeam


class TotalTeamAdapter(
    val context: Context,val list:ArrayList<ModelTotalTeam.Result.Data>) :
    RecyclerView.Adapter<TotalTeamAdapter.ItemView>() {
    lateinit var onClickItem: OnClickItem

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemView {
        val v = ListTotalTeamBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ItemView(v)
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {

        holder.binding.level.text= "Level ".plus(list[position].level.toString())
        holder.binding.total.text= "Members ".plus(list[position].player.size)

        if (list[position].player.size>0)
        {

            for (item in list[position].player) {
                val itemview = ListTeamNameBinding.inflate(LayoutInflater.from(context), null,false)
                itemview.name.text=item.name
                itemview.username.text=item.username
                itemview.refrence.text=item.reference
                itemview.staking.text=item.staking.toString()
                holder.binding.llview.addView(itemview.root)
            }
        }

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

    class ItemView(itemView: ListTotalTeamBinding) :
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