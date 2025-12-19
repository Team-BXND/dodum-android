package com.example.dodum_android.ui.component.markdown

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun insertMarkdown(
    value: TextFieldValue,
    insertText: String
): TextFieldValue {
    val start = value.selection.start
    val end = value.selection.end

    val newText = buildString {
        append(value.text.substring(0, start))
        append(insertText)
        append(value.text.substring(end))
    }

    return value.copy(
        text = newText,
        selection = TextRange(start + insertText.length)
    )
}

fun wrapSelection(
    value: TextFieldValue,
    prefix: String,
    suffix: String = prefix
): TextFieldValue {
    val start = value.selection.start
    val end = value.selection.end

    if (start == end) {
        return insertMarkdown(value, "$prefix$suffix")
    }

    val selected = value.text.substring(start, end)

    val newText = buildString {
        append(value.text.substring(0, start))
        append(prefix)
        append(selected)
        append(suffix)
        append(value.text.substring(end))
    }

    return value.copy(
        text = newText,
        selection = TextRange(
            start + prefix.length,
            end + prefix.length
        )
    )
}