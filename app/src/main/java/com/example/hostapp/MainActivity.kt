package com.example.hostapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import io.flutter.facade.Flutter
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterView

class MainActivity : AppCompatActivity() {

    private lateinit var flutterView: FlutterView

    var hostData = "这是一条来自Host的数据"

    private lateinit var mOriginLayout: LinearLayout
    private lateinit var mFlutterLayout: FrameLayout

    companion object {

        const val Channel_Sweeper_About = "Channel_Sweeper_About"
        const val Channel_Global_Bridge = "Channel_Global_Bridge"
        const val Channel_Sweeper_Offline = "Channel_Sweeper_Offline"

        const val Route_About_Sweeper = "Route_About_Sweeper"
        const val Route_Sweeper_Offline = "Route_Sweeper_Offline"

        const val Method_Get_Sweeper_Name = "getSweeperName"
        const val Method_Get_Serial_Num = "getSerialNum"
        const val Method_Get_Device_Version = "getDeviceVersion"
        const val Method_Get_Which_Native = "getWhichNative"
        const val Method_Get_Offline_Title = "getOfflineTitle"
        const val Method_Close_Current_Page = "closeCurrentPage"

        const val Method_Config_NetWork = "configNetWork"
        const val Method_Reset_Device = "resetDevice"
        const val Method_Copy_Serial_Num = "copySerialNum"

        const val Route_Data = "route"

        const val Sweeper_Name = "Sweeper_Name"
        const val Serial_Num = "Serial_Num"
        const val Device_Version = "Device_Version"
        const val Offline_Title = "Offline_Title"

    }

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

            MethodChannel(flutterView, Channel_Sweeper_About).setMethodCallHandler(object :
                MethodChannel.MethodCallHandler {
                override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                    when {
                        call.method!!.contentEquals("getSharedText") -> result.success(hostData)
                        call.method!!.contentEquals(Method_Get_Sweeper_Name) -> result.success(getSweeperName())
                        call.method!!.contentEquals(Method_Get_Serial_Num) -> result.success(getSerialNum())
                        call.method!!.contentEquals(Method_Get_Device_Version) -> result.success(getDeviceVersion())

                        call.method!!.contentEquals(Method_Config_NetWork) -> {
                            configNetWork()
                        }
                        call.method!!.contentEquals(Method_Reset_Device) -> {
                            resetDevice()
                        }
                        call.method!!.contentEquals(Method_Copy_Serial_Num) -> {
                            copySerialNum()
                        }
                    }
                }
            })

            MethodChannel(flutterView, Channel_Global_Bridge).setMethodCallHandler(object :
                MethodChannel.MethodCallHandler {
                override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                    when {
                        call.method!!.contentEquals(Method_Get_Which_Native) -> result.success("Android")
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
        intent.action = Intent.ACTION_RUN
        intent.putExtra(TestContainerActivity2.ROUTE_DATA, routeData)
        intent.setClass(this@MainActivity, TestContainerActivity2::class.java)
        startActivity(intent)
    }

    /**
     * 获取扫地机名称
     */
    fun getSweeperName(): String {
        return "智能激光扫地机"
    }

    /**
     * 获取序列号
     */
    fun getSerialNum(): String {
        return "12234566"
    }

    /**
     * 获取硬件版本号
     */
    fun getDeviceVersion(): String {
        return "2.2.39"
    }

    /**
     * 配置网络
     */
    //功能调整放到外面页面了
    fun configNetWork() {

    }

    /**
     * 恢复出厂设置
     */
    //功能调整放到外面页面了
    fun resetDevice() {

    }

    /**
     * 复制设备序列号
     */
    fun copySerialNum() {
        Toast.makeText(this, "序列号复制成功", Toast.LENGTH_SHORT).show()
    }

}
