package com.dlrjsgml.memoa.feature.main.profile.my.setting.description

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dlrjsgml.memoa.backhandler.safePopBackStack
import com.dlrjsgml.memoa.ui.animation.noRippleClickable
import com.dlrjsgml.memoa.ui.component.button.BackButton
import com.dlrjsgml.memoa.ui.component.textfield.SettingTextField
import com.dlrjsgml.memoa.ui.theme.Purple60
import com.dlrjsgml.memoa.ui.theme.caption1Regular

@Composable
fun DescriptionScreen(
    navController: NavHostController,
    viewModel: DescriptionSettingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                DescriptionSideEffect.Failure -> {}
                DescriptionSideEffect.Success -> navController.safePopBackStack()
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
            BackButton("자기소개 변경") {
                navController.safePopBackStack()
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
            value = uiState.description,
            onValueChange = viewModel::updateTitle,
            hint = "변경할 자기소개를 입력해주세요"
        )

    }


}