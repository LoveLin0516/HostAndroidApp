package com.example.hostapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.FrameLayout
import io.flutter.facade.Flutter



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
//        button1.text = stringFromJNI()
        button1.setOnClickListener {
            val flutterView = Flutter.createView(
                this@MainActivity,
                lifecycle,
                "route1"
            )
            val layout = FrameLayout.LayoutParams(600, 800)
            layout.leftMargin = 100
            layout.topMargin = 200
            addContentView(flutterView, layout)
        }

        button2.setOnClickListener {
            val tx = supportFragmentManager.beginTransaction()
            tx.replace(R.id.container, Flutter.createFragment("route1"))
            tx.commit()
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
