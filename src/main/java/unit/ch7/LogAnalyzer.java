package unit.ch7;

public class LogAnalyzer {
    public void analyzer(String fileName) {
        if(fileName.length() < 8) {
            LoggingFacility.log("File too short: " + fileName);
        }
    }
}
