package com.create.whc.baserecyclerview.item

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.create.whc.baserecyclerview.baseadapter.Item
import com.create.whc.baserecyclerview.baseadapter.ItemController
import com.create.whc.baserecyclerview.R

class TextItem(private val content: String) : Item {

    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_single_text, parent, false)
            val textView = view.findViewById<TextView>(R.id.tv_single_text)
            return ViewHolder(view, textView)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {
            holder as ViewHolder
            item as TextItem
            holder.textView.text = item.content
        }

        private class ViewHolder(itemView: View?, val textView: TextView) : RecyclerView.ViewHolder(itemView)
    }

    override val controller: ItemController
        get() = Controller

    override fun areContentsTheSame(newItem: Item,oldItem: Item): Boolean {
        if (newItem is TextItem && oldItem is TextItem){
            return newItem.content==oldItem.content
        }else{
            return false
        }
    }

    override fun areItemsTheSame(newItem: Item,oldItem: Item): Boolean {
        return areContentsTheSame(newItem,oldItem)
    }

}
fun MutableList<Item>.singleText(content: String) = add(TextItem(content))