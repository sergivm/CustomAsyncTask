package com.sergivm.customasynctask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

/**
 * @author Sergi Villa <svm7979@gmail.com> Copyright 2025
 */
public abstract class CustomAsyncTask extends BasicAsyncTask {

    private Exception exception;

    public CustomAsyncTask() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.exception = null;
    }


    // <editor-fold default-state="collapsed" desc="ABSTRACT METHODS">

    public abstract void onFailed(Exception exception);

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PUBLIC METHODS">

    @Override
    protected void startBackground() {
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

    // </editor-fold>

}
