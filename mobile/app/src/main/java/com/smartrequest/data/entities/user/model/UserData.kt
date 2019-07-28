package com.smartrequest.data.entities.user.model

import android.support.annotation.Keep
import java.util.*

@Keep
data class UserData(val id: String,
                    val fio: String,
                    val address: String,
                    val email: String,
                    val phoneNumber: String) {

}