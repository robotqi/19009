import java.io.*;
import java.sql.*;

/**
 @author Chelsea Dorich (Email: <a href="mailto:"robotqi@gmail.com>robotqi@gmail.com</a>)
 @version 1.1 04/17/2014
 @assignment.number A190-10
 @prgm.usage Called from the operating system
 @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th Edition"
 @see "<a href='http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>

 */
public class A19010
{
    public static void main(String[] args)throws Exception
    {
        DBUpdt db = new DBUpdt();
        INET net = new INET();
        boolean blnFileExists = net.fileExists("C:\\Users\\Chelsea\\A19010\\Data\\Worlds.txt");
        String strWmdb = "jdbc:derby:Weather;create=true";
       getFile("C:\\Users\\Chelsea\\A19010\\Data\\Worlds.txt","http://weather.noaa.gov/data/nsd_bbsss.txt",blnFileExists);
        Connection dbConn = DriverManager.getConnection(strWmdb);
        DriverManager.getConnection(strWmdb);
        Statement dbCmdText =  dbConn.createStatement();
//execute command
        dbCmdText.execute("DROP TABLE stations");
        try
        {
            dbCmdText.execute("CREATE TABLE stations (" +
                    "stationid CHAR(25), city CHAR(100),state CHAR(100), latitude CHAR(25), longitude CHAR(25), "
                    + "windsaloft CHAR(100),temperature CHAR(15), humidity CHAR(20),windspeed CHAR(100), " +
                    "winddirection CHAR(100), elevation CHAR(15), pressure CHAR(20), dewpoint CHAR(100) " +
                    ")");
           dbCmdText.close();
           db.openConnection("Weather");
            System.out.println("Table created");
        } catch (SQLException ex)
        {
            db.status("fail");
        }
        BufferedReader inputFile;
        inputFile = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Chelsea\\A19010\\Data\\Worlds.txt")));
           while (inputFile.ready())
           {
               String strRecord = inputFile.readLine();

            if(strRecord.substring(7,8).equals("K"))
            {
            String strStaField = "";
            int intField = 0;
              //  db.openConnection("weather");
            String[] strFieldsAry = strRecord.split(";");
            try
            {
                strStaField= strFieldsAry[intField];
                String strStaID = strFieldsAry[2];
                String strCity = strFieldsAry[3];
                String strState = strFieldsAry[4];
                String strLatitude = strFieldsAry[8];
                String strLongitude = strFieldsAry[7];
                String strElevation = strFieldsAry[11];
                    db.addRecord("stations","stationid", strStaID);
                db.setField("stations","stationid",strStaID,"city", net.properCase(strCity));
                db.setField("stations","stationid",strStaID,"state", strState);
                db.setField("stations","stationid",strStaID,"latitude", strLatitude);
                db.setField("stations","stationid",strStaID,"longitude", strLongitude);
                db.setField("stations","stationid",strStaID,"elevation", strElevation);
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                strStaField = "";
            }
            }

               }
        dbCmdText.close();
               db.openConnection("Weather");
        PrintWriter outputFile = new PrintWriter("data\\FBOUT.txt");
            db.query("SELECT * FROM stations");
               while(db.moreRecords())
               {
                   String strID  = db.getField("stationid");
                   String strCity  = db.getField("city");
                   String strState  = db.getField("state");
                   String strLatitude  = db.getField("latitude");
                   String strLongitude  = db.getField("longitude");
                   String strElevation  = db.getField("elevation");
                  outputFile.println(strID+";\t"+strCity+";\t"+strState+";\t"+strLatitude+";\t"+strLongitude+";\t"+
                          strElevation+"\r\n");
        }


    }

    /**
     * gets file from internet and cleans contents
     * @param strFileName name of file to be created
     * @param strURL url where data is to be found
     * @param blnExtract ndicates whether to extract  or not
     */
    private static void getFile(String strFileName, String strURL, Boolean blnExtract)
    {
        String webPageContentsRaw = "";
        String webPageContentsCleaned = "";
        String webPageURL = strURL;
        INET net = new INET();

        if(!blnExtract)
        {
            System.out.println(strFileName);
            try {
                webPageContentsRaw = net.getURLRaw(webPageURL);
            } catch (Exception e) {
                e.printStackTrace();
            }

            webPageContentsCleaned = webPageContentsRaw.trim();

            if (strFileName.equals(strFileName.endsWith("data/FBIN.txt")||strFileName.endsWith("data/World.txt"))) {
                webPageContentsCleaned = net.getPREData(webPageContentsRaw);
            }

            webPageContentsCleaned = webPageContentsCleaned.trim();

            try {
                net.saveToFile(strFileName, webPageContentsCleaned);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}}
