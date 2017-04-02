package com.cliUtils.receivers.htmlPageReceiver;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParser {

    private String page;

    public CodeParser(String page) {
        this.page = page;
    }

    public List<String> getBodyWordsContent() {

        String result = "";

        //eject body content
        String tagForRegex = "<body(.+?)</body>";
        Pattern TAG_REGEX = Pattern.compile(tagForRegex);
        Matcher matcher = TAG_REGEX.matcher(page);
        while (matcher.find())
            result += matcher.group(1);

        //remove content <script>,	<image>, <object> tags
        result = removeScriptObjectImageTagsInContent(result);

        //eject words content between tags
        result = result.replaceAll("<[^>]*>", "");

        //remove all non-alphabetic symbols
        result =  result.replaceAll("[^\\p{IsAlphabetic}^\\p{IsDigit}]", " ");

        //remove digits and words with digits
        result =  result.replaceAll("\\w*\\d\\w*", "");

        //remove punctuations
        result =  result.replaceAll("\\p{P}", "");

        String[] split = result.replaceAll("/[A-Za-zА-Яа-я ]/", " ").trim().split("\\s+");

        return Arrays.asList(split);
    }

    public Map<String, Long> getWordsCountMap(List<String> words) {
        Map<String, Long> map = new TreeMap<>(String::compareToIgnoreCase);
        for (String s : words)
            map.put(s, words.stream().filter(a -> a.equals(s)).count());
        return map;
    }

    private String removeScriptObjectImageTagsInContent(String result){

        int startIndex = 0, stopIndex = 0;

        for(int i = 0; i < result.length(); i++){

            if (i + 7 < result.length() && result.substring(i, i + 7).equals("<script"))
                startIndex = i;
            if (i + 8 < result.length() && result.substring(i, i + 9).equals("</script>")) {
                stopIndex = i + 9;
                result = result.substring(0, startIndex) + result.substring(stopIndex, result.length());
                i = startIndex;
            }

            if (i + 6 < result.length() && result.substring(i, i + 6).equals("<image"))
                startIndex = i;
            if (i + 7 < result.length() && result.substring(i, i + 8).equals("</image>")) {
                stopIndex = i + 8;
                result = result.substring(0, startIndex) + result.substring(stopIndex, result.length());
                i = startIndex;
            }

            if (i + 7 < result.length() && result.substring(i, i + 7).equals("<object"))
                startIndex = i;
            if (i + 8 < result.length() && result.substring(i, i + 9).equals("</object>")) {
                stopIndex = i + 9;
                result = result.substring(0, startIndex) + result.substring(stopIndex, result.length());
                i = startIndex;
            }
        }

        return result;
    }

}
