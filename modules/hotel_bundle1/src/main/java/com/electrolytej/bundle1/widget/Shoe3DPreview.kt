package com.electrolytej.bundle1.widget

import android.annotation.SuppressLint
import android.net.http.SslError
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Shoe3DPreview(
    modelUrl: String,
    modifier: Modifier = Modifier,
    autoRotate: Boolean = true,
    cameraControls: Boolean = true,
    backgroundColor: String = "#FFFFFF",
    onReady: (() -> Unit)? = null,
    onError: ((String) -> Unit)? = null
) {
    val context = LocalContext.current

    // 当 url/配置变化时，需要重新 load
    var isLoaded by remember(modelUrl, autoRotate, cameraControls, backgroundColor) { mutableStateOf(false) }

    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                mediaPlaybackRequiresUserGesture = false
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = true
                displayZoomControls = false
                cacheMode = WebSettings.LOAD_NO_CACHE
                // model-viewer 引入 https 资源，避免 http 页面混合内容出问题
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            }
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    if (!isLoaded) {
                        isLoaded = true
                        onReady?.invoke()
                    }
                }

                override fun onReceivedError(
                    view: WebView,
                    request: WebResourceRequest,
                    error: WebResourceError
                ) {
                    super.onReceivedError(view, request, error)
                    onError?.invoke(error.description?.toString().orEmpty())
                }

                override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                    // 默认按系统策略处理 SSL 错误（不要 handler.proceed()，避免安全风险）
                    super.onReceivedSslError(view, handler, error)
                    onError?.invoke("SSL error: ${error.primaryError}")
                }
            }
        }
    }

    // 当参数变化时触发一次 reload
    LaunchedEffect(modelUrl, autoRotate, cameraControls, backgroundColor) {
        isLoaded = false
    }

    DisposableEffect(webView) {
        onDispose {
            webView.destroy()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { webView },
            update = { wv ->
                if (!isLoaded) {
                    val html = generateModelViewerHtml(
                        modelUrl = modelUrl,
                        autoRotate = autoRotate,
                        cameraControls = cameraControls,
                        backgroundColor = backgroundColor
                    )
                    wv.loadDataWithBaseURL(
                        "https://modelviewer.dev/",
                        html,
                        "text/html",
                        "UTF-8",
                        null
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun generateModelViewerHtml(
    modelUrl: String,
    autoRotate: Boolean,
    cameraControls: Boolean,
    backgroundColor: String
): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }
                html, body {
                    width: 100%;
                    height: 100%;
                    overflow: hidden;
                    background-color: $backgroundColor;
                }
                model-viewer {
                    width: 100%;
                    height: 100%;
                    --poster-color: transparent;
                }
            </style>
            <script type="module" src="https://ajax.googleapis.com/ajax/libs/model-viewer/3.3.0/model-viewer.min.js"></script>
        </head>
        <body>
            <model-viewer
                src="$modelUrl"
                ios-src=""
                alt="3D Shoe Model"
                poster=""
                shadow-intensity="1"
                camera-controls="$cameraControls"
                auto-rotate="$autoRotate"
                auto-rotate-delay="0"
                rotation-per-second="30deg"
                camera-orbit="45deg 55deg 2.5m"
                field-of-view="30deg"
                exposure="0.9"
                shadow softness="1"
                exposure="1.0"
                environment-image="neutral"
                magic-leap
                ar
                ar-modes="webxr scene-viewer quick-look"
                ar-scale="auto"
                ar-placement="floor"
                loading="eager"
                reveal="auto"
                gesture-version="1"
                disable-zoom="false"
                interaction-prompt="none"
            >
                <div slot="progress-bar"></div>
            </model-viewer>
            <script>
                const modelViewer = document.querySelector('model-viewer');
                
                modelViewer.addEventListener('load', function() {
                    console.log('Model loaded successfully');
                });
                
                modelViewer.addEventListener('error', function(e) {
                    console.error('Model error:', e.detail);
                });
                
                modelViewer.addEventListener('ar-status', function(e) {
                    console.log('AR Status:', e.detail.status);
                });
            </script>
        </body>
        </html>
    """.trimIndent()
}

@Composable
fun Shoe3DPreviewWithZoom(
    modelUrl: String,
    modifier: Modifier = Modifier,
    onReady: (() -> Unit)? = null,
    onError: ((String) -> Unit)? = null
) {
    Shoe3DPreview(
        modelUrl = modelUrl,
        modifier = modifier,
        autoRotate = true,
        cameraControls = true,
        backgroundColor = "#FFFFFF",
        onReady = onReady,
        onError = onError
    )
}

@Preview(showBackground = true)
@Composable
private fun Shoe3DPreview_Preview() {
    // 预览环境通常不允许/不适合加载 WebView 网络资源，这里仅用于占位验证布局不崩。
    Shoe3DPreview(
        modelUrl = "https://modelviewer.dev/shared-assets/models/Astronaut.glb",
        modifier = Modifier.fillMaxSize(),
        autoRotate = true,
        cameraControls = true
    )
}
