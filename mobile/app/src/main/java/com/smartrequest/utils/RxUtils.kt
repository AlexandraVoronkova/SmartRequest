package com.smartrequest.utils

import io.reactivex.CompletableTransformer
import io.reactivex.Flowable
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


object RxUtils {

	fun shouldDispose(disposable: Disposable?): Boolean {
		return disposable != null && !disposable.isDisposed
	}

	fun disposeIfNeeded(disposable: Disposable) {
		if (shouldDispose(disposable)) {
			disposable.dispose()
		}
	}

	fun <T> applySchedulers(): ObservableTransformer<T, T> {
		return ObservableTransformer { upstream ->
			upstream.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
		}
	}

	fun <T> applySingleSchedulers(): SingleTransformer<T, T> {
		return SingleTransformer { single ->
			single.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
		}
	}

	fun applyCompletableSchedulers(): CompletableTransformer {
		return CompletableTransformer { completable ->
			completable.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
		}
	}

	fun applyCompletableExponentialBackoff(maxRetries: Int = 5, delayBase: Int = 5): CompletableTransformer {
		return CompletableTransformer { completable ->
			completable.retryWhen { errors ->
				return@retryWhen errors.zipWith(Flowable.range(1, maxRetries), BiFunction<Throwable, Int, Int> { _, u -> u })
						.flatMap { retryCount ->
							return@flatMap Flowable.timer(Math.pow(delayBase.toDouble(), retryCount.toDouble()).toLong(), TimeUnit.SECONDS)
						}
			}
		}
	}

}