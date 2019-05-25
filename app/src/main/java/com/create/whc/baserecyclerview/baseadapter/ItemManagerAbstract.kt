package com.create.whc.baserecyclerview.baseadapter

import android.support.v7.widget.RecyclerView

interface ItemManagerAbstract : MutableList<Item> {
    var observer: RecyclerView.Adapter<RecyclerView.ViewHolder>?
}