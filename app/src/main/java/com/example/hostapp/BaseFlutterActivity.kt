package com.example.hostapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.flutter.app.FlutterActivityDelegate
import io.flutter.app.FlutterActivityEvents
import io.flutter.plugin.common.PluginRegistry
import io.flutter.view.FlutterNativeView
import io.flutter.view.FlutterView

/**
 * Created by Yagami3zZ hiqlong@163.com on 2019/8/29 0029.
 * Description:
 */
open class BaseFlutterActivity :AppCompatActivity(), FlutterView.Provider, PluginRegistry,
        FlutterActivityDelegate.ViewFactory {

    private val TAG = "BaseContainerActivity"
    private val delegate = FlutterActivityDelegate(this, this)
    private var eventDelegate: FlutterActivityEvents
    private var viewProvider: FlutterView.Provider
    private var pluginRegistry: PluginRegistry

    init {
        this.eventDelegate = this.delegate
        this.viewProvider = this.delegate
        this.pluginRegistry = this.delegate
    }

    override fun getFlutterView(): FlutterView {
        return this.viewProvider.flutterView
    }

    override fun createFlutterView(context: Context): FlutterView? {
        return null
    }

    override fun createFlutterNativeView(): FlutterNativeView? {
        return null
    }

    override fun retainFlutterNativeView(): Boolean {
        return false
    }

    override fun hasPlugin(key: String): Boolean {
        return this.pluginRegistry.hasPlugin(key)
    }

    override fun <T> valuePublishedByPlugin(pluginKey: String): T {
        return this.pluginRegistry.valuePublishedByPlugin(pluginKey)
    }

    override fun registrarFor(pluginKey: String): PluginRegistry.Registrar {
        return this.pluginRegistry.registrarFor(pluginKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.eventDelegate.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        this.eventDelegate.onStart()
    }

    override fun onResume() {
        super.onResume()
        this.eventDelegate.onResume()
    }

    override fun onDestroy() {
        this.eventDelegate.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (!this.eventDelegate.onBackPressed()) {
            super.onBackPressed()
        }

    }

    override fun onStop() {
        this.eventDelegate.onStop()
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        this.eventDelegate.onPause()
    }

    override fun onPostResume() {
        super.onPostResume()
        this.eventDelegate.onPostResume()
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        this.eventDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!this.eventDelegate.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onNewIntent(intent: Intent) {
        this.eventDelegate.onNewIntent(intent)
    }

    public override fun onUserLeaveHint() {
        this.eventDelegate.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        this.eventDelegate.onTrimMemory(level)
    }

    override fun onLowMemory() {
        this.eventDelegate.onLowMemory()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        this.eventDelegate.onConfigurationChanged(newConfig)
    }

}