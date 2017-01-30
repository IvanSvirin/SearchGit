/**
 * Copyright 2015 Eugene Matsyuk (matzuk2@mail.ru)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package com.example.ivansv.searchgit.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivansv.searchgit.R;
import com.example.ivansv.searchgit.data.model.Item;
import com.example.ivansv.searchgit.data.rest.ResponseManager;
import com.example.ivansv.searchgit.util.AutoLoadingRecyclerView;
import com.example.ivansv.searchgit.view.adapter.LoadingRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AutoLoadingFragment extends Fragment {
    private final static int LIMIT = 50;
    @Bind(R.id.recyclerView)
    AutoLoadingRecyclerView<Item> recyclerView;
    private LoadingRecyclerViewAdapter recyclerViewAdapter;
    private String query;

    public static AutoLoadingFragment newInstance(String query) {
        AutoLoadingFragment autoLoadingFragment = new AutoLoadingFragment();;
        Bundle args = new Bundle();
        args.putString("query", query);
        autoLoadingFragment.setArguments(args);
        return autoLoadingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = getArguments().getString("query", "");
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        ButterKnife.bind(this, rootView);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init(Bundle savedInstanceState) {
        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewLayoutManager.supportsPredictiveItemAnimations();
        // init adapter for the first time
        if (savedInstanceState == null) {
            recyclerViewAdapter = new LoadingRecyclerViewAdapter(getContext());
            recyclerViewAdapter.setHasStableIds(true);
        }
        recyclerView.setSaveEnabled(true);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setLimit(LIMIT);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLoadingObservable(offsetAndLimit ->
                ResponseManager.getInstance().getResponse(query, offsetAndLimit.getOffset(), offsetAndLimit.getLimit()));
        // start loading for the first time
        if (savedInstanceState == null) {
            recyclerView.startLoading();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        // start loading after reorientation
        if (savedInstanceState != null) {
            recyclerView.startLoading();
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
