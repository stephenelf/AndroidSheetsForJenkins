#!/usr/bin/env groovy
import com.stephenelf.jenkins.Utils
import com.stephenelf.jenkins.iOSUtils

def cloneiOSApp(def String path,def String origin_target, def String new_target,def destination,def assetsUrl, def String googleServicesUrl,
                String slug_race,String primary, String accent){
    iOSUtils.copytarget(path,origin_target,new_target,destination)
    Utils.downloadAssets(assetsUrl,"assets.zip","${destination}${new_target}/${new_target}.xcassets")
    Utils.downloadGoogleServices(googleServicesUrl,"GoogleService-Info.plist","${destination}${new_target}")
    iOSUtils.updateSMConstants(new File("${destination}${new_target}/SMConstants.swift"),slug_race,
            primary,primary)
}