package com.world.play.ui.feature.news_detail;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.world.play.R;
import com.world.play.models.StoryDetail;
import com.world.play.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_title)
    TextView textTitle;

    private StoryDetail storyDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_news_details);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            storyDetail = getIntent().getParcelableExtra(AppConstants.STORY_DETAILS);
            setData();
        }
    }

    @OnClick(R.id.img_back)
    public void onBackSelect() {
        onBackPressed();
    }

    private void setData() {
        if (storyDetail != null) {
            textTitle.setText(storyDetail.getTitle());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.loadUrl(storyDetail.getUrl());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
