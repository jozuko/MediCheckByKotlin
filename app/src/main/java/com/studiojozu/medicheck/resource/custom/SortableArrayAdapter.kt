package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.resource.fragment.MessageAlertDialogFragment

class SortableArrayAdapter<T>(
        context: Context?,
        objects: MutableList<T>,
        private val fragment: Fragment,
        private val showTextListener: SortableArrayAdapter.OnShowTextListener<T>,
        private val onDeleteClickListener: SortableArrayAdapter.OnDeleteClickListener<T>)
    : ArrayAdapter<T>(context, RESOURCE, 0, objects), MessageAlertDialogFragment.AlertDialogFragmentCallback {

    /** 定数定義 */
    companion object {
        private val REQUEST_CODE_DELETE_DIALOG = 1
        private val KEY_POSITION = "position"
        private val RESOURCE: Int = R.layout.list_item_edit_delete
    }

    /** このPositionのアイテムはINVISIBLEになる */
    private var draggingPosition: Int = -1

    /**
     * ItemViewの取得
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = convertView ?: LayoutInflater.from(context).inflate(RESOURCE, null)

        // 表示する文字を取得
        itemView.findViewById<TextView>(android.R.id.text1).text = showTextListener.getText(getItem(position))

        // 編集ボタンは親のListViewのItemClickと同じ
        itemView.findViewById<ShrinkButton>(R.id.button_edit).setOnClickListener {
            (parent as? SortableListView)?.performItemClick(itemView, position, -1)
        }

        // 削除ボタンは確認ダイアログを表示して、削除を通知する
        itemView.findViewById<ShrinkButton>(R.id.button_trash).setOnClickListener {
            showDeleteConfirmDialog(position)
        }

        itemView.visibility = if (position == draggingPosition) View.INVISIBLE else View.VISIBLE
        return itemView
    }

    /**
     * 削除ダイアログ表示
     */
    private fun showDeleteConfirmDialog(position: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_POSITION, position)

        val dialogFragment = MessageAlertDialogFragment.build(fragment, {
            requestCode = REQUEST_CODE_DELETE_DIALOG
            message = context.resources.getString(R.string.dialog_delete_message, showTextListener.getText(getItem(position)))
            positiveButtonLabel = context.resources.getString(android.R.string.yes)
            negativeButtonLabel = context.resources.getString(android.R.string.no)
            params = bundle
            callback = this@SortableArrayAdapter
        })

        dialogFragment.show(fragment.childFragmentManager, "delete_dialog")
    }

    /**
     * ドラッグ開始
     */
    fun onStartDrag(position: Int) {
        draggingPosition = position
    }

    /**
     * ドラッグ中
     */
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

    /**
     * ダイアログ終了時のリスナー
     */
    override fun onDismissListener(requestCode: Int, resultCode: Int, params: Bundle?) {
        if (requestCode == REQUEST_CODE_DELETE_DIALOG) {
            dismissDeleteDialog(resultCode, params)
        }
    }

    /**
     * 削除確認ダイアログの終了時処理
     */
    private fun dismissDeleteDialog(resultCode: Int, params: Bundle?) {
        params ?: return

        val position = params.getInt(KEY_POSITION, -1)
        if (position < 0) return

        if (resultCode == DialogInterface.BUTTON_POSITIVE)
            onDeleteClickListener.onDeleteClicked(getItem(position), position)
    }

    /** View生成時にテキストを取得するためのリスナー */
    interface OnShowTextListener<in T> {
        fun getText(targetObject: T): String
    }

    /** 各Viewの削除ボタンが押されたこと通知するリスナー */
    interface OnDeleteClickListener<in T> {
        fun onDeleteClicked(targetObject: T, position: Int)
    }

}