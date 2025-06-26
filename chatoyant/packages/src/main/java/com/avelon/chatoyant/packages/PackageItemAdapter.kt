package com.avelon.chatoyant.packages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.packages.databinding.ItemBinding

class PackageItemAdapter(
    ctx: Context?,
    val data: ArrayList<PackageItemData>,
) : BaseAdapter() {
    companion object {
        val TAG = DLog.forTag(PackageItemAdapter::class.java)
    }

    private var binding: ItemBinding? = null

    val inflater: LayoutInflater = ctx!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        DLog.d(TAG, "getCount()")
        return data.size
    }

    override fun getItem(position: Int): PackageItemData? {
        DLog.d(TAG, "getItem(): $position")
        return data.get(position)
    }

    override fun getItemId(position: Int): Long {
        DLog.d(TAG, "getItemId(): $position")
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?,
    ): View? {
        DLog.d(TAG, "getView(): $position")

        binding = ItemBinding.inflate(inflater)

        binding!!.pkg.text = data[position].text
        binding!!.drawable.setImageDrawable(data[position].icon)
        //   binding.text.text = data[position].text

        return binding!!.root
    }
}
