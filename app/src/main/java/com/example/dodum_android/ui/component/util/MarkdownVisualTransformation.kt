package com.example.dodum_android.ui.component.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import java.util.regex.Pattern

class MarkdownVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val builder = AnnotatedString.Builder()
        val offsetMap = mutableListOf<Int>()

        var currentIndex = 0

        // 정규식 패턴: **Bold**, *Italic*, <u>Underline</u>
        // 그룹 1: 전체, 그룹 2: 내용
        val boldPattern = Pattern.compile("(\\*\\*(.*?)\\*\\*)")
        val italicPattern = Pattern.compile("(\\*(.*?)\\*)")
        val underlinePattern = Pattern.compile("(<u>(.*?)</u>)")

        val styledText = AnnotatedString.Builder(text).apply {
            // Bold (**...**)
            val boldMatcher = boldPattern.matcher(originalText)
            while (boldMatcher.find()) {
                val start = boldMatcher.start()
                val end = boldMatcher.end()
                addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                // 태그 부분 색상 흐리게 (선택 사항)
                addStyle(SpanStyle(color = Color.LightGray), start, start + 2)
                addStyle(SpanStyle(color = Color.LightGray), end - 2, end)
            }

            // Italic (*...*)
            val italicMatcher = italicPattern.matcher(originalText)
            while (italicMatcher.find()) {
                val start = italicMatcher.start()
                val end = italicMatcher.end()
                // **Bold** 패턴에 걸린 *는 제외하기 위한 간단한 체크 필요하지만 생략
                addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                addStyle(SpanStyle(color = Color.LightGray), start, start + 1)
                addStyle(SpanStyle(color = Color.LightGray), end - 1, end)
            }

            // Underline (<u>...</u>)
            val underlineMatcher = underlinePattern.matcher(originalText)
            while (underlineMatcher.find()) {
                val start = underlineMatcher.start()
                val end = underlineMatcher.end()
                addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                addStyle(SpanStyle(color = Color.LightGray), start, start + 3)
                addStyle(SpanStyle(color = Color.LightGray), end - 4, end)
            }
        }.toAnnotatedString()

        // 1:1 매핑 (글자를 숨기지 않음)
        return TransformedText(styledText, OffsetMapping.Identity)
    }
}