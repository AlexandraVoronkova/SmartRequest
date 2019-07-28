package com.smartrequest.data.synchronizer

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

open class SynchronizerImpl : com.smartrequest.data.synchronizer.Synchronizer {

	var tasks: List<com.smartrequest.data.synchronizer.SynchronizerTask> = emptyList()
	var intervalInSeconds: Long = 0

	var disposable: Disposable? = null

	override fun start() {
		disposable = Observable.interval(0, intervalInSeconds, TimeUnit.SECONDS)
				.flatMap<Long> { aLong ->
					return@flatMap Observable.fromIterable(tasks)
							.flatMapCompletable { it ->
								return@flatMapCompletable Completable.fromAction { it.execute() }
							}
							.andThen<Long>(Observable.defer {
								Observable.just(aLong)
							})
							.onErrorReturnItem(aLong)
				}
				.subscribe({
					Timber.d("Synchronization completed")
				}, {
					Timber.w(it)
				})

	}

	override fun stop() {
		disposable?.dispose()
		disposable = null
	}

}