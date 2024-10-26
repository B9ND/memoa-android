package com.dlrjsgml.memoa.feature.main.main.deatil

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dlrjsgml.memoa.feature.main.main.ArticlesSideEffect
import com.dlrjsgml.memoa.feature.main.main.ArticlesState
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.remote.TemporaryToken
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class DetailInfoState(
    val id: Int = 1,
    val title: String = "오류",
    val content : String = "오류",
    val author : String = "ERROR",
    val tags : List<String> = emptyList(),
    val createdAt : String = "2008.12.13",
    val images: List<String> = emptyList(),
)

sealed interface DetailInfoSideEffect {
    data object Success: DetailInfoSideEffect
    data object Failure: DetailInfoSideEffect
}

class DetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DetailInfoState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DetailInfoSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    suspend fun getDetailInfo(id : Int){
        try {
            val response = RetrofitClient.getDetailService.getDetailArticle(TemporaryToken.AccessToken,id)
            _uiState.update {it.copy(
                id = response.id,
                title = response.title,
                content = response.content,
                author = response.author,
                tags = response.tags,
                createdAt = response.createdAt,
                images = response.images
            )  }
            _uiEffect.emit(DetailInfoSideEffect.Success)

        } catch (e:Exception){
            _uiEffect.emit(DetailInfoSideEffect.Failure)
            Log.d("디테일", "$e 디테일뷰모델 오류");

        }

    }
}