package com.smartrequest.ui.other

import android.text.Editable
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxSearchObservable {

	companion object {

		fun fromView(editText: EditText): Observable<String> {

			val subject: PublishSubject<String> = PublishSubject.create()

			editText.addTextChangedListener (object: TextWatcherAdapter() {
				override fun afterTextChanged(editable: Editable) {
					subject.onNext(editable.toString())
				}
			})
			return subject

		}
	}
}