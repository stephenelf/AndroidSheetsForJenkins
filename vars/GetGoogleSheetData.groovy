#!/usr/bin/env groovy
@Grab("com.google.apis:google-api-services-sheets:v4-rev20201130-1.31.0")
@Grab(module='google-oauth-client', group='com.google.oauth-client', version='1.31.2')
@Grab(module='google-oauth-client-appengine', group='com.google.oauth-client', version='1.31.2')
@Grab(module='google-oauth-client-java6', group='com.google.oauth-client', version='1.31.2')
@Grab(module='google-oauth-client-jetty', group='com.google.oauth-client', version='1.31.2')
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange

def call(def spreadsheetId,def credentialsFile, def range, def root_path,def port, def user){
    def JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    def HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    def APPLICATION_NAME = "Google Sheets  API Java Quickstart";
    echo ("sheet id=(${spreadsheetId}, range=(${range})")

    Credential credential=getCredentials(HTTP_TRANSPORT,credentialsFile,root_path,port,user)

    echo ("got credentials, building sheet...")
    Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY,credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    echo ("Sheets service created.")
    ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
    echo ("Got response")
    List<List<Object>> values = response.getValues();
    if (values == null || values.isEmpty()) {
        System.out.println("No data found.");
        echo "No data found."
    } else {
//        System.out.println("Name, Major");
        for (List row : values) {
            for (int i=0;i<row.size();i++)
                System.out.print(""+row.get(i)+",")
            System.out.println("\n")
        }
    }
    return values
}

def getCredentials(def HTTP_TRANSPORT, def credentialsFile, def root_path, def port, def user){
    // def CREDENTIALS_FILE_PATH = "credentials.json";
    def JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    def TOKENS_DIRECTORY_PATH = root_path+"/tokens/";
    def List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    // Load client secrets.
    //InputStream ins = GetGoogleSheetData.class.getResourceAsStream(credentialsFile);
    echo "getting credentials using path=(${root_path}), file (${credentialsFile}), port (${port}), user (${user})"
    echo "credential tokens path=(${TOKENS_DIRECTORY_PATH})"
    InputStream ins =new FileInputStream(new File(root_path+"/"+credentialsFile))
    if (ins == null) {
        echo "file not found"
        throw new FileNotFoundException("Resource not found: " + credentialsFile);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(ins));
    echo "GoogleClientSecrets created."
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setApprovalPrompt("force")
            .setAccessType("offline")
            .build();
    echo "GoogleAuthorizationCodeFlow created."
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(port as int).build();
    echo "LocalServerReceiver created."
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize(user);
}
