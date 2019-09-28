package com.smartrequest.utils

import com.smartrequest.R
import java.io.File
import java.util.*

class CacheFileProvider {

	companion object {

		fun createCacheImageFile(): File {
			val path = com.smartrequest.components.AppContext.instance.cacheDir
			if (!path.exists()) {
				path.mkdir()
			}
			val image = File(path, com.smartrequest.components.AppContext.instance.getString(R.string.app_name).plus(" ").plus(Date().time).plus(".jpg"))
			return image
		}

		fun createCacheVoiceFile(): File {
			val path = com.smartrequest.components.AppContext.instance.cacheDir
			if (!path.exists()) {
				path.mkdir()
			}

			return File(path, com.smartrequest.components.AppContext.instance
					.getString(R.string.app_name)
					.plus(" ")
					.plus(Date().time)
					.plus(".m4a"))
		}
	}
}