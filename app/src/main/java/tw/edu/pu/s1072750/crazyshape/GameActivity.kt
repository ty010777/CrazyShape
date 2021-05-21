package tw.edu.pu.s1072750.crazyshape

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import org.tensorflow.lite.support.image.TensorImage
import tw.edu.pu.s1072750.crazyshape.ml.Shapes

class GameActivity : AppCompatActivity(),View.OnClickListener,View.OnTouchListener {
    //var P : String=intent.getStringExtra("picture",imgNext.drawable.toString())
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        intent = getIntent()
        //var P : String=intent.getStringExtra("picture",imgNext.drawable.toString())
        btnBack.setOnClickListener(this)
        btn.setOnClickListener(this)
        handv.setOnTouchListener(this)
        txvMsg.setText("請畫出正方形")
        btnBack.isEnabled = false
    }

    override fun onClick(v: View) {
        if (v.id.equals(R.id.btnBack)) {
            finish()
        } else {
            handv.path.reset()
            handv.invalidate()
        }
    }


    override fun onTouch(p0: View?, event: MotionEvent): Boolean {
        var xPos = event.getX()
        var yPos = event.getY()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> handv.path.moveTo(xPos, yPos)
            MotionEvent.ACTION_MOVE -> handv.path.lineTo(xPos, yPos)
            MotionEvent.ACTION_UP -> {
                //將handv轉成Bitmap
                val b = Bitmap.createBitmap(
                    handv.measuredWidth, handv.measuredHeight,
                    Bitmap.Config.ARGB_8888
                )
                val c = Canvas(b)
                handv.draw(c)
                classifyDrawing(b)
            }
        }

        handv.invalidate()
        return true
    }

    fun classifyDrawing(bitmap: Bitmap) {
        val model = Shapes.newInstance(this)

        // Creates inputs for reference.
        val image = TensorImage.fromBitmap(bitmap)

        // Runs model inference and gets result.
        /*
        val outputs = model.process(image)
        val probability = outputs.probabilityAsCategoryList
         */
        val outputs = model.process(image)
            .probabilityAsCategoryList.apply {
                sortByDescending { it.score } // 排序，高匹配率優先
            }.take(1)  //取最高的1個
        var Result: String = ""
        when (outputs[0].label) {
            "circle" -> Result = "圓形"
            "square" -> Result = "方形"
            "star" -> Result = "星形"
            "triangle" -> Result = "三角形"
        }
        if (Result != "方形") {
            Toast.makeText(this, "您畫的是" + Result + "請再試試看喔!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "恭喜順利通關!", Toast.LENGTH_SHORT).show()
            btnBack.isEnabled = true
        }
    }
}