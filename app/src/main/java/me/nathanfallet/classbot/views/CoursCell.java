package me.nathanfallet.classbot.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.nathanfallet.classbot.extensions.DateExtension;
import me.nathanfallet.classbot.extensions.IntExtension;
import me.nathanfallet.classbot.extensions.StringExtension;
import me.nathanfallet.classbot.models.Cours;

public class CoursCell extends LinearLayout {

    private TextView name;
    private TextView date;

    public CoursCell(Context context) {
        // Init linearLayout
        super(context);

        // Size of dp
        int dp4 = IntExtension.dpToPixel(4, getResources());
        int dp16 = IntExtension.dpToPixel(16, getResources());

        // Configure LinearLayout
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundResource(android.R.color.white);

        // Create an horizontal LinearLayout
        LinearLayout horizontal = new LinearLayout(context);
        horizontal.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        horizontal.setOrientation(LinearLayout.HORIZONTAL);

        // Init name
        name = new TextView(context);
        LayoutParams nameParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        nameParams.setMargins(dp16, dp16, dp4, dp16);
        name.setLayoutParams(nameParams);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        name.setTextColor(getResources().getColor(android.R.color.black));
        name.setTypeface(name.getTypeface(), Typeface.BOLD);

        // Init field
        date = new TextView(context);
        LayoutParams dateParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dateParams.setMargins(0, dp16, dp16, dp16);
        date.setLayoutParams(dateParams);
        date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        date.setTextColor(getResources().getColor(android.R.color.black));
        date.setTextAlignment(TEXT_ALIGNMENT_VIEW_END);

        // Add them to layout
        horizontal.addView(name);
        horizontal.addView(date);
        addView(horizontal);
        addView(new Separator(context));
    }

    public void with(Cours cours) {
        name.setText(cours.name);
        date.setText(DateExtension.toRenderedString(StringExtension.toDate(cours.start)));
    }

}
