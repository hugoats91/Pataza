package com.app.pataza.core.functional

data class  BaseResponse <T> (val success: Boolean, val message: String?, val data: T, val error: ErrorResponse)