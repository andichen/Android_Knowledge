// GuardAidl.aidl
package com.example.keepalivelibrary;

// Declare any non-default types here with import statements

interface GuardAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    //相互唤醒服务
    void wakeUp(String title, String discription, int iconRes);
}
