package com.itl.kg.androidlabkt.coroutineLab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.itl.kg.androidlabkt.databinding.FragmentCoroutineLabBinding
import kotlinx.coroutines.*

/**
 *  Coroutine Lab
 *
 *  參考資料：https://developer.android.com/kotlin/coroutines
 */

class CoroutineLabFragment : Fragment() {

    companion object {
        private const val TAG = "CoroutineLabFragment"
    }

    private var _binding: FragmentCoroutineLabBinding? = null
    private val binding get() = _binding!!

    private var counterJob: Job? = null
    private var taskJob: Job? = null
    private var blockJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoroutineLabBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.btStartCounter.setOnClickListener {
            startCounterJob()
        }

        binding.btStartBackgroundTask.setOnClickListener {
            startTask()
        }

        binding.btStartBlockTask.setOnClickListener {
            startBlockTask()
        }

        binding.tvResult.setOnClickListener {
            taskJob?.cancel()
        }
    }

    // 開始計數器
    private fun startCounterJob() {
        // 如果counterJob為null，則啟動一個新的Coroutine
        if (counterJob == null) {
            // launch方法會回傳一個Job物件，可以用於取得Thread操作的方法與參數
            // 為了避免App出現ARN的錯誤，因此執行耗時工作都會建議在Main以外的Thread執行
            counterJob = lifecycleScope.launch(Dispatchers.IO) {
                var counter = 0
                // 執行無限迴圈
                while (true) {
                    delay(1000) // 延遲一秒

                    // 注意！更新UI的工作必須要回到MainThread。
                    lifecycleScope.launch(Dispatchers.Main) {
                        showThreadName("Counter Task set the counter")
                        binding.tvCounter.text = counter.toString()
                        counter++
                    }
                }
            }
        } else {
            counterJob?.cancel() // 取消
            counterJob = null // 記得要設為null
            binding.tvCounter.text = "---"
        }

    }


    private fun startTask() {
        // 預設來說，launch或withContext不指定Dispatchers則表示跑在當下的Thread
        // 以這邊來說就是MainThread
        taskJob = lifecycleScope.launch {

            showThreadName("Start Task")

            // Coroutine當中使用async並回傳變數Deferred<T>，其可呼叫await方法等待結果出來後繼續執行
            val resultA = async(Dispatchers.IO) {
                // 內部計算耗時工作
                showThreadName("Result A")
                delay(3000)

                showLog("startTask: 取得ResultA")
                200 // 回傳結果
            }

            val resultB = async(Dispatchers.IO) {
                // 內部計算耗時工作
                showThreadName("Result B")
                delay(8000)

                showLog("startTask: 取得ResultB")
                200 // 回傳結果
            }

            // 這邊要注意的是，程式碼要等到await的結果都回來了才會往下執行
            showLog("startTask: before await")
            binding.tvResult.text = (resultA.await() + resultB.await()).toString()
            showLog("startTask: after await")
        }

    }

    // 這邊要特別說明一下，runBlocking主要的特色會阻塞當下的Thread，簡單來說就是會處理完Block中的程式碼才會往下執行
    // 下面的程式碼Coroutine預設是在MainThread，當中加上runBlocking會跳出黃底警告，顯示inappropriate blocking method call
    // 細節可以點進runBlocking查看詳細的註解，可以搭配第一個計數器實驗來觀察狀況。
    private fun startBlockTask() {
        blockJob = lifecycleScope.launch {
            showThreadName("run Block")
            runBlocking {
                delay(5000)
            }
        }
    }

    // 顯示當下執行的Thread
    private fun showThreadName(taskName: String) {
        Log.d(TAG, "$taskName - Thread name: [${Thread.currentThread().name}]")
    }

    private fun showLog(message: String) {
        Log.d(TAG, message)
    }


}