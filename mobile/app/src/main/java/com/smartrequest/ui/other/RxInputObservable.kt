package com.smartrequest.ui.other

import android.text.Editable
import android.widget.EditText
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject



object RxEditTextObservable {

	fun fromEditText(editText: EditText): Observable<String> {
		val subject = PublishSubject.create<String>()

		editText.addTextChangedListener(object : TextWatcherAdapter() {

			override fun afterTextChanged(editable: Editable) {
				super.afterTextChanged(editable)
				subject.onNext(editable.toString())
			}
		})

		return subject
	}

}