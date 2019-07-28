package com.smartrequest.data.entities.auth.model

sealed class InviteCodeCheckResponse

object InviteCodeChildModeResponse : com.smartrequest.data.entities.auth.model.InviteCodeCheckResponse()
data class InviteCodeCheckBooleanResponse(val isValid: Boolean) : com.smartrequest.data.entities.auth.model.InviteCodeCheckResponse()