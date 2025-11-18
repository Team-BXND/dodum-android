package com.example.dodum_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubDropDownMenu(
    selectedClub: String,
    onClubSelected:(String) -> Unit
){
    val ClubList = listOf("BIND", "3D", "두카미", "Louter", "CNS", "모디", "ALT", "Chatty", "NONE")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
            value = selectedClub,
            onValueChange = {},
            textStyle = LocalTextStyle.current.copy(fontSize = 19.sp),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.colors(
                // 테두리
                unfocusedIndicatorColor = Color(0xFF9B9B9B),
                focusedIndicatorColor = Color(0xFF9B9B9B),
                // 컨테이너
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false},
                modifier = Modifier.background(Color.White, RoundedCornerShape(8.dp))

            ) {
                ClubList.forEach{ club ->
                    DropdownMenuItem(
                        text = { Text(club, fontSize = 19.sp) },
                        onClick = {
                            onClubSelected(club)
                            expanded = false
                        }

                    )

                }
            }
    }
}