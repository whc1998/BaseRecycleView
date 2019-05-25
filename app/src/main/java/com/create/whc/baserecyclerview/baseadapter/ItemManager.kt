package com.create.whc.baserecyclerview.baseadapter

import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.support.v7.widget.RecyclerView

class ItemManager(private val delegated: MutableList<Item> = mutableListOf()) : ItemManagerAbstract {
    override var observer: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    val itemListSnapshot: List<Item> get() = delegated

    init {
        ensureControllers(delegated)
    }

    companion object ItemControllerManager {
        private var viewType = 0

        private val c2vt = mutableMapOf<ItemController, Int>()

        private val vt2c = mutableMapOf<Int, ItemController>()

        private fun ensureController(item: Item) {
            val controller = item.controller
            if (!c2vt.contains(controller)) {
                c2vt[controller] = viewType
                vt2c[viewType] = controller
                viewType++
            }
        }

        private fun ensureControllers(items: Collection<Item>): Unit =
                items.distinctBy(Item::controller).forEach(ItemControllerManager::ensureController)

        fun getViewType(controller: ItemController): Int = c2vt[controller]
                ?: throw IllegalStateException("ItemController $controller is not ensured")

        fun getController(viewType: Int): ItemController = vt2c[viewType]
                ?: throw IllegalStateException("ItemController $viewType is unused")
    }


    override val size: Int get() = delegated.size

    override fun contains(element: Item) = delegated.contains(element)

    override fun containsAll(elements: Collection<Item>) = delegated.containsAll(elements)

    override fun get(index: Int): Item = delegated[index]

    override fun indexOf(element: Item) = delegated.indexOf(element)

    override fun isEmpty() = delegated.isEmpty()

    override fun iterator() = delegated.iterator()

    override fun lastIndexOf(element: Item) = delegated.lastIndexOf(element)

    override fun listIterator() = delegated.listIterator()

    override fun listIterator(index: Int) = delegated.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int) = delegated.subList(fromIndex, toIndex)

    override fun add(element: Item) =
            delegated.add(element).also {
                ensureController(element)
                if (it) observer?.notifyItemInserted(size)
            }

    override fun add(index: Int, element: Item) =
            delegated.add(index, element).also {
                ensureController(element)
                observer?.notifyItemInserted(index)
            }

    override fun addAll(index: Int, elements: Collection<Item>) =
            delegated.addAll(elements).also {
                ensureControllers(elements)
                if (it) observer?.notifyItemRangeInserted(index, elements.size)
            }

    override fun addAll(elements: Collection<Item>) =
            delegated.addAll(elements).also {
                ensureControllers(elements)
                if (it) observer?.notifyItemRangeInserted(size, elements.size)
            }

    override fun clear() =
            delegated.clear().also {
                observer?.notifyItemRangeRemoved(0, size)
            }

    override fun remove(element: Item): Boolean =
            delegated.remove(element).also {
                if (it) observer?.notifyDataSetChanged()
            }

    override fun removeAll(elements: Collection<Item>): Boolean =
            delegated.removeAll(elements).also {
                if (it) observer?.notifyDataSetChanged()
            }

    override fun removeAt(index: Int) =
            delegated.removeAt(index).also {
                observer?.notifyItemRemoved(index)
            }

    override fun retainAll(elements: Collection<Item>) =
            delegated.retainAll(elements).also {
                if (it) observer?.notifyDataSetChanged()
            }

    override fun set(index: Int, element: Item) =
            delegated.set(index, element).also {
                ensureController(element)
                observer?.notifyItemChanged(index)
            }

    fun refreshAll(elements: List<Item>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = delegated[oldItemPosition]
                val newItem = elements[newItemPosition]
                return oldItem.areItemsTheSame(newItem,oldItem)
            }

            override fun getOldListSize(): Int = delegated.size
            override fun getNewListSize(): Int = elements.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = delegated[oldItemPosition]
                val newItem = elements[newItemPosition]
                return oldItem.areContentsTheSame(newItem,oldItem)
            }
        }
        val result = DiffUtil.calculateDiff(diffCallback, true)
        delegated.clear()
        delegated.addAll(elements)
        ensureControllers(elements)
        result.dispatchUpdatesTo(updatecallback)
    }

    fun refreshAll(init: MutableList<Item>.() -> Unit) = refreshAll(mutableListOf<Item>().apply(init))

    fun autoRefresh(init: MutableList<Item>.() -> Unit) {
        val snapshot = this.itemListSnapshot.toMutableList()
        snapshot.apply(init)
        refreshAll(snapshot)
    }

    private val updatecallback = object : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {
            observer?.notifyItemRangeChanged(position, count, payload)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            observer?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onInserted(position: Int, count: Int) {
            observer?.notifyItemRangeInserted(position, count)
        }

        override fun onRemoved(position: Int, count: Int) {
            observer?.notifyItemRangeRemoved(position, count)
        }
    }

}
