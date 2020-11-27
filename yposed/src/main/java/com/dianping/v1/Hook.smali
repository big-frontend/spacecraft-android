.class public Lcom/dianping/v1/Hook;
.super Ljava/lang/Object;
.source "Hook.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 18
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static init(Ljava/lang/ClassLoader;)V
    .registers 9
    .param p0, "classLoader"    # Ljava/lang/ClassLoader;

    .prologue
    const/4 v5, 0x1

    .line 20
    invoke-static {}, Landroid/app/ActivityThread;->getPackageManager()Landroid/content/pm/IPackageManager;

    move-result-object v2

    .line 21
    .local v2, "origin":Landroid/content/pm/IPackageManager;
    invoke-static {}, Landroid/app/ActivityThread;->currentActivityThread()Landroid/app/ActivityThread;

    move-result-object v0

    .line 22
    .local v0, "activityThread":Landroid/app/ActivityThread;
    new-array v5, v5, [Ljava/lang/Class;

    const/4 v6, 0x0

    const-class v7, Landroid/content/pm/IPackageManager;

    aput-object v7, v5, v6

    new-instance v6, Lcom/dianping/v1/PackageManagerProxy;

    invoke-direct {v6, v2}, Lcom/dianping/v1/PackageManagerProxy;-><init>(Landroid/content/pm/IPackageManager;)V

    invoke-static {p0, v5, v6}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/content/pm/IPackageManager;

    .line 24
    .local v3, "packageManagerProxy":Landroid/content/pm/IPackageManager;
    :try_start_1b
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v5

    const-string v6, "sPackageManager"

    invoke-virtual {v5, v6}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v4

    .line 25
    .local v4, "sPackageManagerField":Ljava/lang/reflect/Field;
    const/4 v5, 0x1

    invoke-virtual {v4, v5}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 26
    invoke-virtual {v4, v0, v3}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_2c
    .catch Ljava/lang/NoSuchFieldException; {:try_start_1b .. :try_end_2c} :catch_2d
    .catch Ljava/lang/IllegalAccessException; {:try_start_1b .. :try_end_2c} :catch_3b

    .line 33
    .end local v4    # "sPackageManagerField":Ljava/lang/reflect/Field;
    :goto_2c
    return-void

    .line 27
    :catch_2d
    move-exception v1

    .line 28
    .local v1, "e":Ljava/lang/ReflectiveOperationException;
    :goto_2e
    const-string v5, "hook"

    invoke-static {v1}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v6

    invoke-static {v5, v6}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 29
    invoke-virtual {v1}, Ljava/lang/ReflectiveOperationException;->printStackTrace()V

    goto :goto_2c

    .line 27
    .end local v1    # "e":Ljava/lang/ReflectiveOperationException;
    :catch_3b
    move-exception v1

    goto :goto_2e
.end method
