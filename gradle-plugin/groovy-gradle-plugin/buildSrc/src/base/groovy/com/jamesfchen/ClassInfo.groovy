package com.jamesfchen
class ClassInfo{
    File mather//jar or dir
    File classFile
    InputStream classStream
    String canonicalName

    ClassInfo(File mather, InputStream classStream, String canonicalName) {
        this.mather = mather
        this.classStream = classStream
        this.canonicalName = canonicalName
    }

    ClassInfo(File mather, File classFile, String canonicalName) {
        this.mather = mather
        this.classFile = classFile
        this.canonicalName = canonicalName
    }

    @Override
    String toString() {
        if (classFile !=null){
            String info = "ClassInfo{" +
                    "mather=" + mather +
                    ", classFile=" + classFile +
                    ", canonicalName='" + canonicalName + '\'' +
                    '}';
            return  "Class In Dir "+info
        }else {
            String info = "ClassInfo{" +
                    "mather=" + mather +
                    ", classStream=" + classStream +
                    ", canonicalName='" + canonicalName + '\'' +
                    '}';
            return  "Class In Jar "+info
        }

    }
}