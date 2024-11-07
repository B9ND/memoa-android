package com.dlrjsgml.memoa.feature.main.profile.user


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.dlrjsgml.memoa.network.main.ArticleResponse
import com.dlrjsgml.memoa.remote.RetrofitClient
import com.dlrjsgml.memoa.remote.TemporaryToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserProfileState(
    val email: String = "",
    val nickname: String = "",
    val description: String? = "",
    val profileImage: String = "",
    val articles: List<ArticleResponse> = listOf(),
)

data class UserFollowingState(
    val following: Int = -1,
    val follower: Int = -1,
)

sealed interface UserProfileEffect {
    data object Success : UserProfileEffect
    data object Failed : UserProfileEffect
}

sealed interface UserFollowingEffect {
    data object Success : UserFollowingEffect
    data object Failed : UserFollowingEffect
}

sealed interface UserArticlesSideEffect {
    data object Success : UserArticlesSideEffect
    data object Failed : UserArticlesSideEffect
}


class UserProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserProfileState())
    val uiState: StateFlow<UserProfileState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UserProfileEffect>()
    val uiEffect: SharedFlow<UserProfileEffect> = _uiEffect.asSharedFlow()


    private val _followUiState = MutableStateFlow(UserFollowingState())
    val followUiState: StateFlow<UserFollowingState> = _followUiState.asStateFlow()

    private val _followingUiEffect = MutableSharedFlow<UserFollowingEffect>()
    val followingUiEffect: SharedFlow<UserFollowingEffect> = _followingUiEffect.asSharedFlow()
    private val _userArticlesSideEffect = MutableSharedFlow<UserArticlesSideEffect>()
    val userArticlesSideEffect: SharedFlow<UserArticlesSideEffect> = _userArticlesSideEffect.asSharedFlow()


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
                _userArticlesSideEffect.emit(UserArticlesSideEffect.Success)
            } catch (e: Exception) {
                Log.d("유저", "오류 : $e");
                _userArticlesSideEffect.emit(UserArticlesSideEffect.Failed)
            }
        }

    }

    fun getUserProfileInfo(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.getUserProfileService.getUserProfileInfo(userName)
                Log.d("유저", "성공 : $response");
                _uiState.update {
                    it.copy(
                        email = response.email,
                        nickname = response.nickname,
                        description = response.description,
                        profileImage = response.profileImage
                    )
                }
                _uiEffect.emit(UserProfileEffect.Success)
            } catch (e: Exception) {
                Log.d("유저", "오류 : $e");
                _uiEffect.emit(UserProfileEffect.Failed)
            }
        }
    }

    fun getFollowSize(user: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val followingResponse = RetrofitClient.getFollowingService.getFollowingList(
                    TemporaryToken.AccessToken,
                    user
                )
                Log.d("팔로우", "팔로잉 : $followingResponse");

                val followersResponse = RetrofitClient.getFollowersService.getFollowersList(
                    TemporaryToken.AccessToken,
                    user
                )
                Log.d("팔로우", "팔로우 : $followersResponse");


                _followUiState.update {
                    it.copy(
                        following = followingResponse.size,
                        follower = followersResponse.size
                    )
                }
                _followingUiEffect.emit(UserFollowingEffect.Success)
            } catch (e: Exception) {
                Log.d("팔로우", "뷰모델에서 에러 : $e");

                _followingUiEffect.emit(UserFollowingEffect.Failed)
            }
        }
    }
}