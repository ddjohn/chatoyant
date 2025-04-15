package com.avelon.chatoyant.ui.mapbox

import android.Manifest
import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.avelon.chatoyant.databinding.FragmentMapboxBinding
import com.avelon.chatoyant.logging.DLog
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.style.standard.LightPresetValue
import com.mapbox.maps.extension.compose.style.standard.MapboxStandardStyle
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport

class MapboxFragment : Fragment() {
    companion object {
        private val TAG = DLog.forTag(MapboxFragment::class.java)
    }

    private var _binding: FragmentMapboxBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapboxBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // DAJO
        val mapboxView = binding.composeView
        mapboxView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MapboxMap(
                    modifier = Modifier.fillMaxSize(),
                    mapViewportState =
                        rememberMapViewportState {
                            setCameraOptions {
                                zoom(16.0)
                                center(Point.fromLngLat(11.997013996301572, 57.68784852211992))
                                pitch(60.0)
                                bearing(120.0)
                            }
                        /*flyTo(cameraOptions =  cameraOptions {
                      zoom(16.0)
                      center(Point.fromLngLat(10.997013996301572, 57.68784852211992))
                      //center(Point.fromLngLat(-98.0, 39.5))
                      pitch(60.0)
                      bearing(0.0)
                      mapAnimationOptions { duration(12_000) }
                  }) {
                      isFinished -> Log.e("TAG", "finished");
                  }*/
                        },
                    style = {
                        // MapboxStandardSatelliteStyle()
                        MapboxStandardStyle {
                            lightPreset = LightPresetValue.DUSK
                            lightPreset = LightPresetValue.NIGHT
                        }
                    },
                ) {
                    // Get reference to the raw MapView using MapEffect
                    MapEffect(Unit) { mapView ->

                        mapView.mapboxMap.loadStyle(Style.STANDARD)
                        mapView.mapboxMap.loadStyle(Style.STANDARD_SATELLITE)
                        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS)

                        mapView.debugOptions =
                            setOf(
                                // MapViewDebugOptions.TILE_BORDERS,
                                // MapViewDebugOptions.PARSE_STATUS,
                                // MapViewDebugOptions.TIMESTAMPS,
                                // MapViewDebugOptions.COLLISION,
                                // MapViewDebugOptions.STENCIL_CLIP,
                                // MapViewDebugOptions.DEPTH_BUFFER,
                                // MapViewDebugOptions.MODEL_BOUNDS,
                                // MapViewDebugOptions.TERRAIN_WIREFRAME,
                                // MapViewDebugOptions.CAMERA,
                            )

                        with(mapView) {
                            location.locationPuck = createDefault2DPuck(withBearing = true)
                            location.enabled = true
                            location.puckBearing = PuckBearing.COURSE
                            location.puckBearingEnabled = true
                            location.pulsingEnabled = true
                            viewport.transitionTo(
                                targetState = viewport.makeFollowPuckViewportState(),
                                transition = viewport.makeImmediateViewportTransition(),
                            )
                        }

                            /*
                            val annotationApi = mapView?.annotations
                            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView)
                            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                                .withPoint(Point.fromLngLat(18.06, 59.31))
                            //.withIconImage(YOUR_ICON_BITMAP)
                            pointAnnotationManager?.create(pointAnnotationOptions)
                             */
                    }
                }
            }
        }

        context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        context?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), true)
        DLog.i(TAG, "provider=$provider")
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, MapboxLocationListener())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
