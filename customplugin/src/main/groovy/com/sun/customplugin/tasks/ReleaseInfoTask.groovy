package com.sun.customplugin.tasks

import groovy.xml.MarkupBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction;


class ReleaseInfoTask extends DefaultTask {

    ReleaseInfoTask() {
        /*1：构造器中给task分组，设置描述信息*/
        group = "version_manager"
        description = "release info update"
    }
    /*2：在gradle执行阶段执行*/

    @TaskAction
    void doAction() {
        updateVersionInfo()
    }

    private void updateVersionInfo() {
        // 3、从 realeaseInfo Extension 属性中获取相应的版本信息

        def versionCodeMsg = project.extensions.ReleaseInfoExtensions.versionCode;
        def versionNameMsg = project.extensions.ReleaseInfoExtensions.versionName;
        def versionInfoMsg = project.extensions.ReleaseInfoExtensions.versionInfo;
        def fileName = project.extensions.ReleaseInfoExtensions.fileName;
        def file = project.file(fileName)
        // 4、将实体对象写入到 xml 文件中
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file.text != null && file.text.size() <= 0) {
            //没有内容
            xmlBuilder.releases {
                release {
                    versionCode(versionCodeMsg)
                    versionName(versionNameMsg)
                    versionInfo(versionInfoMsg)
                }
            }
            //直接写入
            file.withWriter { writer -> writer.append(sw.toString())
            }
        } else {
            //已有其它版本内容
            xmlBuilder.release {
                  versionCode(versionCodeMsg)
                  versionName(versionNameMsg)
                  versionInfo(versionInfoMsg)
            }

            //插入到最后一行前面
            def lines = file.readLines()
            def lengths = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { line, index ->
                    if (index != lengths) {
                        writer.append(line + '\n')
                    } else if (index == lengths) {
                        writer.append(sw.toString() + '\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }


    }


}
