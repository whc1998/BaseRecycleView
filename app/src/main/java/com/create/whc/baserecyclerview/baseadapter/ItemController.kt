package com.create.whc.baserecyclerview.baseadapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface ItemController {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Item)
}