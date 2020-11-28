.class public Lcom/dianping/v1/Hook;
.super Ljava/lang/Object;
.source "Hook.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 22
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static init(Ljava/lang/ClassLoader;)V
    .registers 13
    .param p0, "classLoader"    # Ljava/lang/ClassLoader;

    .prologue
    const/4 v11, 0x0

    const/4 v9, 0x1

    .line 44
    invoke-static {}, Landroid/app/ActivityThread;->getPackageManager()Landroid/content/pm/IPackageManager;

    move-result-object v5

    .line 45
    .local v5, "pkgMgrOrigin":Landroid/content/pm/IPackageManager;
    invoke-static {}, Landroid/app/ActivityThread;->currentActivityThread()Landroid/app/ActivityThread;

    move-result-object v0

    .line 46
    .local v0, "activityThread":Landroid/app/ActivityThread;
    new-array v9, v9, [Ljava/lang/Class;

    const-class v10, Landroid/content/pm/IPackageManager;

    aput-object v10, v9, v11

    new-instance v10, Lcom/dianping/v1/PackageManagerProxy;

    invoke-direct {v10, v5}, Lcom/dianping/v1/PackageManagerProxy;-><init>(Landroid/content/pm/IPackageManager;)V

    invoke-static {p0, v9, v10}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/pm/IPackageManager;

    .line 48
    .local v4, "packageManagerProxy":Landroid/content/pm/IPackageManager;
    :try_start_1b
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v9

    const-string v10, "sPackageManager"

    invoke-virtual {v9, v10}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v8

    .line 49
    .local v8, "sPackageManagerField":Ljava/lang/reflect/Field;
    const/4 v9, 0x1

    invoke-virtual {v8, v9}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 50
    invoke-virtual {v8, v0, v4}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V

    .line 52
    const-string v9, "package"

    invoke-static {v9}, Landroid/os/ServiceManager;->getService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v2

    .line 54
    .local v2, "iBinderOrigin":Landroid/os/IBinder;
    const/4 v9, 0x1

    new-array v9, v9, [Ljava/lang/Class;

    const/4 v10, 0x0

    const-class v11, Landroid/os/IBinder;

    aput-object v11, v9, v10

    new-instance v10, Lcom/dianping/v1/IBinderProxy;

    invoke-direct {v10, v2, v4, p0}, Lcom/dianping/v1/IBinderProxy;-><init>(Landroid/os/IBinder;Landroid/content/pm/IPackageManager;Ljava/lang/ClassLoader;)V

    invoke-static {p0, v9, v10}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/os/IBinder;

    .line 55
    .local v3, "iBinderProxy":Landroid/os/IBinder;
    const-class v9, Landroid/os/ServiceManager;

    const-string v10, "sCache"

    invoke-virtual {v9, v10}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v7

    .line 56
    .local v7, "sCacheField":Ljava/lang/reflect/Field;
    const/4 v9, 0x1

    invoke-virtual {v7, v9}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 57
    const/4 v9, 0x0

    invoke-virtual {v7, v9}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/util/Map;

    .line 58
    .local v6, "sCache":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/String;Landroid/os/IBinder;>;"
    const-string v9, "package"

    invoke-interface {v6, v9}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 59
    const-string v9, "package"

    invoke-interface {v6, v9, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_62
    .catch Ljava/lang/Exception; {:try_start_1b .. :try_end_62} :catch_63

    .line 66
    .end local v2    # "iBinderOrigin":Landroid/os/IBinder;
    .end local v3    # "iBinderProxy":Landroid/os/IBinder;
    .end local v6    # "sCache":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/String;Landroid/os/IBinder;>;"
    .end local v7    # "sCacheField":Ljava/lang/reflect/Field;
    .end local v8    # "sPackageManagerField":Ljava/lang/reflect/Field;
    :goto_62
    return-void

    .line 60
    :catch_63
    move-exception v1

    .line 61
    .local v1, "e":Ljava/lang/Exception;
    const-string v9, "cjf"

    invoke-static {v1}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 62
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_62
.end method

.method private static printAttrib(Ljava/lang/Class;)V
    .registers 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class",
            "<*>;)V"
        }
    .end annotation

    .prologue
    .local p0, "clz":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    const/4 v12, 0x1

    const/4 v6, 0x0

    .line 24
    const-string v7, "cjf"

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "clz:"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {p0}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-static {v7, v8}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 25
    invoke-virtual {p0}, Ljava/lang/Class;->getDeclaredClasses()[Ljava/lang/Class;

    move-result-object v1

    .line 26
    .local v1, "declaredClasses":[Ljava/lang/Class;, "[Ljava/lang/Class<*>;"
    array-length v8, v1

    move v7, v6

    :goto_24
    if-ge v7, v8, :cond_47

    aget-object v0, v1, v7

    .line 27
    .local v0, "c":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    const-string v9, "cjf"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "inner clz:"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v0}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 26
    add-int/lit8 v7, v7, 0x1

    goto :goto_24

    .line 29
    .end local v0    # "c":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    :cond_47
    invoke-virtual {p0}, Ljava/lang/Class;->getDeclaredFields()[Ljava/lang/reflect/Field;

    move-result-object v2

    .line 30
    .local v2, "declaredFields":[Ljava/lang/reflect/Field;
    array-length v8, v2

    move v7, v6

    :goto_4d
    if-ge v7, v8, :cond_79

    aget-object v4, v2, v7

    .line 31
    .local v4, "f":Ljava/lang/reflect/Field;
    invoke-virtual {v4, v12}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 32
    const-string v9, "cjf"

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "field name\uff1a"

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v4}, Ljava/lang/reflect/Field;->getName()Ljava/lang/String;

    move-result-object v11

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    const-string v11, " "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v9, v10}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 30
    add-int/lit8 v7, v7, 0x1

    goto :goto_4d

    .line 35
    .end local v4    # "f":Ljava/lang/reflect/Field;
    :cond_79
    invoke-virtual {p0}, Ljava/lang/Class;->getDeclaredMethods()[Ljava/lang/reflect/Method;

    move-result-object v3

    .line 36
    .local v3, "declaredMethods":[Ljava/lang/reflect/Method;
    array-length v7, v3

    :goto_7e
    if-ge v6, v7, :cond_aa

    aget-object v5, v3, v6

    .line 37
    .local v5, "m":Ljava/lang/reflect/Method;
    invoke-virtual {v5, v12}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 38
    const-string v8, "cjf"

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "method name\uff1a"

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v5}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v10

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, " "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 36
    add-int/lit8 v6, v6, 0x1

    goto :goto_7e

    .line 41
    .end local v5    # "m":Ljava/lang/reflect/Method;
    :cond_aa
    return-void
.end method
