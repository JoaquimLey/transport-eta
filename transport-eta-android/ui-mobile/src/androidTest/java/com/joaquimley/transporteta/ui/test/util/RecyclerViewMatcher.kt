package com.joaquimley.transporteta.ui.test.util

import android.content.res.Resources
import androidx.test.InstrumentationRegistry
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import org.hamcrest.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


open class RecyclerViewMatcher constructor(var recyclerViewId: Int) {

    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            internal var resources: Resources? = null
            internal var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = Integer.toString(recyclerViewId)
                if (this.resources != null) {
                    try {
                        idDescription = this.resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        idDescription = String.format("%s (resource title not found)",
                                *arrayOf<Any>(Integer.valueOf(recyclerViewId)))
                    }

                }

                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<androidx.recyclerview.widget.RecyclerView>(recyclerViewId)
                            as androidx.recyclerview.widget.RecyclerView
                    if (recyclerView.id == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position).itemView
                    } else {
                        return false
                    }
                }

                if (targetViewId == -1) {
                    return view === childView
                } else {
                    val targetView = childView?.findViewById<View>(targetViewId)
                    return view === targetView
                }

            }
        }
    }

    companion object {

        fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
            return RecyclerViewMatcher(recyclerViewId)
        }

     fun waitForAdapterChange(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val latch = CountDownLatch(1)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            recyclerView.adapter?.registerAdapterDataObserver(
                    object : androidx.recyclerview.widget.RecyclerView.AdapterDataObserver() {
                        override fun onChanged() {
                            latch.countDown()
                        }

                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            latch.countDown()
                        }
                    })
        }
        if (recyclerView.adapter?.itemCount ?: -1 > 0) {
            return
        }
        MatcherAssert.assertThat(latch.await(10, TimeUnit.SECONDS), CoreMatchers.`is`(true))
    }

    }

}