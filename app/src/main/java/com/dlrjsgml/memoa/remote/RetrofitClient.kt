package com.dlrjsgml.memoa.remote

import com.dlrjsgml.memoa.BuildConfig
import com.dlrjsgml.memoa.network.bookmark.GetBookMarkService
import com.dlrjsgml.memoa.network.bookmark.PostBookMarkService
import com.dlrjsgml.memoa.network.follow.FollowService
import com.dlrjsgml.memoa.network.data.login.LoginService
import com.dlrjsgml.memoa.network.data.school.SchoolService
import com.dlrjsgml.memoa.network.data.signup.GetCodeService
import com.dlrjsgml.memoa.network.data.signup.LastSignupService
import com.dlrjsgml.memoa.network.data.signup.SendCodeService
import com.dlrjsgml.memoa.network.follow.GetFollowersService
import com.dlrjsgml.memoa.network.follow.GetFollowingService
import com.dlrjsgml.memoa.network.main.GetMainService
import com.dlrjsgml.memoa.network.main.detail.DetailService
import com.dlrjsgml.memoa.network.profile.PatchUserInfo
import com.dlrjsgml.memoa.network.profile.GetProfileInfoService
import com.dlrjsgml.memoa.network.profile.GetUserArticles
import com.dlrjsgml.memoa.network.profile.GetUserProfileInfoService
import com.dlrjsgml.memoa.network.token.TokenService
import com.dlrjsgml.memoa.network.write.WriteService
import com.dlrjsgml.memoa.network.write.image.UpLoadImgService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {
//    private lateinit var appContext: Context
//    fun initializeContext(context: Context) {
//        appContext = context.applicationContext
//    }


    private const val BASE_URL = BuildConfig.API_KEY
    var gson = GsonBuilder().setLenient().create()
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // 요청 메서드 및 URL만 로그에 남기기
    }


    val interceptorClient = OkHttpClient().newBuilder().addInterceptor(RequestInterceptor())
        .addInterceptor(ResponseInterceptor()).build()

    val client = OkHttpClient.Builder().addInterceptor(logging).build()

    val instance: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(interceptorClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }


    val writeService: WriteService by lazy { instance.create(WriteService::class.java) }
    val upLoadImgService: UpLoadImgService by lazy { instance.create(UpLoadImgService::class.java) }
    val getMainService: GetMainService by lazy { instance.create(GetMainService::class.java) }
    val getDetailService: DetailService by lazy { instance.create(DetailService::class.java) }
    val getProfileService: GetProfileInfoService by lazy { instance.create(GetProfileInfoService::class.java) }
    val getUserProfileService: GetUserProfileInfoService by lazy { instance.create(GetUserProfileInfoService::class.java) }
    val patchProfileService: PatchUserInfo by lazy { instance.create(PatchUserInfo::class.java) }
    val getFollowingService: GetFollowingService by lazy { instance.create(GetFollowingService::class.java) }
    val getFollowersService: GetFollowersService by lazy { instance.create(GetFollowersService::class.java) }
    val followService: FollowService by lazy { instance.create(FollowService::class.java) }
    val getBookMarkService: GetBookMarkService by lazy { instance.create(GetBookMarkService::class.java) }
    val postBookMarkService: PostBookMarkService by lazy { instance.create(PostBookMarkService::class.java) }
    val getUserArticles: GetUserArticles by lazy { instance.create(GetUserArticles::class.java) }
    val getSchoolService: SchoolService by lazy { instance.create(SchoolService::class.java) }
    val getLoginService: LoginService by lazy { instance.create(LoginService::class.java) }
    val sendCodeService: SendCodeService by lazy { instance.create(SendCodeService::class.java) }
    val getCodeService: GetCodeService by lazy { instance.create(GetCodeService::class.java) }
    val signupService: LastSignupService by lazy { instance.create(LastSignupService::class.java) }
    val tokenService: TokenService by lazy { instance.create(TokenService::class.java) }
}