package com.smartrequest.data.entities.file

import io.reactivex.Single
import java.io.File

interface FileService {

	fun uploadFile(file: File): Single<String>

	fun uploadVoiceFile(file: File): Single<String>
	
}