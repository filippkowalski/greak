package com.greak.ui.screens.post

import android.content.Context

import com.greak.data.database.UserActionsPreferences
import com.greak.data.database.UserManager

/**
 * Created by Filip Kowalski on 21.05.17.
 */

class PostHtmlUtils {

    fun prepareHtmlStyling(context: Context, title: String, body: String): String {
        val userManager = UserManager(context)

        val css = UserActionsPreferences.getHtmlStyling(context)
        val fontSizeForP = "p { font-size: " + userManager.fontSize + "px; !important"
        val fontSizeForA = "a { font-size: " + userManager.fontSize + "px; !important"
        val styling = "<style type=\"text/css\">$css$fontSizeForP$fontSizeForA</style>"

        val htmlHeader = "<html><head>$styling</head><body>"
        val htmlTitle = "<h1>$title</h1>"
        val htmlFooter = "</body></html>"
        return htmlHeader + htmlTitle + body + htmlFooter
    }

    companion object {

        val DEFAULT_MIME_TYPE = "text/html"
        val DEFAULT_ENCODING = "UTF-8"
        val DEFAULT_STYLING =
                "@font-face {" +
                        "   font-family: LatoLatin;" +
                        "   src: url(\"file:///android_asset/fonts/LatoLatin-Regular.ttf\")" +
                        "}" +
                        "@font-face {" +
                        "   font-family: PlayFairBlack;" +
                        "   src: url(\"file:///android_asset/fonts/PlayfairDisplay-Black.otf\")" +
                        "}" +
                        "body {" +
                        "    margin: 20px;" +
                        "}" +
                        "h1 {" +
                        "    font-family: PlayFairBlack;" +
                        "    font-size: 24px;" +
                        "    text-align: left;" +
                        "    color: #000000" +
                        "}" +
                        "p {" +
                        "    color: rgba(0, 0, 0, 0.6);" +
                        "    font-family: LatoLatin;" +
                        "    line-height: 24px;" +
                        "    text-align: justify;" +
                        "}" +
                        "a {" +
                        "    color: #44c1a5;" +
                        "    text-decoration: none;" +
                        "}"
    }
}