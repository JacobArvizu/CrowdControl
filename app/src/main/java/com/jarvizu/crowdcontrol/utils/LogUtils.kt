package com.jarvizu.crowdcontrol.utils

import android.content.Intent


object IntentDumper {
    fun dumpIntent(i: Intent): String {

        val bundle = i.extras
        if (bundle != null) {
            val keys = bundle.keySet()
            val stringBuilder = StringBuilder()
            stringBuilder.append("Intent \n\r")
            stringBuilder.append("-------------------------------------------------------------\n\r")
            for (key in keys) {
                stringBuilder.append(key).append(":").append(bundle[key]).append("\n\r")
            }
            stringBuilder.append("-------------------------------------------------------------\n\r")
            return stringBuilder.toString()
        } else {
            return i.toUri(0)
        }
    }
}
