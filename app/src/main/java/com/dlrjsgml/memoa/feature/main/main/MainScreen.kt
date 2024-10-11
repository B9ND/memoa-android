package com.dlrjsgml.memoa.feature.main.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.feature.main.main.deatil.DetailScreen
import com.dlrjsgml.memoa.root.NavGroup
import com.dlrjsgml.memoa.ui.component.items.ArticleList
import com.dlrjsgml.memoa.ui.component.MemoaDropDown
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainScreen(
    navController: NavHostController
) {
    Column {
        Row(modifier = Modifier
            .background(Color.White)
            .padding(start = 25.dp, end = 27.dp, top = 10.dp)
            .padding(vertical = 8.dp)
            ) {
            MemoaDropDown(
                selectList = listOf("대구소프트웨어마이스터고등학교", "교학웨트프소구대"),
                modifier = Modifier.weight(6f)
            ) {
            }
            MemoaDropDown(
                selectList = listOf("1학년", "2학년", "3학년"),
                modifier = Modifier.weight(2.4f)

            ) {
            }
        }

        LazyColumn {
           items(5){
               ArticleList(
                   name = "안녕하세요",
                   date = "2024년 8월 13일",
                   title = "국어, 과학 필기 공유합니다!",
                   profile = "https://image.dongascience.com/Photo/2017/03/1489737117788.png",
                   tag = persistentListOf("국어", "과학"),
                   image = persistentListOf(
                       "https://newsimg.hankookilbo.com/cms/articlerelease/2021/04/26/813324fb-5b9a-4065-a064-cb52e7c21156.jpg",
                       "https://upload.wikimedia.org/wikipedia/commons/e/ea/Korean_Jindo_Dog.jpg"
                   ),
                   onClick = {navController.navigate("${NavGroup.DETAIL}/phone=ddddddd")},
                   bookmarkClick = {},
                   commentClick = {navController.navigate("${NavGroup.COMMENT}/phone=ddddddd")}
               )
           }
        }
    }
}

@Preview
@Composable
private fun afdjajfd() {
}