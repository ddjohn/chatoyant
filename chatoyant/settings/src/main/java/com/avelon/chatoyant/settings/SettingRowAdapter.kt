package com.avelon.chatoyant.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.settings.databinding.RowSettingsBinding

class SettingRowAdapter(
    ctx: Context?,
    val data: ArrayList<SettingRowData>,
) : BaseAdapter() {
    companion object {
        val TAG = DLog.forTag(SettingRowAdapter::class.java)
    }

    private var _binding: RowSettingsBinding? = null
    val binding get() = _binding!!

    val inflater: LayoutInflater = ctx!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        DLog.d(TAG, "getCount()")
        return data.size
    }

    override fun getItem(position: Int): SettingRowData? {
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

        _binding = RowSettingsBinding.inflate(inflater)

        binding.pkg.text = data[position].pkg
        binding.name.text = data[position].name
        binding.text.text = data[position].text

        return binding.root
    }
}
