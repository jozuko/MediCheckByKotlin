package com.studiojozu.common.log

/**
 * ログ出力クラス
 */
class Log(private val mSourceClass: Class<*>) {
    companion object {
        private val BASE_TAG = "MediCheck."
    }

    /**
     * ログ出力時のタグを取得する
     *
     * @return ログ出力用TAG
     */
    private val tag: String
        get() = BASE_TAG + mSourceClass.simpleName

    /**
     * android.util.Log.dを実行する
     *
     * @param message ログメッセージ
     */
    fun d(message: String) {
        android.util.Log.d(tag, message)
    }

    /**
     * android.util.Log.iを実行する
     *
     * @param message ログメッセージ
     */
    fun i(message: String) {
        android.util.Log.i(tag, message)
    }

    /**
     * android.util.Log.iを実行する
     *
     * @param throwable 例外
     */
    fun i(throwable: Throwable) {
        android.util.Log.i(tag, getThrowableMessage(throwable), throwable)
    }

    /**
     * android.util.Log.wを実行する
     *
     * @param message ログメッセージ
     */
    fun w(message: String) {
        android.util.Log.w(tag, message)
    }

    /**
     * android.util.Log.eを実行する
     *
     * @param throwable 例外
     */
    fun e(throwable: Throwable) {
        android.util.Log.e(tag, getThrowableMessage(throwable), throwable)
    }

    /**
     * 例外情報からログ出力用メッセージを取得する
     *
     * @param throwable 例外情報
     * @return 例外情報からログ出力用メッセージ
     */
    private fun getThrowableMessage(throwable: Throwable): String = throwable.message ?: throwable.toString()
}
