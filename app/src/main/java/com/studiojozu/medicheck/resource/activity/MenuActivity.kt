package com.studiojozu.medicheck.resource.activity

import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife
import butterknife.OnClick
import com.studiojozu.medicheck.R

/**
 * メニュー画面
 */
class MenuActivity : ABaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        ButterKnife.bind(this)
    }

    /**
     * 今日のお薬ボタン Clickイベント処理
     */
    @OnClick(R.id.menu_button_today_medicine)
    fun onClickTodayMedicineButton() {
        // TODO startNextActivity(TodayMedicineActivity.class, R.id.menu_button_today_medicine);
    }

    /**
     * 服用履歴ボタン Clickイベント処理
     */
    @OnClick(R.id.menu_button_history_medicine)
    fun onClickHistoryMedicineButton() {
        // TODO 服用履歴画面遷移
    }

    /**
     * お薬一覧ボタン Clickイベント処理
     */
    @OnClick(R.id.menu_button_list_medicine)
    fun onClickListMedicineButton() {
        // TODO お薬一覧画面遷移
    }

    /**
     * お薬登録ボタン Clickイベント処理
     */
    @OnClick(R.id.menu_button_register_medicine)
    fun onClickRegisterMedicineButton() {
        // TODO startNextActivity(RegisterMedicineChoiceActivity.class, R.id.menu_button_register_medicine);
    }

    /**
     * 設定ボタン Clickイベント処理
     */
    @OnClick(R.id.menu_button_setting)
    fun onClickSettingButton() =
            startNextActivity(nextActivityClass = SettingActivity::class.java, requestCode = R.id.menu_button_setting)

    /**
     * 画面を遷移する。
     *
     * @param nextActivityClass 次画面のActivityクラス
     * @param requestCode       リクエストコード
     */
    private fun startNextActivity(nextActivityClass: Class<*>, requestCode: Int) {
        val intent = Intent(this, nextActivityClass)
        startActivityForResult(intent, requestCode)
    }
}
