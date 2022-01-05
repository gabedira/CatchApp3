package com.example.catchapp3;

import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.ImageView;

public class RunnerPositioner {

    public static void position(ImageView imageView, double percentage){
        percentage = percentage % 1;
        double offsetX, offsetY;
        if(0 <= percentage && percentage <= 0.2607){
            double theta = Math.PI * percentage / 0.2607 - Math.PI/2;
            offsetX = 220 + 145 * Math.cos(theta);
            offsetY = -15 - 145 * Math.sin(theta);
        }else if (0.2607 <= percentage && percentage <= 0.5){
            offsetX = (percentage - 0.2607)/(0.5 - 0.2607) * -470 + 220;
            offsetY = -160;
        }else if (0.5 <= percentage && percentage <= 0.5 + 0.2607){
            double theta = Math.PI * (percentage - 0.5)/ 0.2607 + Math.PI/2;
            offsetX = -250 + 115 * Math.cos(theta);
            offsetY = -15 - 145 * Math.sin(theta);
        }else{
            offsetX = (1 - percentage)/(0.5 - 0.2607) * -470 + 220;
            offsetY = 130;
        }
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) offsetX, Resources.getSystem().getDisplayMetrics());
        float py = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) offsetY, Resources.getSystem().getDisplayMetrics());

        imageView.setTranslationX(px);
        imageView.setTranslationY(py);
        if(offsetY <= 15)
            imageView.setScaleX(-1);
        else
            imageView.setScaleX(1);


    }
}
