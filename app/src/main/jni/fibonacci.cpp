//
// Created by fuyingang on 2017/7/28.
//

#include <jni.h>
//#include <utils/Log.h>

#include <android/log.h>

#include <stdio.h>

#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, "hello-libs::", __VA_ARGS__))

extern "C" JNIEXPORT jlong JNICALL
Java_leeco_fibonacci_IFibonacciServiceImpl_recursiveNative(JNIEnv *env, jobject thiz, jlong num) {

    if (num == 1)
        return 0;
    if (num == 2)
        return 1;

    return Java_leeco_fibonacci_IFibonacciServiceImpl_recursiveNative(env, thiz, num-2) + Java_leeco_fibonacci_IFibonacciServiceImpl_recursiveNative(env, thiz, num-1);
}

extern "C" JNIEXPORT jlong JNICALL
Java_leeco_fibonacci_IFibonacciServiceImpl_iterateNative(JNIEnv *env, jobject thiz, jlong num) {

    long resultN_2, resultN_1, result;

    long i;

    //LOGI("iterate native been called");

    if (num == 1)
        return 0;
    if (num == 2)
        return 1;

    resultN_2 = 0;
    resultN_1 = 1;

    for(i = 3; i<= num; i++) {
        result = resultN_2 + resultN_1;
        resultN_2 = resultN_1;
        resultN_1 = result;
    }
    return result;
}