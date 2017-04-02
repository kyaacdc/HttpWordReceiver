package com.cliUtils.receivers.htmlPageReceiver;

public class Main{

    private Main() {}

    public static void main(String[] args) {

        ParseProcessor parseProcessor = new ParseProcessor();

        //Checking and/or input URL and content on html-page
        parseProcessor.intro(args);

        //Process with getting words content
        parseProcessor.showPageInfo();

    }
}
