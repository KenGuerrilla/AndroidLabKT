package com.itl.kg.androidlabkt.workManagerLab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.*
import com.itl.kg.androidlabkt.databinding.FragmentWorkManagerLabBinding
import java.util.concurrent.TimeUnit

/**
 *
 * Work Manager Lab - WorkManager Demo
 *
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
            initTaskManager()
        }

        binding.mTaskCheckBtn.setOnClickListener {

        }

    }


    private fun initTaskManager() {

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


}