-dontobfuscate
-dontwarn InnerClasses
-keep class com.chrono.** { *; }


-keep class com.fasterxml.** { *; }
-dontwarn com.fasterxml.**

-dontwarn com.shaded.fasterxml.**
-dontwarn org.apache.**
-dontwarn org.shaded.apache.**
-keepnames class com.shaded.fasterxml.jackson.** { *; }
-keepnames class org.shaded.apache.**

## Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-dontwarn okio.**

-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.picasso.**

# rxjava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue

# Firebase Auth
-keepattributes Signature
-keepattributes *Annotation*