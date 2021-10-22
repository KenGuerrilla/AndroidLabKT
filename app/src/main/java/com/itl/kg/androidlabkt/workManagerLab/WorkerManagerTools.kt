package com.itl.kg.androidlabkt.workManagerLab

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

/**
 *  使用情境，App啟動時需要一連串的初始化，每個步驟可能都需要一些前置作業才可以執行，
 *  此時就可以使用WorkManager分階段執行各階段的Worker工作
 */

class WorkerManagerTools {

    companion object {
        private const val TAG = "WorkerManagerTools"
        const val WORKER_FIRST_ID = "first"
        const val WORKER_SECOND_ID = "second"
        const val WORKER_THIRD_ID = "third"
        const val WORKER_FOURTH_ID = "fourth"
    }

    fun initOneTimeWork(context: Context): Map<String, UUID> {

        val idList = mutableMapOf<String, UUID>()

        val first = getFirstWorker().also {
            idList[WORKER_FIRST_ID] = it.id
        }

        val second = getSecondWorker().also {
            idList[WORKER_SECOND_ID] = it.id
        }

        val third = getThirdWorker().also {
            idList[WORKER_THIRD_ID] = it.id
        }

        val fourth = getFourthWorker().also {
            idList[WORKER_FOURTH_ID] = it.id
        }

        // Worker可以使用List集合一次丟進去，記得最後要呼叫enqueue()方法
        val workManager = WorkManager.getInstance(context)
            .beginWith(first)
            .then(second)
            .then(third)
            .then(fourth)
            .enqueue()


        // 可加上Listener
        workManager.result.addListener(
            { Log.d(TAG, "這裡是Runnable!!")},
            { Log.d(TAG, "這裡是Executor!!")})

        return idList
    }


    private fun getFirstWorker(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<FirstWorkerTask>().build()
    }

    private fun getSecondWorker(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<SecondWorkerTask>().build()
    }

    private fun getThirdWorker(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<ThirdWorkerTask>().build()
    }

    private fun getFourthWorker(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<FourthWorkerTask>().build()
    }

}

// 最基本款的Worker
class FirstWorkerTask(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    companion object {
        private const val TAG = "FirstWorkerTask"
    }

    override fun doWork(): Result {
        // 這邊你可以處理自己想跑的事情

        Log.d(TAG, "doWork: 處理了第一階段的事情")

        // 需要回傳一個Result，success當中也可以回傳數值，可以使用建立Worker時取得的UUID透過WorkerManager
        // 來取得WorkerInfo，可選擇Callback或LiveData來監聽回傳數值

        val str = "This is a success message."
        val output: Data = workDataOf(WorkerManagerTools.WORKER_FIRST_ID to str)

        return Result.success(output)
    }
}



// 可以使用Coroutine執行異步的Worker
// 需要注意的是下一個工作仍需要等到這個Worker執行完畢才會繼續執行
class SecondWorkerTask(context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters) {

    companion object {
        private const val TAG = "SecondWorkerTask"
        private const val SEC = 10L
    }

    override suspend fun doWork(): Result {

        withContext(Dispatchers.IO) {
            var count = 0
            while (count <= 5) {
                delay(1000)
                Log.d(TAG, "doWork in withCoroutine: $count")
                count ++
            }
        }

        Log.d(TAG, "doWork: 這邊做第二步耗時工作，耗時十秒")
        delay(SEC * 1000)

        val str = "This is second worker message."
        val output = workDataOf(WorkerManagerTools.WORKER_SECOND_ID to str)

        return Result.success(output)
    }
}

// 第三階段的工作使用CoroutineWorker
class ThirdWorkerTask(context: Context, workerParameters: WorkerParameters)
    : CoroutineWorker(context, workerParameters) {

    companion object {
        private const val TAG = "ThirdWorkerTask"
        private const val SEC = 10L
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: 第三階段")

        // 另一個耗時工作
        delay(SEC * 1000)

        val str = "This is third worker with failure result."
        val output = workDataOf(WorkerManagerTools.WORKER_THIRD_ID to str)

        // 注意，如果回傳結果為失敗，則WorkManager不會繼續後續的工作
        return Result.failure(output)
//        return Result.success(output)
    }
}

// 第四階段因為第三階段的失敗，所以不會執行
class FourthWorkerTask(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    companion object {
        private const val TAG = "FourthWorkerTask"
    }

    override fun doWork(): Result {
        Log.d(TAG, "doWork: 我打賭你一定看不到我啦！")

        val str = "This is a [ YOU CAN't SEE ME ] message."
        val output = workDataOf(WorkerManagerTools.WORKER_FOURTH_ID to str)

        return Result.success(output)
    }
}



