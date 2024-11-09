package com.diego.matesanz.arcaneum.ui.common

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.View.TEXT_ALIGNMENT_TEXT_END
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import android.widget.TextView
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(
    text: String,
    style: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier
) {
    val resolver: FontFamily.Resolver = LocalFontFamilyResolver.current
    val typeface: Typeface = remember(resolver, style) {
        resolver.resolve(fontFamily = style.fontFamily)
    }.value as Typeface
    val color = style.color.takeOrElse { LocalContentColor.current }

    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { textView ->
            textView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
            textView.setTextColor(color.toArgb())
            textView.typeface = typeface
            textView.textSize = style.fontSize.value
            textView.maxLines = maxLines
            textView.ellipsize = if (maxLines == Int.MAX_VALUE) null else TextUtils.TruncateAt.END
            textView.textAlignment = when (textAlign) {
                TextAlign.Center, TextAlign.Justify -> TEXT_ALIGNMENT_CENTER
                TextAlign.End, TextAlign.Right -> TEXT_ALIGNMENT_TEXT_END
                else -> TEXT_ALIGNMENT_TEXT_START
            }
        }
    )
}
