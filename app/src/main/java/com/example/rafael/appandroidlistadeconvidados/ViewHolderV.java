package com.example.rafael.appandroidlistadeconvidados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Rafael on 29/10/2017.
 */

public class ViewHolderV extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    final TextView nome;
    final ImageView icon;
    private ItemClickListener itemClickListener;

    public ViewHolderV(View view){
        super(view);
        nome    = view.findViewById(R.id.txtMonth);
        icon    = view.findViewById(R.id.userImg);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClickListener(view,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClickListener(view,getAdapterPosition(),true);
        return true;
    }
}
