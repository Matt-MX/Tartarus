package com.mattmx.tartarus.world.guis.text;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormattedString {
    private List<ColoredString> message = new ArrayList<>();
    private String string;
    private boolean isFormatted = false;

    public FormattedString(String str) {
        this.string = str;
        compile();
    }

    private final Pattern hex = Pattern.compile("#[a-fA-F0-9]{6}");
    private final Pattern colored = Pattern.compile("(&[0-9a-f])(?!.*\\1).+");
    public void compile() {
        message.clear();
        Matcher matcher = colored.matcher(string);
        while (matcher.find()) {
            ColoredString section = new ColoredString(string.substring(matcher.start() + 2, matcher.end()));
            section.setColor(ColoredString.get(string.substring(matcher.start() + 1, matcher.start() + 2)));
            message.add(section);
            System.out.println(section.getString());
            isFormatted = true;
        }
    }

    public boolean isFormatted() {
        return isFormatted;
    }

    @Override
    public String toString() {
        return string;
    }

    public List<ColoredString> get() {
        return message;
    }
}
