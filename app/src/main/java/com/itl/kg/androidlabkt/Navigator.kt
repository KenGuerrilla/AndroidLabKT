package com.itl.kg.androidlabkt

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

object Navigator {

    fun setupAction(action: NavDirections, view: View) {
        Navigation.findNavController(view).navigate(action)
    }

}