# Room
-keep class com.snbt.mathmentor.data.local.entity.** { *; }
-keepclassmembers class com.snbt.mathmentor.data.local.entity.** { *; }

# Domain models
-keep class com.snbt.mathmentor.domain.model.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepclasseswithmembernames class * { @javax.inject.Inject <fields>; }

# Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** { volatile <fields>; }

# Kotlin serialization (jika dipakai di masa depan)
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep BuildConfig
-keep class com.snbt.mathmentor.BuildConfig { *; }

# Suppress warnings
-dontwarn kotlinx.**
-dontwarn org.conscrypt.**
