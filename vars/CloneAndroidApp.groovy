#!/usr/bin/env groovy
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// Get the out variable
def out = getBinding().out;

def call(def root, def name,def app_name,def predominant, def primary, def accent,def brand){
    Path path= Paths.get(root+"/"+name+"/res")
    out.println("Using path="+path.toString())
    Files.createDirectories(path)
    Path path2=Paths.get(path.toString()+"/values")
    Files.createDirectories(path2)
    createStringsXML(path2.toString(),app_name)
    createColorsXML(path2.toString(),predominant,primary,accent,brand)

    path2=Paths.get(path.toString()+"/values-es")
    out.println("Using path="+path2.toString())
    Files.createDirectories(path2)
    createStringsXML(path2.toString(),app_name)
    createColorsXML(path2.toString(),predominant,primary,accent,brand)

    path2=Paths.get(path.toString()+"/values-ca")
    out.println("Using path="+path2.toString())
    Files.createDirectories(path2)
    createStringsXML(path2.toString(),app_name)
    createColorsXML(path2.toString(),predominant,primary,accent,brand)

    path2=Paths.get(path.toString()+"/values-fr")
    out.println("Using path="+path2.toString())
    Files.createDirectories(path2)
    createStringsXML(path2.toString(),app_name)
    createColorsXML(path2.toString(),predominant,primary,accent,brand)


    path2=Paths.get(path.toString()+"/values-ru-rRU")
    out.println("Using path="+path2.toString())
    Files.createDirectories(path2)
    createStringsXML(path2.toString(),app_name)
    createColorsXML(path2.toString(),predominant,primary,accent,brand)
}



def createStringsXML(def path, def app_name){
    out.println("Creating strings.xml")
    def STRINGS_XML="""<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">${app_name}</string>
</resources>
"""
    new File(path+"/strings.xml").text = STRINGS_XML
}

def createColorsXML(def path,def predominant, def primary, def accent,def brand){
    out.println("Creating colors.xml")
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