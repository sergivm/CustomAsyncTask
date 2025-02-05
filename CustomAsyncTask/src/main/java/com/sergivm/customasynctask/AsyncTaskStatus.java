package com.sergivm.customasynctask;

public enum AsyncTaskStatus {
    /**
     * Indicates that the task has not been executed yet.
     */
    PENDING,

    /**
     * Indicates that the task is running.
     */
    RUNNING,

    /**
     * Indicates that {@link BasicAsyncTask#onPostExecute} has finished.
     */
    FINISHED,

    /**
     * Idicates that {@link BasicAsyncTask#doInBackground} was canceled.
     */
    CANCELLED,

    /**
     * Indicates that {@link BasicAsyncTask#doInBackground} failed.
     */
    FAILED
}
