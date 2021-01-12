#!/usr/bin/env groovy
import com.stephenelf.jenkins.Utils

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


def call(def root, def name,def app_name,def predominant, def primary, def accent,def brand){
    Utils.cloneAndroidApp(root,name,app_name,predominant,primary,accent,brand)
}

