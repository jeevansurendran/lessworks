package com.jeevan.data.dynamiclinks

import android.net.Uri
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.googleAnalyticsParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.dynamiclinks.ktx.socialMetaTagParameters
import com.jeevan.BuildConfig
import com.jeevan.utils.Constants
import com.jeevan.utils.Constants.SHARE_DESC
import com.jeevan.utils.Constants.SHARE_IMAGE
import com.jeevan.utils.Constants.SHARE_TITLE
import com.jeevan.utils.suspendAndWait
import javax.inject.Inject

class DynamicLinkSource
@Inject constructor(
    private val firebaseDynamicLinks: FirebaseDynamicLinks
) {
    suspend fun createShareableLink(url: Uri): Uri? {
        val shortLink = firebaseDynamicLinks.shortLinkAsync(ShortDynamicLink.Suffix.SHORT) {
            link = url
            domainUriPrefix = Constants.DYNAMIC_LINK_URI_PREFIX
            googleAnalyticsParameters {
                source = "app"
                medium = "share"
                campaign = "general"
            }
            androidParameters(BuildConfig.APPLICATION_ID) {
                minimumVersion = 1
            }
            socialMetaTagParameters {
                title = SHARE_TITLE
                description = SHARE_DESC
                imageUrl = Uri.parse(SHARE_IMAGE)
            }
        }.suspendAndWait()
        return shortLink.shortLink
    }
}