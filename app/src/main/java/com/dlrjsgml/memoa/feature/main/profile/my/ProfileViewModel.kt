package com.dlrjsgml.memoa.feature.main.profile.my

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.main.profile.user.UserArticlesSideEffect
import com.dlrjsgml.memoa.network.main.ArticleResponse
import com.dlrjsgml.memoa.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MyProfileState(
    val email: String = "",
    val nickname: String = "",
    val description: String? = "",
    val profileImage: String = "",
    val articles: List<ArticleResponse> = listOf(),
    )

data class MyFollowingState(
    val following : Int =-1,
    val follower : Int =-1
)

sealed interface MyProfileEffect {
    data object Success : MyProfileEffect
    data object Failed : MyProfileEffect
}

sealed interface MyFollowingEffect {
    data object Success : MyFollowingEffect
    data object Failed : MyFollowingEffect
}
sealed interface MyArticlesSideEffect {
    data object Success : MyArticlesSideEffect
    data object Failed : MyArticlesSideEffect
}

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyProfileState())
    val uiState: StateFlow<MyProfileState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MyProfileEffect>()
    val uiEffect: SharedFlow<MyProfileEffect> = _uiEffect.asSharedFlow()

    private val _userArticlesSideEffect = MutableSharedFlow<MyArticlesSideEffect>()
    val userArticlesSideEffect: SharedFlow<MyArticlesSideEffect> = _userArticlesSideEffect.asSharedFlow()
    private val _followUiState = MutableStateFlow(MyFollowingState())
    val followUiState: StateFlow<MyFollowingState> = _followUiState.asStateFlow()

    private val _followingUiEffect = MutableSharedFlow<MyFollowingEffect>()
    val followingUiEffect: SharedFlow<MyFollowingEffect> = _followingUiEffect.asSharedFlow()

    fun getProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response =
                    RetrofitClient.getProfileService.getProfileInfo()
                Log.d("프로필", "리스폰스보기 : $response");
                _uiState.update {
                    it.copy(
                        email = response.email,
                        nickname = response.nickname,
                        description = response.description,
                        profileImage = response.profileImage
                    )
                }
                _uiEffect.emit(MyProfileEffect.Success)
            } catch (e: Exception) {
                Log.d("프로필", "뷰모델에서 에러 : $e");
                _uiEffect.emit(MyProfileEffect.Failed)
            }
        }
    }


    fun getUsersArticles(author: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.getUserArticles.getUserArticles(author)
                Log.d("유저", "성공 : $response");
                _uiState.update {
                    it.copy(
                        articles = response
                    )
                }
                _userArticlesSideEffect.emit(MyArticlesSideEffect.Success)
            } catch (e: Exception) {
                Log.d("유저", "오류 : $e");
                _userArticlesSideEffect.emit(MyArticlesSideEffect.Failed)
            }
        }

    }
    fun getFollowSize(user:String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val followingResponse = RetrofitClient.getFollowingService.getFollowingList(user)
                Log.d("팔로우", "팔로잉 : $followingResponse");

                val followersResponse = RetrofitClient.getFollowersService.getFollowersList(user)
                Log.d("팔로우", "팔로우 : $followersResponse");


                _followUiState.update {
                    it.copy(
                        following = followingResponse.size,
                        follower = followersResponse.size
                    )
                }
                _followingUiEffect.emit(MyFollowingEffect.Success)
            }catch (e:Exception){
                Log.d("팔로우", "뷰모델에서 에러 : $e");

                _followingUiEffect.emit(MyFollowingEffect.Failed)
            }
        }
    }
}