package com.dlrjsgml.memoa

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MemoaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MemoaApplication.context = applicationContext
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getContext() = context
    }
}