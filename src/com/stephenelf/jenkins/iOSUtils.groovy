package com.stephenelf.jenkins

@Grab(module = 'commons-io', group = 'commons-io', version = '2.6')

import org.apache.commons.io.FileUtils


import java.nio.file.Path
import java.nio.file.Paths

class iOSUtils {
    static def copytarget(def String path,def String origin_target, def String new_target,def destination){

        Path org = Paths.get("${path}${origin_target}")
        Path dest = Paths.get("${destination}${new_target}")
        //Files.createDirectories(dest)
        println "copy target from ${org} to ${dest}"
        FileUtils.copyDirectoryToDirectory(new File(org.toString()),new File(dest.toString()))
       // (new File("${destination}${origin_target}")).renameTo "${destination}${new_target}"
        (new File("${destination}${new_target}/${origin_target}.xcassets")).renameTo "${destination}${new_target}/${new_target}.xcassets"
        (new File("${destination}${new_target}/${new_target}.xcassets/${origin_target}-AppIcon.appiconset")).renameTo "${destination}${new_target}/${new_target}.xcassets/${new_target}-AppIcon.appiconset"
        (new File("${destination}${new_target}/${origin_target}-Info.plist")).renameTo "${destination}${new_target}/${new_target}-Info.plist"
    }


    static def updateSMConstants(File source,String races_group,String primary, String accent){
        String test=" races_group = \"activo-sports\" races_group = \"activo-sports\""
        System.out.println("matches="+test.matches(" races_group = \"(.*?)\""))
        changeTextInFile(source,"races_group = \"(.*?)\"","races_group = \"${races_group}\"")
        changeTextInFile(source,"primary_color = \"(.*?)\"","races_group = \"${primary}\"")
        changeTextInFile(source,"accent_color = \"(.*?)\"","races_group = \"${accent}\"")
        changeTextInFile(source,"form_button_color = \"(.*?)\"","races_group = \"${primary}\"")
    }

    def changeTextInFile(File source,String toSearch,String replacement){
        source.write(source.text.replaceAll(toSearch, replacement))
    }
}
