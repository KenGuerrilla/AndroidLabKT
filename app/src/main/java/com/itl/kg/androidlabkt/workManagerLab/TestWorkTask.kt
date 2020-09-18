package com.itl.kg.androidlabkt.workManagerLab

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 *
 * Created by kenguerrilla on 2020/9/16.
 *
 */


class TestWorkTask(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        private val TAG = TestWorkTask::class.java.simpleName
    }


    // 如果為週期性的工作，最後僅有CANCELLED狀態，而success or failed則為一次才有的狀態
    override fun doWork(): Result {

        Log.d(TAG, "Do work method active!!")

        return Result.success()
    }

}