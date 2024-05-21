package com.main.app.callbacks;

public interface IActionCallback<T> {
    default void onSuccess(T result) {}
    default void onSuccess(){}
    default void onProgress(T progress) {}
    default void onFailure(String message){}
    default void onError(Exception e){}
}
