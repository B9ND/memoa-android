package com.dlrjsgml.memoa.ui.component.textfield

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import com.dlrjsgml.memoa.R

@Composable
fun AuthText() {
    val context = LocalContext.current

    val authString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.text_black)
            )
        ) {
            append("계정을 생성함으로써,\n")
        }

        val tosStart = length
        withStyle(
            SpanStyle(
                fontSize = 11.sp,
                color = colorResource(R.color.auth_text),
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("이용약관")
        }
        val tosEnd = length

        withStyle(
            SpanStyle(
                fontSize = 11.sp,
                color = colorResource(R.color.text_black)
            )
        ) {
            append("과 ")
        }

        val privacyStart = length
        withStyle(
            SpanStyle(
                fontSize = 11.sp,
                color = colorResource(R.color.auth_text),
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("개인정보처리방침")
        }
        val privacyEnd = length

        withStyle(
            SpanStyle(
                fontSize = 11.sp,
                color = colorResource(R.color.text_black)
            )
        ) {
            append("에 동의하셨음을 확인합니다.")
        }

        addStringAnnotation(
            tag = "TOS_URL",
            annotation = "https://trusting-sparrow-13c.notion.site/4024b7443e664458862ea52b55d91f79?pvs=4",
            start = tosStart,
            end = tosEnd
        )

        addStringAnnotation(
            tag = "PRIVACY_URL",
            annotation = "https://www.dodge.com/",
            start = privacyStart,
            end = privacyEnd
        )
    }

    ClickableText(
        text = authString,
        modifier = Modifier.fillMaxWidth(),
        style = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        onClick = { offset ->
            authString.getStringAnnotations(
                tag = "TOS_URL",
                start = offset,
                end = offset
            ).firstOrNull()?.let { annotation ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(annotation.item)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Log.e("AuthText", "Error opening TOS URL", e)
                }
            }
            authString.getStringAnnotations(
                tag = "PRIVACY_URL",
                start = offset,
                end = offset
            ).firstOrNull()?.let { annotation ->
                try {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(annotation.item)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Log.e("AuthText", "Error opening Privacy URL", e)
                }
            }
        },
    )
}
