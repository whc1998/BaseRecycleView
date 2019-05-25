package com.create.whc.baserecyclerview.baseadapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class ItemAdapter(val itemManager: ItemManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), MutableList<Item> by itemManager {

    init {
        itemManager.observer = this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ItemManager.getController(viewType).onCreateViewHolder(parent)

    override fun getItemCount() = itemManager.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            itemManager[position].controller.onBindViewHolder(holder, itemManager[position])

    override fun getItemViewType(position: Int) = ItemManager.getViewType(itemManager[position].controller)
}

fun RecyclerView.withItems(items: List<Item>) {
    adapter = ItemAdapter(ItemManager(items.toMutableList()))
}

fun RecyclerView.withItems(init: MutableList<Item>.() -> Unit) = withItems(mutableListOf<Item>().apply(init))

fun RecyclerView.getItemManager()=(adapter as ItemAdapter).itemManager