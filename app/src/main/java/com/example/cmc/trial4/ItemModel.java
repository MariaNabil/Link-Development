package com.example.cmc.trial4;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemModel implements Parcelable {

        private String author ;
        private String description;
        private String title ;
        private String  imageUrl;
        private String PublishedDate;
        private String url;


        public ItemModel() {
        }

        public ItemModel(String author, String description, String title, String imageUrl, String PublishedDate) {
            this.author = author;
            this.description = description;
            this.title = title;
            this.imageUrl = imageUrl;
            this.PublishedDate = PublishedDate;

        }


    protected ItemModel(Parcel in) {
        author = in.readString();
        description = in.readString();
        title = in.readString();
        imageUrl = in.readString();
        PublishedDate = in.readString();
        url = in.readString();
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

    public String getAuthor() {
            return author;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }


        public String getImage_url() {
            return imageUrl;
        }

        public String getPublishedDate() {
            return PublishedDate;
        }

        public String getUrl() {
            return url;
        }



        public void setAuthor(String author) {
            this.author = "By "+author;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public void setImage_url(String image_url) {
            this.imageUrl = image_url;
        }

        public void setPublishedDate(String PublishedDate) {
            this.PublishedDate = PublishedDate;
            //android.text.format.DateFormat df = new android.text.format.DateFormat();
            //df.format("MMMMM d , yyyy", new java.util.Date());("yyyy-MM-dd'T'HH:mm:ss'Z'")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date=new Date();
            try {
                 date = format.parse(PublishedDate);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d , yyyy");
            //Date date = new Date();
            String dateTime = dateFormat.format(date);
            this.PublishedDate=dateTime;
            //            this.PublishedDate=DateFormat.getDateInstance(DateFormat.LONG).format(PublishedDate);
        }

        public void setUrl(String url) {
            this.url = url;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(PublishedDate);
        dest.writeString(url);
    }
}
