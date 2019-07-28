package com.smartrequest.ui.widget

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.ACTION_UP
import android.view.KeyEvent.KEYCODE_DEL
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper


class BackSpaceListeningEditText : AppCompatEditText {


	constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

	constructor(context: Context) : super(context) {}

	override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
		return BackSpaceInputConnection(super.onCreateInputConnection(outAttrs), true)
	}

	private inner class BackSpaceInputConnection(target: InputConnection, mutable: Boolean) : InputConnectionWrapper(target, mutable) {

		override fun sendKeyEvent(event: KeyEvent): Boolean {
			if (event.action == ACTION_DOWN && event.keyCode == KEYCODE_DEL) {
				// Un-comment if you wish to cancel the backspace:
				// return false;
			}
			return super.sendKeyEvent(event)
		}

		override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
			// in latest Android, deleteSurroundingText(1, 0) will be called for backspace
			return if (beforeLength == 1 && afterLength == 0) {
				// backspace
				sendKeyEvent(KeyEvent(ACTION_DOWN, KEYCODE_DEL)) && sendKeyEvent(KeyEvent(ACTION_UP, KEYCODE_DEL))
			} else super.deleteSurroundingText(beforeLength, afterLength)

		}

	}

}