package com.sun.customplugin

import com.sun.customplugin.tasks.ReleaseInfoTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class SunPlugin implements Plugin<Project> {
    protected Project project
    private final String EXTENSION_NAME = "ReleaseInfoExtensions"
    protected ReleaseInfoExtionsion extionsion;

    @Override
    void apply(Project project) {
        this.project = project
        createExtension()

        project.tasks.create("sunPlugin", ReleaseInfoTask.class)

    }
    /**
     * 创建用于设置版本信息的扩展属性
     */
    private void createExtension() {
        extionsion = project.extensions.create(EXTENSION_NAME, ReleaseInfoExtionsion)
    }
}