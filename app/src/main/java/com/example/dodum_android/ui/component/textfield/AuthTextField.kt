package com.example.dodum_android.ui.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.ErrorColor
import com.example.dodum_android.ui.theme.FontGray

@Composable
fun AuthTextField(
    fieldname: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    iserror: Boolean,
    errortext: String? = null
) {
    Column (modifier = Modifier
        .width(330.dp)
    ) {
        Text(text = fieldname,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal)

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = ErrorColor,
                errorTextColor = ErrorColor,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = FontGray,
                    fontSize = 12.sp
                )
            },
            isError = iserror,
            modifier = Modifier.fillMaxWidth()
        )

        if (iserror && errortext != null) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = errortext,
                color = ErrorColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }
    }

}