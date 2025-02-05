package com.sergivm.customasynctask;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

/**
 * @author Sergi Villa <svm7979@gmail.com> Copyright 2025
 */
public abstract class CustomAsyncTask extends BasicAsyncTask {

    private Exception exception;
    private AsyncTaskStatus status;

    public CustomAsyncTask() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.exception = null;
        this.status = AsyncTaskStatus.PENDING;
    }


    // <editor-fold default-state="collapsed" desc="ABSTRACT METHODS">

    public abstract void onFailed(Exception exception);

    public abstract void onCancelled();

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PUBLIC METHODS">

    public boolean isRunning() {
        return this.status == AsyncTaskStatus.RUNNING;
    }

    public boolean isFinished() {
        return this.status == AsyncTaskStatus.FINISHED;
    }

    public boolean isCancelled() {
        return this.status == AsyncTaskStatus.CANCELLED;
    }

    public boolean isFailed() {
        return this.status == AsyncTaskStatus.FAILED;
    }

    // </editor-fold>

    // <editor-fold default-state="collapsed" desc="PROTECTED METHODS">

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

    @Override
    protected void cancel() {
        super.cancel();
        onCancelled();
    }

    // </editor-fold>

}
