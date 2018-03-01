package com.studiojozu.medicheck.resource.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.studiojozu.medicheck.R

class MessageAlertDialogFragment : DialogFragment() {

    /** ダイアログ タイトル */
    private var title = ""
    /** ダイアログ メッセージ */
    private var message = ""
    /** ダイアログ コールバック */
    private var callback: AlertDialogFragmentCallback? = null
    /** リクエストコード */
    private var requestCode = -1
    /** OK,Yes系のボタンラベル */
    private var positiveButtonLabel = ""
    /** Cancel系のボタンラベル */
    private var negativeButtonLabel = ""
    /** 真ん中に表示されるのボタンラベル */
    private var neutralButtonLabel = ""
    /** コールバックの時に引き渡すパラメータ */
    private var params: Bundle? = null

    /**
     * 定数定義
     */
    companion object {
        fun build(activity: AppCompatActivity, f: MessageAlertDialogFragment.Builder.() -> Unit): MessageAlertDialogFragment {
            val builder = MessageAlertDialogFragment.Builder(activity)
            builder.f()
            return builder.build()
        }

        fun build(fragment: Fragment, f: MessageAlertDialogFragment.Builder.() -> Unit): MessageAlertDialogFragment {
            val builder = MessageAlertDialogFragment.Builder(fragment)
            builder.f()
            return builder.build()
        }
    }

    /**
     * ダイアログ生成
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val clickListener = DialogInterface.OnClickListener { _, which ->
            dismiss()
            callback?.onDismissListener(requestCode, which, params)
        }

        val builder = AlertDialog.Builder(activity, R.style.StandardAlertDialog)
        if (!TextUtils.isEmpty(title)) builder.setTitle(title)
        if (!TextUtils.isEmpty(message)) builder.setMessage(message)
        if (!TextUtils.isEmpty(positiveButtonLabel)) builder.setPositiveButton(positiveButtonLabel, clickListener)
        if (!TextUtils.isEmpty(negativeButtonLabel)) builder.setNegativeButton(negativeButtonLabel, clickListener)
        if (!TextUtils.isEmpty(neutralButtonLabel)) builder.setNeutralButton(neutralButtonLabel, clickListener)

        return builder.create()
    }

    /**
     * キャンセル処理
     */
    override fun onCancel(dialog: DialogInterface?) {
        callback?.onDismissListener(requestCode, 0, params)
    }

    /**
     * ダイアログに対するコールバック
     */
    interface AlertDialogFragmentCallback {
        /**
         * @param requestCode ダイアログ生成時に指定したリクエストコード
         * @param resultCode  0 : Cancel, -1 : DialogInterface.BUTTON_POSITIVE, -2 : DialogInterface.BUTTON_NEGATIVE, -3 : DialogInterface.BUTTON_NEUTRAL
         */
        fun onDismissListener(requestCode: Int, resultCode: Int, params: Bundle?)
    }

    /**
     * ダイアログ生成クラス
     */
    class Builder {
        constructor(activity: AppCompatActivity) {
            parentActivity = activity
            parentFragment = null
        }

        constructor(fragment: Fragment) {
            parentActivity = null
            parentFragment = fragment
        }

        private val parentActivity: AppCompatActivity?
        private val parentFragment: Fragment?
        var callback: AlertDialogFragmentCallback? = null
        var requestCode = -1
        var title = ""
        var message = ""
        var positiveButtonLabel = ""
        var negativeButtonLabel = ""
        var neutralButtonLabel = ""
        var params: Bundle? = null

        fun build(): MessageAlertDialogFragment {
            val dialogFragment = MessageAlertDialogFragment()
            dialogFragment.requestCode = requestCode
            dialogFragment.callback = callback
            dialogFragment.title = title
            dialogFragment.message = message
            dialogFragment.positiveButtonLabel = positiveButtonLabel
            dialogFragment.negativeButtonLabel = negativeButtonLabel
            dialogFragment.neutralButtonLabel = neutralButtonLabel
            dialogFragment.params = params

            return dialogFragment
        }
    }
}