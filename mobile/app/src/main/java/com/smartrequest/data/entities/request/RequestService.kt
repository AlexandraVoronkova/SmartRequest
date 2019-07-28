package com.smartrequest.data.entities.request

import com.smartrequest.data.entities.request.model.Request
import io.reactivex.Completable
import io.reactivex.Single

interface RequestService {

    fun getStatusList(): Single<List<Request>>

    fun sendRequest(userId: Int, categoryId: Int, text: String): Completable

    fun addRequest(request: Request)

}