package com.example.abl.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.abl.R
import com.example.abl.databinding.FragmentDashboardBinding
import com.example.abl.databinding.FragmentForgotPasswordBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.util.ArrayList

class DashboardFragment : Fragment() {


    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        init()

        setAnim()
        val labels = monthList()
        initChart(binding, labels)

        return binding.root
    }

    private fun init(){

        binding = FragmentDashboardBinding.inflate(layoutInflater)
        binding.barchart.description.isEnabled = false
        binding.barchart.setMaxVisibleValueCount(60)
    }

    private fun initChart(binding: FragmentDashboardBinding, months: List<String>?) {
        binding.barchart.description.isEnabled = false
        binding.barchart.setMaxVisibleValueCount(60)
        val monthNames = ArrayList<String>()
        for (i in months!!.indices) {
            monthNames.add(months[i])
        }
        // scaling can now only be done on x- and y-axis separately
        binding.barchart.setPinchZoom(false)
        binding.barchart.setDrawBarShadow(false)
        binding.barchart.setDrawGridBackground(false)
        val xAxis: XAxis = binding.barchart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        xAxis.setDrawGridLines(false)
        binding.barchart.xAxis.labelCount = monthNames.size
        binding.barchart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return monthNames[value.toInt()]
            }
        }
        binding.barchart.axisLeft.setDrawGridLines(false)
        months.let { setDataBarChart(it) }
        // add a nice and smooth animation
        binding.barchart.animateY(1500)
    }


    private fun setDataBarChart(months: List<String>) {
        val values = ArrayList<BarEntry>()
        var salesList = ArrayList<Float>()
        var achievementList = ArrayList<Float>()
        val colors = ArrayList<Int>()
        val xAxisLabel = ArrayList<String>()
        for (i in months.indices) {
            xAxisLabel.add(months[i])

            colors.add(resources.getColor(R.color.colorPrimary))
            colors.add(resources.getColor(R.color.colorAccent))
            salesList = salesTarget()
            achievementList = achievement()
            values.add(BarEntry((i).toFloat(), floatArrayOf(salesList[i], achievementList[i])))
        }
        val set1: BarDataSet
        if (binding.barchart.data != null &&
            binding.barchart.data.dataSetCount > 0
        ) {
            set1 = binding.barchart.data.getDataSetByIndex(0) as BarDataSet
            set1.setDrawIcons(false)
            set1.values = values
            binding.barchart.xAxis.labelCount = xAxisLabel.size
            binding.barchart.data.notifyDataChanged()
        } else {
            binding.barchart.notifyDataSetChanged()
            binding.barchart.xAxis.valueFormatter = object :
                ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return xAxisLabel[value.toInt()]
                }
            }
            val xl: XAxis = binding.barchart.xAxis
            // xl.setAvoidFirstLastClipping(true)
            xl.isGranularityEnabled = true
            xl.position = XAxis.XAxisPosition.BOTTOM
            xl.textSize = 9f

            set1 = BarDataSet(values, "")
            set1.setDrawIcons(false)
            set1.setDrawValues(false) //hide value from bar entry
            set1.colors = colors
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val yl: YAxis = binding.barchart.axisLeft
            yl.textSize = 8f

            val data = BarData(dataSets)
            binding.barchart.data = data
            binding.barchart.setScaleEnabled(false)

        }
        removeGridLines()
        setCustomLegend()
        binding.barchart.axisLeft.axisMinimum = 0f // remove space from month label
        binding.barchart.setFitBars(true)
        binding.barchart.invalidate()

    }

    private fun salesTarget(): ArrayList<Float> {
        val salesList = ArrayList<Float>()
        salesList.add(30f)
        salesList.add(40f)
        salesList.add(30f)
        salesList.add(40f)
        salesList.add(50f)
        salesList.add(60f)
        salesList.add(70f)
        salesList.add(70f)
        salesList.add(50f)
        salesList.add(70f)
        salesList.add(30f)
        salesList.add(60f)
        return salesList
    }

    private fun achievement(): ArrayList<Float> {
        val achievementList = ArrayList<Float>()
        achievementList.add(70f)
        achievementList.add(60f)
        achievementList.add(70f)
        achievementList.add(60f)
        achievementList.add(50f)
        achievementList.add(40f)
        achievementList.add(30f)
        achievementList.add(30f)
        achievementList.add(50f)
        achievementList.add(30f)
        achievementList.add(70f)
        achievementList.add(40f)
        return achievementList
    }

    private fun removeGridLines() {
        binding.barchart.setTouchEnabled(true)
        binding.barchart.isClickable = false
        binding.barchart.isDoubleTapToZoomEnabled = false

        binding.barchart.setDrawBorders(false)
        binding.barchart.setDrawGridBackground(false)

        binding.barchart.description.isEnabled = false
        binding.barchart.legend.isEnabled = true //bottom label

        binding.barchart.axisLeft.setDrawGridLines(false)
        binding.barchart.axisLeft.setDrawLabels(true)
        binding.barchart.axisLeft.setDrawAxisLine(false)

        binding.barchart.xAxis.setDrawGridLines(false)
        binding.barchart.xAxis.setDrawLabels(true)
        binding.barchart.xAxis.setDrawAxisLine(false)

        binding.barchart.axisRight.setDrawGridLines(false)
        binding.barchart.axisRight.setDrawLabels(false)
        binding.barchart.axisRight.setDrawAxisLine(false)
    }

    private fun setCustomLegend() {
        val l: Legend = binding.barchart.legend
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        binding.barchart.extraBottomOffset = 9f  // set margin from top
        l.isWordWrapEnabled = true
        l.textSize = 10f
        val l1 = LegendEntry(
            resources.getString(R.string.sales_target), Legend.LegendForm.SQUARE, 10f,
            2f, null, resources.getColor(R.color.colorAccent)
        )
        val l2 = LegendEntry(
            resources.getString(R.string.achievement), Legend.LegendForm.SQUARE, 10f,
            2f, null, resources.getColor(R.color.colorPrimary)
        )
        l.setCustom(arrayOf(l1, l2))
        l.isEnabled = true
    }

    private fun monthList(): ArrayList<String> {
        val months = ArrayList<String>()
        months.add("Jan")
        months.add("Feb")
        months.add("Mar")
        months.add("Apr")
        months.add("May")
        months.add("June")
        months.add("July")
        months.add("Aug")
        months.add("Sep")
        months.add("Oct")
        months.add("Nov")
        months.add("Dec")
        return months
    }

    private fun setAnim() {
        binding.llTargetVsAchievement.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.righttoleft
            )
        )

        binding.llTodaysCounter.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.righttoleft
            )
        )
    }
}