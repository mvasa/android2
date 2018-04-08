package com.vogella.android.colorfinder
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intent.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by manoj on 3/5/2018.
 */
class IntentActivity : AppCompatActivity() {
    var red = " "
    var green = " "
    var blue = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(red, color1.text.toString())
            intent.putExtra(green, color1.text.toString())
            intent.putExtra(blue, color1.text.toString())
            startActivity(intent)
        }
    }
}