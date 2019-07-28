package com.smartrequest.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import android.support.annotation.StringRes
import java.util.*


class PickImageIntentManager {

	companion object {

		private fun getGalleriesIntents(context: Context): Array<Parcelable> {

			val galleryIntent = Intent()
			galleryIntent.type = "image/*"
			galleryIntent.action = Intent.ACTION_GET_CONTENT

			val galleryIntents = LinkedList<Intent>()
			val listGal = context.packageManager.queryIntentActivities(galleryIntent, 0)
			listGal.forEach {
				val pacakgeName = it.activityInfo.packageName
				val finalIntent = Intent(galleryIntent)
				finalIntent.component = ComponentName(pacakgeName, it.activityInfo.name)
				finalIntent.setPackage(pacakgeName)
				galleryIntents.add(finalIntent)
			}

			return galleryIntents.map { it as Parcelable }.toTypedArray()
		}

		fun getCameraIntent(context: Context, photoUri: Uri): Intent {

			val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
			return cameraIntent
		}

		fun getCameraAndGalleryChooser(context: Context, photoUri: Uri, @StringRes chooserResId: Int): Intent {

			val cameraIntent = getCameraIntent(context, photoUri)
			val galleryArray = getGalleriesIntents(context)

			val chooser = Intent.createChooser(cameraIntent,
					context.getString(chooserResId))
			chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, galleryArray)

			return chooser

		}

		fun getGalleryChooser(context: Context, @StringRes chooserResId: Int): Intent {

			val intent = Intent()
			intent.type = "image/*"
			intent.action = Intent.ACTION_GET_CONTENT

			return Intent.createChooser(intent, context.getString(chooserResId))

		}
	}
}