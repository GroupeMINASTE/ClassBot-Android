package me.nathanfallet.classbot.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.nathanfallet.classbot.R;
import me.nathanfallet.classbot.extensions.IntExtension;
import me.nathanfallet.classbot.models.APIRequest;
import me.nathanfallet.classbot.models.APIResponseStatus;
import me.nathanfallet.classbot.models.APIVerification;

public class ConfigurationActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollView scrollView;
    private LinearLayout contentView;
    private TextView hostLabel;
    private EditText hostField;
    private Button submitButton;
    private Button instructionsButton;
    private boolean checking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scrollView = new ScrollView(this);

        contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        contentView.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(contentView);

        int dp8 = IntExtension.dpToPixel(8, getResources());
        int dp16 = IntExtension.dpToPixel(16, getResources());
        int dp32 = IntExtension.dpToPixel(32, getResources());

        hostLabel = new TextView(this);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        labelParams.setMargins(dp16, dp32, dp16, 0);
        hostLabel.setLayoutParams(labelParams);
        hostLabel.setText(R.string.hostLabel);
        hostLabel.setTextColor(getResources().getColor(android.R.color.black));
        hostLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        hostLabel.setTypeface(hostLabel.getTypeface(), Typeface.BOLD);
        hostLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        contentView.addView(hostLabel);

        hostField = new EditText(this);
        LinearLayout.LayoutParams fieldParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        fieldParams.setMargins(dp16, dp8, dp16, 0);
        hostField.setLayoutParams(fieldParams);
        hostField.setHint(R.string.hostField);
        hostField.setSingleLine();
        hostField.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        contentView.addView(hostField);

        submitButton = new Button(this);
        LinearLayout.LayoutParams submitParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        submitParams.setMargins(dp16, dp32, dp16, 0);
        submitButton.setLayoutParams(submitParams);
        submitButton.setText(R.string.submitButton);
        submitButton.setTextColor(getResources().getColor(android.R.color.white));
        GradientDrawable buttonBackground = new GradientDrawable();
        buttonBackground.setColor(getResources().getColor(R.color.colorPrimary));
        buttonBackground.setCornerRadius(dp8);
        submitButton.setBackground(buttonBackground);
        submitButton.setOnClickListener(this);
        contentView.addView(submitButton);

        instructionsButton = new Button(this);
        LinearLayout.LayoutParams instructionsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        instructionsParams.setMargins(dp16, dp8, dp16, dp32);
        instructionsButton.setLayoutParams(instructionsParams);
        instructionsButton.setText(R.string.instructionsButton);
        instructionsButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        instructionsButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        instructionsButton.setOnClickListener(this);
        contentView.addView(instructionsButton);

        setContentView(scrollView);
    }

    @Override
    public void onClick(View view) {
        if (view == submitButton) {
            // Get host
            if (!checking && !hostField.getText().toString().trim().isEmpty()) {
                // Check
                final String host = hostField.getText().toString().trim();
                checking = true;
                submitButton.setText(R.string.verification);

                // Query API
                new APIRequest("GET", host, "/api/classbot", new APIRequest.CompletionHandler() {
                    @Override
                    public void completionHandler(@Nullable Object object, APIResponseStatus status) {
                        // Check data and bool
                        if (object instanceof JSONObject && new APIVerification((JSONObject) object).classbot) {
                            // Validate and dismiss
                            Intent result = new Intent();
                            result.putExtra("host", host);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        } else {
                            // Host is not correct
                            checking = false;
                            submitButton.setText(R.string.submitButton);

                            // Show an alert
                            new AlertDialog.Builder(ConfigurationActivity.this).setTitle(R.string.error_host_title).setMessage(R.string.error_host_description).setPositiveButton(R.string.ok, null).show();
                        }
                    }
                }).execute();
            }
        } else if (view == instructionsButton) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/GroupeMINASTE/ClassBot/blob/master/README.md"));
            startActivity(browserIntent);
        }
    }

}
