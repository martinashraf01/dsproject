package ds.datastructprjc;

import java.util.Stack;

public class Prettifying {

    static StringBuffer Prettifying (StringBuffer file) {

        Stack <String> stack = new Stack<>();
        StringBuffer prettyFile = new StringBuffer();
        int i =0;
        char current;
        char next;
        char prev;

        while(i < file.length()){
            current = file.charAt(i);  //current char
            if(i<file.length()-1){
                next = file.charAt(i + 1); //next char
            }else{
                next='"';
            }

            //condition for open label
            if (current == '<' && next != '/' && next !='!') {

                String openlabel = findWordAtIndex(file, i + 1); //get label
                for (int j = 0; j <= openlabel.length(); j++) {
                    i++;
                }
                current = file.charAt(i);  //current char
                if(i<file.length()-1){
                    next = file.charAt(i + 1); //next char
                }else{
                    next='"';
                }

                //pushing label onto the stack

                if(prettyFile.length() > 0 && prettyFile.charAt(prettyFile.length() - stack.size()) != '\n'){
                    prettyFile=prettyFile.append('\n');
                }

                for(int j=0;j< stack.size();j++){
                    prettyFile = prettyFile.append('\t');
                }
                prettyFile = prettyFile.append('<'+openlabel+'>');

                stack.push(openlabel + "");


                //find first char that isn't a \s \n \t then append all to string until \n
                String word = getSubstringUntilWhitespace(file, i+1);
                if(word.length()>=1){
                    prettyFile=prettyFile.append('\n');
                    for(int j=0;j< stack.size();j++){
                        prettyFile = prettyFile.append('\t');
                    }
                    prettyFile = prettyFile.append(word);
                }

            } //condition for closedlabel
            else if (current == '<' && next == '/') {
                String closedlabel = findWordAtIndex(file, i + 2); //get closing label
                for (int j = 0; j <= closedlabel.length(); j++) {
                    i++;
                }
                current = file.charAt(i);  //current char

                //pop stack
                stack.pop();
                prettyFile = prettyFile.append('\n');
                for(int j=0;j< stack.size();j++){
                    prettyFile = prettyFile.append('\t');
                }
                prettyFile = prettyFile.append("</"+closedlabel+'>');

            }
            i++;
        }
        removePattern(prettyFile);
        return prettyFile;
    }

    //-----------------------------------------------------------------------------------------------
    //function to get labels
    public static String findWordAtIndex(StringBuffer text, int startIndex) {
        int spaceIndex = text.indexOf(">", startIndex); // Find the next space from startIndex

        // If spaceIndex is -1, set it to the end of the string
        if (spaceIndex == -1) {
            spaceIndex = text.length();
        }

        // Extract the word using substring
        String word = text.substring(startIndex, spaceIndex);

        return word;
    }

    //-------------------------------------------------------------------------------------------------
    //function to remove last char (using it for tags only)in a stringbuffer
    public static void removeLastChar(StringBuffer strBuffer) {
        if (strBuffer.length() > 0) {
            strBuffer.deleteCharAt(strBuffer.length() - 1);
        }
    }

    //-------------------------------------------------------------------------------------------------
    //function to ignore all white spaces at the begining of a string
    public static String getSubstringUntilWhitespace(StringBuffer input, int startIndex) {
        int actualStartIndex = startIndex;

        // Skip whitespace characters if the starting index contains any of them
        while (actualStartIndex < input.length() &&
                (input.charAt(actualStartIndex) == ' ' ||
                        input.charAt(actualStartIndex) == '\n' ||
                        input.charAt(actualStartIndex) == '\t')) {
            actualStartIndex++;
        }

        int whitespaceIndex = input.length(); // Initialize with end of string by default

        // Find the index of the next whitespace character (\n, \t, or space)
        for (int i = actualStartIndex; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '\n' || currentChar == '\t' || currentChar == '<') {
                whitespaceIndex = i;
                break;
            }
        }

        return input.substring(actualStartIndex, whitespaceIndex);
    }

    //-------------------------------------------------------------------------------------------------
    //function that removes all extra spaces at the end of string
    public static String removeExtraSpacesAtEnd(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Return input if it's null or empty
        }

        int endIndex = input.length() - 1;

        // Find the last non-space character index from the end
        while (endIndex >= 0 && input.charAt(endIndex) == ' ') {
            endIndex--;
        }

        // Return the substring from start to the last non-space character index
        return input.substring(0, endIndex + 1);
    }
    //-----------------------------------------------------------------------------------------------------
    //fun to remove consec '\n'
    public static void removePattern(StringBuffer stringBuffer) {
        int i = 0;
        while (i < stringBuffer.length() - 2) {
            if (stringBuffer.charAt(i) == '\n' && stringBuffer.charAt(i + 1) == '\n' && stringBuffer.charAt(i + 2) == '\t') {
                stringBuffer.deleteCharAt(i);
                i++; // Move past the deleted '\n'
            } else {
                i++;
            }
        }
    }
}


