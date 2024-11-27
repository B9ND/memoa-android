package com.dlrjsgml.memoa.feature.main.follower

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlrjsgml.memoa.feature.main.profile.user.FollowEffect
import com.dlrjsgml.memoa.network.follow.FollowResponse
import com.dlrjsgml.memoa.network.profile.ProfileResponse
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

data class FollowState(
    val followings: List<FollowResponse> = emptyList(),
    val followers: List<FollowResponse> = emptyList(),
    val isFollowing: Boolean = false,
    val isLoaded: Boolean = false
)

sealed interface FollowSideEffect {
    data object Success : FollowSideEffect
    data object Failed : FollowSideEffect
}


class FollowViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FollowState())
    val uiState: StateFlow<FollowState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<FollowSideEffect>()
    val uiEffect: SharedFlow<FollowSideEffect> = _uiEffect.asSharedFlow()

    fun getFollow(user: String): String {
        Log.d("팔로우", "함수를 부름");
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val followingResponse = RetrofitClient.getFollowingService.getFollowingList(
                    user
                )
                val followersResponse = RetrofitClient.getFollowersService.getFollowersList(
                    user
                )
                _uiState.update {
                    it.copy(
                        followings = followingResponse,
                        followers = followersResponse,
                        isLoaded = true
                    )
                }
                _uiEffect.emit(FollowSideEffect.Success)
            } catch (e: Exception) {
                Log.d("팔로우", "뷰모델에서 에러 : $e");
                _uiEffect.emit(FollowSideEffect.Failed)
            }
        }
        return "했음"
    }

    fun follow(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.followService.follow(userName)
                Log.d("팔로우", "팔로우 : $response");
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun changeToFollowers() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isFollowing = false) }
        }
    }

    fun changeToFollowings() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isFollowing = true) }
        }
    }
}