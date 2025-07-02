package com.avelon.chatoyant.packages

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.crosscutting.DLog
import com.avelon.chatoyant.packages.ui.theme.ChatoyantTheme

class PackagesFragment :  Fragment() {
    companion object {
        internal val TAG = DLog.Companion.forTag(PackagesFragment::class.java)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        DLog.d(TAG, "onCreate(): $savedInstanceState")
        super.onCreate(savedInstanceState)

        val itemArray = ArrayList<PackageItemData>()
        val packages = context?.packageManager!!.getInstalledPackages(PackageManager.MATCH_ALL)
        for (pkg in packages) {
            val appInfo = pkg.applicationInfo
            val appName = context?.packageManager?.getApplicationLabel(appInfo!!)

            if(context?.packageManager?.getLaunchIntentForPackage(pkg.packageName) != null) {
                itemArray.add(
                    PackageItemData(
                        pkg.packageName,
                        pkg.applicationInfo?.loadIcon(context?.packageManager),
                        appName.toString(),
                    ),
                )
            }
        }

        return ComposeView(requireContext()).apply {
            setContent {
                ChatoyantTheme {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ScrollableTabRow(selectedTabIndex = 0) {
                            Tab(selected = true, onClick = {}, text = { Text(text = "Bye") })
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 128.dp)
                        ) {
                            items(itemArray) { item ->
                                Card(
                                    modifier = Modifier.size(128.dp, 128.dp).padding(8.dp),
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_MAIN)
                                        intent.addCategory(Intent.CATEGORY_LAUNCHER)
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                                        intent.setPackage(item.pkg)
                                        context?.startActivity(context?.packageManager?.getLaunchIntentForPackage(item.pkg))                                },
                                    content = {
                                        Text(text = item.text)
                                        Image(
                                            modifier = Modifier.size(64.dp, 64.dp),
                                            bitmap = item.icon?.toBitmap()?.asImageBitmap()!!,
                                            contentDescription = "",
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    ChatoyantTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            ScrollableTabRow(selectedTabIndex = 0) {
                Text(text = "Hello")
                Greeting("habba")

                Tab(selected = true, onClick = {}, text = {Text(text = "Bye")})
            }

            LazyColumn {
                item {
                    Spacer(Modifier.height(16.dp))
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "Highly Rated",
                        modifier = Modifier.padding(start = 16.dp),
                    )
                    Spacer(Modifier.height(10.dp))
                }
                items(100) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(horizontal = 16.dp),
                    ) {
                        for (item in 0..100) {
                            Card(
                                content = {Text(text= "$item") }
                            )
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                }
            }
        }
    }
}
