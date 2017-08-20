package com.dg.watcher.validation

import hudson.model.AbstractProject
import hudson.util.FormValidation
import java.io.File
import hudson.util.FormValidation.error
import hudson.util.FormValidation.ok
import java.io.File.separator
import java.lang.Float.*


fun validateThresholdInMb(input: String): FormValidation {
    return try {
        val thresholdInMb = parseFloat(input)

        if(thresholdInMb >= 0f) {
            ok()
        }
        else {
            error("The threshold can not be negative.")
        }
    }
    catch(e: Exception) {
        error("The threshold must be a floating point number.")
    }
}

fun validateCustomPathToApk(input: String, project: AbstractProject<*, *>): FormValidation {
    return if(noPathSpecified(input) || validPathSpecified(input, project)) {
        ok()
    }
    else {
        error("The provided path does not exist.")
    }
}

private fun noPathSpecified(input: String) = input.isEmpty()

private fun validPathSpecified(input: String, project: AbstractProject<*, *>) =
        File(retrieveWorkSpaceRoot(project) + input).exists()

private fun retrieveWorkSpaceRoot(project: AbstractProject<*, *>) =
        "${project.getSomeWorkspace().toString()}$separator"