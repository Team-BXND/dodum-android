package com.example.dodum_android.feature.misc.write


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dodum_android.root.NavGroup
import com.example.dodum_android.ui.component.bar.TopBar
import com.example.dodum_android.ui.component.button.AnimatedButton
import com.example.dodum_android.ui.component.markdown.insertMarkdown
import com.example.dodum_android.ui.component.markdown.wrapSelection
import com.example.dodum_android.ui.theme.MainColor
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun MWriteScreen(
    navController: NavController,
) {
    val viewModel: MWriteViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.updateContent(
                    insertMarkdown(uiState.content, "\n![image]($it)\n")
                )
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        TopBar(navController)

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(24.dp)
        ) {

            /* 카테고리 */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Category.values().forEach {
                    Text(
                        text = it.label,
                        fontSize = 20.sp,
                        color = if (it == uiState.category) Color(0xFF26874E) else Color(0xFFADADAD),
                        modifier = Modifier.clickable { viewModel.selectCategory(it) }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            /* 제목 */
            Column {
                TextField(
                    value = uiState.title,
                    onValueChange = viewModel::updateTitle,
                    placeholder = {
                        Text("제목을 입력하세요", fontSize = 24.sp)
                    },
                    textStyle = TextStyle(fontSize = 24.sp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Divider(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    color = Color.Gray,
                    thickness = 1.dp
                )
            }

            Spacer(Modifier.height(12.dp))

            /* 본문 */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                if (uiState.isPreview) {
                    MarkdownText(
                        markdown = uiState.content.text,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 12.dp
                            )
                            .verticalScroll(rememberScrollState()),
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 24.sp
                        )
                    )
                } else {
                    TextField(
                        value = uiState.content,
                        onValueChange = viewModel::updateContent,
                        placeholder = { Text("본문을 입력하세요", fontSize = 20.sp) },
                        modifier = Modifier
                            .fillMaxSize(),
                        textStyle = TextStyle(fontSize = 20.sp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            /* 마크다운 툴바 */
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!uiState.isPreview) {
                    IconButton(onClick = {
                        viewModel.updateContent(
                            wrapSelection(
                                uiState.content,
                                "**"
                            )
                        )
                    }) {
                        Icon(Icons.Default.FormatBold, contentDescription = null)
                    }
                    IconButton(onClick = {
                        viewModel.updateContent(
                            wrapSelection(
                                uiState.content,
                                "*"
                            )
                        )
                    }) {
                        Icon(Icons.Default.FormatItalic, contentDescription = null)
                    }
                    IconButton(onClick = { imagePicker.launch("image/*") }) {
                        Icon(Icons.Default.Photo, contentDescription = null)
                    }
                }

                Spacer(Modifier.weight(1f))

                /* 미리보기 토글 */
                IconButton(onClick = { viewModel.togglePreview() }) {
                    Icon(
                        imageVector = if (uiState.isPreview) Icons.Default.Edit else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                /* 등록 */
                AnimatedButton(
                    onClick = { val request = viewModel.toCreateRequest() },
                    modifier = Modifier
                        .width(76.dp)
                        .height(43.dp)
                        .background(MainColor, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "등록",
                        fontSize = 19.sp,
                        color = Color.White,
                    )
                }

                /* 취소 */
                AnimatedButton(
                    onClick = { navController.navigate(NavGroup.MSHARE) },
                    modifier = Modifier
                        .width(84.dp)
                        .height(43.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "취소",
                        fontSize = 19.sp,
                        color = Color.White,
                    )
                }
            }
        }
    }
}