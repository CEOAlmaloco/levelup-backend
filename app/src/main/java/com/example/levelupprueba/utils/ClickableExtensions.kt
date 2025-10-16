package com.example.levelupprueba.utils

import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier

fun Modifier.debouncedClickable(
    debounceTime: Long = 1000L,
    onClick: () -> Unit
): Modifier{
    var lastClickTime = 0L
    return clickable {
        val currentime = System.currentTimeMillis()
        if (currentime - lastClickTime > debounceTime){
            lastClickTime = currentime
            onClick()
        }
    }
}