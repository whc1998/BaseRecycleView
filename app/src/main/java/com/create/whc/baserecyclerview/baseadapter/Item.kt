package com.create.whc.baserecyclerview.baseadapter

interface Item {
    val controller: ItemController

    fun areItemsTheSame(newItem: Item,oldItem: Item): Boolean {
        return newItem.hashCode()==oldItem.hashCode()
    }

    fun areContentsTheSame(newItem: Item,oldItem: Item): Boolean {
        return newItem.hashCode()==oldItem.hashCode()
    }
}