package com.mod6.ae2_abpro1_listeylor.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String?,
    val website: String?,
    val address: Address?,
    val company: Company?
) : Parcelable

@Parcelize
data class Address(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?
) : Parcelable

@Parcelize
data class Company(
    val name: String?
) : Parcelable