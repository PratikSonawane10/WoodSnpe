package com.mobitechs.woodsnipe.adapter;


import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.mobitechs.woodsnipe.R;
import com.mobitechs.woodsnipe.model.BDay_Member_Items;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Bday_Yesterday_Adaper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<BDay_Member_Items> listFilteredItem;
    List<BDay_Member_Items> listItem;
    View v;
    RecyclerView.ViewHolder viewHolder;
    private static final int VIEW_TYPE_EMPTY = 1;

    public Bday_Yesterday_Adaper(List<BDay_Member_Items> items) {
        this.listFilteredItem = items;
        this.listItem = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_EMPTY) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empty_view, viewGroup, false);
            EmptyViewHolder emptyViewHolder = new EmptyViewHolder(v);
            return emptyViewHolder;
        }
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bday_list_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder vHolder = (ViewHolder) viewHolder;
            BDay_Member_Items itemOflist = listFilteredItem.get(position);
            vHolder.bindListDetails(itemOflist );
        }
    }

    @Override
    public int getItemCount() {
        if(listFilteredItem.size() > 0){
            return listFilteredItem.size();
        }else {
            return 1;
        }
//        return listFilteredItem.size();
    }
    public int getItemViewType(int position) {
        if (listFilteredItem.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public CircleImageView image;
        public View cardView;

        BDay_Member_Items listFilteredItem = new BDay_Member_Items();

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            image = (CircleImageView) itemView.findViewById(R.id.image);
            cardView = itemView;

        }

        public void bindListDetails(BDay_Member_Items listFilteredItem) {
            this.listFilteredItem = listFilteredItem;

            String imagepath = listFilteredItem.getempImage();
            if (imagepath == null || imagepath.equals("")) {
                image.setImageResource(R.drawable.user_logo);

            } else {
                RequestOptions options = new RequestOptions().centerCrop().error(R.drawable.user_logo);

                Glide.with(image.getContext())
                        .asBitmap()
                        .load(listFilteredItem.getempImage())
                        .apply(options)

                        .into(new BitmapImageViewTarget(image) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(image.getContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                image.setImageDrawable(circularBitmapDrawable);
                            }
                        });
            }
            name.setText(listFilteredItem.getname());
        }

        @Override
        public void onClick(View v) {

        }
    }
    public class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View v) {
            super(v);
//            TextView emptyTextView;
//            emptyTextView = (TextView) v.findViewById(R.id.emptyTextView);
//            emptyTextView.setText("PTA Member Not Available.");
        }
    }
}


