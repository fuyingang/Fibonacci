LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := fibonacci

LOCAL_SRC_FILES := fibonacci.cpp
LOCAL_LDLIBS := -llog

include $(BUILD_SHARED_LIBRARY)