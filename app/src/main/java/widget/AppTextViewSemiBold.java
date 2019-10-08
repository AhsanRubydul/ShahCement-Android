package widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class AppTextViewSemiBold extends TextView {

    public AppTextViewSemiBold(Context context) {
        super(context);
    }

    public AppTextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "font/Montserrat-SemiBold.ttf");
        setTypeface(tf);
    }


    @SuppressLint("DefaultLocale")
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text.toString(), type);
    }
}