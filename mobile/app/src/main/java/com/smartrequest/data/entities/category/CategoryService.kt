package com.smartrequest.data.entities.category

import com.smartrequest.data.entities.category.model.Category
import io.reactivex.Observable
import io.reactivex.Single

interface CategoryService {

    fun getCategoryList(): Single<List<Category>>

    fun getCategoryListById(id: Int): Single<List<Category>>

}