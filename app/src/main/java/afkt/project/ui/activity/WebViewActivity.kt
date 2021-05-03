package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.ActivityWebviewBinding
import android.net.http.SslError
import android.view.KeyEvent
import android.view.View.OnLongClickListener
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.HitTestResult
import android.webkit.WebViewClient
import dev.assist.WebViewAssist
import dev.engine.log.DevLogEngine

/**
 * detail: WebView 辅助类
 * @author Ttt
 */
class WebViewActivity : BaseActivity<ActivityWebviewBinding>() {

    // WebView 辅助类
    private val mWebViewAssist = WebViewAssist()

    override fun baseLayoutId(): Int = R.layout.activity_webview

    override fun initValue() {
        super.initValue()

        // 长按监听事件
        binding.vidAwWebview.setOnLongClickListener(OnLongClickListener { view ->
            ((view as WebView).hitTestResult)?.let { result ->
                when (result.type) {
                    HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> {
                        val imgUrl = result.extra
                        DevLogEngine.getEngine().dTag(TAG, "SRC_IMAGE_ANCHOR_TYPE %s", imgUrl)
                        return@OnLongClickListener true
                    }
                    else -> {
                    }
                }
            }
            false
        })

        // 设置 WebView
        mWebViewAssist.webView = binding.vidAwWebview

        // 设置辅助 WebView 处理 Javascript 对话框、标题等对象
        mWebViewAssist.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                position: Int
            ) {
                // 加载进度监听
                if (position == 100) { // 加载完成
                    DevLogEngine.getEngine().dTag(TAG, "加载完成")
                }
                super.onProgressChanged(view, position)
            }
        }
        // 设置处理各种通知和请求事件对象
        mWebViewAssist.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed() // 接受所有网站的证书
//                handler.cancel() // Android 默认的处理方式
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                // 重定向 URL 请求, 返回 true 表示拦截此 url, 返回 false 表示不拦截此 url
                if (url.startsWith("weixin://")) {
                    mWebViewAssist.loadUrl(
                        url.replace("weixin://", "http://")
                    )
                    return true
                }
                // 在本页面的 WebView 打开, 防止外部浏览器打开此链接
                mWebViewAssist.loadUrl(url)
                return true
            }
        }
        /**
         * 默认使用全局配置
         * [BaseApplication.initWebViewBuilder]
         */
//        // 如果使用全局配置, 则直接调用 apply 方法
//        mWebViewAssist.apply()

        // 也可以传入自定义配置
        val builder = WebViewAssist.getGlobalBuilder().clone(true) // new WebViewAssist.Builder()
        builder.setBuiltInZoomControls(false).isDisplayZoomControls = false
        // 当克隆方法 ( clone ) 传入 true 表示复用 OnApplyListener 但是想要能够添加其他配置处理则如下方法操作
        val applyListener = builder.applyListener
        builder.setOnApplyListener { webViewAssist, builder ->
            applyListener?.onApply(webViewAssist, builder)
            // BaseApplication 也会打印 WebViewAssist Builder onApply
            DevLogEngine.getEngine().dTag(TAG, "自定义监听")
            // 全局配置或者自定义配置以外, 再次配置操作
            // 加载网页
            mWebViewAssist.loadUrl("https://www.csdn.net/")
        }
        // 设置配置并应用
        mWebViewAssist.setBuilder(builder, true)
    }

    override fun onKeyDown(
        keyCode: Int,
        event: KeyEvent
    ): Boolean {
        if (mWebViewAssist.handlerKeyDown(keyCode, event)) return true
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        mWebViewAssist.destroy()
    }
}