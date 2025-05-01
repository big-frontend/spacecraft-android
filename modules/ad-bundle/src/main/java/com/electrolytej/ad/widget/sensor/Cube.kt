package com.electrolytej.ad.widget.sensor

import android.opengl.GLES30
import java.nio.ByteBuffer
import java.nio.ByteOrder

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