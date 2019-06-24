package com.example.hostapp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import io.flutter.app.FlutterActivity
import io.flutter.app.FlutterFragmentActivity
import io.flutter.facade.Flutter
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterView

/**
 * Created by Yagami3zZ hiqlong@163.com on 2019/3/21 0021.
 * Description: 继承自FlutterActivity 没法自定义FlutterView
 */
class TestContainerActivity2 : FlutterActivity() {

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

        const val CHANNEL_NAME = "com.example.native.data"
        const val ROUTE_DATA = "route"
        const val NATIVE_DATA = "[\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 2,\n" +
                "    \"title\": \"qui est esse\",\n" +
                "    \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 3,\n" +
                "    \"title\": \"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\n" +
                "    \"body\": \"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"\n" +
                "  }]"
    }


    private lateinit var routeData: String

    private lateinit var mFlutterLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        //最好是继承FlutterApplicaiton
        FlutterMain.startInitialization(getApplicationContext())
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        routeData = intent.getStringExtra(ROUTE_DATA)


//        val listeners = arrayOfNulls<FlutterView.FirstFrameListener>(1)
//        listeners[0] = FlutterView.FirstFrameListener {
//            mFlutterLayout.visibility = View.VISIBLE
//        }
//
//        flutterView.addFirstFrameListener(listeners[0])

        MethodChannel(flutterView, Channel_Sweeper_About).setMethodCallHandler(object : MethodChannel.MethodCallHandler {
            override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                when {
                    call.method!!.contentEquals("getNativeData") -> result.success(getNativeData())
                    call.method!!.contentEquals(MainActivity.Method_Get_Sweeper_Name) -> result.success(getSweeperName())
                    call.method!!.contentEquals(MainActivity.Method_Get_Serial_Num) -> result.success(getSerialNum())
                    call.method!!.contentEquals(MainActivity.Method_Get_Device_Version) -> result.success(getDeviceVersion())

                    call.method!!.contentEquals(MainActivity.Method_Config_NetWork) -> {
                        configNetWork()
                    }
                    call.method!!.contentEquals(MainActivity.Method_Reset_Device) -> {
                        resetDevice()
                    }
                    call.method!!.contentEquals(MainActivity.Method_Copy_Serial_Num) -> {
                        copySerialNum()
                    }
                }
            }
        })

        MethodChannel(flutterView, Channel_Global_Bridge).setMethodCallHandler(object : MethodChannel.MethodCallHandler {
            override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                when {
                    call.method!!.contentEquals(Method_Get_Which_Native) -> result.success("Android")
                }
            }
        })
    }

    fun getNativeData() :String{
        return NATIVE_DATA
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