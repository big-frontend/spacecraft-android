# Spacecraft Android — Copilot Instructions

## 项目概述

这是一个多模块 Android 应用，使用 Kotlin + Jetpack Compose，包名 `com.electrolytej.spacecraft`。模块系统由自定义 Gradle 插件 `io.github.electrolytej.module-assembler` 驱动，支持动态/静态模块组装。

---

## 构建 / 测试 / Lint 命令

```bash
# 构建
./gradlew assembleDebug
./gradlew assembleRelease
./gradlew :app:assembleDebug

# 测试
./gradlew test                                                        # 所有单元测试
./gradlew :modules:feeds-bundle:test                                  # 指定模块
./gradlew test --tests "com.electrolytej.feeds.ExampleUnitTest"       # 指定测试类
./gradlew test --tests "com.electrolytej.feeds.ExampleUnitTest.methodName"  # 指定方法
./gradlew connectedDebugAndroidTest                                   # 仪器测试（需设备）

# Lint
./gradlew lint
./gradlew :modules:feeds-bundle:lint
```

构建产物支持 `tvDebug` / `tvRelease` flavor（TV 端）。

---

## 模块架构

### 层次结构

```
app（宿主）
  └── modules/（业务模块）
        ├── *-bundle   → 业务功能实现（nsbundle = 静态，ndbundle = 动态）
        └── *-export   → 跨模块 API 接口（format: api）
base/（framework-base）→ 所有模块的基础库，含 NDK、Room、Hilt
lint-rules/            → 自定义 Lint 规则
kcp/                   → Kotlin 编译器插件
build-logic/convention/→ 自定义 Gradle Convention 插件
```

### 已有业务模块

| group         | bundle                         | export        |
|---------------|--------------------------------|---------------|
| main          | main-bundle                    | main-export   |
| feeds         | feeds-bundle                   | —             |
| account       | account-bundle                 | —             |
| hotel         | hotel-bundle1/2, hotel-foundation, hotel-tool | hotel-export |
| im            | im-bundle                      | im-export     |
| ad/commerce   | ad-bundle                      | ad-export     |
| livestreaming | livestreaming-bundle           | —             |
| codescanner   | codescanner-bundle             | —             |
| update        | update-bundle                  | —             |
| uamp          | uamp-module                    | —             |

### 模块间通信规则

- **bundle 模块**使用 `compileOnly(project.moduleify("framework-base"))` 依赖基础层，不直接依赖其他 bundle。
- **export 模块**（`api-plugin`）只暴露接口，无实现。其他模块通过 export 模块通信。
- 模块配置统一在 `module_config.json` 中管理（供 IDEA 插件和 Gradle 插件读取）。

---

## Gradle 插件体系

### 外部模块插件（`io.github.electrolytej.*`）

| 插件 ID | 用途 |
|---------|------|
| `io.github.electrolytej.app-plugin` | 宿主 App 模块 |
| `io.github.electrolytej.foundation-plugin` | 基础库模块（base/） |
| `io.github.electrolytej.static-bundle-plugin` | 静态业务 bundle（nsbundle） |
| `io.github.electrolytej.dynamic-bundle-plugin` | 动态业务 bundle（ndbundle） |
| `io.github.electrolytej.api-plugin` | export/API 模块 |
| `io.github.electrolytej.tool-plugin` | 工具模块（如 hotel-tool） |
| `io.github.electrolytej.module-assembler-settings-plugin` | settings 级别模块组装 |
| `io.github.electrolytej.module-publisher-plugin` | 模块发布 |

### Convention 插件（`build-logic/convention/`）

| 插件 ID | 用途 |
|---------|------|
| `electrolytej.android.application` | App 基础配置 |
| `electrolytej.android.application.compose` | App + Compose |
| `electrolytej.android.application.jacoco` | App + Jacoco 覆盖率 |
| `electrolytej.android.application.firebase` | Firebase 集成 |
| `electrolytej.android.application.flavors` | 构建 Flavor 配置 |
| `electrolytej.android.library` | Library 基础配置 |
| `electrolytej.android.library.compose` | Library + Compose |
| `electrolytej.android.library.jacoco` | Library + Jacoco 覆盖率 |
| `electrolytej.android.feature` | Feature 模块配置 |
| `electrolytej.android.test` | 测试模块配置 |
| `electrolytej.android.lint` | 自定义 Lint 规则 |
| `electrolytej.android.room` | Room 数据库配置 |
| `electrolytej.hilt` | Hilt DI 配置 |
| `electrolytej.jvm.library` | JVM 库配置 |

### 公共 Gradle 脚本（`script/` 目录）

所有模块通过 `apply from` 引入：

- `ui_tradition.gradle` — ViewBinding、AIDL、RecyclerView、SmartRefreshLayout、分页等传统 UI 依赖
- `ui_compose.gradle` — Compose + BOM 相关配置
- `test_impl.gradle` — 测试依赖（JUnit、Espresso 等）
- `app_config.gradle` — App 级构建配置（排除 Guava、Matrix APK 检查等）

---

## 关键技术约定

### 依赖注入
使用 **Hilt**（`alias(libs.plugins.hilt)`），所有需要注入的模块需同时配置 `kapt` 或 `ksp`。

### 数据库
使用 **Room**（`alias(libs.plugins.room)`），KSP 处理器参数统一为：
```kotlin
ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}
```

### WebView
项目使用腾讯 **X5 WebView**（`com.tencent.smtt.sdk.WebView`），不要使用系统 WebView。

### 导航
使用 `androidx.navigation`（Fragment + Compose 均有），版本由 `libs.versions.toml` 统一管理。

### 版本目录
所有依赖版本通过 `gradle/libs.versions.toml` 管理，禁止在 build.gradle 中硬编码版本号。

### NDK
`base/` 和 `main-bundle` 含 NDK 代码，CMake 配置使用：
- STL：`c++_shared`
- ABI：`arm64-v8a`, `armeabi-v7a`
- C++ 标准：`c++14`，开启 `-fvisibility=hidden`

### Kotlin / Java 版本
所有模块统一使用 `JavaVersion.VERSION_17`，`jvmTarget = "17"`。

### 签名
签名配置通过 `gradle.properties` 中的 `keyAlias`、`keyPassword`、`storePassword`、`storeFilePath` 属性读取，密钥文件位于 `keystore/`。

---

## 测试框架

- **JUnit 4** — 单元测试（`junit:junit:4.13.2`）
- **Mockito** — Mock 依赖
- **Robolectric** — Android 框架模拟（无需设备）
- **Roborazzi** — 截图测试（`1.7.0`）
- **Turbine** — Flow/协程测试（`1.1.0`）
- **Espresso** — UI 仪器测试

测试依赖通过 `apply from: "$rootDir/script/test_impl.gradle"` 统一引入。方法命名格式：`testMethodName_Scenario_ExpectedResult`，遵循 AAA 模式。

---

## 常见问题

- **模块找不到**：检查 `module_config.json` 中是否已注册该模块，`settings.gradle.kts` 由 `module-assembler-settings-plugin` 自动处理。
- **Compose 依赖冲突**：使用 `platform(libs.compose.bom)` 统一管理版本，不要单独指定 Compose 库版本。
- **Kotlin + Compose 插件顺序**：确保 `kotlin.android` 在 Compose 相关插件之前 apply。
