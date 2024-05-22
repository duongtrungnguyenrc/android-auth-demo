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

# Bảo vệ lớp SecureTokenStorageUtil và các thành phần của nó
-keep class com.main.app.utils.SecureTokenStorageUtil { *; }

# Bảo vệ các thuộc tính và phương thức trong lớp SecureTokenStorageUtil
-keepclassmembers class com.main.app.utils.SecureTokenStorageUtil {
    private <fields>;
}

# Bảo vệ các lớp và phương thức liên quan đến mã hóa và lưu trữ dữ liệu
-keep class androidx.security.crypto.EncryptedSharedPreferences { *; }
-keep class androidx.security.crypto.MasterKey { *; }
-keep class javax.crypto.Cipher { *; }
-keep class javax.crypto.KeyGenerator { *; }
-keep class javax.crypto.SecretKey { *; }
-keep class android.security.keystore.KeyGenParameterSpec { *; }
-keep class android.security.keystore.KeyProperties { *; }


# Bảo vệ các phương thức thành phần của android
-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider


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

# Bảo vệ các file đặc biệt
-optimizations !code/simplification/arithmetic
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Loại bỏ thông tin debug
-dontusemixedcaseclassnames
-dontpreverify

# Bảo vệ các thư viện cơ bản
-keep class org.xmlpull.v1.**
-keep class org.apache.**
-keep class com.google.gson.** { *; }
