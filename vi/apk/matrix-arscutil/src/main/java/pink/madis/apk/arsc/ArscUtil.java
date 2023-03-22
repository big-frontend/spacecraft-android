package pink.madis.apk.arsc;

import com.tencent.matrix.javalib.util.Log;
import com.tencent.mm.arscutil.data.ResPackage;
import com.tencent.mm.arscutil.data.ResStringBlock;
import com.tencent.mm.arscutil.data.ResType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArscUtil {
    private static final String TAG = "ArscUtil.ArscUtil";

    public static String toUTF16String(byte[] buffer) {
        CharBuffer charBuffer = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).asCharBuffer();
        int index = 0;
        for (; index < charBuffer.length(); index++) {
            if (charBuffer.get() == 0x00) {
                break;
            }
        }
        charBuffer.limit(index).position(0);
        return charBuffer.toString();
    }

    public static int getPackageId(int resourceId) {
        return (resourceId & 0xFF000000) >> 24;
    }

    public static int getResourceTypeId(int resourceId) {
        return (resourceId & 0x00FF0000) >> 16;
    }

    public static int getResourceEntryId(int resourceId) {
        return resourceId & 0x0000FFFF;
    }
    public static PackageChunk findResPackage(ResourceTableChunk resTable, int packageId) {
        PackageChunk resPackage = null;
        for (PackageChunk pkg : resTable.getPackages()) {
            if (pkg.getId() == packageId) {
                resPackage = pkg;
                break;
            }
        }
        return resPackage;

    }
    public static List<TypeChunk> findResType(PackageChunk packageChunk, int resourceId) {
        int typeId = (resourceId & 0X00FF0000) >> 16;
        int entryId = resourceId & 0x0000FFFF;
        List<TypeChunk> resTypeList = new ArrayList();
        Collection<TypeChunk> resTypeArray = packageChunk.getTypeChunks();
        if (resTypeArray != null) {
            for (TypeChunk typeChunk : resTypeArray) {
                if (typeChunk.getType() == Chunk.Type.TABLE_TYPE && typeChunk.getId() == typeId) {
                    int entryCount = typeChunk.getTotalEntryCount();
                    if (entryId < entryCount) {
                        resTypeList.add(typeChunk);
                    }
                }
            }
        }
        return resTypeList;

    }
    public static void removeResource(ResourceTableChunk resTable, int resourceId, String resourceName) throws IOException {
        PackageChunk resPackage = findResPackage(resTable, getPackageId(resourceId));
        if (resPackage != null) {
            List<TypeChunk> resTypeList = findResType(resPackage, resourceId);
            int resNameStringPoolIndex = -1;
            for (TypeChunk resType : resTypeList) {
                int entryId = getResourceEntryId(resourceId);
                resNameStringPoolIndex =  resType.getEntries().get(entryId).keyIndex();
                resType.getEntries().remove(entryId);
//                resType.refresh();
            }
            if (resNameStringPoolIndex != -1) {
//                resPackage.getTypeStringPool()
//                Log.i(TAG, "try to remove %s (%H), find resource %s", resourceName, resourceId, ResStringBlock.resolveStringPoolEntry(resPackage.getResNamePool().getStrings().get(resNameStringPoolIndex).array(), resPackage.getResNamePool().getCharSet()));
            }
//            resPackage.shrinkResNameStringPool();
//            resPackage.refresh();
//            resTable.refresh();
        }

    }
    public static boolean replaceFileResource(ResourceTableChunk resTable, int sourceResId, String sourceFile, int targetResId, String targetFile) throws IOException {
        //nothing
        return false;
    }
    public static void replaceResEntryName(ResourceTableChunk resTable, Map<Integer, String> resIdProguard) {
        //nothing
    }
    public static boolean replaceResFileName(ResourceTableChunk resTable, int resId, String srcFileName, String targetFileName) {
        //nothing
        return false;

    }
}
