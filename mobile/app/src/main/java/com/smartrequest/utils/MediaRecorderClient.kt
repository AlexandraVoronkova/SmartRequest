package com.smartrequest.utils

import android.media.MediaRecorder
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit.MILLISECONDS

class MediaRecorderClient {

	companion object {

		private const val UPDATE_TIME_IN_MS = 100L
	}

	private var mediaRecorder: MediaRecorder? = null
	private var timerObservable: Disposable? = null
	private var timerSubject = PublishSubject.create<Long>()
	private var isStarted = false

	fun record(outputFilePath: String): Observable<Long> {
		mediaRecorder?.release()
		mediaRecorder = MediaRecorder().apply {
			setAudioSource(MediaRecorder.AudioSource.MIC)
			setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
			setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
			setOutputFile(outputFilePath)

			try {
				prepare()
			} catch (e: IOException) {
				Timber.w(e, "IO exception when preparing MediaRecorder")
			}

			try {
				start()
				startTimer()
			} catch (e: IllegalStateException) {
				Timber.w(e, "Start record failed")
			}
		}
		return timerSubject
	}

	fun stop(): Boolean {
		return try {
			mediaRecorder?.stop()
			release()
			true
		} catch (e: RuntimeException) {
			Timber.w(e, "Stop record failed")
			release()
			false
		}
	}

	fun release() {
		try {
			stopTimer()
			mediaRecorder?.reset()
			mediaRecorder?.release()
			mediaRecorder = null
		} catch (throwable: Throwable) {
			Timber.w(throwable)
		}

	}

	private fun startTimer() {
		isStarted = true
		timerObservable = Observable.interval(UPDATE_TIME_IN_MS, MILLISECONDS)
				.takeWhile { isStarted }
				.doOnNext { timerSubject.onNext(it * 100) }
				.subscribe()
	}

	private fun stopTimer() {
		isStarted = false
		timerSubject.onComplete()
		timerObservable?.dispose()
		timerSubject = PublishSubject.create()
	}


}