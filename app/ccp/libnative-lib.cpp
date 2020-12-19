#include <limits.h>
//
// Created by Johan Lindström on 12/19/20.
//
#include <jni.h>
#include <string>

std::string getData(int x) {
    std::string app_secret = "Null";

    if (x == 1) app_secret = "AIzaSyBi9sglBeE2u7V-pUOWfm0su6a_H-poBqQ"; //The first key that you want to protect against decompilation

    // The number of parameters to be protected can be increased.

    return app_secret;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_nearby_finder_NearbyFinderApplication_getApiKey(
        JNIEnv *env,
        jobject,
        jint x) {
    std::string app_secret = "Null";
    app_secret = getData(x);
    return env->NewStringUTF(app_secret.c_str());
}
