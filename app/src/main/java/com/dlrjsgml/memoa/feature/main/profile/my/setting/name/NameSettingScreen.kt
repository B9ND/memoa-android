package com.dlrjsgml.memoa.feature.main.profile.my.setting.name

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dlrjsgml.memoa.feature.main.profile.my.setting.description.DescriptionSideEffect
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.textfield.SettingTextField
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@Composable
fun NameSettingScreen(
    navController: NavHostController,
    viewModel: NameSettingViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DescriptionSideEffect.Failure -> {}
                DescriptionSideEffect.Success -> navController.popBackStack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(horizontal = 20.dp)
        ) {
            BackButton("이름변경") {
                navController.popBackStack()
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.noRippleClickable {
                    viewModel.changeName()
                },
                text = "완료",
                color = Purple60,
                style = caption1Regular.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        Spacer(modifier = Modifier.height(44.dp))
        SettingTextField(
            modifier = Modifier.padding(horizontal = 24.dp),
            value = uiState.name,
            onValueChange = viewModel::updateTitle,
            hint = "변경할 이름을 입력해주세요"
        )

    }

}

@Preview
@Composable
private fun afdjkadfjkafdlk() {
    NameSettingScreen(navController = rememberNavController())

}