package com.example.dodum_android.ui.component.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dodum_android.ui.theme.ErrorColor


@Composable
fun AuthDropdown(
    fieldname: String,
    placeholder: String,
    selects: List<String>,
    selectedClub: String,
    onClubSelected: (String) -> Unit,
    isError: Boolean = false,
    width: Int
) {

    var expanded by remember { mutableStateOf(false) }

    Column {

        Row (
            modifier = Modifier.align(Alignment.Start)
        ) {
//            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = fieldname,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        val borderColor = if (isError) ErrorColor else Color.Black.copy(alpha = 0.7f)

        Box {
            OutlinedTextField(
                value = selectedClub,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "null"
                        )
                    }
                },
                modifier = Modifier
                    .width(width.dp)
                    .height(52.dp)
                    .clickable { expanded = !expanded }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(width.dp)
            ) {
                selects.forEach { list ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = list,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        onClick = {
                            onClubSelected(list)
                            expanded = false
                        }
                    )
                }
            }
        }

        if (isError){
            Text(text = "다시 선택해주세요.",
                color = ErrorColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
        }

    }
}