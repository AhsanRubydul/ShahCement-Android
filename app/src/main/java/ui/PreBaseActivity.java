package ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.shahcement.nirmaneyaamii.R;

import utiity.Constants;
import widget.AppAlertDialog;

/**
 * Created by Rubydul Ahsan on 9/26/20
 */
public class PreBaseActivity extends AppCompatActivity {

    public void showGridItemDetails(int position) {
        Intent intent = new Intent(this, GridItemDetailsActivity.class);
        intent.putExtra(Constants.POSITION, position);
        startActivity(intent);
    }

    public void showVideoList(Context context) {
        AppAlertDialog dialog = new AppAlertDialog(this);
        dialog.showDialog(Constants.title, Constants.alertMessage, Constants.positiveButton, Constants.negativeButton);
        dialog.setAppAlertDialogListerner(() ->
                startActivity(new Intent(context, VideoListActivity.class)));
    }

    public void sendEmail(String email) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{email});
        intent.putExtra("android.intent.extra.SUBJECT", "Subject text here...");
        intent.putExtra("android.intent.extra.TEXT", "Body of the content here...");
        intent.putExtra("android.intent.extra.CC", "mailcc@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }

    public void openDialer(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void showMore() {
        Intent intent = new Intent(this, MoreActivity.class);
        startActivity(intent);
    }

    public void showView(View view){
        view.setVisibility(View.VISIBLE);
    }

    public void hideView(View view){
        view.setVisibility(View.GONE);
    }

    public void showURLInApp(String url) {
        Intent intent = new Intent();
        intent.setClass(this, WebViewActivity.class);
        intent.putExtra("url", url);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
