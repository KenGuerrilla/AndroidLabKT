package com.itl.kg.androidlabkt.workManagerLab

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.*
import com.itl.kg.androidlabkt.databinding.FragmentWorkManagerLabBinding
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * Work Manager Lab - WorkManager Demo
 *
 * WorkManager用於處理一系列或需要長時間周期性的工作，而Worker建立時可回傳一個UUID用來觀察工作結果
 *
 *
 * 參考資料：https://developer.android.com/topic/libraries/architecture/workmanager/advanced
 */

class WorkManagerLabFragment : Fragment() {

    private var _binding: FragmentWorkManagerLabBinding? = null
    private val binding get() = _binding!!

    companion object {
        val TAG = WorkManagerLabFragment::class.java.simpleName
        val WORK_TAG = WorkManagerLabFragment::class.java.simpleName
    }

    private var workManager: Operation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkManagerLabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()

    }

    private fun initListener() {

        binding.mTaskStartBtn.setOnClickListener {
            initPeriodicWorker()
        }

        binding.mTaskCheckBtn.setOnClickListener {
            initOneTimeWorker()
        }

    }

    private fun initPeriodicWorker() {

        // 可以指定裝置狀態來觸發工作
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // 週期最小數值為15 minute，Retry間距至少為30秒
        val workRequest = PeriodicWorkRequestBuilder<TestWorkTask>(
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .addTag(WORK_TAG)
            .build()


        workManager = WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(WORK_TAG, ExistingPeriodicWorkPolicy.REPLACE, workRequest)

    }

    private fun initOneTimeWorker() {
        val manager = WorkerManagerTools()
        val idList = manager.initOneTimeWork(requireContext())

        initLiveData(idList)
    }


    // 注意！使用LiveData被觸發的時機，會有回傳null的情況
    private fun initLiveData(idList: Map<String, UUID>) {

        idList[WorkerManagerTools.WORKER_FIRST_ID]?.let { id ->
            WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(id)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    val result = it.outputData.getString(WorkerManagerTools.WORKER_FIRST_ID)
                    Log.d(TAG, "First Worker Result: $result")
                })
        }

        idList[WorkerManagerTools.WORKER_SECOND_ID]?.let { id ->
            WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(id)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    val result = it.outputData.getString(WorkerManagerTools.WORKER_SECOND_ID)
                    Log.d(TAG, "Second Worker Result: $result")
                })
        }

        // addListener還需要研究一下
        idList[WorkerManagerTools.WORKER_THIRD_ID]?.let { id ->
            WorkManager.getInstance(requireContext()).getWorkInfoById(id)
                .addListener(
                    { Log.d(TAG, "Third Worker Result: 使用Runnable") }
                    , { Log.d(TAG, "Third Worker Result: 這裡是Executor") })
        }

        idList[WorkerManagerTools.WORKER_FOURTH_ID]?.let { id ->
            WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(id)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    val result = it.outputData.getString(WorkerManagerTools.WORKER_FOURTH_ID)
                    Log.d(TAG, "Fourth Worker Result: $result")
                })
        }

    }


}