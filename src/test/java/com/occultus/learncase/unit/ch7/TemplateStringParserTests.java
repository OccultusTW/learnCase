package com.occultus.learncase.unit.ch7;

public abstract class TemplateStringParserTests {
    private final String EXPECTED_SINGLE_DIGIT = "1";
    private final String EXPECTED_WITH_REVISION = "1.1";
    private final String EXPECTED_WITH_MINOR_VERSION = "1.1.1";

    public abstract String headerVersion_SingleDigit();
    public abstract String headerVersion_WithMinorVersion();
    public abstract String headerVersion_WithRevision();

    public void TestGetStringVersionFromHeader_SingleDigit_Found() {
        String input = headerVersion_SingleDigit();
        
    }
    public abstract void TestGetStringVersionFromHeader_WithMinorVersion_Found();
    public abstract void TestGetStringVersionFromHeader_WithRevision_Found();
}
