package com.sergivm.customasynctask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Sergi Villa <svm7979@gmail.com> Copyright 2025
 */
public abstract class BasicAsyncTask {

    protected ExecutorService executorService;

    public BasicAsyncTask() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // <editor-fold default-state="collapsed" desc="ABSTRACT METHODS">

    public abstract void onPreExecute();
    public abstract void doInBackground();
    public abstract void onPostExecute();

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PUBLIC METHODS">

    public void execute() {
        startBackground();
        if (!isShutdown())
            shutdown();
    }

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PROTECTED METHODS">

    protected void startBackground() {
        onPreExecute();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                doInBackground();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute();
                    }
                });
            }
        });
    }

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PRIVATE METHODS">

    private void shutdown() {
        executorService.shutdown();
    }

    private boolean isShutdown() {
        return executorService.isShutdown();
    }

    // </editor-fold>

}
