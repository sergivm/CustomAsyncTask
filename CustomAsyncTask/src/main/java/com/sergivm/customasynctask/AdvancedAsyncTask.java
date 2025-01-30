package com.sergivm.customasynctask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

/**
 * @author Sergi Villa <svm7979@gmail.com> Copyright 2025
 */
public abstract class AdvancedAsyncTask extends BasicAsyncTask {

    private Exception exception;
    private AsyncTaskStatus status;

    public AdvancedAsyncTask() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.exception = null;
        this.status = AsyncTaskStatus.PENDING;
    }


    // <editor-fold default-state="collapsed" desc="ABSTRACT METHODS">

    public abstract void onFailed(Exception exception);

    public AsyncTaskStatus getStatus() {
        return this.status;
    }

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PUBLIC METHODS">

    @Override
    protected void startBackground() {
        onPreExecute();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    status = AsyncTaskStatus.RUNNING;
                    doInBackground();
                } catch (Exception e) {
                    exception = e;
                    status = AsyncTaskStatus.FAILED;
                }

                if (exception == null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            onPostExecute();
                            status = AsyncTaskStatus.FINISHED;
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
