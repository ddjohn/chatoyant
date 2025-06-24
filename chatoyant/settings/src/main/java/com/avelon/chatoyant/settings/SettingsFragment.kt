package com.avelon.chatoyant.settings

import android.app.Activity.RESULT_OK
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.settings.databinding.FragmentSettingsBinding

class SettingsFragment :
    Fragment(),
    OnItemClickListener {
    companion object {
        private val TAG = DLog.forTag(SettingsFragment::class.java)
    }

    private var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreateView()")

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // DAJO
        val listView = binding.listView
        val rowDataArray = ArrayList<SettingRowData>()

        val intent = Intent()
        intent.setAction("com.avelon.intent.action.SETTING")
        intent.addFlags(Intent.FLAG_DEBUG_LOG_RESOLUTION)

        val activities = context?.packageManager?.queryIntentActivities(intent, PackageManager.MATCH_ALL)
        activities?.forEach {
            val activity = it

            DLog.i(TAG, "Found activity: $activity")

            val info =
                context?.packageManager?.getActivityInfo(
                    ComponentName(activity.activityInfo.packageName, activity.activityInfo.name),
                    PackageManager.GET_META_DATA,
                )

            val rowData =
                SettingRowData(
                    info!!.packageName,
                    info.name,
                    info.metaData["com.avelon.meta.setting.name"].toString(),
                )
            DLog.i(TAG, "rowData=$rowData")
            rowDataArray.add(rowData)
        }

        DLog.i(TAG, "rowAdapter=$rowDataArray")
        DLog.i(TAG, "rowAdapter=${rowDataArray.size}")

        listView.setAdapter(SettingRowAdapter(context, rowDataArray))
        listView.onItemClickListener = this

        return root
    }

    override fun onDestroyView() {
        DLog.d(TAG, "onDestroyView()")
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        DLog.d(TAG, "onItemClick()")
        val rowData = parent?.getItemAtPosition(position) as SettingRowData

        val intent = Intent()
        intent.setComponent(ComponentName(rowData.pkg, rowData.name))

        DLog.i(TAG, "Start activity - $intent")
        // startActivity(intent);
        startActivityForResult(intent, 666)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        DLog.d(TAG, "onActivityResult(): $requestCode, $resultCode, data")
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            Toast
                .makeText(
                    context,
                    "Setting saved! - from ${data?.getStringExtra("name")}",
                    Toast.LENGTH_LONG,
                ).show()
        } else {
            Toast.makeText(context, "Failed to launch debug activity", Toast.LENGTH_LONG).show()
        }
    }
}
