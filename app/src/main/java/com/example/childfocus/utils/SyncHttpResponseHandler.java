package com.example.childfocus.utils;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public abstract class SyncHttpResponseHandler extends AsyncHttpResponseHandler {

    private volatile boolean finished;

    public boolean isFinished() {
        return finished;
    }

    @Override
    public final void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        finished = true;
        onSyncSuccess(statusCode, headers, responseBody);
    }

    protected abstract void onSyncSuccess(int statusCode, Header[] headers, byte[] responseBody);

    @Override
    public final void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        finished = true;
        onSyncFailure(statusCode, headers, responseBody, error);
    }

    protected abstract void onSyncFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);

    public void waitForFinish() {
        while (!isFinished()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
    }
}
