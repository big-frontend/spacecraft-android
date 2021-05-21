#include <jni.h>
#include <string.h>
#include <time.h>
#include <math.h>
#include "data_struct_ext.h"
void j_getKey(int *key, int col, char *result) {
    int v3; // [sp+Ch] [bp-1Ch]
    int i; // [sp+10h] [bp-18h]

    for (i = 0; i < col; ++i) {
        v3 = key[i];
        if (v3 <= 0)
            break;
        result[i] = seed[v3 - 1];
    }
}
int j_getRand() {
    unsigned int v0; // r0
    int v1; // ST24_4
    int v2; // ST20_4
    int v3; // ST1C_4
    int v4; // ST18_4
    int v5; // ST14_4
    int v6; // ST10_4
    int v7; // ST0C_4
    int v8; // ST08_4
    int v9; // ST04_4
    int v11; // [sp+34h] [bp-Ch]
    v0 = time(0);
    srand(v0);
    switch (rand() % 16) {
        case 0:
            v1 = rand() % 7907;
            v11 = rand() % 7867 + v1;
            break;
        case 1:
            v2 = rand() % 7917;
            v11 = rand() % 7877 + v2;
            break;
        case 2:
            v3 = rand() % 7927;
            v11 = rand() % 7887 + v3;
            break;
        case 3:
            v4 = rand() % 7937;
            v11 = rand() % 7897 + v4;
            break;
        case 4:
            v5 = rand() % 7947;
            v11 = rand() % 7807 + v5;
            break;
        case 5:
            v6 = rand() % 7957;
            v11 = rand() % 7817 + v6;
            break;
        case 6:
            v7 = rand() % 7967;
            v11 = rand() % 7827 + v7;
            break;
        case 7:
            v8 = rand() % 7977;
            v11 = rand() % 7837 + v8;
            break;
        default:
            v9 = rand() % 7987;
            v11 = rand() % 7847 + v9;
            break;
    }
    return v11;
}

int j_getRand1() {
    int v0; // r11
    unsigned int v1; // r0
    int v2; // r0
    int v4; // [sp+Ch] [bp-3Ch]
    int v5; // [sp+10h] [bp-38h]
    int v6; // [sp+14h] [bp-34h]
    int v7; // [sp+18h] [bp-30h]
    int v8; // [sp+1Ch] [bp-2Ch]
    int v9; // [sp+20h] [bp-28h]
    int v10; // [sp+24h] [bp-24h]
    int v11; // [sp+28h] [bp-20h]
    int v12; // [sp+2Ch] [bp-1Ch]
    int v13; // [sp+30h] [bp-18h]
    int v14; // [sp+38h] [bp-10h]

    v14 = v0;
    v4 = 373;
    v5 = 379;
    v6 = 383;
    v7 = 389;
    v8 = 397;
    v9 = 401;
    v10 = 409;
    v11 = 419;
    v12 = 421;
    v13 = 431;
    v1 = time(0);
    srand(v1);
    v2 = rand();
    return *(&v4
             + v2
             - 10
               * (((unsigned int) ((unsigned long long) (1717986919LL * v2) >> 32) >> 2)
                  + ((unsigned int) ((unsigned long long) (1717986919LL * v2) >> 32) >> 31)));
}

char *j_getRand2(int i) {
    char *result; // r0
    char *v2; // [sp+10h] [bp-30h]
    int v3; // [sp+34h] [bp-Ch]

    switch (i) {
        case 1:
            v2 = (char *) &elf_gnu_hash_nbuckets + 1;
            break;
        case 2:
            v2 = const_cast<char *>("__gnu_Unwind_Find_exidx" + 13);
            break;
        case 3:
            v2 = (_BYTE * )(elf_hash_bucket + 55);
            break;
        case 4:
            v2 = (_BYTE * )(elf_gnu_hash_chain + 29);
            break;
        case 5:
            v2 = const_cast<char *>("getRandX" + 8);
            break;
        case 6:
            v2 = (_BYTE * )(elf_gnu_hash_indexes + 23);
            break;
        case 7:
            v2 = const_cast<char *>("fflush" + 4);
            break;
        default:
            v2 = 0;
            break;
    }
    result = v2;
    return result;
}

int j_getRand3() {
    unsigned int v0; // r0
    int result; // r0
    time_t v2; // [sp+8h] [bp-18h]
    int v3; // [sp+8h] [bp-18h]
    time_t timer; // [sp+10h] [bp-10h]
    int v5; // [sp+14h] [bp-Ch]

    timer = time(0);
    v2 = time(&timer);
    v0 = time(0);
    srand(v0);
    if (rand() % 2 == 1)
        v3 = -16843010 - v2;
    else
        v3 = v2 + 269488144;
    result = v3;
    return result;
}

time_t j_getRand4() {
    time_t timer; // [sp+10h] [bp-10h]
    timer = time(0);
    return time(&timer);
}

int j_getRand5() {
    unsigned int v0; // r0

    v0 = time(0);
    srand(v0);
    return 2 * (rand() % 305419896);
}

int j_a2i(char a) {
    char v2; // [sp+6h] [bp-2h]
    if ((signed int) a >= 48 && (signed int) a <= 57)v2 = a - 48;
    if ((signed int) a >= 65 && (signed int) a <= 70)v2 = a - 55;
    return v2;
}

int j_i2a(int i) {
    char v2; // [sp+7h] [bp-5h]
    if (i >= 0 && i <= 9) v2 = i + 48;
    if (i >= 10 && i <= 15)v2 = i + 55;
    return v2;
}

char *j_ss1(const char *str, int a2, jlong token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    int v14; // ST30_4
    int v15; // lr
    int v16; // r0
    char *result; // r0
    int v18; // [sp+2Ch] [bp-35Ch]
    int v19; // [sp+34h] [bp-354h]
    int v20; // [sp+38h] [bp-350h]
    int v21; // [sp+3Ch] [bp-34Ch]
    int v22; // [sp+54h] [bp-334h]
    int v23; // [sp+80h] [bp-308h]
    int v24; // [sp+8Ch] [bp-2FCh]
    int v25; // [sp+98h] [bp-2F0h]
    int v26; // [sp+9Ch] [bp-2ECh]
    int v27; // [sp+A0h] [bp-2E8h]
    int v28; // [sp+A4h] [bp-2E4h]
    int v29; // [sp+A8h] [bp-2E0h]
    int v30; // [sp+ACh] [bp-2DCh]
    int v31; // [sp+B0h] [bp-2D8h]
    int v32; // [sp+B4h] [bp-2D4h]
    int v33; // [sp+B8h] [bp-2D0h]
    int v34; // [sp+BCh] [bp-2CCh]
    int v35; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v38; // [sp+CCh] [bp-2BCh]
    char *v39; // [sp+D0h] [bp-2B8h]
    int v40; // [sp+D4h] [bp-2B4h]
    long long v41; // [sp+D8h] [bp-2B0h]
    const char *v42; // [sp+E4h] [bp-2A4h]
    char *v43; // [sp+E8h] [bp-2A0h]
    char v44[320]; // [sp+ECh] [bp-29Ch]
    char v45[320]; // [sp+22Ch] [bp-15Ch]
    int v46; // [sp+36Ch] [bp-1Ch]

    v42 = str;
    v41 = token_sizse2;
    v23 = j_getRand4();
    v29 = a + 1732584193;
    v28 = -271733879 - a;
    v27 = a - 1732584194;
    v26 = 271733878 - a;
    v25 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v45[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v45[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v45[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v45[4 * v9] = -899497514 - b;
    }
    v10 = v41 + ((unsigned int) (SHIDWORD(v41) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v41 - v10 < 57)
        v22 = ((v41 + ((unsigned int) (SHIDWORD(v41) >> 31) >> 26)) & 0xFFFFFFC0) - v41 + 64;
    else
        v22 = ((v41 + ((unsigned int) (SHIDWORD(v41) >> 31) >> 26)) & 0xFFFFFFC0) - v41 + 128;
    size = v22 + v41;
    v39 = (char *) malloc(v22 + v41);
    if (v39) {
        for (i = 0; i < v41; ++i)
            v39[i + 3 - 2 * (i % 4)] = v42[i];
        v39[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v39[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v39[size - 4] = 8 * v41;
        *(_DWORD * ) & v39[size - 8] = v41 >> 29;
        v38 = (size_t) &v39[size];
        while ((unsigned int) v39 < v38) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v44[4 * i] = *(_DWORD * ) & v39[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v44[4 * i];
                v40 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v40, 31);
            }
            v34 = v29;
            v33 = v28;
            v32 = v27;
            v31 = v26;
            v30 = v25;
            for (i = 0; i <= 79; ++i) {
                v40 = v34;
                if (i > 39) {
                    if (i > 59)
                        v19 = v33 ^ v32 ^ v31;
                    else
                        v19 = v33 & (v32 | v31) | v32 & v31;
                    v20 = v19;
                } else {
                    if (i > 19)
                        v21 = v33 ^ v32 ^ v31;
                    else
                        v21 = v31 & ~v33 | v32 & v33;
                    v20 = v21;
                }
                v12 = v20 + __ROR4__(v34, 27) + v30 + *(_DWORD * ) & v44[4 * i];
                v13 = *(_DWORD * ) & v45[4 * i];
                v35 = v12 + v13;
                v30 = v31;
                v31 = v32;
                v40 = v33;
                v32 = __ROR4__(v33, 2);
                v33 = v34;
                v34 = v12 + v13;
            }
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v26 += v31;
            v25 += v30;
            v39 += 64;
        }
        free(&v39[-size]);
        v14 = j_getRand2(1);
        v24 = j_getRand1() * v14;
        v18 = j_getRand5();
        v15 = v18 - 1;
        if (!((v23 - j_getRand4()) / 200))
            v15 = v18;
        v16 = j_getRand3();
        sprintf((char *) src, "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X", v29, v28, v27, v26, v25,
                2146566143 - a * a, 2094460654 - b * b, v24, v15, v16);
        v43 = src;
    } else {
        v43 = 0;
    }
    result = v43;
    return result;
}

char *j_ss2(const char *token, long long int token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    char *v14; // ST30_4
    time_t v15; // r0
    signed int v16; // r1
    int v17; // ST88_4
    int v18; // r0
    char *result; // r0
    int v20; // [sp+2Ch] [bp-35Ch]
    int v21; // [sp+34h] [bp-354h]
    int v22; // [sp+38h] [bp-350h]
    int v23; // [sp+3Ch] [bp-34Ch]
    int v24; // [sp+54h] [bp-334h]
    time_t v25; // [sp+80h] [bp-308h]
    int v26; // [sp+8Ch] [bp-2FCh]
    int v27; // [sp+98h] [bp-2F0h]
    int v28; // [sp+9Ch] [bp-2ECh]
    int v29; // [sp+A0h] [bp-2E8h]
    int v30; // [sp+A4h] [bp-2E4h]
    int v31; // [sp+A8h] [bp-2E0h]
    int v32; // [sp+ACh] [bp-2DCh]
    int v33; // [sp+B0h] [bp-2D8h]
    int v34; // [sp+B4h] [bp-2D4h]
    int v35; // [sp+B8h] [bp-2D0h]
    int v36; // [sp+BCh] [bp-2CCh]
    int v37; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v40; // [sp+CCh] [bp-2BCh]
    char *v41; // [sp+D0h] [bp-2B8h]
    int v42; // [sp+D4h] [bp-2B4h]
    long long v43; // [sp+D8h] [bp-2B0h]
    const char *v44; // [sp+E4h] [bp-2A4h]
    char *v45; // [sp+E8h] [bp-2A0h]
    char v46[320]; // [sp+ECh] [bp-29Ch]
    char v47[320]; // [sp+22Ch] [bp-15Ch]
    int v48; // [sp+36Ch] [bp-1Ch]

    v44 = token;
    v43 = token_sizse2;
    v25 = j_getRand4();
    v31 = a + 1732584194;
    v30 = -271733879 - a;
    v29 = a - 1732584194;
    v28 = 271733878 - a;
    v27 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v47[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v47[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v47[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v47[4 * v9] = -899497514 - b;
    }
    v10 = v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v43 - v10 < 57)
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 64;
    else
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 128;
    size = v24 + v43;
    v41 = (char *) malloc(v24 + v43);
    if (v41) {
        for (i = 0; i < v43; ++i)
            v41[i + 3 - 2 * (i % 4)] = v44[i];
        v41[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v41[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v41[size - 4] = 8 * v43;
        *(_DWORD * ) & v41[size - 8] = v43 >> 29;
        v40 = (size_t) &v41[size];
        while ((unsigned int) v41 < v40) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v46[4 * i] = *(_DWORD * ) & v41[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v46[4 * i];
                v42 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v42, 31);
            }
            v36 = v31;
            v35 = v30;
            v34 = v29;
            v33 = v28;
            v32 = v27;
            for (i = 0; i <= 79; ++i) {
                v42 = v36;
                if (i > 39) {
                    if (i > 59)
                        v21 = v35 ^ v34 ^ v33;
                    else
                        v21 = v35 & (v34 | v33) | v34 & v33;
                    v22 = v21;
                } else {
                    if (i > 19)
                        v23 = v35 ^ v34 ^ v33;
                    else
                        v23 = v33 & ~v35 | v34 & v35;
                    v22 = v23;
                }
                v12 = v22 + __ROR4__(v36, 27) + v32 + *(_DWORD * ) & v46[4 * i];
                v13 = *(_DWORD * ) & v47[4 * i];
                v37 = v12 + v13;
                v32 = v33;
                v33 = v34;
                v42 = v35;
                v34 = __ROR4__(v35, 2);
                v35 = v36;
                v36 = v12 + v13;
            }
            v31 += v36;
            v30 += v35;
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v41 += 64;
        }
        free(&v41[-size]);
        v14 = j_getRand2(2);
        v26 = j_getRand1() * (_DWORD) v14;
        v20 = j_getRand5();
        v15 = j_getRand4();
        v16 = 3;
        if (!((v25 - v15) / 200))
            v16 = 2;
        v17 = v20 - v16;
        v18 = j_getRand3();
        sprintf(
                (char *) src,
                "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X",
                v31,
                v30,
                v29,
                v28,
                v27,
                2146631679 - a * a,
                2094395118 - b * b,
                v26,
                v17,
                v18);
        v45 = src;
    } else {
        v45 = 0;
    }
    result = v45;
    return result;
}

char *j_ss3(const char *token, long long int token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    char *v14; // ST30_4
    time_t v15; // r0
    signed int v16; // r1
    int v17; // ST88_4
    int v18; // r0
    char *result; // r0
    int v20; // [sp+2Ch] [bp-35Ch]
    int v21; // [sp+34h] [bp-354h]
    int v22; // [sp+38h] [bp-350h]
    int v23; // [sp+3Ch] [bp-34Ch]
    int v24; // [sp+54h] [bp-334h]
    time_t v25; // [sp+80h] [bp-308h]
    int v26; // [sp+8Ch] [bp-2FCh]
    int v27; // [sp+98h] [bp-2F0h]
    int v28; // [sp+9Ch] [bp-2ECh]
    int v29; // [sp+A0h] [bp-2E8h]
    int v30; // [sp+A4h] [bp-2E4h]
    int v31; // [sp+A8h] [bp-2E0h]
    int v32; // [sp+ACh] [bp-2DCh]
    int v33; // [sp+B0h] [bp-2D8h]
    int v34; // [sp+B4h] [bp-2D4h]
    int v35; // [sp+B8h] [bp-2D0h]
    int v36; // [sp+BCh] [bp-2CCh]
    int v37; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v40; // [sp+CCh] [bp-2BCh]
    char *v41; // [sp+D0h] [bp-2B8h]
    int v42; // [sp+D4h] [bp-2B4h]
    long long v43; // [sp+D8h] [bp-2B0h]
    const char *v44; // [sp+E4h] [bp-2A4h]
    char *v45; // [sp+E8h] [bp-2A0h]
    char v46[320]; // [sp+ECh] [bp-29Ch]
    char v47[320]; // [sp+22Ch] [bp-15Ch]
    int v48; // [sp+36Ch] [bp-1Ch]

    v44 = token;
    v43 = token_sizse2;
    v25 = j_getRand4();
    v31 = a + 1732584195;
    v30 = -271733879 - a;
    v29 = a - 1732584194;
    v28 = 271733878 - a;
    v27 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v47[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v47[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v47[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v47[4 * v9] = -899497514 - b;
    }
    v10 = v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v43 - v10 < 57)
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 64;
    else
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 128;
    size = v24 + v43;
    v41 = (char *) malloc(v24 + v43);
    if (v41) {
        for (i = 0; i < v43; ++i)
            v41[i + 3 - 2 * (i % 4)] = v44[i];
        v41[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v41[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v41[size - 4] = 8 * v43;
        *(_DWORD * ) & v41[size - 8] = v43 >> 29;
        v40 = (size_t) &v41[size];
        while ((unsigned int) v41 < v40) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v46[4 * i] = *(_DWORD * ) & v41[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v46[4 * i];
                v42 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v42, 31);
            }
            v36 = v31;
            v35 = v30;
            v34 = v29;
            v33 = v28;
            v32 = v27;
            for (i = 0; i <= 79; ++i) {
                v42 = v36;
                if (i > 39) {
                    if (i > 59)
                        v21 = v35 ^ v34 ^ v33;
                    else
                        v21 = v35 & (v34 | v33) | v34 & v33;
                    v22 = v21;
                } else {
                    if (i > 19)
                        v23 = v35 ^ v34 ^ v33;
                    else
                        v23 = v33 & ~v35 | v34 & v35;
                    v22 = v23;
                }
                v12 = v22 + __ROR4__(v36, 27) + v32 + *(_DWORD * ) & v46[4 * i];
                v13 = *(_DWORD * ) & v47[4 * i];
                v37 = v12 + v13;
                v32 = v33;
                v33 = v34;
                v42 = v35;
                v34 = __ROR4__(v35, 2);
                v35 = v36;
                v36 = v12 + v13;
            }
            v31 += v36;
            v30 += v35;
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v41 += 64;
        }
        free(&v41[-size]);
        v14 = j_getRand2(3);
        v26 = j_getRand1() * (_DWORD) v14;
        v20 = j_getRand5();
        v15 = j_getRand4();
        v16 = 5;
        if (!((v25 - v15) / 200))
            v16 = 4;
        v17 = v20 - v16;
        v18 = j_getRand3();
        sprintf(
                (char *) src,
                "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X",
                v31,
                v30,
                v29,
                v28,
                v27,
                2146697215 - a * a,
                2094329582 - b * b,
                v26,
                v17,
                v18);
        v45 = src;
    } else {
        v45 = 0;
    }
    result = v45;
    return result;
}

char *j_ss4(const char *token, long long int token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    char *v14; // ST30_4
    time_t v15; // r0
    signed int v16; // r1
    int v17; // ST88_4
    int v18; // r0
    char *result; // r0
    int v20; // [sp+2Ch] [bp-35Ch]
    int v21; // [sp+34h] [bp-354h]
    int v22; // [sp+38h] [bp-350h]
    int v23; // [sp+3Ch] [bp-34Ch]
    int v24; // [sp+54h] [bp-334h]
    time_t v25; // [sp+80h] [bp-308h]
    int v26; // [sp+8Ch] [bp-2FCh]
    int v27; // [sp+98h] [bp-2F0h]
    int v28; // [sp+9Ch] [bp-2ECh]
    int v29; // [sp+A0h] [bp-2E8h]
    int v30; // [sp+A4h] [bp-2E4h]
    int v31; // [sp+A8h] [bp-2E0h]
    int v32; // [sp+ACh] [bp-2DCh]
    int v33; // [sp+B0h] [bp-2D8h]
    int v34; // [sp+B4h] [bp-2D4h]
    int v35; // [sp+B8h] [bp-2D0h]
    int v36; // [sp+BCh] [bp-2CCh]
    int v37; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v40; // [sp+CCh] [bp-2BCh]
    char *v41; // [sp+D0h] [bp-2B8h]
    int v42; // [sp+D4h] [bp-2B4h]
    long long v43; // [sp+D8h] [bp-2B0h]
    const char *v44; // [sp+E4h] [bp-2A4h]
    char *v45; // [sp+E8h] [bp-2A0h]
    char v46[320]; // [sp+ECh] [bp-29Ch]
    char v47[320]; // [sp+22Ch] [bp-15Ch]
    int v48; // [sp+36Ch] [bp-1Ch]

    v44 = token;
    v43 = token_sizse2;
    v25 = j_getRand4();
    v31 = a + 1732584196;
    v30 = -271733879 - a;
    v29 = a - 1732584194;
    v28 = 271733878 - a;
    v27 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v47[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v47[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v47[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v47[4 * v9] = -899497514 - b;
    }
    v10 = v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v43 - v10 < 57)
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 64;
    else
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 128;
    size = v24 + v43;
    v41 = (char *) malloc(v24 + v43);
    if (v41) {
        for (i = 0; i < v43; ++i)
            v41[i + 3 - 2 * (i % 4)] = v44[i];
        v41[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v41[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v41[size - 4] = 8 * v43;
        *(_DWORD * ) & v41[size - 8] = v43 >> 29;
        v40 = (size_t) &v41[size];
        while ((unsigned int) v41 < v40) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v46[4 * i] = *(_DWORD * ) & v41[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v46[4 * i];
                v42 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v42, 31);
            }
            v36 = v31;
            v35 = v30;
            v34 = v29;
            v33 = v28;
            v32 = v27;
            for (i = 0; i <= 79; ++i) {
                v42 = v36;
                if (i > 39) {
                    if (i > 59)
                        v21 = v35 ^ v34 ^ v33;
                    else
                        v21 = v35 & (v34 | v33) | v34 & v33;
                    v22 = v21;
                } else {
                    if (i > 19)
                        v23 = v35 ^ v34 ^ v33;
                    else
                        v23 = v33 & ~v35 | v34 & v35;
                    v22 = v23;
                }
                v12 = v22 + __ROR4__(v36, 27) + v32 + *(_DWORD * ) & v46[4 * i];
                v13 = *(_DWORD * ) & v47[4 * i];
                v37 = v12 + v13;
                v32 = v33;
                v33 = v34;
                v42 = v35;
                v34 = __ROR4__(v35, 2);
                v35 = v36;
                v36 = v12 + v13;
            }
            v31 += v36;
            v30 += v35;
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v41 += 64;
        }
        free(&v41[-size]);
        v14 = j_getRand2(4);
        v26 = j_getRand1() * (_DWORD) v14;
        v20 = j_getRand5();
        v15 = j_getRand4();
        v16 = 7;
        if (!((v25 - v15) / 200))
            v16 = 6;
        v17 = v20 - v16;
        v18 = j_getRand3();
        sprintf(
                (char *) src,
                "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X",
                v31,
                v30,
                v29,
                v28,
                v27,
                2146762751 - a * a,
                2094264046 - b * b,
                v26,
                v17,
                v18);
        v45 = src;
    } else {
        v45 = 0;
    }
    result = v45;
    return result;
}

char *j_ss5(const char *token, long long int token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    char *v14; // ST30_4
    time_t v15; // r0
    signed int v16; // r1
    int v17; // ST88_4
    int v18; // r0
    char *result; // r0
    int v20; // [sp+2Ch] [bp-35Ch]
    int v21; // [sp+34h] [bp-354h]
    int v22; // [sp+38h] [bp-350h]
    int v23; // [sp+3Ch] [bp-34Ch]
    int v24; // [sp+54h] [bp-334h]
    time_t v25; // [sp+80h] [bp-308h]
    int v26; // [sp+8Ch] [bp-2FCh]
    int v27; // [sp+98h] [bp-2F0h]
    int v28; // [sp+9Ch] [bp-2ECh]
    int v29; // [sp+A0h] [bp-2E8h]
    int v30; // [sp+A4h] [bp-2E4h]
    int v31; // [sp+A8h] [bp-2E0h]
    int v32; // [sp+ACh] [bp-2DCh]
    int v33; // [sp+B0h] [bp-2D8h]
    int v34; // [sp+B4h] [bp-2D4h]
    int v35; // [sp+B8h] [bp-2D0h]
    int v36; // [sp+BCh] [bp-2CCh]
    int v37; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v40; // [sp+CCh] [bp-2BCh]
    char *v41; // [sp+D0h] [bp-2B8h]
    int v42; // [sp+D4h] [bp-2B4h]
    long long v43; // [sp+D8h] [bp-2B0h]
    const char *v44; // [sp+E4h] [bp-2A4h]
    char *v45; // [sp+E8h] [bp-2A0h]
    char v46[320]; // [sp+ECh] [bp-29Ch]
    char v47[320]; // [sp+22Ch] [bp-15Ch]
    int v48; // [sp+36Ch] [bp-1Ch]

    v44 = token;
    v43 = token_sizse2;
    v25 = j_getRand4();
    v31 = a + 1732584197;
    v30 = -271733879 - a;
    v29 = a - 1732584194;
    v28 = 271733878 - a;
    v27 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v47[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v47[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v47[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v47[4 * v9] = -899497514 - b;
    }
    v10 = v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v43 - v10 < 57)
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 64;
    else
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 128;
    size = v24 + v43;
    v41 = (char *) malloc(v24 + v43);
    if (v41) {
        for (i = 0; i < v43; ++i)
            v41[i + 3 - 2 * (i % 4)] = v44[i];
        v41[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v41[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v41[size - 4] = 8 * v43;
        *(_DWORD * ) & v41[size - 8] = v43 >> 29;
        v40 = (size_t) &v41[size];
        while ((unsigned int) v41 < v40) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v46[4 * i] = *(_DWORD * ) & v41[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v46[4 * i];
                v42 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v42, 31);
            }
            v36 = v31;
            v35 = v30;
            v34 = v29;
            v33 = v28;
            v32 = v27;
            for (i = 0; i <= 79; ++i) {
                v42 = v36;
                if (i > 39) {
                    if (i > 59)
                        v21 = v35 ^ v34 ^ v33;
                    else
                        v21 = v35 & (v34 | v33) | v34 & v33;
                    v22 = v21;
                } else {
                    if (i > 19)
                        v23 = v35 ^ v34 ^ v33;
                    else
                        v23 = v33 & ~v35 | v34 & v35;
                    v22 = v23;
                }
                v12 = v22 + __ROR4__(v36, 27) + v32 + *(_DWORD * ) & v46[4 * i];
                v13 = *(_DWORD * ) & v47[4 * i];
                v37 = v12 + v13;
                v32 = v33;
                v33 = v34;
                v42 = v35;
                v34 = __ROR4__(v35, 2);
                v35 = v36;
                v36 = v12 + v13;
            }
            v31 += v36;
            v30 += v35;
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v41 += 64;
        }
        free(&v41[-size]);
        v14 = j_getRand2(5);
        v26 = j_getRand1() * (_DWORD) v14;
        v20 = j_getRand5();
        v15 = j_getRand4();
        v16 = 9;
        if (!((v25 - v15) / 200))
            v16 = 8;
        v17 = v20 - v16;
        v18 = j_getRand3();
        sprintf(
                (char *) src,
                "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X",
                v31,
                v30,
                v29,
                v28,
                v27,
                2146828287 - a * a,
                2094198510 - b * b,
                v26,
                v17,
                v18);
        v45 = src;
    } else {
        v45 = 0;
    }
    result = v45;
    return result;
}

char *j_ss6(const char *token, long long int token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    char *v14; // ST30_4
    time_t v15; // r0
    signed int v16; // r1
    int v17; // ST88_4
    int v18; // r0
    char *result; // r0
    int v20; // [sp+2Ch] [bp-35Ch]
    int v21; // [sp+34h] [bp-354h]
    int v22; // [sp+38h] [bp-350h]
    int v23; // [sp+3Ch] [bp-34Ch]
    int v24; // [sp+54h] [bp-334h]
    time_t v25; // [sp+80h] [bp-308h]
    int v26; // [sp+8Ch] [bp-2FCh]
    int v27; // [sp+98h] [bp-2F0h]
    int v28; // [sp+9Ch] [bp-2ECh]
    int v29; // [sp+A0h] [bp-2E8h]
    int v30; // [sp+A4h] [bp-2E4h]
    int v31; // [sp+A8h] [bp-2E0h]
    int v32; // [sp+ACh] [bp-2DCh]
    int v33; // [sp+B0h] [bp-2D8h]
    int v34; // [sp+B4h] [bp-2D4h]
    int v35; // [sp+B8h] [bp-2D0h]
    int v36; // [sp+BCh] [bp-2CCh]
    int v37; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v40; // [sp+CCh] [bp-2BCh]
    char *v41; // [sp+D0h] [bp-2B8h]
    int v42; // [sp+D4h] [bp-2B4h]
    long long v43; // [sp+D8h] [bp-2B0h]
    const char *v44; // [sp+E4h] [bp-2A4h]
    char *v45; // [sp+E8h] [bp-2A0h]
    char v46[320]; // [sp+ECh] [bp-29Ch]
    char v47[320]; // [sp+22Ch] [bp-15Ch]
    int v48; // [sp+36Ch] [bp-1Ch]

    v44 = token;
    v43 = token_sizse2;
    v25 = j_getRand4();
    v31 = a + 1732584198;
    v30 = -271733879 - a;
    v29 = a - 1732584194;
    v28 = 271733878 - a;
    v27 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v47[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v47[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v47[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v47[4 * v9] = -899497514 - b;
    }
    v10 = v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v43 - v10 < 57)
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 64;
    else
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 128;
    size = v24 + v43;
    v41 = (char *) malloc(v24 + v43);
    if (v41) {
        for (i = 0; i < v43; ++i)
            v41[i + 3 - 2 * (i % 4)] = v44[i];
        v41[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v41[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v41[size - 4] = 8 * v43;
        *(_DWORD * ) & v41[size - 8] = v43 >> 29;
        v40 = (size_t) &v41[size];
        while ((unsigned int) v41 < v40) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v46[4 * i] = *(_DWORD * ) & v41[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v46[4 * i];
                v42 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v42, 31);
            }
            v36 = v31;
            v35 = v30;
            v34 = v29;
            v33 = v28;
            v32 = v27;
            for (i = 0; i <= 79; ++i) {
                v42 = v36;
                if (i > 39) {
                    if (i > 59)
                        v21 = v35 ^ v34 ^ v33;
                    else
                        v21 = v35 & (v34 | v33) | v34 & v33;
                    v22 = v21;
                } else {
                    if (i > 19)
                        v23 = v35 ^ v34 ^ v33;
                    else
                        v23 = v33 & ~v35 | v34 & v35;
                    v22 = v23;
                }
                v12 = v22 + __ROR4__(v36, 27) + v32 + *(_DWORD * ) & v46[4 * i];
                v13 = *(_DWORD * ) & v47[4 * i];
                v37 = v12 + v13;
                v32 = v33;
                v33 = v34;
                v42 = v35;
                v34 = __ROR4__(v35, 2);
                v35 = v36;
                v36 = v12 + v13;
            }
            v31 += v36;
            v30 += v35;
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v41 += 64;
        }
        free(&v41[-size]);
        v14 = j_getRand2(6);
        v26 = j_getRand1() * (_DWORD) v14;
        v20 = j_getRand5();
        v15 = j_getRand4();
        v16 = 11;
        if (!((v25 - v15) / 200))
            v16 = 10;
        v17 = v20 - v16;
        v18 = j_getRand3();
        sprintf(
                (char *) src,
                "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X",
                v31,
                v30,
                v29,
                v28,
                v27,
                2146893823 - a * a,
                2094132974 - b * b,
                v26,
                v17,
                v18);
        v45 = src;
    } else {
        v45 = 0;
    }
    result = v45;
    return result;
}

char *j_ss7(const char *token, long long int token_sizse2, int a, int b, char *src) {
    int v6; // r1
    int v7; // r1
    int v8; // r1
    int v9; // r1
    long long v10; // r2
    char *v11; // r0
    int v12; // r0
    int v13; // r2
    char *v14; // ST30_4
    time_t v15; // r0
    signed int v16; // r1
    int v17; // ST88_4
    int v18; // r0
    char *result; // r0
    int v20; // [sp+2Ch] [bp-35Ch]
    int v21; // [sp+34h] [bp-354h]
    int v22; // [sp+38h] [bp-350h]
    int v23; // [sp+3Ch] [bp-34Ch]
    int v24; // [sp+54h] [bp-334h]
    time_t v25; // [sp+80h] [bp-308h]
    int v26; // [sp+8Ch] [bp-2FCh]
    int v27; // [sp+98h] [bp-2F0h]
    int v28; // [sp+9Ch] [bp-2ECh]
    int v29; // [sp+A0h] [bp-2E8h]
    int v30; // [sp+A4h] [bp-2E4h]
    int v31; // [sp+A8h] [bp-2E0h]
    int v32; // [sp+ACh] [bp-2DCh]
    int v33; // [sp+B0h] [bp-2D8h]
    int v34; // [sp+B4h] [bp-2D4h]
    int v35; // [sp+B8h] [bp-2D0h]
    int v36; // [sp+BCh] [bp-2CCh]
    int v37; // [sp+C0h] [bp-2C8h]
    int i; // [sp+C4h] [bp-2C4h]
    size_t size; // [sp+C8h] [bp-2C0h]
    size_t v40; // [sp+CCh] [bp-2BCh]
    char *v41; // [sp+D0h] [bp-2B8h]
    int v42; // [sp+D4h] [bp-2B4h]
    long long v43; // [sp+D8h] [bp-2B0h]
    const char *v44; // [sp+E4h] [bp-2A4h]
    char *v45; // [sp+E8h] [bp-2A0h]
    char v46[320]; // [sp+ECh] [bp-29Ch]
    char v47[320]; // [sp+22Ch] [bp-15Ch]
    int v48; // [sp+36Ch] [bp-1Ch]

    v44 = token;
    v43 = token_sizse2;
    v25 = j_getRand4();
    v31 = a + 1732584199;
    v30 = -271733879 - a;
    v29 = a - 1732584194;
    v28 = 271733878 - a;
    v27 = a - 1009589776;
    i = 0;
    while (i <= 19) {
        v6 = i++;
        *(_DWORD * ) & v47[4 * v6] = b + 1518500249;
    }
    i = 20;
    while (i <= 39) {
        v7 = i++;
        *(_DWORD * ) & v47[4 * v7] = 1859775393 - b;
    }
    i = 40;
    while (i <= 59) {
        v8 = i++;
        *(_DWORD * ) & v47[4 * v8] = b - 1894007588;
    }
    i = 60;
    while (i <= 79) {
        v9 = i++;
        *(_DWORD * ) & v47[4 * v9] = -899497514 - b;
    }
    v10 = v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26);
    LODWORD(v10) = v10 & 0xFFFFFFC0;
    if (v43 - v10 < 57)
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 64;
    else
        v24 = ((v43 + ((unsigned int) (SHIDWORD(v43) >> 31) >> 26)) & 0xFFFFFFC0) - v43 + 128;
    size = v24 + v43;
    v41 = (char *) malloc(v24 + v43);
    if (v41) {
        for (i = 0; i < v43; ++i)
            v41[i + 3 - 2 * (i % 4)] = v44[i];
        v41[i + 3 - 2 * (i % 4)] = -128;
        ++i;
        while (i < (signed int) size) {
            v41[i + 3 - 2 * (i % 4)] = 0;
            ++i;
        }
        *(_DWORD * ) & v41[size - 4] = 8 * v43;
        *(_DWORD * ) & v41[size - 8] = v43 >> 29;
        v40 = (size_t) &v41[size];
        while ((unsigned int) v41 < v40) {
            for (i = 0; i <= 15; ++i)
                *(_DWORD * ) & v46[4 * i] = *(_DWORD * ) & v41[4 * i];
            for (i = 16; i <= 79; ++i) {
                v11 = &v46[4 * i];
                v42 = *((_DWORD *) v11 - 3) ^ *((_DWORD *) v11 - 8) ^ *((_DWORD *) v11 - 14) ^
                      *((_DWORD *) v11 - 16);
                *(_DWORD *) v11 = __ROR4__(v42, 31);
            }
            v36 = v31;
            v35 = v30;
            v34 = v29;
            v33 = v28;
            v32 = v27;
            for (i = 0; i <= 79; ++i) {
                v42 = v36;
                if (i > 39) {
                    if (i > 59)
                        v21 = v35 ^ v34 ^ v33;
                    else
                        v21 = v35 & (v34 | v33) | v34 & v33;
                    v22 = v21;
                } else {
                    if (i > 19)
                        v23 = v35 ^ v34 ^ v33;
                    else
                        v23 = v33 & ~v35 | v34 & v35;
                    v22 = v23;
                }
                v12 = v22 + __ROR4__(v36, 27) + v32 + *(_DWORD * ) & v46[4 * i];
                v13 = *(_DWORD * ) & v47[4 * i];
                v37 = v12 + v13;
                v32 = v33;
                v33 = v34;
                v42 = v35;
                v34 = __ROR4__(v35, 2);
                v35 = v36;
                v36 = v12 + v13;
            }
            v31 += v36;
            v30 += v35;
            v29 += v34;
            v28 += v33;
            v27 += v32;
            v41 += 64;
        }
        free(&v41[-size]);
        v14 = j_getRand2(7);
        v26 = j_getRand1() * (_DWORD) v14;
        v20 = j_getRand5();
        v15 = j_getRand4();
        v16 = 13;
        if (!((v25 - v15) / 200))
            v16 = 12;
        v17 = v20 - v16;
        v18 = j_getRand3();
        sprintf(
                (char *) src,
                "%08X%08X%08X%08X%08X%08X%08X%08X%08X%08X",
                v31,
                v30,
                v29,
                v28,
                v27,
                2146959359 - a * a,
                2094067438 - b * b,
                v26,
                v17,
                v18);
        v45 = src;
    } else {
        v45 = 0;
    }
    result = v45;
    return result;
}

char *
j_s1(const char *token, int token_size/*没有被使用*/, long long token_sizse2, jlong time, char *output) {
    int a = j_getRand();
    int b = j_getRand();
    char src;
    switch (j_getRand() % 7) {
        case 0:
            j_ss1(token, a, token_sizse2, a, b, &src);
            break;
        case 1:
            j_ss2(token, token_sizse2, a, b, &src);
            break;
        case 2:
            j_ss3(token, token_sizse2, a, b, &src);
            break;
        case 3:
            j_ss4(token, token_sizse2, a, b, &src);
            break;
        case 4:
            j_ss5(token, token_sizse2, a, b, &src);
            break;
        case 5:
            j_ss6(token, token_sizse2, a, b, &src);
            break;
        case 6:
            j_ss7(token, token_sizse2, a, b, &src);
            break;
        default:
            j_ss1(token, a, token_sizse2, a, b, &src);
            break;
    }
    int v9 = 0;
    strcpy(output, &src);
    while (v9 <= 15) {
        int v6 = j_a2i(output[79 - v9]);
        int v7 = j_a2i(output[2 * v9]);
        output[79 - v9++] = j_i2a(v7 ^ v6);
    }
    return output;
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_hawksjamesf_myhome_api_Crypto_getClientKey(JNIEnv *env, jobject thiz, jstring jstr_token,
                                                    jlong time) {
    const char *token = env->GetStringUTFChars(jstr_token, 0);
    int token_size = env->GetStringLength(jstr_token);
    char output;
    j_s1(token, token_size, token_size, time, &output);
    env->ReleaseStringUTFChars(jstr_token, token);
    return env->NewStringUTF(&output);
}