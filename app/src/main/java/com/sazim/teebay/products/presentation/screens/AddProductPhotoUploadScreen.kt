/*
 * Created by Saeedus Salehin on 19/7/25, 8:52 PM.
 */

package com.sazim.teebay.products.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.sazim.teebay.products.domain.utils.uriToByteArray
import com.sazim.teebay.products.presentation.ProductsState
import com.sazim.teebay.products.presentation.ProductsViewModel
import com.sazim.teebay.products.presentation.UserAction
import com.sazim.teebay.products.presentation.components.BackNextNavigationRow
import com.sazim.teebay.products.presentation.components.StepProgressIndicator

@Composable
fun AddProductPhotoUploadScreen(
    modifier: Modifier = Modifier,
    state: ProductsState,
    viewModel: ProductsViewModel
) {
    val context = LocalContext.current

    val tempImageUri = remember {
        val imagePath = java.io.File(context.cacheDir, "temp_images")
        imagePath.mkdirs()
        val newFile = java.io.File(imagePath, "captured_photo_${System.currentTimeMillis()}.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", newFile)
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            viewModel.onAction(UserAction.ImageSelected(uriToByteArray(context, tempImageUri)))
        } else {
            null
        }
    }


    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onAction(UserAction.ImageSelected(uriToByteArray(context, uri!!)))
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(32.dp))

        StepProgressIndicator(
            totalSteps = state.totalStepsToAddProduct,
            currentStep = 4
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = "Upload product picture",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { takePictureLauncher.launch(tempImageUri) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Take Picture using Camera")
        }

        Button(
            onClick = { pickImageLauncher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Upload Picture from Gallery")
        }

        state.selectedImageUri?.let { uri ->
            Spacer(Modifier.height(16.dp))
            AsyncImage(
                model = uri,
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(Modifier.weight(2f))

        BackNextNavigationRow(
            onBack = { viewModel.onAction(UserAction.OnBackPressed) },
            onNext = { viewModel.onAction(UserAction.NextPressedFromImgUpload) },
            isNextEnabled = state.selectedImageUri != null
        )

        Spacer(Modifier.height(32.dp))
    }
}