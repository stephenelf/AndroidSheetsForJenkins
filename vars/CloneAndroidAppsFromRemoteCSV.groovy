#!/usr/bin/env groovy

def cloneFromRemoteCSV(def root, String urlCSV, boolean skipFirstLine) {
    def filename = root + "/csvfile_" + System.currentTimeMillis()
    System.out.println("Downloading to:${filename}")
    //  if (!urlCSV) {
    redirectFollowingDownload(urlCSV,filename+".zip")//,{unzip(filename+".zip",filename+".csv")})
    /*
    def num_waits=5
    def file=new File(filename+".csv")
    while (num_waits>0){
        if(file.exists()&&file.size()>0 ) {
            num_waits=0
            System.out.println("file exists")
            //cloneFromCSV(root, filename, skipFirstLine)
        }else{
            Thread.sleep(2000)
            num_waits--
        }
        System.out.println("num =${num_waits} exists"+file.exists()+" size="+file.size())
    }


     */
    //}
}