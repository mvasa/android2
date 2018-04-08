package com.vogella.android.colorfinder
import android.support.v7.app.AppCompatActivity
import android.graphics.Color
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem
import android.util.Log
import android.view.View
import android.widget.*
import java.io.File
import java.util.*

/*import com.vogella.android.colorfinder.R.id.*
import com.vogella.android.colorfinder.R.string.red
import kotlinx.android.synthetic.main.activity_main.view.*
import android.app.AlertDialog
import android.os.Environment
import android.support.v7.app.ActionBar
import android.app.Activity*/

class MainActivity : AppCompatActivity()  {

    var red = 0
    var green = 0
    var blue = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.activity_intent)
        setSupportActionBar(taco)
        supportActionBar!!.setIcon(R.drawable.pacman)
        setSavedColors()

        fun updateColor(r:Int, g:Int, b:Int){
            colorBox.setBackgroundColor(Color.rgb(r, g, b))
        }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                color1.text = "$progress"
                red = progress
                updateColor(red, green, blue)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                color1.text = "${seekBar.progress}"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                color1.text = "${seekBar.progress}"
            }
        })

        seekBar2.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                color2.text = "$progress"
                green = progress
                updateColor(red, green, blue)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                color2.text = "${seekBar2.progress}"
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                color2.text = "${seekBar2.progress}"
            }
        })
        seekBar3.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
            color3.text = "$progress"
                blue = progress
                updateColor(red, green, blue)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                color3.text = "${seekBar3.progress}"
             }

            override fun onStopTrackingTouch(p0: SeekBar?)
            {
                color3.text = "${seekBar3.progress}"
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    var call = 0
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.saveButton ->{
            saveColor()
            true
        }
        R.id.selectButton->{
            selectColor()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
        }

    private var rColor = 0
    private var gColor = 0
    private var bColor = 0
    val colors = arrayOf("","","","","")
    var inc = -1

    fun saveColor(){
        rColor = red
        gColor = green
        bColor = blue

        inc +=  1
        if(inc == colors.size){
            inc = 0
        }
        colors[inc] = (rColor.toString() +"_"+ gColor.toString() +"_"+ bColor.toString())

        writeColorsToFile()
    }

    //read from file
    private fun setSavedColors()
    {
        val spinner = colorPicker as Spinner
        val f2 = File(filesDir,"Colors.txt")
        val s = Scanner(f2)
        var i = 0
        while(s.hasNext())
        {
            colors[i] = s.next()
            i++
        }
        spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, colors)
    }

    //write to file
    private fun writeColorsToFile(){
        val f = File(filesDir,"Colors.txt")
        val pr = f.printWriter()
        for(i in colors) {
            pr.println(i)
        }
        pr.close()
    }

    //don't need this!!!
    //used for testing readinf from file
    //delete when you're completely sure
    private fun readColorsFromFile(){
        val f2 = File(filesDir,"Colors.txt")
        val s = Scanner(f2)
        while(s.hasNext())
        {
            Log.i("save color", s.next())

        }
    }

    var co1 = ""
    var co2 = ""
    var co3 = ""
    fun getRGB(color: String){
        co1 = ""
        co2 = ""
        co3 = ""
        var increment = 1

        for(letter in color)
        {
            if(letter == '_')
            {
                increment += 1
            }
            else
            {
                if (increment == 1) {
                    co1 += letter
                } else if (increment == 2) {
                    co2 += letter
                } else if (increment == 3) {
                    co3 += letter
                }
            }
        }

    }
    private fun selectColor(){

        val spinner = colorPicker as Spinner
        spinner.visibility = View.VISIBLE
        spinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, colors)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                call++
                if(call%2 == 0){
                var currentColor: String = spinner.selectedItem.toString()
                getRGB(currentColor)
                color1.text = co1
                color2.text = co2
                color3.text = co3

                Log.i("spinner", "text set")

                var redCo = co1.toInt()
                var greenCo = co2.toInt()
                var blueCo = co3.toInt()

                colorBox.setBackgroundColor(Color.rgb(redCo, greenCo, blueCo))

                seekBar.progress = redCo
                seekBar2.progress = greenCo
                seekBar3.progress = blueCo


                    spinner.visibility = View.INVISIBLE
                }
                else{
                    Log.i("spinner","No action taken on this call")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

}
