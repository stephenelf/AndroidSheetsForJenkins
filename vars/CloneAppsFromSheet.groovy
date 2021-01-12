#!/usr/bin/env groovy
import com.stephenelf.jenkins.Utils

def call(def root, def spreadsheetId,def credentialsFile, def range){
    List<List<Object>> values = GetGoogleSheetData().call(spreadsheetId,credentialsFile,range)
    for (List row : values) {
        try {
            Utils.cloneAndroidApp(root, row.get(4), row.get(6), row.get(7), row.get(8), row.get(9), row.get(10))
        }catch(Exception e){
            System.out.println("Fail to create app sufix ("+row.get(4)+")")
        }
    }
}
