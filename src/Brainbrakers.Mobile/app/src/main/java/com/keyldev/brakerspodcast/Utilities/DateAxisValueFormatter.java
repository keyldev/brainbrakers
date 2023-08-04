package com.keyldev.brakerspodcast.Utilities;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAxisValueFormatter extends ValueFormatter {

    private final SimpleDateFormat mFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(new Date((long) value));
    }
}