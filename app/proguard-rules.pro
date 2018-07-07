# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\development\DevTools\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# fb
-keep class com.google.ads.mediation.facebook.FacebookAdapter {
      *;
    }


# ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepnames class * { @butterknife.Bind *;}




-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Dagger
-keepclassmembers,allowobfuscation class * {
    @javax.inject.* *;
    @dagger.* *;
    <init>();
}

-keep class javax.inject.** { *; }
-keep class **$$ModuleAdapter
-keep class **$$InjectAdapter
-keep class **$$StaticInjection
-keep class dagger.** { *; }

# Picaso
-dontwarn com.squareup.okhttp.**

# Flurry
-keep class com.flurry.** { *; }
-dontwarn com.flurry.**



# gson
-keepattributes Signature
# POJOs used with GSON
# The variable names are JSON key values and should not be obfuscated
-keepclassmembers class com.example.apps.android.Categorias { <fields>; }
# You can apply the rule to all the affected classes also
-keepclassmembers class com.example.apps.android.model.** { <fields>; }
# Keep the annotations
#-keepattributes *Annotation*

# gson dcoder specific

-keep class com.paprbit.bhamashah.net.model.** {
  *;
}


# fabric
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception



# square/okio
-dontwarn okio.**

# retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# mozilla character encoding detect package
-dontwarn org.mozilla.universalchardet.prober.statemachine.**


# In SelectActivity for Search menu icon
-keep class android.support.v7.widget.SearchView{
*;
}

#Alot of crashes seen with Noclassdefenitionfound exception
#FOR APPCOMPAT 23.2.1:
-keep class !android.support.v7.view.menu.*MenuBuilder*, android.support.v7.** { *; }
-keep interface android.support.v7.* { *; }


