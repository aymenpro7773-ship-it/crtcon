package com.krtcon

import android.Manifest
import com.example.krtcon.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // تأكد أن ملف activity_main.xml يحتوي على WebView بـ id: webview
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        setupWebView()
        checkPermissions()
    }

    private fun setupWebView() {
        val settings: WebSettings = webView.settings

        // تفعيل الخصائص اللازمة لتشغيل الواجهة البرمجية
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true

        // ضمان فتح الروابط داخل التطبيق نفسه
        webView.webViewClient = WebViewClient()

        // ربط الواجهة (React) مع كود الأندرويد تحت اسم "Android"
        webView.addJavascriptInterface(WebAppInterface(this), "Android")

        // تحميل ملف الواجهة الأساسي من مجلد Assets
        webView.loadUrl("file:///android_asset/index.html")
    }

    private fun checkPermissions() {
        // طلب صلاحيات الرسائل والحماية الحيوية والتشغيل في الخلفية
        val permissions = arrayOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.USE_BIOMETRIC,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
        )

        val listPermissionsNeeded = ArrayList<String>()
        for (p in permissions) {
            if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 1)
        }
    }

    // التعامل مع زر الرجوع في الهاتف للتنقل داخل صفحات التطبيق
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}