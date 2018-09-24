package com.joaquimley.transporteta.ui.util.extensions

import com.airbnb.lottie.Cancellable
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieComposition

fun LottieAnimationView.load(jsonString: String) {
    this.setAnimation(jsonString)
    this.playAnimation()
}

fun LottieAnimationView.loadAsync(jsonString: String): Cancellable {
    return LottieComposition.Factory.fromJsonString(jsonString) {
        it?.let {
            this.setComposition(it)
            this.playAnimation()
        }
    }
}