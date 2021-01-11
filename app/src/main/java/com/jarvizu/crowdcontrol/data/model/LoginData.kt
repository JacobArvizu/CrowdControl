package com.jarvizu.crowdcontrol.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginData(val key: String, val value : String) : Parcelable
