package com.stephenelf.jenkins

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.zip.ZipFile

class Utils {


   static def cloneAndroidApp(def root, def name,def app_name,def predominant, def primary, def accent,def brand,def assetsUrl){
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

       path2=Paths.get(root+"/"+name)
       downloadAssets(assetsUrl,"assets.zip",path2.toString())
    }



    static def createStringsXML(def path, def app_name){
        echo "Creating strings.xml"
        def STRINGS_XML="""<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">${app_name}</string>
</resources>
"""
        new File(path+"/strings.xml").text = STRINGS_XML
    }

    static def createColorsXML(def path,def predominant, def primary, def accent,def brand){
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

    static def unzip(String zipFileName, String outputDir){
        def zip = new ZipFile(new File(zipFileName))
        zip.entries().each{
            if (!it.isDirectory()){
                def fOut = new File(outputDir+ File.separator + it.name)
                //create output dir if not exists
                new File(fOut.parent).mkdirs()
                def fos = new FileOutputStream(fOut)
                //println "name:${it.name}, size:${it.size}"
                def buf = new byte[it.size]
                def len = zip.getInputStream(it).read(buf) //println zip.getInputStream(it).text
                fos.write(buf, 0, len)
                fos.close()
            }
        }
        zip.close()
    }

    static def downloadAssets(String url, String filename, String destination){
        redirectFollowingDownload(url, filename)
        unzip(filename,destination)
    }


    static def redirectFollowingDownload( String url, String filename ) {
        while( url ) {
            new URL( url ).openConnection().with { conn ->
                conn.instanceFollowRedirects = false
                url = conn.getHeaderField( "Location" )
                if( !url ) {
                    new File( filename ).withOutputStream { out ->
                        conn.inputStream.with { inp ->
                            out << inp
                            inp.close()
                        }
                    }
                }
            }
        }
    }

    static def downloadGoogleServices(String url, String filename, String destination) {
        System.out.println("url=${url}")
        System.out.println("Downloading google services:${destination}/${filename}")
        redirectFollowingDownload(url, "${destination}/${filename}")
    }
}
