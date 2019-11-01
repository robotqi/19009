/**
 @author Chelsea Dorich (Email: <a href="mailto:"robotqi@gmail.com>robotqi@gmail.com</a>)
 @version 1.1 04/17/2014
 @assignment.number A190-10
 @prgm.usage Called from the operating system
 @see "Gaddis, 2013, Starting out with Java, From Control Structures, 5th Edition"
 @see "<a href='http://docs.oracle.com/javase/7/docs/technotes/guides/javadoc/index.html'>JavaDoc Documentation</a>

 */
public interface DBTemplate
{
    public boolean addRecord(java.lang.String strTable,
                             java.lang.String strKeyName,
                             java.lang.String strKeyContents);
    public boolean deleteAll(java.lang.String strTable);

    public void close();
    public  String getField(java.lang.String strFieldName);
    public Boolean moreRecords();
    public void openConnection(java.lang.String strDataSourceName);
    public void query(java.lang.String strSQL);
    public Boolean setField(java.lang.String strTable,
                            java.lang.String strKeyName,
                            java.lang.String strKeyContents,
                            java.lang.String strFieldName,
                            java.lang.String strFieldContents);
    public void status(java.lang.String strVar);

}
