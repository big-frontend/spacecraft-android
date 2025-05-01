//package com.electrolytej.ad.widget
//
//import android.content.Context
//import android.hardware.Sensor
//import android.hardware.SensorEvent
//import android.hardware.SensorEventListener
//import android.hardware.SensorManager
//import android.opengl.GLSurfaceView
//import android.util.AttributeSet
//import com.google.android.filament.*
//import com.google.android.filament.gltfio.AssetLoader
//import com.google.android.filament.gltfio.MaterialProvider
//import java.nio.ByteBuffer
//
//class SensorView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null
//) : GLSurfaceView(context, attrs), SensorEventListener {
//
//    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//    private val rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
//    private var filament: Filament? = null
//    private var engine: Engine? = null
//    private var scene: Scene? = null
//    private var model: Entity? = null
//
//    // Filament 初始化
//    init {
//
//        setRenderer(Renderer {
//            filament = Filament.init(context.assets)
//            engine =
//            setupScene()
//        })
//        renderMode = RENDERMODE_CONTINUOUSLY
//    }
//
//    private fun setupScene() {
//        engine?.let { engine ->
//            scene = Scene(engine)
//
//            // 加载3D模型 (需将 model.glb 放入 assets/)
//            val assetLoader = AssetLoader(engine, MaterialProvider(engine), EntityManager.get())
//            val buffer = context.assets.open("model.glb").use { input ->
//                ByteBuffer.allocateDirect(input.available()).apply {
//                    input.channel.read(this)
//                    rewind()
//                }
//            }
//            val asset = assetLoader.createAsset(buffer)
//            model = asset.entities[0]
//
//            // 设置初始位置
//            val transform = TransformManager.get().getInstance(model!!)
//            TransformManager.get().setTransform(transform, Matrix4x4.identity())
//
//            scene?.addEntity(model!!)
//        }
//    }
//
//    // 传感器数据更新
//    override fun onSensorChanged(event: SensorEvent) {
//        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
//            val rotationMatrix = FloatArray(9)
//            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
//
//            // 转换为四元数
//            val quaternion = FloatArray(4)
//            SensorManager.getQuaternionFromVector(quaternion, event.values)
//
//            // 更新模型姿态
//            engine?.let {
//                val transform = TransformManager.get().getInstance(model!!)
//                TransformManager.get().setTransform(transform,
//                    Matrix4x4(quaternion[0], quaternion[1], quaternion[2], quaternion[3])
//                )
//            }
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
//
//    override fun onResume() {
//        super.onResume()
//        rotationSensor?.let {
//            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
//        }
//    }
//
//    override fun onPause() {
//        sensorManager.unregisterListener(this)
//        super.onPause()
//    }
//
//    override fun onDetachedFromWindow() {
//        engine?.destroy()
//        super.onDetachedFromWindow()
//    }
//}