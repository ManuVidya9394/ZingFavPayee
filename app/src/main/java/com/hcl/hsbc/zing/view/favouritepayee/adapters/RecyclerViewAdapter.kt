package com.hcl.hsbc.zing.view.favouritepayee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hcl.hsbc.zing.R
import com.hcl.hsbc.zing.model.favouritepayee.FavouritePayeeSummaryModel

class RecyclerViewAdapter(
    private val items: List<FavouritePayeeSummaryModel>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var filteredItems: List<FavouritePayeeSummaryModel> = items
    private var listeners: OnItemClickListener = onItemClickListener

    interface OnItemClickListener {
        fun onItemClick(item: FavouritePayeeSummaryModel?)

        fun editClick(item: FavouritePayeeSummaryModel?)

        fun deleteClick(item: FavouritePayeeSummaryModel?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favouritepayeeitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.account_no.text = filteredItems[position].accountNumber
        holder.name.text = filteredItems[position].payeeName
        holder.bank.text = filteredItems[position].bankName
        holder.root.setOnClickListener {
            listeners.onItemClick(filteredItems[position])
        }
        holder.edit.setOnClickListener {
            listeners.editClick(filteredItems[position])

        }
        holder.delete.setOnClickListener {
            listeners.deleteClick(filteredItems[position])

        }
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    fun getItems(): List<FavouritePayeeSummaryModel> {
        return items
    }

    fun filterList(filteredlist: ArrayList<FavouritePayeeSummaryModel>) {
        filteredItems = filteredlist
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.payee_name)
        val account_no: TextView = itemView.findViewById(R.id.account_no)
        val bank: TextView = itemView.findViewById(R.id.bank_name)
        val root: CardView = itemView.findViewById(R.id.root)
        val edit:TextView = itemView.findViewById(R.id.edit)
        val delete: TextView = itemView.findViewById(R.id.delete)
    }
}