package com.mobitechs.woodsnipe.adapter;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.mobitechs.woodsnipe.R;

import java.io.File;
import java.util.List;

public class Multiple_Images_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<File> imgFileList;
    private View v;
    private RecyclerView.ViewHolder viewHolder;

    public Multiple_Images_Adapter(List<File> imgFileList) {
        this.imgFileList = imgFileList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.multiple_image_items, viewGroup, false);
        viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vHolder = (ViewHolder) viewHolder;
        File imgFile = imgFileList.get(position);
        vHolder.bindListDetails(imgFile);
    }

    @Override
    public int getItemCount() {
        return imgFileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.image);
        }


        public void bindListDetails(File imgFile) {
            image.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        }
    }
}
