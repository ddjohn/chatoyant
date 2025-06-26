package com.avelon.chatoyant.packages

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.packages.databinding.FragmentPackagesBinding

class PackagesFragment :
    Fragment(),
    AdapterView.OnItemClickListener {
    companion object {
        private val TAG = DLog.Companion.forTag(PackagesFragment::class.java)
    }

    private var binding: FragmentPackagesBinding? = null

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.Companion.d(TAG, "onCreateView()")
        binding = FragmentPackagesBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        val itemArray = ArrayList<PackageItemData>()

        val packageManager = context?.packageManager
        val packages = packageManager!!.getInstalledPackages(PackageManager.MATCH_ALL)
        for (pkg in packages) {
            DLog.i(TAG, "package=${pkg.packageName}")
            DLog.i(TAG, "package=${pkg.packageName}")
            DLog.i(TAG, "package=${pkg.applicationInfo?.packageName}")
            DLog.i(TAG, "info=${pkg.applicationInfo}")
            DLog.i(TAG, "info=${pkg.applicationInfo?.name}")
            DLog.i(TAG, "info=${pkg.applicationInfo?.metaData}")

            val appInfo = pkg.applicationInfo
            val appName = packageManager.getApplicationLabel(appInfo!!)

            itemArray.add(
                PackageItemData(
                    pkg.packageName,
                    pkg.applicationInfo?.loadIcon(context?.packageManager),
                    appName.toString(),
                ),
            )
        }

        val gridView = binding!!.gridView
        gridView.setAdapter(PackageItemAdapter(context, itemArray))
        gridView.onItemClickListener = this

        return root
    }

    override fun onDestroyView() {
        DLog.Companion.d(TAG, "onDestroyView()")
        super.onDestroyView()

        binding = null
    }

    override fun onItemClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        DLog.i(TAG, "onItemClick(): $position $id")
        val itemData = parent?.getItemAtPosition(position) as PackageItemData

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        intent.setPackage(itemData.pkg)
        // context?.startActivity(intent)
        context?.startActivity(context?.packageManager?.getLaunchIntentForPackage(itemData.pkg))
    }
}
