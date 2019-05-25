package com.create.whc.baserecyclerview.item

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.create.whc.baserecyclerview.R
import com.create.whc.baserecyclerview.baseadapter.Item
import com.create.whc.baserecyclerview.baseadapter.ItemController

class ImageItem : Item {

    override val controller: ItemController
        get() = Controller

    companion object Controller : ItemController {
        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_image, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item) {

        }

        private class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
    }

}

fun MutableList<Item>.imageItem() = add(ImageItem())