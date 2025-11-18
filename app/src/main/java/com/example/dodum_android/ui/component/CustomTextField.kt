package com.example.dodum_android.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    onTextChange: (String) -> Unit,
    placeholderText: String
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        placeholder = { Text(placeholderText) },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF9B9B9B),
            unfocusedBorderColor = Color(0xFF9B9B9B),
            focusedLabelColor = Color.White,
            cursorColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}