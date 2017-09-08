package com.dg.watcher.validation

import com.dg.watcher.base.Project
import hudson.util.FormValidation
import hudson.util.FormValidation.error
import hudson.util.FormValidation.ok


fun validateThresholdInMb(input: String): FormValidation =
    try {
        val thresholdInMb = input.toFloat()

        if(thresholdInMb >= 0f) {
            ok()
        }
        else {
            error("The threshold cannot be negative.")
        }
    }
    catch(e: Exception) {
        error("The threshold must be a floating point number.")
    }

fun validateCustomPathToApk(input: String, project: Project): FormValidation =
    if(noPathSpecified(input) || validPathSpecified(input, project)) {
        ok()
    }
    else {
        error("The specified path does not exist.")
    }

private fun noPathSpecified(input: String) = input.isBlank()

private fun validPathSpecified(input: String, project: Project) =
        getWorkSpaceRoot(project)?.child(input)?.exists() == true

private fun getWorkSpaceRoot(project: Project) =
        project.getSomeWorkspace()