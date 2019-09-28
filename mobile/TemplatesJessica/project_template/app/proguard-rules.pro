# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# General
-keepattributes SourceFile,LineNumberTable,*Annotation*,EnclosingMethod,Signature,Exceptions,InnerClasses
-keepattributes LocalVariableTable,LocalVariableTypeTable

#dagger
-dontwarn com.google.errorprone.annotations.*

# Kotlin
-dontwarn kotlin.**

#Android iconics
-keep class .R
-keep class **.R$* {
    <fields>;
}

# Jackson 2
-keep class com.fasterxml.jackson.databind.ObjectMapper {
    public <methods>;
    protected <methods>;
}
-keep class com.fasterxml.jackson.databind.ObjectWriter {
    public ** writeValueAsString(**);
}
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.databind.**

#Moxy
-keep class **$$PresentersBinder
-keep class **$$State
-keep class **$$ParamsHolder
-keep class **$$ViewStateClassNameProvider
-keepnames class * extends com.arellomobile.mvp.*
-dontwarn com.arellomobile.mvp.MoxyReflector

# SearchView
-keep class android.support.v7.widget.SearchView { *; }

-dontwarn net.bytebuddy.**
-dontwarn org.mockito.**
-dontwarn sun.misc.Unsafe

# OkHttp
-dontwarn com.squareup.okhttp.**
-dontwarn org.conscrypt.**
