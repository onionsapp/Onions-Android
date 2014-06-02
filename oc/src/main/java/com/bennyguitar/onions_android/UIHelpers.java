package com.bennyguitar.onions_android;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;

import com.mattyork.colours.Colour;

/**
 * Created by BenG on 5/31/14.
 */
public class UIHelpers {
    // Gradient
    public static GradientDrawable purpleGradient() {
        GradientDrawable newGradient = new GradientDrawable();
        int colors[] = {lightPurpleColor(),darkPurpleColor()};
        newGradient.setColors(colors);
        return newGradient;
    }

    // Colors
    public static int lightPurpleColor() {
        return Color.parseColor("#922d8d");
    }

    public static int darkPurpleColor() {
        return Color.parseColor("#65318f");
    }

    public static int buttonColor() {
        int bColor = Color.BLACK;
        return Color.argb(255/2, Color.red(bColor), Color.green(bColor), Color.blue(bColor));
    }

    // Button Styling
    public static void styleOnionButton(Button button, boolean enabled) {
        if (button != null) {
            int bgColor = enabled ? buttonColor() : Color.argb(255/4, Color.red(buttonColor()), Color.green(buttonColor()), Color.blue(buttonColor()));
            int textColor = enabled ? Color.WHITE : Color.argb(255/4, 255, 255, 255);
            button.setBackgroundColor(bgColor);
            button.setAllCaps(true);
            button.setTextColor(textColor);
            button.setEnabled(enabled);
        }
    }

    public static void styleClearButton(Button button, int textColor) {
        if (button != null) {
            button.setBackgroundColor(Color.argb(0, 0, 0, 0));
            button.setTextColor(textColor != 0 ? textColor : Color.WHITE);
        }
    }
}
