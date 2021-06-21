package com.makentoshe.androidgithubcitemplate

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.androidplot.xy.*



import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*

class StatsActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        //кнопка перехода на главную страницу

        val backBtn = findViewById<Button>(R.id.BackBtn2)
        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //тестовые массивы

        val time : Array<Int> = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14)
        val h : Array<Int> = arrayOf(20, 22, 24, 23, 26, 27, 25, 24, 25, 26)
        val p: Array<Int> = arrayOf(4, 5, 6, 8, 10, 9, 11, 8, 7, 9, 10, 8, 8)


        // функция отрисовки нрафиков
        fun DrawGraphics(timeArray: Array<Int>, herbivoreArray: Array<Int>, predatorArray: Array<Int>) {
            //first plot
            val domainLabelsHerbivore = timeArray
            val series1NumberHerbivore = herbivoreArray

            val series1: XYSeries = SimpleXYSeries(Arrays.asList(* series1NumberHerbivore), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 1")
            val series1Format = LineAndPointFormatter(Color.BLUE, Color.RED, null, null)
            val plot1 = findViewById<XYPlot>(R.id.plot)
            series1Format.setInterpolationParams(CatmullRomInterpolator.Params(15, CatmullRomInterpolator.Type.Centripetal))

            plot1.addSeries(series1, series1Format)
            plot1.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
                override fun format(obj: Any?, toAppendTo: StringBuffer, pos: FieldPosition?): StringBuffer {
                    val i = Math.round((obj as Number).toFloat())
                    return toAppendTo.append(domainLabelsHerbivore[i])
                }

                override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                    return null
                }
            }
            PanZoom.attach(plot1)

            //second plot

            val domainLabelsPredator = timeArray
            val series1NumberPredator = predatorArray

            val series2: XYSeries = SimpleXYSeries(Arrays.asList(* series1NumberPredator), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series 1")
            val series2Format = LineAndPointFormatter(Color.BLUE, Color.RED, null, null)
            val plot2 = findViewById<XYPlot>(R.id.plot2)

            series2Format.setInterpolationParams(CatmullRomInterpolator.Params(20, CatmullRomInterpolator.Type.Centripetal))


            plot2.addSeries(series2, series2Format)
            plot2.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
                override fun format(
                    obj: Any?,
                    toAppendTo: StringBuffer,
                    pos: FieldPosition?
                ): StringBuffer {
                    val i = Math.round((obj as Number).toFloat())
                    return toAppendTo.append(domainLabelsPredator[i])
                }

                override fun parseObject(source: String?, pos: ParsePosition?): Any? {
                    return null
                }
            }
            PanZoom.attach(plot2)
        }
        //Quantity of minions

        fun Quantity(herbivoreArray: Array<Int>, predatorArray: Array<Int>){
            var qntOfHerbivores : Int = herbivoreArray.last()
            var qntOfPredators : Int = predatorArray.last()

            var herbivores = findViewById<TextView>(R.id.textView2)
            herbivores.setText("Quantity of herbivores: $qntOfHerbivores")
            var predators = findViewById<TextView>(R.id.textView3)
            predators.setText("Quantity of predators: $qntOfPredators")
        }

        DrawGraphics(time, h, p)
        Quantity(h, p)
    }
}