package com.itl.kg.androidlabkt

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation


/**
 *
 * Created by kenguerrilla on 2020/6/10.
 *
 */

object Navigator {

    fun setupAction(action: NavDirections, view: View) {
        Navigation.findNavController(view).navigate(action)
    }

}