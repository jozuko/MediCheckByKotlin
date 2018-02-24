package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

abstract class SortableArrayAdapter<T> : ArrayAdapter<T> {

    @LayoutRes
    private val resource: Int

    /** このPositionのアイテムはINVISIBLEになる */
    private var draggingPosition: Int = -1

    constructor(context: Context?, @LayoutRes resource: Int, objects: MutableList<T>) : this(context, resource, 0, objects)
    constructor(context: Context?, @LayoutRes resource: Int, @IdRes textViewResourceId: Int, objects: MutableList<T>) : super(context, resource, textViewResourceId, objects) {
        this.resource = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(resource, null)
        itemView.visibility = if (position == draggingPosition) View.INVISIBLE else View.VISIBLE
        return itemView
    }

    fun onStartDrag(position: Int) {
        draggingPosition = position
    }

    fun onDuringDrag(positionFrom: Int, positionTo: Int): Int {
        if (positionFrom < 0 || positionTo < 0) return positionFrom

        val from = if (positionFrom > count - 1) count - 1 else positionFrom
        val to = if (positionTo > count - 1) count - 1 else positionTo
        if (from == to) return positionFrom

        val draggingObject = getItem(from)
        var index: Int = from
        if (from < to) {
            while (index < to) {
                val moveTarget: T = getItem(index + 1)
                remove(getItem(index))
                insert(moveTarget, index)
                index++
            }
        } else {
            while (index > to) {
                val moveTarget: T = getItem(index - 1)
                remove(getItem(index))
                insert(moveTarget, index)
                index--
            }
        }
        remove(getItem(to))
        insert(draggingObject, to)
        draggingPosition = to
        return draggingPosition
    }

    fun onStopDrag() {
        draggingPosition = -1
    }

}