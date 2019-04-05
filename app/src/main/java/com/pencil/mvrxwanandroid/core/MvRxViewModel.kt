package com.pencil.mvrxwanandroid.core

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState

open class MvRxViewModel<S : MvRxState>(initialSate : S) : BaseMvRxViewModel<S>(initialSate, BuildConfig.DEBUG)