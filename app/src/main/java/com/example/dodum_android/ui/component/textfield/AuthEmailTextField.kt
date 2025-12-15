package com.example.dodum_android.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.ErrorColor
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun AuthEmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    ischeck: Int,
    modifier: Modifier = Modifier,
    errortext: String? = null,
    onclick: () -> Unit
) {
    Column (modifier = Modifier
        .width(318.dp)
    ) {
        Text(text = "이메일",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier .height(7.dp))

        Row {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    errorBorderColor = ErrorColor,
                    errorTextColor = ErrorColor
                ),
                placeholder = {
                    Text(
                        text = "예: example@email.com",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                },
                isError = isError,
                modifier = modifier
                    .width(224.dp)
            )

            Spacer(modifier .width(8.dp))

            Button(
                onClick = onclick,
                modifier = Modifier
                    .width(88.dp)
                    .height(42.dp)
                    .background(MainColor, shape = RoundedCornerShape(12.dp))
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor,
                    contentColor = Color.White
                )
            ) {
                Box(modifier
                    .width(88.dp)
                    .height(42.dp)
                ){
                    Text(text = "인증",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.Center)
//                            .fillMaxSize()
                    )
                }
            }
        }
        if (ischeck >= 1) {
            Text(text = "인증 번호가 전송 되었습니다.",
                color = Color.Blue,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }
        if (isError){
            Text(text = errortext.toString(),
                color = ErrorColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}