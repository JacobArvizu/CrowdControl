package com.jarvizu.crowdcontrol.app

import timber.log.Timber.DebugTree

open class MyTree : DebugTree() {
    override fun log(
            priority: Int,
            tag: String?,
            message: String,
            t: Throwable?
    ) {
        super.log(priority, "Timber", message, t)
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        // Add log statements line number to the log
        return super.createStackElementTag(element) + " - " + element.lineNumber
    }
}