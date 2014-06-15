package com.bennyguitar.onions_android.Utilities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bennyguitar.onions_android.MainActivity;
import com.bennyguitar.onions_android.R;

/**
 * Created by BenG on 6/11/14.
 */
public class OnionDialog {
    // Enum
    public enum OnionDialogType {
        SUCCESS,
        FAILURE
    }

    // Show
    public static void show(Context context, String message, OnionDialogType type, int duration) {
        LinearLayout dialog = dialogView(context, message, type);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(dialog);
        toast.show();
    }

    // Build
    private static LinearLayout dialogView(Context context, String message, OnionDialogType type) {
        // Set Up
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialogLayout = (LinearLayout)inflater.inflate(R.layout.dialog_onion, null, false);
        TextView messageTextView = (TextView)dialogLayout.findViewById(R.id.onionDialogMessage);
        ImageView imageView = (ImageView)dialogLayout.findViewById(R.id.onionDialogImageView);

        // Values
        messageTextView.setText(message);
        imageView.setImageResource(imageResourceId(type));

        // Return it
        return dialogLayout;
    }

    // Resource Id
    private static int imageResourceId(OnionDialogType type) {
        switch (type) {
            case SUCCESS:
                return R.drawable.success;
            case FAILURE:
                return R.drawable.unsuccess;
        }

        return 0;
    }

}
