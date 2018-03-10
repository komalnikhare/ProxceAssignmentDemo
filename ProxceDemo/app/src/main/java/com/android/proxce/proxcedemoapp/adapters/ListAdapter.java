/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.proxce.proxcedemoapp.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.proxce.proxcedemoapp.R;
import com.android.proxce.proxcedemoapp.utils.UserClickCallback;

import java.util.List;

/**
 * Created by Komal on 3/10/2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.UserViewHolder> {

    List<? extends Items> mItemsList;
    private Context mContext;

    @Nullable
    private final UserClickCallback mUserClickCallback;

    public ListAdapter(@Nullable UserClickCallback clickCallback, Context context) {
        mUserClickCallback = clickCallback;
        mContext = context;
    }

    public void setItemsList(final List<? extends Items> userList) {
        mItemsList = userList;
        notifyDataSetChanged();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final Items item = mItemsList.get(position);
        holder.name.setText(item.getName());
        holder.alpha2_code.setText(item.getAlpha2_code());
        holder.alpha3_code.setText(item.getAlpha3_code());


        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserClickCallback.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemsList == null ? 0 : mItemsList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView name, alpha2_code, alpha3_code;
        public View rootView;

        public UserViewHolder(View view) {
            super(view);
            rootView = view.findViewById(R.id.rootView);
            name = view.findViewById(R.id.name);
            alpha2_code = view.findViewById(R.id.code1);
            alpha3_code = view.findViewById(R.id.code2);

        }
    }
}
