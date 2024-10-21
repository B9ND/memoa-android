package com.dlrjsgml.memoa.ui.component.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.theme.Gray20
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.boardName

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemoaSimpleDialog(
    modifier: Modifier = Modifier,
    content : String,
    onClickConfirm: () -> Unit
){
    Dialog(onDismissRequest = onClickConfirm) {
        Column(modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .width(260.dp)
            .border(width = 1.dp, color = Gray20, shape = RoundedCornerShape(16.dp))) {
            Spacer(modifier = modifier.height(24.dp))
            Text(modifier = modifier.align(Alignment.CenterHorizontally), text = content, style = boardName.copy(fontWeight = FontWeight.SemiBold))
            Spacer(modifier = modifier.height(16.dp))
            Box(modifier = modifier
                .background(Gray20)
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp))
            Spacer(modifier = modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().noRippleClickable(onClickConfirm)){
                Text(modifier = modifier
                    .align(Alignment.Center)
                    , text = "확인", style = boardName.copy(), color = Purple60)
            }


            Spacer(modifier = modifier.height(24.dp))


        }


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun SimpleDialogPreView(){
    MemoaSimpleDialog(content = "글 작성이 취소 됐습니다.") {
        
    }
}