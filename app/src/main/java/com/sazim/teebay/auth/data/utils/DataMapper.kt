/*
 * Created by Saeedus Salehin on 19/7/25, 12:40 AM.
 */

package com.sazim.teebay.auth.data.utils

import com.sazim.teebay.auth.data.dto.LoginResponseDto
import com.sazim.teebay.auth.data.dto.SignUpResponseDto
import com.sazim.teebay.auth.data.dto.UserDto
import com.sazim.teebay.auth.domain.model.LoginResponse
import com.sazim.teebay.auth.domain.model.SignUpResponse
import com.sazim.teebay.auth.domain.model.User

fun LoginResponseDto.toDomain(): LoginResponse {
    return LoginResponse(
        message = message,
        user = user.toDomain()
    )
}

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        address = address,
        firebaseConsoleManagerToken = firebaseConsoleManagerToken,
        password = password,
        dateJoined = dateJoined
    )
}

fun SignUpResponseDto.toDomain(): SignUpResponse {
    return SignUpResponse(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        address = address,
        firebaseConsoleManagerToken = firebaseConsoleManagerToken,
        password = password,
        dateJoined = dateJoined
    )
}