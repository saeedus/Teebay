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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
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
        Text(text = stringResource(R.string.sign_in), style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* TODO Handle login */ }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.login))
        }

        Spacer(Modifier.height(20.dp))

        AuthPrompt(
            promptText = stringResource(R.string.dont_have_account),
            actionText = stringResource(R.string.sign_up)
        ) { }
    }
}