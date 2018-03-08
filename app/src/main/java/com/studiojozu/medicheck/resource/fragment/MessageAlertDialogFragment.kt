package com.studiojozu.medicheck.resource.fragment

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.studiojozu.medicheck.R
import java.io.Serializable

class MessageAlertDialogFragment : DialogFragment() {
    /** ダイアログ コールバック */
    private var callback: AlertDialogFragmentCallback? = null
    /** リクエストコード */
    private var requestCode = -1
    /** コールバックの時に引き渡すパラメータ */
    private var params: Bundle? = null

    /**
     * 定数定義
     */
    companion object {
        private const val KEY_REQUEST_CODE = "requestCode"
        private const val KEY_CALLBACK = "callback"
        private const val KEY_TITLE = "title"
        private const val KEY_MESSAGE = "message"
        private const val KEY_POSITIVE_BUTTON_LABEL = "positiveButtonLabel"
        private const val KEY_NEGATIVE_BUTTON_LABEL = "negativeButtonLabel"
        private const val KEY_NEUTRAL_BUTTON_LABEL = "neutralButtonLabel"
        private const val KEY_PARAMS = "params"

        fun newInstance(callback: AlertDialogFragmentCallback? = null,
                        requestCode: Int = -1,
                        title: String = "",
                        message: String = "",
                        positiveButtonLabel: String = "",
                        negativeButtonLabel: String = "",
                        neutralButtonLabel: String = "",
                        params: Bundle? = null): MessageAlertDialogFragment {
            val dialogFragment = MessageAlertDialogFragment()

            dialogFragment.arguments = Bundle()
            dialogFragment.arguments.putInt(KEY_REQUEST_CODE, requestCode)
            dialogFragment.arguments.putString(KEY_TITLE, title)
            dialogFragment.arguments.putString(KEY_MESSAGE, message)
            dialogFragment.arguments.putString(KEY_POSITIVE_BUTTON_LABEL, positiveButtonLabel)
            dialogFragment.arguments.putString(KEY_NEGATIVE_BUTTON_LABEL, negativeButtonLabel)
            dialogFragment.arguments.putString(KEY_NEUTRAL_BUTTON_LABEL, neutralButtonLabel)
            dialogFragment.arguments.putSerializable(KEY_CALLBACK, callback)
            dialogFragment.arguments.putBundle(KEY_PARAMS, params)

            return dialogFragment
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

        val title = arguments?.getString(KEY_TITLE)
        val message = arguments?.getString(KEY_MESSAGE)
        val positiveButtonLabel = arguments?.getString(KEY_POSITIVE_BUTTON_LABEL)
        val negativeButtonLabel = arguments?.getString(KEY_NEGATIVE_BUTTON_LABEL)
        val neutralButtonLabel = arguments?.getString(KEY_NEUTRAL_BUTTON_LABEL)
        callback = arguments?.getSerializable(KEY_CALLBACK) as? AlertDialogFragmentCallback
        requestCode = arguments?.getInt(KEY_REQUEST_CODE) ?: -1
        params = arguments?.getBundle(KEY_PARAMS)

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
        callback?.onDismissListener(requestCode, Activity.RESULT_CANCELED, params)
    }

    /**
     * ダイアログに対するコールバック
     */
    interface AlertDialogFragmentCallback : Serializable {
        /**
         * @param requestCode ダイアログ生成時に指定したリクエストコード
         * @param resultCode  0 : Activity.RESULT_CANCELED, -1 : DialogInterface.BUTTON_POSITIVE, -2 : DialogInterface.BUTTON_NEGATIVE, -3 : DialogInterface.BUTTON_NEUTRAL
         */
        fun onDismissListener(requestCode: Int, resultCode: Int, params: Bundle?)
    }
}