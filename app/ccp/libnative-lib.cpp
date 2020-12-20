#include <limits.h>
//
// Created by Johan Lindström on 12/19/20.
//
#include <jni.h>
#include <string>

std::string getData(int x) {
    std::string app_secret = "Null";
    
    // The key that you want to protect against decompilation
    // add more keys if necessary
    if (x == 1) app_secret = "AIzaSyBi9sglBeE2u7V-pUOWfm0su6a_H-poBqQ";

    return app_secret;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_nearby_1finder_NearbyFinderApplication_getApiKey(
        JNIEnv *env,
        jobject,
        jint x) {
    std::string app_secret = "Null";
    app_secret = getData(x);
    return env->NewStringUTF(app_secret.c_str()); // NewStringUTF() method is used for the UTF-8 encoding
}
