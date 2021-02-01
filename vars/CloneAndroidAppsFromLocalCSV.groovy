#!/usr/bin/env groovy
import com.stephenelf.jenkins.Utils

def call(def root, String csvfile, boolean skipFirstLine) {

    new File(csvfile).splitEachLine(",") {
        fields ->
            if (!skipFirstLine)
                try {
                    System.out.println("ready=" + fields[0])
                    if (fields[0] != null && fields[0].toBoolean())
                        Utils.createAndroidDirectories(root, fields[4], fields[6], fields[7], fields[8], fields[9], fields[10],
                                fields[11], fields[13])
                } catch (Exception e) {
                    System.out.println("Fail to create app sufix (" + fields[11] + ")")
                    e.printStackTrace()
                }
            else skipFirstLine = false
    }

}
