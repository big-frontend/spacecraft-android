.class public Lcom/dianping/v1/PackageManagerProxy;
.super Ljava/lang/Object;
.source "PackageManagerProxy.java"

# interfaces
.implements Ljava/lang/reflect/InvocationHandler;


# instance fields
.field private mPackageManager:Landroid/content/pm/IPackageManager;

.field public qqSign:[B


# direct methods
.method public constructor <init>(Landroid/content/pm/IPackageManager;)V
    .registers 3
    .param p1, "packageManager"    # Landroid/content/pm/IPackageManager;

    .prologue
    .line 26
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 24
    const/16 v0, 0x263

    new-array v0, v0, [B

    fill-array-data v0, :array_10

    iput-object v0, p0, Lcom/dianping/v1/PackageManagerProxy;->qqSign:[B

    .line 27
    iput-object p1, p0, Lcom/dianping/v1/PackageManagerProxy;->mPackageManager:Landroid/content/pm/IPackageManager;

    .line 28
    return-void

    .line 24
    nop

    :array_10
    .array-data 1
        0x30t
        -0x7et
        0x2t
        0x5ft
        0x30t
        -0x7et
        0x1t
        -0x38t
        -0x60t
        0x3t
        0x2t
        0x1t
        0x2t
        0x2t
        0x4t
        0x4at
        -0x7ct
        -0x3ft
        -0x2bt
        0x30t
        0xdt
        0x6t
        0x9t
        0x2at
        -0x7at
        0x48t
        -0x7at
        -0x9t
        0xdt
        0x1t
        0x1t
        0x5t
        0x5t
        0x0t
        0x30t
        0x74t
        0x31t
        0xbt
        0x30t
        0x9t
        0x6t
        0x3t
        0x55t
        0x4t
        0x6t
        0x13t
        0x2t
        0x43t
        0x4et
        0x31t
        0x11t
        0x30t
        0xft
        0x6t
        0x3t
        0x55t
        0x4t
        0x8t
        0x13t
        0x8t
        0x73t
        0x68t
        0x61t
        0x6et
        0x67t
        0x68t
        0x61t
        0x69t
        0x31t
        0x11t
        0x30t
        0xft
        0x6t
        0x3t
        0x55t
        0x4t
        0x7t
        0x13t
        0x8t
        0x73t
        0x68t
        0x61t
        0x6et
        0x67t
        0x68t
        0x61t
        0x69t
        0x31t
        0x15t
        0x30t
        0x13t
        0x6t
        0x3t
        0x55t
        0x4t
        0xat
        0x13t
        0xct
        0x64t
        0x69t
        0x61t
        0x6et
        0x70t
        0x69t
        0x6et
        0x67t
        0x2et
        0x63t
        0x6ft
        0x6dt
        0x31t
        0x15t
        0x30t
        0x13t
        0x6t
        0x3t
        0x55t
        0x4t
        0xbt
        0x13t
        0xct
        0x64t
        0x69t
        0x61t
        0x6et
        0x70t
        0x69t
        0x6et
        0x67t
        0x2et
        0x63t
        0x6ft
        0x6dt
        0x31t
        0x11t
        0x30t
        0xft
        0x6t
        0x3t
        0x55t
        0x4t
        0x3t
        0x13t
        0x8t
        0x54t
        0x75t
        0x20t
        0x59t
        0x69t
        0x6dt
        0x69t
        0x6et
        0x30t
        0x1et
        0x17t
        0xdt
        0x30t
        0x39t
        0x30t
        0x38t
        0x31t
        0x34t
        0x30t
        0x31t
        0x34t
        0x35t
        0x35t
        0x37t
        0x5at
        0x17t
        0xdt
        0x33t
        0x36t
        0x31t
        0x32t
        0x33t
        0x30t
        0x30t
        0x31t
        0x34t
        0x35t
        0x35t
        0x37t
        0x5at
        0x30t
        0x74t
        0x31t
        0xbt
        0x30t
        0x9t
        0x6t
        0x3t
        0x55t
        0x4t
        0x6t
        0x13t
        0x2t
        0x43t
        0x4et
        0x31t
        0x11t
        0x30t
        0xft
        0x6t
        0x3t
        0x55t
        0x4t
        0x8t
        0x13t
        0x8t
        0x73t
        0x68t
        0x61t
        0x6et
        0x67t
        0x68t
        0x61t
        0x69t
        0x31t
        0x11t
        0x30t
        0xft
        0x6t
        0x3t
        0x55t
        0x4t
        0x7t
        0x13t
        0x8t
        0x73t
        0x68t
        0x61t
        0x6et
        0x67t
        0x68t
        0x61t
        0x69t
        0x31t
        0x15t
        0x30t
        0x13t
        0x6t
        0x3t
        0x55t
        0x4t
        0xat
        0x13t
        0xct
        0x64t
        0x69t
        0x61t
        0x6et
        0x70t
        0x69t
        0x6et
        0x67t
        0x2et
        0x63t
        0x6ft
        0x6dt
        0x31t
        0x15t
        0x30t
        0x13t
        0x6t
        0x3t
        0x55t
        0x4t
        0xbt
        0x13t
        0xct
        0x64t
        0x69t
        0x61t
        0x6et
        0x70t
        0x69t
        0x6et
        0x67t
        0x2et
        0x63t
        0x6ft
        0x6dt
        0x31t
        0x11t
        0x30t
        0xft
        0x6t
        0x3t
        0x55t
        0x4t
        0x3t
        0x13t
        0x8t
        0x54t
        0x75t
        0x20t
        0x59t
        0x69t
        0x6dt
        0x69t
        0x6et
        0x30t
        -0x7ft
        -0x61t
        0x30t
        0xdt
        0x6t
        0x9t
        0x2at
        -0x7at
        0x48t
        -0x7at
        -0x9t
        0xdt
        0x1t
        0x1t
        0x1t
        0x5t
        0x0t
        0x3t
        -0x7ft
        -0x73t
        0x0t
        0x30t
        -0x7ft
        -0x77t
        0x2t
        -0x7ft
        -0x7ft
        0x0t
        -0x35t
        -0x64t
        -0x46t
        -0x1t
        -0x53t
        -0x76t
        0xft
        -0x31t
        -0x1t
        0x42t
        0x19t
        -0xat
        0x7ft
        0x64t
        -0x59t
        -0x2ft
        0x3t
        -0x13t
        0x2ct
        -0x2ft
        0x3dt
        -0x47t
        0x66t
        -0x3bt
        -0x4at
        0x3at
        0x64t
        0x3at
        0x2et
        0x34t
        0x7at
        0x54t
        0x3ct
        -0x74t
        -0x4et
        0x7dt
        -0x2dt
        0x2bt
        -0x7et
        -0x43t
        -0x1bt
        -0x2ct
        0x26t
        -0x26t
        -0x25t
        -0xet
        -0x61t
        -0x72t
        0x7et
        0x78t
        -0x1dt
        0x65t
        0x4bt
        -0x22t
        -0x13t
        0x4et
        -0x2ct
        0x53t
        -0x6ct
        0x61t
        -0x59t
        0x32t
        -0x51t
        -0x10t
        -0x6dt
        0x57t
        -0x79t
        -0x25t
        -0x7t
        -0x78t
        -0x7et
        -0x6t
        -0x5ct
        -0xat
        0x70t
        -0xct
        -0x1ft
        0x6dt
        -0xct
        0x13t
        -0x7et
        -0x37t
        -0x45t
        0x11t
        -0x36t
        0x65t
        -0x68t
        0x24t
        0x64t
        -0x6dt
        -0x37t
        0x76t
        -0x7at
        -0x37t
        -0x34t
        -0x7ft
        -0x2et
        0x0t
        0x7ft
        -0x3ct
        0x45t
        -0x75t
        -0x23t
        -0x35t
        -0x68t
        -0x72t
        -0x5bt
        0x1et
        0x6ct
        -0x57t
        0x3at
        0x38t
        0x65t
        -0x77t
        0x3bt
        -0x5ft
        -0x2at
        -0x20t
        0x11t
        0x21t
        -0x3bt
        -0x21t
        -0x5ct
        -0x75t
        -0x28t
        0x21t
        -0x5ct
        -0x4dt
        0x2t
        0x3t
        0x1t
        0x0t
        0x1t
        0x30t
        0xdt
        0x6t
        0x9t
        0x2at
        -0x7at
        0x48t
        -0x7at
        -0x9t
        0xdt
        0x1t
        0x1t
        0x5t
        0x5t
        0x0t
        0x3t
        -0x7ft
        -0x7ft
        0x0t
        0xat
        0xbt
        -0x77t
        -0x3ft
        -0x56t
        0x70t
        -0x34t
        0xct
        0x48t
        0x7ct
        -0x24t
        -0x33t
        0x2at
        -0x51t
        0x1dt
        -0x52t
        0x7et
        0x4at
        0x62t
        0x7ft
        0x70t
        -0x61t
        -0x6ft
        0x8t
        -0x67t
        0x1ct
        -0x2bt
        -0x5dt
        0x63t
        -0x7bt
        -0x29t
        -0x1dt
        0x48t
        -0x4t
        -0x53t
        0x36t
        0x5at
        -0xdt
        -0x5t
        -0x3bt
        0x15t
        0x48t
        0x30t
        -0x80t
        0x3ft
        0x1t
        -0x46t
        0x52t
        0x20t
        0x6at
        0x70t
        -0x7bt
        0x72t
        0x48t
        -0x3at
        0x75t
        0x9t
        0x57t
        0x5ft
        0x71t
        -0x33t
        -0x33t
        -0x32t
        0x54t
        0x47t
        0x1ct
        0x22t
        0x5at
        -0x4dt
        -0x4ct
        -0x6bt
        0x7dt
        0x30t
        -0x64t
        0x64t
        -0x2t
        -0x6et
        0x8t
        -0x40t
        0x20t
        -0x5et
        0x39t
        -0x63t
        -0x2ft
        -0x59t
        -0x1et
        -0x53t
        0x2dt
        0x31t
        -0x55t
        0x3ct
        -0x31t
        0x36t
        -0x35t
        -0x48t
        -0x3et
        -0x65t
        -0x41t
        0x70t
        -0x7dt
        -0x30t
        -0x1at
        -0x6bt
        0x3bt
        -0x14t
        0x47t
        -0x73t
        0x75t
        -0x9t
        -0x7bt
        -0x1bt
        -0xdt
        0x28t
        0x6et
        -0x54t
        0x63t
        0x7at
        -0x42t
        -0x1dt
        -0x39t
        -0x68t
        0x3ct
        -0x49t
        0x7bt
        0x23t
        -0x24t
        0x3ct
        -0x5et
    .end array-data
.end method

.method public static sha1ToHexString([B)Ljava/lang/String;
    .registers 10
    .param p0, "cert"    # [B

    .prologue
    .line 55
    :try_start_0
    const-string v7, "SHA1"

    invoke-static {v7}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v4

    .line 56
    .local v4, "md":Ljava/security/MessageDigest;
    invoke-virtual {v4, p0}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v5

    .line 57
    .local v5, "publicKey":[B
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    .line 58
    .local v2, "hexString":Ljava/lang/StringBuilder;
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_10
    array-length v7, v5

    if-ge v3, v7, :cond_38

    .line 59
    aget-byte v7, v5, v3

    and-int/lit16 v7, v7, 0xff

    invoke-static {v7}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v7

    sget-object v8, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {v7, v8}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v0

    .line 60
    .local v0, "appendString":Ljava/lang/String;
    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v7

    const/4 v8, 0x1

    if-ne v7, v8, :cond_2d

    const-string v7, "0"

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 61
    :cond_2d
    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 62
    const-string v7, ":"

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 58
    add-int/lit8 v3, v3, 0x1

    goto :goto_10

    .line 64
    .end local v0    # "appendString":Ljava/lang/String;
    :cond_38
    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 65
    .local v6, "result":Ljava/lang/String;
    const/4 v7, 0x0

    invoke-virtual {v6}, Ljava/lang/String;->length()I

    move-result v8

    add-int/lit8 v8, v8, -0x1

    invoke-virtual {v6, v7, v8}, Ljava/lang/String;->substring(II)Ljava/lang/String;
    :try_end_46
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_46} :catch_48

    move-result-object v7

    .line 69
    .end local v2    # "hexString":Ljava/lang/StringBuilder;
    .end local v3    # "i":I
    .end local v4    # "md":Ljava/security/MessageDigest;
    .end local v5    # "publicKey":[B
    .end local v6    # "result":Ljava/lang/String;
    :goto_47
    return-object v7

    .line 66
    :catch_48
    move-exception v1

    .line 67
    .local v1, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v1}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 69
    const/4 v7, 0x0

    goto :goto_47
.end method


# virtual methods
.method public invoke(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
    .registers 11
    .param p1, "proxy"    # Ljava/lang/Object;
    .param p2, "method"    # Ljava/lang/reflect/Method;
    .param p3, "args"    # [Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    .prologue
    const/4 v4, 0x1

    const/4 v6, 0x0

    .line 32
    const-string v1, "hook"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "method name:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 33
    const-string v1, "getPackageInfo"

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_147

    .line 34
    array-length v1, p3

    const/4 v2, 0x3

    if-ne v1, v2, :cond_12e

    aget-object v1, p3, v6

    check-cast v1, Ljava/lang/String;

    const-string v2, "dianping"

    invoke-virtual {v1, v2}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_12e

    aget-object v1, p3, v4

    check-cast v1, Ljava/lang/Integer;

    .line 36
    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    const/16 v2, 0x40

    if-eq v1, v2, :cond_52

    aget-object v1, p3, v4

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    const/high16 v2, 0x8000000

    if-ne v1, v2, :cond_12e

    .line 37
    :cond_52
    const-string v2, "cjf"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    aget-object v3, p3, v6

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, "  "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    aget-object v1, p3, v4

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, " "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const/4 v3, 0x2

    aget-object v3, p3, v3

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v2, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 38
    iget-object v1, p0, Lcom/dianping/v1/PackageManagerProxy;->mPackageManager:Landroid/content/pm/IPackageManager;

    invoke-virtual {p2, v1, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/pm/PackageInfo;

    .line 39
    .local v0, "info":Landroid/content/pm/PackageInfo;
    if-nez v0, :cond_8d

    const/4 v0, 0x0

    .line 50
    .end local v0    # "info":Landroid/content/pm/PackageInfo;
    :goto_8c
    return-object v0

    .line 40
    .restart local v0    # "info":Landroid/content/pm/PackageInfo;
    :cond_8d
    const-string v1, "cjf"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "hook "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " before:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-wide v4, v0, Landroid/content/pm/PackageInfo;->firstInstallTime:J

    invoke-virtual {v2, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-wide v4, v0, Landroid/content/pm/PackageInfo;->lastUpdateTime:J

    invoke-virtual {v2, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \n"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, v0, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    aget-object v3, v3, v6

    invoke-virtual {v3}, Landroid/content/pm/Signature;->toByteArray()[B

    move-result-object v3

    invoke-static {v3}, Lcom/dianping/v1/PackageManagerProxy;->sha1ToHexString([B)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 43
    iget-object v1, v0, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    new-instance v2, Landroid/content/pm/Signature;

    iget-object v3, p0, Lcom/dianping/v1/PackageManagerProxy;->qqSign:[B

    invoke-direct {v2, v3}, Landroid/content/pm/Signature;-><init>([B)V

    aput-object v2, v1, v6

    .line 44
    const-string v1, "cjf"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "hook "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p2}, Ljava/lang/reflect/Method;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " after:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-wide v4, v0, Landroid/content/pm/PackageInfo;->firstInstallTime:J

    invoke-virtual {v2, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-wide v4, v0, Landroid/content/pm/PackageInfo;->lastUpdateTime:J

    invoke-virtual {v2, v4, v5}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, " \n"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, v0, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    aget-object v3, v3, v6

    invoke-virtual {v3}, Landroid/content/pm/Signature;->toByteArray()[B

    move-result-object v3

    invoke-static {v3}, Lcom/dianping/v1/PackageManagerProxy;->sha1ToHexString([B)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto/16 :goto_8c

    .line 47
    .end local v0    # "info":Landroid/content/pm/PackageInfo;
    :cond_12e
    const-string v1, "hook"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "getPackageInfo\u65b9\u6cd5\u5f62\u53c2\u6570\u91cf:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    array-length v3, p3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 50
    :cond_147
    iget-object v1, p0, Lcom/dianping/v1/PackageManagerProxy;->mPackageManager:Landroid/content/pm/IPackageManager;

    invoke-virtual {p2, v1, p3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    goto/16 :goto_8c
.end method
