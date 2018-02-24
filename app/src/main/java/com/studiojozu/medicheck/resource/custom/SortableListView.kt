package com.studiojozu.medicheck.resource.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.ListView

class SortableListView(context: Context, attrs: AttributeSet) : ListView(context, attrs) {

    companion object {
        private const val SCROLL_SPEED_FAST = 25
        private const val SCROLL_SPEED_SLOW = 8
    }

    /** ドラッグ中の場合はtrue */
    private var dragging: Boolean = false

    /** 並べ替えをし始めたリストItemのPosition */
    private var positionFrom: Int = -1

    /** ActionDown発生時のMotionEvent */
    private var actionDownEvent: MotionEvent? = null
        @SuppressLint("Recycle")
        set(value) {
            field?.recycle()
            field = if (value == null)
                null
            else
                MotionEvent.obtain(value)
        }

    /** ドラッグに使用するImageViewの画像を保存する */
    private var dragBitmap: Bitmap? = null

    /** ドラッグに使用するImageView */
    private val dragImageView: ImageView = ImageView(context)

    private val dragImageViewLayoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()

    /** WindowManager */
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val scrollFastSpeed: Int
        get() = (SCROLL_SPEED_FAST * context.resources.displayMetrics.density).toInt()

    private val scrollSlowSpeed: Int
        get() = (SCROLL_SPEED_SLOW * context.resources.displayMetrics.density).toInt()

    /** adapter */
    var adapter: SortableArrayAdapter<*>?
        get() = super.getAdapter() as SortableArrayAdapter<*>
        set(value) = super.setAdapter(value)

    /**
     * Viewの初期化
     */
    init {
        initDragImageView()
        setOnItemLongClickListener { _, _, _, _ -> startDrag() }
    }

    /**
     * ドラッグに使用するImageViewの初期化を行う
     */
    private fun initDragImageView() {
        dragImageView.visibility = View.GONE

        dragImageViewLayoutParams.gravity = Gravity.TOP or Gravity.LEFT
        dragImageViewLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dragImageViewLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        dragImageViewLayoutParams.flags = (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        dragImageViewLayoutParams.format = PixelFormat.TRANSLUCENT
        dragImageViewLayoutParams.windowAnimations = 0
        dragImageViewLayoutParams.x = left
        dragImageViewLayoutParams.y = top

        windowManager.addView(dragImageView, dragImageViewLayoutParams)
    }

    override fun onDetachedFromWindow() {
        recycleDragBitmap()
        actionDownEvent = null

        super.onDetachedFromWindow()
    }

    /**
     * タッチイベントを処理する.
     *
     * ドラッグの開始はsetOnItemLongClickListenerで行い、他の処理はココを使用する
     * sortingがfalseの間は動作しない
     */
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.actionMasked) {
            MotionEvent.ACTION_DOWN ->
                actionDownEvent = motionEvent

            MotionEvent.ACTION_MOVE ->
                if (duringDrag(motionEvent = motionEvent)) return true

            MotionEvent.ACTION_UP ->
                if (stopDrag(isDrop = true)) return true

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE ->
                if (stopDrag()) return true
        }

        return super.onTouchEvent(motionEvent)
    }

    /**
     * 使用できません
     */
    override fun setAdapter(adapter: ListAdapter?): Unit =
            throw UnsupportedOperationException("please use setAdapter(SortableArrayAdapter<*>)")

    /**
     * ドラッグの開始処理
     */
    private fun startDrag(): Boolean {
        if (actionDownEvent == null) return false

        // ドラッグの開始位置にあるListViewのpositionを取得する
        positionFrom = motionEventToPosition(actionDownEvent!!)
        if (positionFrom < 0) return false

        // ドラッグの開始
        dragging = true

        // ドラッグ対象をキャプチャしてImageViewに設定
        captureFromDraggingView()
        showDragImageView()

        // Adapterに対して、ドラッグの開始を通知する
        adapter?.onStartDrag(positionFrom)
        invalidateViews()

        return duringDrag(motionEvent = actionDownEvent!!)
    }

    /**
     * ドラッグ中処理
     */
    private fun duringDrag(motionEvent: MotionEvent): Boolean {
        if (!dragging) return false
        val x = motionEvent.x.toInt()
        val y = motionEvent.y.toInt()

        // スクロール処理
        val scrollSpeed = getScrollSpeed(motionEvent)
        scroll(scrollSpeed)

        // Dragに使用するImageViewのtopを変更
        dragImageView.visibility = if ((dragBitmap?.height ?: -1) < 0) View.INVISIBLE else View.VISIBLE
        updateDragImagePosition(motionEvent)

        // adapterにドラッグ位置を通知する
        positionFrom = adapter?.onDuringDrag(positionFrom, pointToPosition(x, y)) ?: positionFrom
        invalidateViews()

        return true
    }

    /**
     * ドラッグ終了処理
     */
    private fun stopDrag(isDrop: Boolean = false): Boolean {
        if (!dragging) return false
        dragging = false

        // adapterにドラッグ終了を通知
        if (isDrop) adapter?.onStopDrag()

        // ドラッグに使用したViewとBitmapを開放
        dragImageView.visibility = View.GONE
        recycleDragBitmap()

        // MouseDownActionを解放
        actionDownEvent = null

        invalidateViews()
        return true
    }

    /**
     * MotionEventからItemのPositionを取得する
     */
    private fun motionEventToPosition(motionEvent: MotionEvent): Int =
            pointToPosition(motionEvent.x.toInt(), motionEvent.y.toInt())

    /**
     * motionFromにあるItemのキャプチャを取得し、dragBitmapを更新する
     */
    private fun captureFromDraggingView() {
        val targetView = getListItemViewByPosition(positionFrom) ?: return
        recycleDragBitmap()
        dragBitmap = Bitmap.createBitmap(targetView.width, targetView.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas()
        canvas.setBitmap(dragBitmap)
        targetView.draw(canvas)
    }

    /**
     * positionの位置にあるViewを返却する
     */
    private fun getListItemViewByPosition(position: Int): View? {
        val index = position - firstVisiblePosition
        if (index < 0) return null
        return getChildAt(index)
    }

    /**
     * Dragに使用している画像をリサイクルする
     */
    private fun recycleDragBitmap() {
        dragImageView.setImageBitmap(null)
        dragBitmap ?: return

        try {
            dragBitmap!!.recycle()
        } catch (ignore: Exception) {
        }

        dragBitmap = null
    }

    /**
     * ドラッグに使用するImageViewにBitmapを設定する
     */
    private fun showDragImageView() = dragImageView.setImageBitmap(dragBitmap)

    /**
     * ドラッグに使用するImageViewのtopを変更する
     */
    private fun updateDragImagePosition(motionEvent: MotionEvent) {
        dragImageViewLayoutParams.y = top + motionEvent.y.toInt() + ((dragBitmap?.height ?: 0) / 2)
        windowManager.updateViewLayout(dragImageView, dragImageViewLayoutParams)
    }

    /**
     * ドラッグ中にスクロールさせる際のスピード(スクロール量)を計算する
     */
    private fun getScrollSpeed(motionEvent: MotionEvent): Int {
        val y = motionEvent.y.toInt()
        val fastBound = height / 9
        val slowBound = height / 4

        if (motionEvent.eventTime - motionEvent.downTime < 500)
            return 0

        if (y < slowBound)
            return if (y < fastBound) -scrollFastSpeed else -scrollSlowSpeed

        if (y > height - slowBound)
            return if (y > height - fastBound) scrollFastSpeed else scrollSlowSpeed

        return 0
    }

    /**
     * スクロール処理
     * TODO あんまりヌルヌル感がないので、要改良
     */
    private fun scroll(scrollSpeed: Int) {
        if (scrollSpeed == 0) return

        val middle = height / 2
        var middlePosition = pointToPosition(0, middle)

        if (middlePosition == AdapterView.INVALID_POSITION)
            middlePosition = pointToPosition(0, middle + dividerHeight + (dragBitmap?.height ?: 0))

        val middleView = getListItemViewByPosition(middlePosition)
        middleView ?: return
        Handler().postDelayed({
            setSelectionFromTop(middlePosition, middleView.top - scrollSpeed)
        }, 1)
    }
}