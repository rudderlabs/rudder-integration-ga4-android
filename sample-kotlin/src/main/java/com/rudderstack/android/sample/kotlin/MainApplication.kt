package com.rudderstack.android.sample.kotlin

import android.app.Application
import com.rudderstack.android.integration.ga4.GA4IntegrationFactory
import com.rudderstack.android.sdk.core.RudderClient
import com.rudderstack.android.sdk.core.RudderConfig
import com.rudderstack.android.sdk.core.RudderLogger

class MainApplication : Application() {
    companion object {
        lateinit var rudderClient: RudderClient
    }

    override fun onCreate() {
        super.onCreate()
        rudderClient = RudderClient.getInstance(
            this,
            "<WRITE_KEY>",
            RudderConfig.Builder()
                .withDataPlaneUrl("<DATA_PLANE_URL>")
                .withControlPlaneUrl("https://1327-103-77-46-123.ngrok.io")
                .withRecordScreenViews(false)
                .withTrackLifecycleEvents(false)
                .withLogLevel(RudderLogger.RudderLogLevel.VERBOSE)
                .withFactory(GA4IntegrationFactory.FACTORY)
                .build()
        )
    }
}
