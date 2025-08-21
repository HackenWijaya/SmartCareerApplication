package com.example.project.basic_listview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.project.R
import org.w3c.dom.Text

class ListAdapter (
    context: Context,
    private val menulist: List<ListModel>
) :ArrayAdapter<ListModel>(context, 0, menulist)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?:LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val menuItem = getItem(position)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textName: TextView = view.findViewById(R.id.textName)
        val textDesc: TextView = view.findViewById(R.id.textDesc)

        menuItem?.let{
            imageView.setImageResource(it.imageResId)
            textName.text = it.name
            textDesc.text = it.desc
        }

        return view
    }
}