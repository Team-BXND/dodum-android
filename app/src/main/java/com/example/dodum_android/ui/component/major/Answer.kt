package com.example.dodum_android.ui.component.major

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.MainColor

@Composable
fun Answer(
    question: String,
    text: String,
    onTextChange: (String) -> Unit,
){
    Row(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .heightIn(min = 120.dp)
                .width(8.dp)
                .background(MainColor, shape = RoundedCornerShape(16.dp))
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = MainColor,
                    clip = false
                )
                .background(Color.White, RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(23.dp)
            ) {
                Text(
                    text = question,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = text,
                    onValueChange = onTextChange,
                    singleLine = true,
                    placeholder = { Text("텍스트 입력") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        cursorColor = Color.Black,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}