#include <jni.h>
#include <stdio.h>
#include <string>
#include <iostream>
#include <map>

struct Constants {
    static std::map<std::string, std::string> create_map() {
        std::map<std::string, std::string> m;
        m["BASE_URL"] = "https://newsapi.org/v2/";
        return m;
    }

    static const std::map<std::string, std::string> keys;

};

const std::map<std::string, std::string> Constants::keys = Constants::create_map();

extern "C"
JNIEXPORT jstring
JNICALL
Java_com_hb_vovinamsd_StaticData_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,
        jstring key) {
    const char *nativeString = env->GetStringUTFChars(key, JNI_FALSE);
    std::string str;
    str.append(nativeString);
    std::map<std::basic_string<char, std::char_traits<char>, std::allocator<char>>, std::basic_string<char, std::char_traits<char>, std::allocator<char>>>::const_iterator it = Constants::keys.find(
            str);
    if (it != Constants::keys.end()) {
        return env->NewStringUTF(it->second.c_str());
    }
    return nullptr;
}

