package com.example.hostapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import io.flutter.facade.Flutter
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterView

class MainActivity : AppCompatActivity() {

    private lateinit var flutterView: FlutterView

    var hostData = "这是一条来自Host的数据"

    private lateinit var mOriginLayout: LinearLayout
    private lateinit var mFlutterLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mOriginLayout = findViewById(R.id.origin_layout)
        mFlutterLayout = findViewById(R.id.container)

        val listeners = arrayOfNulls<FlutterView.FirstFrameListener>(1)
        listeners[0] = FlutterView.FirstFrameListener {
            mFlutterLayout.visibility = View.VISIBLE
            mOriginLayout.visibility = View.GONE
        }

        button0.setOnClickListener {
            jumpTo("route1")
        }

        button1.setOnClickListener {
            jumpTo("route2")
        }

        button2.setOnClickListener {
            mFlutterLayout.removeAllViews()

            flutterView = Flutter.createView(
                this@MainActivity,
                lifecycle,
                "route1"
            )

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            mFlutterLayout.addView(flutterView, layoutParams)

            flutterView.addFirstFrameListener(listeners[0])

            MethodChannel(flutterView, "app.channel.shared.data").setMethodCallHandler(object : MethodChannel.MethodCallHandler {
                override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                    if (call.method!!.contentEquals("getSharedText")) {
                        result.success(hostData)
                    }
                }
            })
        }

        button3.setOnClickListener {
//            val tx = supportFragmentManager.beginTransaction()
//            tx.replace(R.id.container, Flutter.createFragment("route2"))
//            tx.commit()

            mFlutterLayout.removeAllViews()

            flutterView = Flutter.createView(
                this@MainActivity,
                lifecycle,
                "route2"
            )

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            mFlutterLayout.addView(flutterView, layoutParams)

            flutterView.addFirstFrameListener(listeners[0])

            MethodChannel(flutterView, "app.channel.shared.data").setMethodCallHandler(object :
                MethodChannel.MethodCallHandler {
                override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                    if (call.method!!.contentEquals("getSharedText")) {
                        result.success(hostData)
                    }
                }
            })
        }
    }

    private fun jumpTo(routeData: String) {
        val intent = Intent()
        intent.putExtra(ContainerActivity.ROUTE_DATA, routeData)
        intent.setClass(this@MainActivity, ContainerActivity::class.java)
        startActivity(intent)
    }

}
