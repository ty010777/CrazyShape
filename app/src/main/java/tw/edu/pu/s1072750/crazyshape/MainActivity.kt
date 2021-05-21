package tw.edu.pu.s1072750.crazyshape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
@GlideModule
public final class MyAppGlideModule : AppGlideModule()


class MainActivity : AppCompatActivity(),
    GestureDetector.OnGestureListener,View.OnTouchListener,
    GestureDetector.OnDoubleTapListener {
    lateinit var gDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gDetector = GestureDetector(this, this)
        imgNext.setOnTouchListener(this)

        Toast.makeText(baseContext, "作者：陳嘉良", Toast.LENGTH_LONG).show()

        imgNext.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                intent = Intent(this@MainActivity, GameActivity::class.java)
                startActivity(intent)
                return true
            }
        })
        val img: ImageView = findViewById(R.id.imgTitle)

        GlideApp.with(this)
            .load(R.drawable.cover)
            .override(800, 600)
            .into(img)

    }

    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
        gDetector.onTouchEvent(event)
        return true
    }

    var PictureNo: Int = 0  //目前顯示第幾張圖
    var TotalPictures: Int = 4 //總共幾張圖片(假設僅顯示pu0-pu3)


    fun ShowPicture() {
        when (PictureNo) {
            0 -> imgNext.setImageResource(R.drawable.circle)
            1 -> imgNext.setImageResource(R.drawable.triangle)
            2 -> imgNext.setImageResource(R.drawable.star)
            3 -> imgNext.setImageResource(R.drawable.square)
        }
    }

    override fun onDown(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onShowPress(e: MotionEvent?) {
        TODO("Not yet implemented")
    }


    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        PictureNo = 0
        ShowPicture()
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLongPress(p0: MotionEvent?) {
        PictureNo = TotalPictures - 1
        ShowPicture()
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}
