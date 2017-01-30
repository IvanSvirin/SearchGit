/**
 * Copyright 2015 Eugene Matsyuk (matzuk2@mail.ru)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package com.example.ivansv.searchgit.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivansv.searchgit.R;
import com.example.ivansv.searchgit.data.model.Item;
import com.example.ivansv.searchgit.util.AutoLoadingRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoadingRecyclerViewAdapter extends AutoLoadingRecyclerViewAdapter<Item> {
    private static final int MAIN_VIEW = 0;
    private Context context;
    private Picasso picasso;

    public LoadingRecyclerViewAdapter(Context context) {
        this.context = context;
        picasso = Picasso.with(context);
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_avatar)
        ImageView avatar;
        @Bind(R.id.tv_owner)
        TextView owner;
        @Bind(R.id.tv_name)
        TextView name;
        @Bind(R.id.tv_description)
        TextView description;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MAIN_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
            return new MainViewHolder(v);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return MAIN_VIEW;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case MAIN_VIEW:
                onBindTextHolder(holder, position);
                break;
        }
    }

    private void onBindTextHolder(RecyclerView.ViewHolder holder, int position) {
        MainViewHolder mainHolder = (MainViewHolder) holder;
        mainHolder.owner.setText(getItem(position).getOwner().getLogin());
        mainHolder.name.setText(getItem(position).getName());
        mainHolder.description.setText(getItem(position).getDescription());
        picasso.load(getItem(position).getOwner().getAvatarUrl()).into(mainHolder.avatar);
    }
}