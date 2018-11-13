package com.example.cmc.trial4;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsActivity extends AppCompatActivity {
    RequestOptions options;
    TextView TitleTV;
    TextView authorTV;
    TextView PublishedDate;
    TextView Description;
    ImageView imageView;
    Button openWebButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TitleTV=(TextView)findViewById(R.id.title1);
        authorTV=(TextView)findViewById(R.id.author1) ;
        PublishedDate=(TextView)findViewById(R.id.publishedAt1);
        Description=(TextView)findViewById(R.id.description1);
        imageView=(ImageView)findViewById(R.id.image1);
        openWebButton=(Button) findViewById(R.id.button1);
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);
        Intent intent=getIntent();
        if (intent!=null){
            //String s=intent.getStringExtra(Intent.EXTRA_TEXT);
            //TitleTV.setText(s);
            final ItemModel itemModel = (ItemModel) getIntent().getParcelableExtra("parcel_data");
            TitleTV.setText(itemModel.getTitle());
            authorTV.setText(itemModel.getAuthor());
            PublishedDate.setText(itemModel.getPublishedDate());
            Description.setText(itemModel.getDescription());
            //Glide.with(this).load(itemModel.getImage_url()).apply(options).into(imageView);
            Glide.with(this).load(itemModel.getImage_url()).into(imageView);
            openWebButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(itemModel.getUrl()));
                    startActivity(intent);
                }
            });
        }
    }
}
