package com.example.cmc.trial4;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import com.example.cmc.trial4.R;
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    RequestOptions options;
    private Context mContext;
    static public List<ItemModel> mData;
    private final ItemClickListener mClickHandler;
    public interface ItemClickListener  {
        void onClick(View view ,int position);
    }

    public RvAdapter(Context mContext, List lst,ItemClickListener clickHandler) {
        this.mClickHandler=clickHandler;
        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.one_item, parent, false);
        // click listener here
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.author.setText(mData.get(position).getAuthor());
        holder.date.setText(mData.get(position).getPublishedDate());


        // load image from the internet using Glide
        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(options).into(holder.newsImage);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, author, date;
        ImageView newsImage;


        public MyViewHolder(final View itemView) {
            super(itemView);
            //itemView.setOnClickListener(new View.OnClickListener() {
               /* @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    //intent.putExtra("ItemModel",mData.get(getAdapterPosition()));
                    intent.putExtra("ItemModel","hello ");
                }
            });*/
            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.publishedAt);
            newsImage = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            //String weatherForDay = mData.get(adapterPosition).getTitle();
            mClickHandler.onClick(v,adapterPosition);
        }
    }
}
