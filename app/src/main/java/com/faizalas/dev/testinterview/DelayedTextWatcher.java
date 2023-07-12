package com.faizalas.dev.testinterview;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;

public class DelayedTextWatcher implements TextWatcher {

    private static final long DEFAULT_DELAY_MS = 300;

    private final long delayMs;
    private final TextChangedListener listener;
    private final Handler handler;

    public DelayedTextWatcher(long delayMs, TextChangedListener listener) {
        this.delayMs = delayMs;
        this.listener = listener;
        this.handler = new Handler();
    }

    public DelayedTextWatcher(TextChangedListener listener) {
        this(DEFAULT_DELAY_MS, listener);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        handler.removeCallbacks(updateRunnable);
        handler.postDelayed(updateRunnable, delayMs);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private final Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            listener.onTextChanged(toString());
        }
    };

    public interface TextChangedListener {
        void onTextChanged(String text);
    }
}

