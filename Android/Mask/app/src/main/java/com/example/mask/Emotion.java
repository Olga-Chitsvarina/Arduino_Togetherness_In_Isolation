package com.example.mask;

// Enum with emotions names. Pictures have same names but in lower case.
public enum Emotion {
    ANGRY,
    COFFEE,
    COZY,
    CRYING,
    EXCITING,
    GREAT,
    GRUMPY,
    HAPPY,
    KISS,
    LOVE_IT,
    LOVE_YOU_BEAR,
    MASK,
    NO,
    PLEASE,
    POOP,
    SLEEPY,
    VERY_FUNNY,
    VERY_GOOD;

    // Converting emotion name to lower case (just for convenience)
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    // Try to find emotion name in the sentence which user said
    // If matched successfully return value, otherwise nil (no match found)
    public static Emotion getEmotionFromText(String text) {
        for (Emotion value : Emotion.values()) {
          // Try to find emotion name without underscores
            if (text.contains(value.toString().replaceAll("_", " "))) {
                return value;
            }
        }

        return null;
    }
}
