/*
 * Created by Saeedus Salehin on 18/7/25, 12:35 AM.
 */

package com.sazim.teebay.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            .verticalScroll(state = rememberScrollState())
    ) {
        Text(
            text = stringResource(if (state.isLogin) R.string.sign_in else R.string.sign_up_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!state.isLogin) {
            InputField(
                value = state.firstName,
                onValueChange = { viewModel.onAction(UserAction.OnFirstNameTyped(it)) },
                label = stringResource(id = R.string.first_name),
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.lastName,
                onValueChange = { viewModel.onAction(UserAction.OnLastNameTyped(it)) },
                label = stringResource(id = R.string.last_name),
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.address,
                onValueChange = { viewModel.onAction(UserAction.OnAddressTyped(it)) },
                label = stringResource(id = R.string.address),
                keyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.phoneNumber,
                onValueChange = { viewModel.onAction(UserAction.OnPhoneNumberTyped(it)) },
                label = stringResource(id = R.string.phone_number),
                keyboardType =KeyboardType.Phone
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        InputField(
            value = state.email,
            onValueChange = {
                viewModel.onAction(UserAction.OnEmailTyped(email = it))
            },
            label = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            value = state.password,
            onValueChange = {
                viewModel.onAction(UserAction.OnPasswordTyped(password = it))
            },
            label = stringResource(id = R.string.password),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

        if (!state.isLogin) {
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                value = state.confirmPassword,
                onValueChange = { viewModel.onAction(UserAction.OnConfirmPasswordTyped(it)) },
                label = stringResource(id = R.string.confirm_password),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            Button(
                onClick = {
                    if (state.isLogin) {
                        viewModel.onAction(UserAction.OnSignInTapped)
                    } else {
                        viewModel.onAction(UserAction.OnSignUpTapped)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(if (state.isLogin) R.string.login else R.string.sign_up))
            }
        }

        Spacer(Modifier.height(20.dp))

        if (state.shouldShowBiometricPrompt) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(8.dp)
            ) {
                IconButton(onClick = {
                    viewModel.onAction(UserAction.ShowBiometricPrompt)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_fingerprint),
                        contentDescription = null
                    )
                }
                Text(
                    text = "Use Biometric",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(20.dp))
        }

        AuthPrompt(
            promptText = stringResource(if (state.isLogin) R.string.dont_have_account else R.string.already_have_account),
            actionText = stringResource(if (state.isLogin) R.string.sign_up else R.string.sign_in)
        ) {
            if (state.isLogin) {
                viewModel.onAction(UserAction.ShowSignUpForm)
            } else {
                viewModel.onAction(UserAction.ShowSignInForm)
            }
        }
    }
}