package com.andrewkir.andrewforwork.timem8.ActivityFin

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.andrewkir.andrewforwork.timem8.DataBase.DBfinance
import com.andrewkir.andrewforwork.timem8.R
import kotlinx.android.synthetic.main.activity_finance_result.*
import kotlin.math.abs

class FinanceResult : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemeWelcome)
        setContentView(R.layout.activity_finance_result)
        changeStatusBarColor()
        okFin.setOnClickListener {
            finish()
        }
        val max = intent.getIntExtra("MAX",0)
        val sum = intent.getIntExtra("SUM", 0)
        val map = DBfinance(this).allOpByCat()
        val result = map.toList().sortedBy { (_, value) -> value}.reversed().toMap()
        for ((key,value) in result){
            if(key != "Пополнение") {
                moneySpend.text = "${moneySpend.text}\n• $value\u20BD на ${key.toLowerCase()}."
            } else {
                moneyGain.text = "• ${abs(value)}\u20BD"
            }
        }
        if(moneyGain.text.toString() == ""){
            gainStatic.text = ""
        }
        if(sum<max){
            resTip.text = "Так держать! Вы смогли сэкономить ${max-sum}\u20BD. В таком духе через год у вас накопится ${52*(max-sum)}\u20BD"
        } else if(sum - max <= max*0.05){
            resTip.text = "Чуть - чуть не вышло, вы превысили установленный максимум на ${sum-max}\u20BD"
        } else {
            resTip.text = "К сожалению, вы превысили свой максимум расходов на эту неделю :C"
        }
    }
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#ff906d")
        }
    }
}
