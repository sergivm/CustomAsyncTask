package com.sergivm.customasynctask;

import android.os.CountDownTimer;

public abstract class TimerAsyncTask extends CountDownTimer {

    private CustomAsyncTask customAsyncTask;


    /**
     * {@see CountDownTimer#CountDownTimer(long, long)}
     */
    public TimerAsyncTask(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setCustomAsyncTask(CustomAsyncTask customAsyncTask) {
        this.customAsyncTask = customAsyncTask;
    }

    public CustomAsyncTask getCustomAsyncTask() {
        return this.customAsyncTask;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (customAsyncTask == null) {
            this.cancel();
            return;
        }

        if (customAsyncTask.isCancelled() || customAsyncTask.isFinished() || customAsyncTask.isFailed()) {
            this.cancel();
        }
    }

    @Override
    public void onFinish() {
        if (customAsyncTask != null && customAsyncTask.isRunning()) {
            customAsyncTask.cancel();
            this.cancel();
        }
    }

}
