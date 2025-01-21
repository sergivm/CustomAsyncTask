package com.sergivm.customasynctask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Sergi Villa <svm7979@gmail.com> Copyright 2025
 */
public abstract class CustomAsyncTask {

    private final ExecutorService executorService;
    private Exception exception;

    public CustomAsyncTask() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.exception = null;
    }

    private void startBackground() {
        onPreExecute();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doInBackground();
                } catch (Exception e) {
                    exception = e;
                }

                if (exception == null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            onPostExecute();
                        }
                    });
                }
                else {
                    onFailed(exception);
                }
            }
        });
    }

    public abstract void onPreExecute();
    public abstract void doInBackground();
    public abstract void onPostExecute();
    public abstract void onFailed(Exception exception);

}
