package com.main.app.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class SecureTokenStorageUtil {

    private static final String KEYSTORE_ALIAS = "MyKeyAlias";
    private static final String PREFS_NAME = "secure_prefs";
    private static final String PREF_ACCESS_TOKEN = "access_token";
    private static final String PREF_REFRESH_TOKEN = "refresh_token";
    private static final String AES_MODE = "AES/GCM/NoPadding";
    private KeyStore keyStore;
    private SharedPreferences sharedPreferences;
    private Context context;

    private static SecureTokenStorageUtil instance;

    private SecureTokenStorageUtil(Context context) throws Exception {
        this.context = context;
        initKeyStore();
        initSharedPreferences();
    }

    public static SecureTokenStorageUtil getInstance(Context context) throws Exception {
        if(instance == null) {
            instance = new SecureTokenStorageUtil(context);
        }
        return instance;
    }

    private void initKeyStore() throws Exception {
        keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        if (!keyStore.containsAlias(KEYSTORE_ALIAS)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(
                    new KeyGenParameterSpec.Builder(KEYSTORE_ALIAS,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                            .build());
            keyGenerator.generateKey();
        }
    }

    private void initSharedPreferences() throws Exception {
        MasterKey masterKey = new MasterKey.Builder(this.context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences.create(
                this.context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public void storeTokens(String accessToken, String refreshToken) throws Exception {
        String encryptedAccessToken = encryptData(accessToken);
        String encryptedRefreshToken = encryptData(refreshToken);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_ACCESS_TOKEN, encryptedAccessToken);
        editor.putString(PREF_REFRESH_TOKEN, encryptedRefreshToken);
        editor.apply();
    }

    public String getAccessToken() throws Exception {
        String encryptedAccessToken = sharedPreferences.getString(PREF_ACCESS_TOKEN, null);
        return encryptedAccessToken != null ? decryptData(encryptedAccessToken) : null;
    }

    public String getRefreshToken() throws Exception {
        String encryptedRefreshToken = sharedPreferences.getString(PREF_REFRESH_TOKEN, null);
        return encryptedRefreshToken != null ? decryptData(encryptedRefreshToken) : null;
    }

    private String encryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        SecretKey secretKey = ((KeyStore.SecretKeyEntry)keyStore.getEntry(KEYSTORE_ALIAS, null)).getSecretKey();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptionIv = cipher.getIV();
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + encryptionIv.length + encryptedData.length);
        byteBuffer.putInt(encryptionIv.length);
        byteBuffer.put(encryptionIv);
        byteBuffer.put(encryptedData);

        return android.util.Base64.encodeToString(byteBuffer.array(), android.util.Base64.DEFAULT);
    }

    private String decryptData(String encryptedData) throws Exception {
        byte[] encryptedBytes = android.util.Base64.decode(encryptedData, android.util.Base64.DEFAULT);
        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedBytes);
        int ivLength = byteBuffer.getInt();
        byte[] encryptionIv = new byte[ivLength];
        byteBuffer.get(encryptionIv);

        byte[] encryptedBytesData = new byte[byteBuffer.remaining()];
        byteBuffer.get(encryptedBytesData);

        Cipher cipher = Cipher.getInstance(AES_MODE);
        SecretKey secretKey = ((KeyStore.SecretKeyEntry)keyStore.getEntry(KEYSTORE_ALIAS, null)).getSecretKey();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, encryptionIv));

        return new String(cipher.doFinal(encryptedBytesData), StandardCharsets.UTF_8);
    }
}

