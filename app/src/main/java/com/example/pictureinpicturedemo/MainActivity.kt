package com.example.pictureinpicturedemo

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
val timer = Timer()
    var sec = 0
    lateinit var task: TimerTask
    lateinit var activity:Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this

        task=  object :TimerTask(){
            override fun run() {
                sec++
                activity.runOnUiThread(object :Runnable{
                    override fun run() {
                        if(sec<=10){
                            if(sec>9){
                                textView.text = "00:$sec"
                            }else{
                                textView.text = "00:0$sec"
                            }

                        }else{
//                            timer.cancel()
//                            timer.purge()
                        }
                    }

                })
            }

        }

        button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                val mPipParams = PictureInPictureParams.Builder()
                val display = windowManager.defaultDisplay
                val point = Point()
                display.getSize(point)
                mPipParams.setAspectRatio(Rational(point.x,point.y))
                enterPictureInPictureMode(mPipParams.build())
            }else{
                Toast.makeText(this,"Your Device is not supported! ask google why ", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        if(isInPictureInPictureMode){
            sec = 0
            timer.schedule(task,0,1000)
            supportActionBar?.hide()
            textView.visibility= View.VISIBLE
            button.visibility = View.GONE
        }else{
            textView.visibility= View.GONE
            button.visibility = View.VISIBLE
        }
    }
}
