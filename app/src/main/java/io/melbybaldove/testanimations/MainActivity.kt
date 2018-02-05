package io.melbybaldove.testanimations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text_view.setOnClickListener {
            if (isLollipopOrHigher()) {
                if (overlay.visibility == View.VISIBLE) {
                    animateRemoveOverlayAround(text_view)

                } else {
                    animateShowOverlayAround(text_view)
                }
            }
            else {
                toggleOverlay()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateShowOverlayAround(view: View) {
        val anim = ViewAnimationUtils.createCircularReveal(overlay, view.centerXposition(),
                        view.centerYPosition(), view.radius(), overlay.radius() * 2)
        // make the view visible and start the animation
        anim.start()
        overlay.visibility = View.VISIBLE

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRemoveOverlayAround(view: View) {
        val anim = ViewAnimationUtils.createCircularReveal(overlay, view.centerXposition(),
                view.centerYPosition(), overlay.radius() * 2, 0f)

        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                overlay.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    private fun toggleOverlay() {
        overlay.visibility = when(overlay.visibility) {
            View.VISIBLE -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    fun View.centerXposition(): Int {
        return this.left + this.width / 2
    }

    fun View.centerYPosition(): Int {
        return this.top + this.height / 2
    }

    fun View.radius(): Float {
        val cx = this.width / 2
        val cy = this.height / 2
        return Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
    }

    private fun isLollipopOrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }
}
