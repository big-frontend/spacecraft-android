package com.electrolytej.ad.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.hardware.SensorManager
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.AttributeSet
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CubeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
    // OpenGL 渲染组件
    val renderer = SensorGLRenderer()

    /**
     * 原始传感器旋转矩阵：由 getRotationMatrixFromVector 得到。
     * 需求：坐标轴要严格按这个坐标系绘制。
     */
    private val rotationMatrix = FloatArray(16).apply { Matrix.setIdentityM(this, 0) }

    /**
     * 为了 thread-safe、以及避免被 onDrawFrame 读到一半：单独维护一份渲染用矩阵。
     */
    private val renderRotationMatrix = FloatArray(16).apply { Matrix.setIdentityM(this, 0) }

    /** 是否显示左上角坐标轴颜色图例 */
    var axisLegendEnabled: Boolean = true
        set(value) {
            field = value
            invalidate()
        }

    private val legendTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 28f
    }
    private val legendLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
    }

    init {
        setupGLEnvironment()
        // 允许 onDraw 绘制 2D overlay
        setWillNotDraw(false)
    }

    private fun setupGLEnvironment() {
        setEGLContextClientVersion(3)
        setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
        preserveEGLContextOnPause = true
    }

    fun updateModelMatrix(rotationVector: FloatArray) {
        // 1) 按 getRotationMatrixFromVector 的坐标系获取旋转矩阵（不 remap）
        SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)

        // 2) 拷贝到渲染用矩阵（避免并发读写引起的抖动/撕裂）
        System.arraycopy(rotationMatrix, 0, renderRotationMatrix, 0, 16)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!axisLegendEnabled) return
        drawAxisLegend(canvas)
    }

    private fun drawAxisLegend(canvas: Canvas) {
        // 左上角：彩色线段 + 文本
        val padding = 20f
        val lineLen = 46f
        val lineGap = 18f
        val rowGap = 36f

        val x = padding
        var y = padding + 30f

        fun drawRow(yPos: Float, label: String, color: Int): Float {
            legendLinePaint.color = color
            canvas.drawLine(x, yPos, x + lineLen, yPos, legendLinePaint)
            canvas.drawText(label, x + lineLen + lineGap, yPos + 10f, legendTextPaint)
            return yPos + rowGap
        }

        y = drawRow(y, "X = Red", Color.RED)
        y = drawRow(y, "Y = Green", Color.GREEN)
        drawRow(y, "Z = Blue", Color.BLUE)
    }

    inner class SensorGLRenderer : Renderer {
        private lateinit var cube: Cube
        private lateinit var axes: Axes

        val projectionMatrix = FloatArray(16)
        private val viewMatrix = FloatArray(16).apply {
            Matrix.setLookAtM(
                this, 0,
                0f, 0f, 3f,   // 相机位置
                0f, 0f, 0f,   // 观察点
                0f, 1f, 0f    // 上方向
            )
        }
        private val mvpMatrix = FloatArray(16)

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES30.glEnable(GLES30.GL_DEPTH_TEST)
            GLES30.glEnable(GLES30.GL_BLEND)
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA)

            cube = Cube().apply { init() }
            axes = Axes().apply { init() }
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES30.glViewport(0, 0, width, height)
            val aspectRatio = width.toFloat() / height
            Matrix.perspectiveM(projectionMatrix, 0, 45f, aspectRatio, 0.1f, 100f)
        }

        override fun onDrawFrame(gl: GL10?) {
            GLES30.glClearColor(0.1f, 0.1f, 0.1f, 1f)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)

            // MVP = P * V * M
            // 这里的 M 使用 getRotationMatrixFromVector 的原始坐标系
            Matrix.multiplyMM(mvpMatrix, 0, viewMatrix, 0, renderRotationMatrix, 0)
            Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0)

            axes.draw(mvpMatrix)
            cube.draw(mvpMatrix)
        }
    }

    /**
     * 绘制坐标轴：
     * - X 轴：红色
     * - Y 轴：绿色
     * - Z 轴：蓝色
     */
    class Axes(
        axisLength: Float = 1.2f,
        arrowLength: Float = 0.18f,
        arrowWidth: Float = 0.10f
    ) {
        // 顶点格式：[x, y, z, r, g, b, a]
        // 用 GL_LINES 绘制：
        // 1) 三条轴线
        // 2) 每条轴末端两条“箭头翼”线段
        private val vertexData = buildAxesVertexData(axisLength, arrowLength, arrowWidth)

        private var vao = 0
        private var vbo = 0
        private var program = 0
        private var mvpMatrixHandle = 0

        private val vertexShaderSource = """
            #version 300 es
            layout(location = 0) in vec4 vPosition;
            layout(location = 1) in vec4 vColor;
            uniform mat4 uMVPMatrix;
            out vec4 fColor;
            void main() {
                gl_Position = uMVPMatrix * vPosition;
                fColor = vColor;
            }
        """.trimIndent()

        private val fragmentShaderSource = """
            #version 300 es
            precision mediump float;
            in vec4 fColor;
            out vec4 fragColor;
            void main() {
                fragColor = fColor;
            }
        """.trimIndent()

        fun init() {
            val vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderSource)
            val fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderSource)
            program = GLES30.glCreateProgram().also {
                GLES30.glAttachShader(it, vertexShader)
                GLES30.glAttachShader(it, fragmentShader)
                GLES30.glLinkProgram(it)
            }

            val vaoArray = IntArray(1)
            GLES30.glGenVertexArrays(1, vaoArray, 0)
            vao = vaoArray[0]
            GLES30.glBindVertexArray(vao)

            val vboArray = IntArray(1)
            GLES30.glGenBuffers(1, vboArray, 0)
            vbo = vboArray[0]

            val vertexBuffer = ByteBuffer
                .allocateDirect(vertexData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData)
            vertexBuffer.position(0)

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
            GLES30.glBufferData(
                GLES30.GL_ARRAY_BUFFER,
                vertexData.size * 4,
                vertexBuffer,
                GLES30.GL_STATIC_DRAW
            )

            // position
            GLES30.glVertexAttribPointer(
                0, 3, GLES30.GL_FLOAT, false,
                7 * 4, 0
            )
            GLES30.glEnableVertexAttribArray(0)

            // color
            GLES30.glVertexAttribPointer(
                1, 4, GLES30.GL_FLOAT, false,
                7 * 4, 3 * 4
            )
            GLES30.glEnableVertexAttribArray(1)

            GLES30.glBindVertexArray(0)
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)

            mvpMatrixHandle = GLES30.glGetUniformLocation(program, "uMVPMatrix")
        }

        fun draw(mvpMatrix: FloatArray) {
            GLES30.glUseProgram(program)
            GLES30.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

            GLES30.glLineWidth(4f)

            GLES30.glBindVertexArray(vao)
            GLES30.glDrawArrays(GLES30.GL_LINES, 0, vertexData.size / 7)
            GLES30.glBindVertexArray(0)
        }

        private fun loadShader(type: Int, shaderCode: String): Int {
            return GLES30.glCreateShader(type).also { shader ->
                GLES30.glShaderSource(shader, shaderCode)
                GLES30.glCompileShader(shader)
            }
        }

        private fun buildAxesVertexData(
            axisLen: Float,
            arrowLen: Float,
            arrowW: Float
        ): FloatArray {
            // 每条线段：2 个点；每个点 7 floats
            val out = ArrayList<Float>(7 * 2 * 9)

            fun addVertex(x: Float, y: Float, z: Float, r: Float, g: Float, b: Float, a: Float = 1f) {
                out.add(x); out.add(y); out.add(z)
                out.add(r); out.add(g); out.add(b); out.add(a)
            }

            fun addLine(
                x1: Float, y1: Float, z1: Float,
                x2: Float, y2: Float, z2: Float,
                r: Float, g: Float, b: Float
            ) {
                addVertex(x1, y1, z1, r, g, b)
                addVertex(x2, y2, z2, r, g, b)
            }

            // X axis (red)
            addLine(0f, 0f, 0f, axisLen, 0f, 0f, 1f, 0f, 0f)
            // arrow head for X axis at (axisLen,0,0), wings go towards -X with +/- Z
            addLine(axisLen, 0f, 0f, axisLen - arrowLen, 0f, +arrowW, 1f, 0f, 0f)
            addLine(axisLen, 0f, 0f, axisLen - arrowLen, 0f, -arrowW, 1f, 0f, 0f)

            // Y axis (green)
            addLine(0f, 0f, 0f, 0f, axisLen, 0f, 0f, 1f, 0f)
            // arrow head for Y axis at (0,axisLen,0), wings go towards -Y with +/- Z
            addLine(0f, axisLen, 0f, 0f, axisLen - arrowLen, +arrowW, 0f, 1f, 0f)
            addLine(0f, axisLen, 0f, 0f, axisLen - arrowLen, -arrowW, 0f, 1f, 0f)

            // Z axis (blue)
            addLine(0f, 0f, 0f, 0f, 0f, axisLen, 0f, 0f, 1f)
            // arrow head for Z axis at (0,0,axisLen), wings go towards -Z with +/- X
            addLine(0f, 0f, axisLen, +arrowW, 0f, axisLen - arrowLen, 0f, 0f, 1f)
            addLine(0f, 0f, axisLen, -arrowW, 0f, axisLen - arrowLen, 0f, 0f, 1f)

            return out.toFloatArray()
        }
    }

    class Cube {

        // 顶点数据格式：[位置X, Y, Z] + [颜色R, G, B, A]
        private val vertexData = floatArrayOf(
            // Front face (Red)
            -0.5f, -0.5f, 0.5f, 1f, 0f, 0f, 1f, // 0
            0.5f, -0.5f, 0.5f, 1f, 0f, 0f, 1f,  // 1
            0.5f, 0.5f, 0.5f, 1f, 0f, 0f, 1f,   // 2
            -0.5f, 0.5f, 0.5f, 1f, 0f, 0f, 1f,  // 3

            // Back face (Green)
            -0.5f, -0.5f, -0.5f, 0f, 1f, 0f, 1f, // 4
            0.5f, -0.5f, -0.5f, 0f, 1f, 0f, 1f,  // 5
            0.5f, 0.5f, -0.5f, 0f, 1f, 0f, 1f,   // 6
            -0.5f, 0.5f, -0.5f, 0f, 1f, 0f, 1f,  // 7

            // Top face (Blue)
            -0.5f, 0.5f, 0.5f, 0f, 0f, 1f, 1f,   // 8
            0.5f, 0.5f, 0.5f, 0f, 0f, 1f, 1f,    // 9
            0.5f, 0.5f, -0.5f, 0f, 0f, 1f, 1f,   // 10
            -0.5f, 0.5f, -0.5f, 0f, 0f, 1f, 1f,  // 11

            // Bottom face (Yellow)
            -0.5f, -0.5f, 0.5f, 1f, 1f, 0f, 1f,  // 12
            0.5f, -0.5f, 0.5f, 1f, 1f, 0f, 1f,   // 13
            0.5f, -0.5f, -0.5f, 1f, 1f, 0f, 1f,  // 14
            -0.5f, -0.5f, -0.5f, 1f, 1f, 0f, 1f, // 15

            // Left face (Cyan)
            -0.5f, -0.5f, 0.5f, 0f, 1f, 1f, 1f,  // 16
            -0.5f, 0.5f, 0.5f, 0f, 1f, 1f, 1f,   // 17
            -0.5f, 0.5f, -0.5f, 0f, 1f, 1f, 1f,  // 18
            -0.5f, -0.5f, -0.5f, 0f, 1f, 1f, 1f, // 19

            // Right face (Magenta)
            0.5f, -0.5f, 0.5f, 1f, 0f, 1f, 1f,   // 20
            0.5f, 0.5f, 0.5f, 1f, 0f, 1f, 1f,    // 21
            0.5f, 0.5f, -0.5f, 1f, 0f, 1f, 1f,   // 22
            0.5f, -0.5f, -0.5f, 1f, 0f, 1f, 1f   // 23
        )

        // 索引数据（每个面2个三角形）
        private val indexData = shortArrayOf(
            // Front
            0, 1, 2, 0, 2, 3,
            // Back
            4, 5, 6, 4, 6, 7,
            // Top
            8, 9, 10, 8, 10, 11,
            // Bottom
            12, 13, 14, 12, 14, 15,
            // Left
            16, 17, 18, 16, 18, 19,
            // Right
            20, 21, 22, 20, 22, 23
        )

        // OpenGL 对象
        private var vao = 0
        private var vbo = 0
        private var ebo = 0
        private var program = 0
        private var mvpMatrixHandle = 0

        // 着色器源码
        private val vertexShaderSource = """
        #version 300 es
        layout(location = 0) in vec4 vPosition;
        layout(location = 1) in vec4 vColor;
        uniform mat4 uMVPMatrix;
        out vec4 fColor;
        void main() {
            gl_Position = uMVPMatrix * vPosition;
            fColor = vColor;
        }
    """.trimIndent()

        private val fragmentShaderSource = """
        #version 300 es
        precision mediump float;
        in vec4 fColor;
        out vec4 fragColor;
        void main() {
            fragColor = fColor;
        }
    """.trimIndent()

        /**
         * 初始化立方体资源
         */
        fun init() {
            // 创建着色器程序
            val vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderSource)
            val fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderSource)
            program = GLES30.glCreateProgram().also {
                GLES30.glAttachShader(it, vertexShader)
                GLES30.glAttachShader(it, fragmentShader)
                GLES30.glLinkProgram(it)
            }

            // 创建VAO
            val vaoArray = IntArray(1)
            GLES30.glGenVertexArrays(1, vaoArray, 0)
            vao = vaoArray[0]
            GLES30.glBindVertexArray(vao)

            // 创建并填充VBO
            val vboArray = IntArray(1)
            GLES30.glGenBuffers(1, vboArray, 0)
            vbo = vboArray[0]
            val vertexBuffer = ByteBuffer
                .allocateDirect(vertexData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData)
            vertexBuffer.position(0)

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo)
            GLES30.glBufferData(
                GLES30.GL_ARRAY_BUFFER,
                vertexData.size * 4,
                vertexBuffer,
                GLES30.GL_STATIC_DRAW
            )

            // 创建并填充EBO
            val eboArray = IntArray(1)
            GLES30.glGenBuffers(1, eboArray, 0)
            ebo = eboArray[0]
            val indexBuffer = ByteBuffer
                .allocateDirect(indexData.size * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indexData)
            indexBuffer.position(0)

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, ebo)
            GLES30.glBufferData(
                GLES30.GL_ELEMENT_ARRAY_BUFFER,
                indexData.size * 2,
                indexBuffer,
                GLES30.GL_STATIC_DRAW
            )

            // 设置顶点属性指针
            GLES30.glVertexAttribPointer(
                0, 3, GLES30.GL_FLOAT, false,
                7 * 4, 0
            )
            GLES30.glEnableVertexAttribArray(0)

            GLES30.glVertexAttribPointer(
                1, 4, GLES30.GL_FLOAT, false,
                7 * 4, 3 * 4
            )
            GLES30.glEnableVertexAttribArray(1)

            // 解绑
            GLES30.glBindVertexArray(0)
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0)
            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0)

            // 获取统一变量位置
            mvpMatrixHandle = GLES30.glGetUniformLocation(program, "uMVPMatrix")
        }

        /**
         * 渲染立方体
         * @param mvpMatrix 模型-视图-投影矩阵
         */
        fun draw(mvpMatrix: FloatArray) {
            GLES30.glUseProgram(program)
            GLES30.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0)

            GLES30.glBindVertexArray(vao)
            GLES30.glDrawElements(
                GLES30.GL_TRIANGLES,
                indexData.size,
                GLES30.GL_UNSIGNED_SHORT,
                0
            )
            GLES30.glBindVertexArray(0)
        }

        /**
         * 清理OpenGL资源
         */
        fun cleanup() {
            GLES30.glDeleteProgram(program)
            GLES30.glDeleteBuffers(1, intArrayOf(vbo), 0)
            GLES30.glDeleteBuffers(1, intArrayOf(ebo), 0)
            GLES30.glDeleteVertexArrays(1, intArrayOf(vao), 0)
        }

        private fun loadShader(type: Int, shaderCode: String): Int {
            return GLES30.glCreateShader(type).also { shader ->
                GLES30.glShaderSource(shader, shaderCode)
                GLES30.glCompileShader(shader)
            }
        }
    }
}