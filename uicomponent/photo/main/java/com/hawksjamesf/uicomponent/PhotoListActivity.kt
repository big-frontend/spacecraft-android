package com.hawksjamesf.uicomponent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage

class PhotoListActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storage = FirebaseStorage.getInstance("gs://spacecraft-22dc1.appspot.com")
        val storageRef = storage.reference
//        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
        //        storageRef.getPath()
//        Uri result = storageRef.getDownloadUrl().getResult();
        storageRef.child("WechatIMG168.jpeg").downloadUrl
                .addOnCompleteListener { task -> Log.d("hawks", "url:" + task.result.toString()) }

    }
}