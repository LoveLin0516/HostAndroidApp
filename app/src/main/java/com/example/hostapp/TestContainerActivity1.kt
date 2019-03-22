package com.example.hostapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import io.flutter.facade.Flutter
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterView

/**
 * Created by Yagami3zZ hiqlong@163.com on 2019/3/21 0021.
 * Description:
 */
class TestContainerActivity1 : AppCompatActivity() {

    companion object {
        const val CHANNEL_NAME = "com.example.native.data"
        const val ROUTE_DATA = "route_data"
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

    private lateinit var mFlutterView: FlutterView

    override fun onCreate(savedInstanceState: Bundle?) {

        //最好是继承FlutterApplicaiton
        FlutterMain.startInitialization(getApplicationContext())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        routeData = intent.getStringExtra(ROUTE_DATA)

        mFlutterLayout = findViewById(R.id.container)

        mFlutterLayout.removeAllViews()

        mFlutterView = Flutter.createView(
            this@TestContainerActivity1,
            lifecycle,
            routeData
        )

        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mFlutterLayout.addView(mFlutterView, layoutParams)

        val listeners = arrayOfNulls<FlutterView.FirstFrameListener>(1)
        listeners[0] = FlutterView.FirstFrameListener {
            mFlutterLayout.visibility = View.VISIBLE
        }

        mFlutterView.addFirstFrameListener(listeners[0])

        MethodChannel(mFlutterView, CHANNEL_NAME).setMethodCallHandler(object : MethodChannel.MethodCallHandler {
            override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                if (call.method!!.contentEquals("getNativeData")) {
                    result.success(getNativeData())
                }
            }
        })
    }

    fun getNativeData() :String{
        return NATIVE_DATA
    }

}