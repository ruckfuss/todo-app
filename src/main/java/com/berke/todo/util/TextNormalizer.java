package com.berke.todo.util;

import java.text.Normalizer;
import java.util.Locale;

public final class TextNormalizer {
    private static final Locale TR = Locale.forLanguageTag("tr-TR");

    private TextNormalizer() {}

    public static String normalize(String input) {
        if (input == null) return "";
        String lower = input.toLowerCase(TR);
        // Remove diacritics (accents) for consistent search
        String normalized = Normalizer.normalize(lower, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalized;
    }
}