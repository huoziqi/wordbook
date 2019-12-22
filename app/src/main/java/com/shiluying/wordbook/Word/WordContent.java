package com.shiluying.wordbook.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample word for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class WordContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<WordItem> ITEMS = new ArrayList<WordItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    private static final int COUNT = 25;

//    static {
        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
//        addItem(new WordItem("1", "Apple"));
//        addItem(new WordItem ("2", "Orange"));
//        addItem(new WordItem ("3", "Banana"));
//        addItem(new WordItem ("4", "Lemon"));
//    }

    private static void addItem(WordItem item) {
        ITEMS.add(item);
    }

//    private static WordItem createWordItem(int position) {
//        return new WordItem(String.valueOf(position), "Item " + position, makewordmeaning(position));
//    }
//
//    private static String makewordmeaning(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("wordmeaning about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore wordmeaning information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of word.
     */
    public static class WordItem {
        public final String word;

        public WordItem(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return word;
        }
    }
}