package com.dlrjsgml.memoa.feature.main.main.paging

sealed class FetchFlow<Data> {
    class Fetching<Data> : FetchFlow<Data>()
    data class Success<Data>(val data: Data) : FetchFlow<Data>()
    class Failure<Data> : FetchFlow<Data>()
}