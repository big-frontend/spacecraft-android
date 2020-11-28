.class public Lcom/dianping/v1/IBinderProxy;
.super Ljava/lang/Object;
.source "IBinderProxy.java"

# interfaces
.implements Ljava/lang/reflect/InvocationHandler;


# instance fields
.field private binderOrigin:Landroid/os/IBinder;

.field private classLoader:Ljava/lang/ClassLoader;

.field private packageManagerProxy:Landroid/content/pm/IPackageManager;


# direct methods
.method public constructor <init>(Landroid/os/IBinder;Landroid/content/pm/IPackageManager;Ljava/lang/ClassLoader;)V
    .registers 4
    .param p1, "binderOrigin"    # Landroid/os/IBinder;
    .param p2, "packageManagerProxy"    # Landroid/content/pm/IPackageManager;
    .param p3, "classLoader"    # Ljava/lang/ClassLoader;

    .prologue
    .line 23
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 24
    iput-object p1, p0, Lcom/dianping/v1/IBinderProxy;->binderOrigin:Landroid/os/IBinder;

    .line 25
    iput-object p2, p0, Lcom/dianping/v1/IBinderProxy;->packageManagerProxy:Landroid/content/pm/IPackageManager;

    .line 26
    iput-object p3, p0, Lcom/dianping/v1/IBinderProxy;->classLoader:Ljava/lang/ClassLoader;

    .line 27
    return-void
.end method


# virtual methods
.method public invoke(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
    .registers 7
    .param p1, "proxy"    # Ljava/lang/Object;
    .param p2, "method"    # Ljava/lang/reflect/Method;
    .param p3, "args"    # [Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    .prologue
    .line 31
    const-string v0, "queryLocalInterface"

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3a

    .line 32
    const-string v0, "cjf"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "queryLocalInterface:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-static {p3}, Ljava/util/Arrays;->toString([Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 33
    array-length v0, p3

    const/4 v1, 0x1

    if-ne v0, v1, :cond_3a

    const-string v0, "android.content.pm.IPackageManager"

    const/4 v1, 0x0

    aget-object v1, p3, v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3a

    .line 34
    iget-object v0, p0, Lcom/dianping/v1/IBinderProxy;->packageManagerProxy:Landroid/content/pm/IPackageManager;

    .line 37
    :goto_39
    return-object v0

    :cond_3a
    iget-object v0, p0, Lcom/dianping/v1/IBinderProxy;->binderOrigin:Landroid/os/IBinder;

    invoke-virtual {p2, v0, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    goto :goto_39
.end method
