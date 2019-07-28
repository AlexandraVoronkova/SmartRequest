package com.smartrequest.utils

import android.app.Application
import android.content.ContentUris
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.util.Log
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


object FileHelper {

	private lateinit var context: Application
	private lateinit var authority: String

	private val DEFAULT_BUFFER_SIZE = 1024 * 4
	private val EOF = -1

	fun initialize(context: Application, authority: String) {
		this.context = context
		this.authority = authority
	}

	fun getFileUri(file: File): Uri {
		return FileProvider.getUriForFile(context, authority, file)
	}

	fun compressImage(file: File, compressedSizeInBytes: Int): ByteArray {
		return compressImage(file.path, compressedSizeInBytes)
	}

	fun compressImage(imageFilePath: String, compressedSizeInBytes: Int): ByteArray {
		val options = BitmapFactory.Options()
		options.inSampleSize = 2
		val bitmap = BitmapFactory.decodeFile(imageFilePath, options)
		var byteArrayOutputStream: ByteArrayOutputStream
		var compressRatio = 90
		do {
			byteArrayOutputStream = ByteArrayOutputStream()
			bitmap.compress(Bitmap.CompressFormat.JPEG, compressRatio, byteArrayOutputStream)
			compressRatio -= 10
		} while (byteArrayOutputStream.size() > compressedSizeInBytes && compressRatio > 10)


		return byteArrayOutputStream.toByteArray()
	}

	fun copyExifData(oldPath: String, newPath: String, excludingTags: List<String>) {
		try {
			val oldExif = ExifInterface(oldPath)

			val attributes = arrayOf(ExifInterface.TAG_APERTURE,
					ExifInterface.TAG_DATETIME,
					ExifInterface.TAG_DATETIME_DIGITIZED,
					ExifInterface.TAG_EXPOSURE_TIME,
					ExifInterface.TAG_FLASH,
					ExifInterface.TAG_FOCAL_LENGTH,
					ExifInterface.TAG_GPS_ALTITUDE,
					ExifInterface.TAG_GPS_ALTITUDE_REF,
					ExifInterface.TAG_GPS_DATESTAMP,
					ExifInterface.TAG_GPS_LATITUDE,
					ExifInterface.TAG_GPS_LATITUDE_REF,
					ExifInterface.TAG_GPS_LONGITUDE,
					ExifInterface.TAG_GPS_LONGITUDE_REF,
					ExifInterface.TAG_GPS_PROCESSING_METHOD,
					ExifInterface.TAG_GPS_TIMESTAMP,
					ExifInterface.TAG_IMAGE_LENGTH,
					ExifInterface.TAG_IMAGE_WIDTH,
					ExifInterface.TAG_ISO,
					ExifInterface.TAG_MAKE,
					ExifInterface.TAG_MODEL,
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.TAG_SUBSEC_TIME,
					ExifInterface.TAG_SUBSEC_TIME_DIG,
					ExifInterface.TAG_SUBSEC_TIME_ORIG,
					ExifInterface.TAG_WHITE_BALANCE)

			val newExif = ExifInterface(newPath)
			for (i in attributes.indices) {
				val attribute = attributes[i]
				val value = oldExif.getAttribute(attribute)
				if (value != null && !excludingTags.contains(attribute))
					newExif.setAttribute(attribute, value)
			}
			newExif.saveAttributes()
		} catch (e: Exception) {
			Timber.w(e)
		}
	}

	fun copyUriContentToFile(uri: Uri, outputFile: File): Boolean {
		val inputStream = context.contentResolver.openInputStream(uri) ?: return false
		try {
            val out = FileOutputStream(outputFile)
			copy(inputStream, out)
			inputStream.close()
			out.close()
			return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
		return false
	}

	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri     The Uri to query.
	 * @author paulburke
	 */
	fun getRealFilePathFromURI(uri: Uri): String? {

		// DocumentProvider
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				val docId = DocumentsContract.getDocumentId(uri)
				val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
				val type = split[0]

				if ("primary".equals(type, ignoreCase = true)) {
					return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
				}
				//TODO handle non-primary volumes
			} else if (isDownloadsDocument(uri)) {

				val id = DocumentsContract.getDocumentId(uri)
				if (!TextUtils.isEmpty(id)) {
					if (id.startsWith("raw:")) {
						return id.replaceFirst("raw:", "")
					}
					try {
						val contentUri = ContentUris.withAppendedId(
								Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
						return getDataColumn(contentUri, null, null)
					} catch (e: NumberFormatException) {
						return null
					}
				} else {
					return null
				}
			} else if (isMediaDocument(uri)) {
				val docId = DocumentsContract.getDocumentId(uri)
				val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
				val type = split[0]

				var contentUri: Uri? = null
				if ("image" == type) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
				} else if ("video" == type) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
				} else if ("audio" == type) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
				}

				val selection = "_id=?"
				val selectionArgs = arrayOf(split[1])

				return getDataColumn(contentUri, selection, selectionArgs)
			}// MediaProvider
			// DownloadsProvider
		} else if ("content".equals(uri.scheme, ignoreCase = true)) {
			return getDataColumn(uri, null, null)
		} else if ("file".equals(uri.scheme, ignoreCase = true)) {
			return uri.path
		}// File
		// MediaStore (and general)

		return null
	}

	//region ===================== Internal ======================

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	private fun getDataColumn(uri: Uri?, selection: String?,
	                          selectionArgs: Array<String>?): String? {

		Log.d("URI : ", uri!!.toString())
		var cursor: Cursor? = null
		val column = "_data"
		//val projection = arrayOf(column)

		try {
			cursor = context.contentResolver.query(uri, null, selection, selectionArgs, null)
			if (cursor != null && cursor.moveToFirst()) {
				val column_index = cursor.getColumnIndexOrThrow(column)
				return cursor.getString(column_index)
			}
		} finally {
			if (cursor != null)
				cursor.close()
		}
		return null
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	private fun isExternalStorageDocument(uri: Uri): Boolean {
		return "com.android.externalstorage.documents" == uri.authority
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private fun isDownloadsDocument(uri: Uri): Boolean {
		return "com.android.providers.downloads.documents" == uri.authority
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private fun isMediaDocument(uri: Uri): Boolean {
		return "com.android.providers.media.documents" == uri.authority
	}

	@Throws(IOException::class)
	private fun copy(input: InputStream, output: OutputStream): Long {
		var count: Long = 0
		var n: Int
		val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
		n = input.read(buffer)
		while (EOF != n) {
			output.write(buffer, 0, n)
			count += n.toLong()
			n = input.read(buffer)
		}
		return count
	}

	//endregion
}