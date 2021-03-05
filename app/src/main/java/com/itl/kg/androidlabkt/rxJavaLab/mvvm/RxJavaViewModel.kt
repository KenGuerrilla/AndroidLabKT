package com.itl.kg.androidlabkt.rxJavaLab.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import com.itl.kg.androidlabkt.rxJavaLab.RxJavaLabFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class RxJavaViewModel : ViewModel() {

    companion object {
        const val TAG = "RxJavaViewModel"
    }

    fun firstDemo() {
        Observable.just(1, 2, 3)
            .filter{ it % 2 == 1 } // 非常類似Kotlin的語法
            .map { it * 2 } // 非常類似Kotlin的語法
            .subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d(RxJavaLabFragment.TAG, "onComplete")
                }

                override fun onSubscribe(d: Disposable?) {
                    Log.d(RxJavaLabFragment.TAG, "onSubscribe")
                }

                override fun onNext(t: Int?) {
                    Log.d(RxJavaLabFragment.TAG, "onNext: $t")
                }

                override fun onError(e: Throwable?) {
                    Log.d(RxJavaLabFragment.TAG, "onError")
                }
            })
    }

    fun secondDemo() {

        Observable.just(1, 2, 3)
            .filter{
                Log.d(TAG, "Filter: ${Thread.currentThread().name}")
                it % 2 == 1
            }
            .map {
                Log.d(TAG, "Map: ${Thread.currentThread().name}")
                it * 2
            }
            .subscribeOn(Schedulers.io()) // Subscribe所在的執行緒，簡而言之即處理時所執行的執行去
            .observeOn(AndroidSchedulers.mainThread()) // Observe所在的執行序，即接收資料流所在的執行序
            .subscribe(object : Observer<Int> {
                override fun onComplete() {
                    Log.d(RxJavaLabFragment.TAG, "onComplete")
                }

                override fun onSubscribe(d: Disposable?) {
                    Log.d(RxJavaLabFragment.TAG, "onSubscribe")
                }

                override fun onNext(t: Int?) {
                    Log.d(RxJavaLabFragment.TAG, "onNext: $t / ${Thread.currentThread().name}")
                }

                override fun onError(e: Throwable?) {
                    Log.d(RxJavaLabFragment.TAG, "onError")
                }
            })

    }





}