package com.dlrjsgml.memoa.feature.main.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.dlrjsgml.memoa.ui.component.ArticleList
import com.dlrjsgml.memoa.ui.component.DropDown
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainScreen() {
    Box {
        DropDown(
            text = "대구소프트웨어마이스터고등학교",
            selectList = listOf("1학년", "2학년", "3학년"),
            onClick = { /*TODO*/ },
            onTextChanged = { newText ->
                Log.d("하이", newText);
            }
        )
        LazyColumn {
           items(5){
               ArticleList(
                   name = "김은찬",
                   date = "2024년 8월 13일",
                   content = "국어, 과학 필기 공유합니다!",
                   profile = "https://image.dongascience.com/Photo/2017/03/1489737117788.png",
                   tag = persistentListOf("국어", "과학"),
                   image = persistentListOf(
                       "https://newsimg.hankookilbo.com/cms/articlerelease/2021/04/26/813324fb-5b9a-4065-a064-cb52e7c21156.jpg",
                       "https://upload.wikimedia.org/wikipedia/commons/e/ea/Korean_Jindo_Dog.jpg"
                   )
               )
           }
        }
    }
}