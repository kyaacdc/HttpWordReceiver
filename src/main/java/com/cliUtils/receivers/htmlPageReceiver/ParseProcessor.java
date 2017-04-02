package com.cliUtils.receivers.htmlPageReceiver;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.cliUtils.receivers.htmlPageReceiver.HttpUrlValidator.getContent;

public class ParseProcessor {

    private String page;

    public void intro(String[] args) {

        Scanner sc;

        System.out.println("Welcome to Html Page Words Receiver!");

        String url;
        if(args.length != 0)
            url = args[0];
        else {
            System.out.println("Please, input Url for receive info");
            sc = new Scanner(System.in);
            url = sc.nextLine();
        }

        while (!HttpUrlValidator.isHttpUrlValid(url)) {
            sc = new Scanner(System.in);
            url = sc.nextLine();
        }

        //Get page content for requested Url
        page = getContent();
    }

    public void showPageInfo(){

        //Get parser instance for processing html-code
        CodeParser codeParser = new CodeParser(page);

        //Get list of body words
        List<String> bodyWords = codeParser.getBodyWordsContent();
        Map<String, Long> wordsMap = codeParser.getWordsCountMap(bodyWords);
        Set<Map.Entry<String, Long>> entriesNoLib = wordsMap.entrySet();

        System.out.println("Count of all words (with repeated) in html body is - " + bodyWords.size());
        System.out.println("Count of all words (no repeated) in html body is - " + wordsMap.size());
        System.out.println("\nBelow is sorted list of words with show count (WORD - COUNT)");

        entriesNoLib.forEach(a -> System.out.println(a.getKey() + " - " + a.getValue()));
    }
}
