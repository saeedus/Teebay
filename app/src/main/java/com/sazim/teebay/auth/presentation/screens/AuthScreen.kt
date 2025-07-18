/*
 * Created by Saeedus Salehin on 18/7/25, 12:35 AM.
 */

package com.sazim.teebay.auth.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sazim.teebay.R
import com.sazim.teebay.auth.presentation.AuthState
import com.sazim.teebay.auth.presentation.AuthViewModel
import com.sazim.teebay.auth.presentation.UserAction
import com.sazim.teebay.auth.presentation.components.AuthPrompt
import com.sazim.teebay.core.presentation.ui.components.InputField

@Composable
fun AuthScreen(modifier: Modifier = Modifier, viewModel: AuthViewModel, state: AuthState) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(if (state.isLogin) R.string.sign_in else R.string.sign_up_title),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!state.isLogin) {
            InputField(
                value = state.firstName,
                onValueChange = { viewModel.onAction(UserAction.OnFirstNameTyped(it)) },
                label = stringResource(id = R.string.first_name),
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.lastName,
                onValueChange = { viewModel.onAction(UserAction.OnLastNameTyped(it)) },
                label = stringResource(id = R.string.last_name),
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.address,
                onValueChange = { viewModel.onAction(UserAction.OnAddressTyped(it)) },
                label = stringResource(id = R.string.address),
                leadingIcon = Icons.Default.Person, // Consider a more appropriate icon
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.phoneNumber,
                onValueChange = { viewModel.onAction(UserAction.OnPhoneNumberTyped(it)) },
                label = stringResource(id = R.string.phone_number),
                leadingIcon = Icons.Default.Phone,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        InputField(
            value = state.email,
            onValueChange = {
                viewModel.onAction(UserAction.OnEmailTyped(email = it))
            },
            label = stringResource(id = R.string.email),
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = state.password,
            onValueChange = {
                viewModel.onAction(UserAction.OnPasswordTyped(password = it))
            },
            label = stringResource(id = R.string.password),
            leadingIcon = Icons.Default.Lock,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )

        if (!state.isLogin) {
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.confirmPassword,
                onValueChange = { viewModel.onAction(UserAction.OnConfirmPasswordTyped(it)) },
                label = stringResource(id = R.string.confirm_password),
                leadingIcon = Icons.Default.Lock,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO Handle login/registration */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(if (state.isLogin) R.string.login else R.string.sign_up))
        }

        Spacer(Modifier.height(20.dp))

        AuthPrompt(
            promptText = stringResource(if (state.isLogin) R.string.dont_have_account else R.string.already_have_account),
            actionText = stringResource(if (state.isLogin) R.string.sign_up else R.string.sign_in)
        ) {
            if (state.isLogin) {
                viewModel.onAction(UserAction.OnSignUpTapped)
            } else {
                viewModel.onAction(UserAction.OnSignInTapped)
            }
        }
    }
}