package com.stephenelf.jenkins

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Utils {


   static def cloneAndroidApp(def root, def name,def app_name,def predominant, def primary, def accent,def brand){
        Path path= Paths.get(root+"/"+name+"/res")
        echo "Using path="+path.toString()
        FileTreeBuilder treeBuilder = new FileTreeBuilder(new File(root))
        treeBuilder.dir(path.toString())

        Files.createDirectories(path)
        Path path2=Paths.get(path.toString()+"/values")
        Files.createDirectories(path2)
        createStringsXML(path2.toString(),app_name)
        createColorsXML(path2.toString(),predominant,primary,accent,brand)

        path2=Paths.get(path.toString()+"/values-es")
        echo "Using path="+path2.toString()
        Files.createDirectories(path2)
        createStringsXML(path2.toString(),app_name)
        createColorsXML(path2.toString(),predominant,primary,accent,brand)

        path2=Paths.get(path.toString()+"/values-ca")
        echo "Using path="+path2.toString()
        Files.createDirectories(path2)
        createStringsXML(path2.toString(),app_name)
        createColorsXML(path2.toString(),predominant,primary,accent,brand)

        path2=Paths.get(path.toString()+"/values-fr")
        echo "Using path="+path2.toString()
        Files.createDirectories(path2)
        createStringsXML(path2.toString(),app_name)
        createColorsXML(path2.toString(),predominant,primary,accent,brand)


        path2=Paths.get(path.toString()+"/values-ru-rRU")
        echo "Using path="+path2.toString()
        Files.createDirectories(path2)
        createStringsXML(path2.toString(),app_name)
        createColorsXML(path2.toString(),predominant,primary,accent,brand)
    }



    def createStringsXML(def path, def app_name){
        echo "Creating strings.xml"
        def STRINGS_XML="""<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">${app_name}</string>
</resources>
"""
        new File(path+"/strings.xml").text = STRINGS_XML
    }

    def createColorsXML(def path,def predominant, def primary, def accent,def brand){
        echo "Creating colors.xml"
        def COLORS_XML="""<?xml version="1.0" encoding="utf-8"?> 
    <resources> 
      <color name="predominant">#CC${predominant}</color> 
      <color name="colorPrimary">${primary}</color> 
      <color name="colorAccent">${accent}</color> 
      <color name="brand_bg">${brand}</color> 
    </resources> 
"""
        new File(path+"/colors.xml").text = COLORS_XML
    }
}
