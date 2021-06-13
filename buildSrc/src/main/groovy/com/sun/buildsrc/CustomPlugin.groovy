package com.sun.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project;

class CustomPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        println 'This is custom plugin'
        project.task('CustomPluginTask') {
            doFirst {
                println 'This is custom plugin Task'
            }
        }

    }
}