package com.smartrequest.data.entities.file

import android.media.ExifInterface
import android.support.annotation.Keep
import com.smartrequest.utils.CacheFileProvider
import com.smartrequest.utils.FileHelper
import com.fasterxml.jackson.core.type.TypeReference
import io.reactivex.Single
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileServiceImpl @Inject constructor(private val apiClient: com.smartrequest.data.network.api.ApiClient) :
        com.smartrequest.data.entities.file.FileService {

	companion object {

		private const val MAX_FILE_SIZE_IN_BYTES = 100 * 1024

	}

	//region ==================== UserService ====================

	override fun uploadFile(file: File): Single<String> {
		return Single.just(file)
				.map { originalFile ->
					val bytes = FileHelper.compressImage(originalFile, com.smartrequest.data.entities.file.FileServiceImpl.Companion.MAX_FILE_SIZE_IN_BYTES)
					val tmpFile = CacheFileProvider.createCacheImageFile()
					val fileOutputStream = FileOutputStream(tmpFile)
					fileOutputStream.write(bytes)
					FileHelper.copyExifData(file.path, tmpFile.path, listOf(ExifInterface.TAG_IMAGE_LENGTH,
							ExifInterface.TAG_IMAGE_WIDTH))

					return@map tmpFile
				}
				.flatMap { compressedFile ->
					return@flatMap apiClient.postFile("files/upload", compressedFile, null, object :
							TypeReference<com.smartrequest.data.entities.file.FileServiceImpl.FileUploadResponse>() {})
							.map { it.fileUrl }
							.doOnSuccess { compressedFile.delete() }
				}

	}

	override fun uploadVoiceFile(file: File): Single<String> {
		val params: Map<String, Any> = mapOf(Pair("type", "audioChat"))
		return apiClient.postFile("files/upload", file, params, object : TypeReference<com.smartrequest.data.entities.file.FileServiceImpl.FileUploadResponse>() {})
				.map { it.fileUrl }
	}

	//endregion

	//region ==================== Internal ====================

	@Keep
	data class FileUploadResponse(val fileUrl: String)

	//endregion

}
