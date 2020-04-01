package com.hb.vovinamsd

import android.util.Log
import com.bumptech.glide.BuildConfig

class StaticData {
    init {
        //initialized native library
        System.loadLibrary("native-lib")
        if (BuildConfig.DEBUG)
            Log.i("StaticData", "Native library initialize successfully")
    }

    //JNI function
    external fun stringFromJNI(key: String): String
}