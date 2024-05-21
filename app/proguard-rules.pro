# Bảo vệ lớp model của Gson (hoặc các thư viện tương tự)
-keep class com.yourpackage.model.** { *; }

# Bảo vệ các lớp và phương thức annotation của ButterKnife (hoặc các thư viện tương tự)
-keep class butterknife.** { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Bảo vệ các lớp và phương thức của thư viện Retrofit
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Giữ lại tên các lớp và phương thức của các lớp hoạt động (activity), dịch vụ (service), bộ nhận (receiver), provider
-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
