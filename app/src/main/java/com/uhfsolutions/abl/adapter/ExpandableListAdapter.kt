package com.uhfsolutions.abl.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.uhfsolutions.abl.R
import java.util.HashMap

class ExpandableListAdapter(context: Context?, listDataHeader: List<String>?,
                            listChildData: HashMap<String, List<String>>?,
                            icons: List<Int>?) : BaseExpandableListAdapter() {

    private var _context: Context? = null
    private var _listDataHeader // header titles
            : List<String>? = null

    // child data in format of header title, child title
    private var _listDataChild: HashMap<String, List<String>>? = null
    private var _icon //icon List
            : List<Int>? = null

    init {
        _context = context
        _listDataHeader = listDataHeader
        _listDataChild = listChildData
        _icon = icons
    }

    override fun getChild(groupPosition: Int, childPosititon: Int): Any? {
        return _listDataChild!![_listDataHeader!![groupPosition]]
            ?.get(childPosititon)
    }

   override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

   override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {


//        if (isLastChild)
//        {
//            convertView.setPadding(0, 0, 0, 30);
//        }
        var convertView = convertView
        val childText = getChild(groupPosition, childPosition) as String?
        if (convertView == null) {
            val infalInflater =
                _context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_item, null)
        }
        val txtListChild = convertView!!.findViewById<TextView>(R.id.lblListItem)
        txtListChild.text = childText
        return convertView
    }

   override fun getChildrenCount(groupPosition: Int): Int {
        var size = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (_listDataChild!![_listDataHeader!![groupPosition]] != null) {
                size = _listDataChild!![_listDataHeader!![groupPosition]]!!.size
            }
        }
        return size
    }

   override fun getGroup(groupPosition: Int): Any? {
        return _listDataHeader!![groupPosition]
    }

   override fun getGroupCount(): Int {
        return _listDataHeader!!.size
    }

   override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }


    @SuppressLint("ResourceAsColor")
   override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as String?
        if (convertView == null) {
            val infalInflater =
                _context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_group, null)
        }
        val iconView = convertView!!.findViewById<ImageView>(R.id.icon)
        iconView.setImageResource(_icon!![groupPosition])
        val lblListHeader = convertView.findViewById<TextView>(R.id.lblListHeader)

//        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.text = headerTitle
        val linearLayout = convertView.findViewById<LinearLayout>(R.id.main_group)
        if (isExpanded) {
            linearLayout.setBackgroundColor(
                ContextCompat.getColor(
                    _context!!,
                    R.color.zxing_transparent
                )
            )
        } else {
            linearLayout.setBackgroundColor(
                ContextCompat.getColor(
                    _context!!,
                    R.color.zxing_transparent
                )
            )
        }
        return convertView
    }

   override fun hasStableIds(): Boolean {
        return false
    }

   override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true

        //txtListChild.setText(childText);
    }

}