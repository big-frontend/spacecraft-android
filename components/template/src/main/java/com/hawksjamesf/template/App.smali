.class public Lcom/hawksjamesf/template/App;
.super Landroid/app/Application;
.source "App.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 22
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    return-void
.end method

.method private haha()V
    .registers 2

    .prologue
    .line 31
    invoke-virtual {p0}, Lcom/hawksjamesf/template/App;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    invoke-static {v0}, Lcom/dianping/v1/Hook;->init(Ljava/lang/ClassLoader;)V

    .line 32
    return-void
.end method

.method private initHotFix()V
    .registers 7

    .prologue
    .line 36
    :try_start_0
    invoke-virtual {p0}, Lcom/hawksjamesf/template/App;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v0

    .line 37
    .local v0, "classLoader":Ljava/lang/ClassLoader;
    new-instance v2, Ljava/io/File;

    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v3

    const-string v4, "yposedplugin-debug.apk"

    invoke-direct {v2, v3, v4}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 38
    .local v2, "pluginFile":Ljava/io/File;
    const-string v3, "HookInfo"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "initHotFix: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 39
    new-instance v1, Ldalvik/system/DexClassLoader;

    invoke-virtual {v2}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0}, Lcom/hawksjamesf/template/App;->getCodeCacheDir()Ljava/io/File;

    move-result-object v4

    invoke-virtual {v4}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v4

    const/4 v5, 0x0

    invoke-direct {v1, v3, v4, v5, v0}, Ldalvik/system/DexClassLoader;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/ClassLoader;)V

    .line 41
    .local v1, "dexClassLoader":Ldalvik/system/DexClassLoader;
    invoke-static {v1, v0}, Llab/galaxy/yahfa/HookMain;->doHookDefault(Ljava/lang/ClassLoader;Ljava/lang/ClassLoader;)V
    :try_end_40
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_40} :catch_41

    .line 55
    .end local v0    # "classLoader":Ljava/lang/ClassLoader;
    .end local v1    # "dexClassLoader":Ldalvik/system/DexClassLoader;
    .end local v2    # "pluginFile":Ljava/io/File;
    :goto_40
    return-void

    .line 52
    :catch_41
    move-exception v3

    goto :goto_40
.end method


# virtual methods
.method protected attachBaseContext(Landroid/content/Context;)V
    .registers 2
    .param p1, "base"    # Landroid/content/Context;

    .prologue
    .line 25
    invoke-super {p0, p1}, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V

    .line 26
    invoke-direct {p0}, Lcom/hawksjamesf/template/App;->initHotFix()V

    .line 28
    invoke-direct {p0}, Lcom/hawksjamesf/template/App;->haha()V

    .line 29
    return-void
.end method
