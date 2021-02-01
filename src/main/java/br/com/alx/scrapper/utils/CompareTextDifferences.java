package br.com.alx.scrapper.utils;

import org.apache.commons.io.LineIterator;
import org.apache.commons.text.diff.CommandVisitor;
import org.apache.commons.text.diff.StringsComparator;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public final class CompareTextDifferences {

    private static final String LINE_BREAK = System.lineSeparator();

    private CompareTextDifferences() {
        super();
    }

    public static List<String> generateHtmlHighlightDifferences(String oldValue, String newValue) throws IOException {
        // Read both texts with line iterator.
        try (Scanner oldValueIterator = new Scanner(oldValue);
             Scanner newValueIterator = new Scanner(newValue)) {

            // Initialize visitor.
            FileCommandsVisitor fileCommandsVisitor = new FileCommandsVisitor();

            // Read line by line so that comparison can be done line by line.
            while (oldValueIterator.hasNext() || newValueIterator.hasNext()) {
                /*
                 * In case both Strings have different number of lines, fill in with empty
                 * strings. Also append newline char at end so next line comparison moves to
                 * next line.
                 */
                String left = (oldValueIterator.hasNext() ? oldValueIterator.nextLine() : "") + LINE_BREAK;
                String right = (newValueIterator.hasNext() ? newValueIterator.nextLine() : "") + LINE_BREAK;

                // Prepare diff comparator with lines from both files.
                StringsComparator comparator = new StringsComparator(left, right);

                if (comparator.getScript().getLCSLength() > (Integer.max(left.length(), right.length()) * 0.4)) {
                    /*
                     * If both lines have at least 40% commonality then only compare with each other
                     * so that they are aligned with each other in final diff HTML.
                     */
                    comparator.getScript().visit(fileCommandsVisitor);
                } else {
                    /*
                     * If both lines do not have 40% commonality then compare each with empty line so
                     * that they are not aligned to each other in final diff instead they show up on
                     * separate lines.
                     */
                    StringsComparator leftComparator = new StringsComparator(left, LINE_BREAK);
                    leftComparator.getScript().visit(fileCommandsVisitor);
                    StringsComparator rightComparator = new StringsComparator(LINE_BREAK, right);
                    rightComparator.getScript().visit(fileCommandsVisitor);
                }
            }

            return fileCommandsVisitor.generateHTML();
        }
    }
}

class FileCommandsVisitor implements CommandVisitor<Character> {

    // Spans with red & green highlights to put highlighted characters in HTML
    private static final String DELETION = "<span style=\"background-color: #FB504B\">${text}</span>";
    private static final String INSERTION = "<span style=\"background-color: #45EA85\">${text}</span>";
    private static final String LINE_BREAK = System.lineSeparator();
    private static final String HTML_LINE_BREAK = "<br/>";

    private String left = "";
    private String right = "";

    @Override
    public void visitKeepCommand(Character c) {
        // For new line use <br/> so that in HTML also it shows on next line.
        String toAppend = LINE_BREAK.equals("" + c) ? HTML_LINE_BREAK : "" + c;
        // KeepCommand means c present in both left & right. So add this to both without
        // any
        // highlight.
        left = left + toAppend;
        right = right + toAppend;
    }

    @Override
    public void visitInsertCommand(Character c) {
        // For new line use <br/> so that in HTML also it shows on next line.
        String toAppend = LINE_BREAK.equals("" + c) ? HTML_LINE_BREAK : "" + c;
        // InsertCommand means character is present in right file but not in left. Show
        // with green highlight on right.
        right = right + INSERTION.replace("${text}", "" + toAppend);
    }

    @Override
    public void visitDeleteCommand(Character c) {
        // For new line use <br/> so that in HTML also it shows on next line.
        String toAppend = LINE_BREAK.equals("" + c) ? HTML_LINE_BREAK : "" + c;
        // DeleteCommand means character is present in left file but not in right. Show
        // with red highlight on left.
        left = left + DELETION.replace("${text}", "" + toAppend);
    }

    public List<String> generateHTML() {
        return Arrays.asList(left, right);
    }
}
