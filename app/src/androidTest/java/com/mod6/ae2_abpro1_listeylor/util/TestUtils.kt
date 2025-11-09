package com.mod6.ae2_abpro1_listeylor.util

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException

fun waitForView(viewMatcher: Matcher<View>, timeout: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = org.hamcrest.Matchers.any(View::class.java)
        override fun getDescription(): String = "Esperando hasta $timeout ms por la vista $viewMatcher"
        override fun perform(uiController: UiController, view: View?) {
            val endTime = System.currentTimeMillis() + timeout
            do {
                for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                    if (viewMatcher.matches(child)) return
                }
                uiController.loopMainThreadForAtLeast(50)
            } while (System.currentTimeMillis() < endTime)
            throw PerformException.Builder()
                .withActionDescription(description)
                .withViewDescription(view.toString())
                .withCause(TimeoutException())
                .build()
        }
    }
}