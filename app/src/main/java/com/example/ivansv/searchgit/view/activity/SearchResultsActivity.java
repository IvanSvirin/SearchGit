package com.example.ivansv.searchgit.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.ivansv.searchgit.R;
import com.example.ivansv.searchgit.view.fragment.AutoLoadingFragment;

public class SearchResultsActivity extends AppCompatActivity {
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        orientation = getResources().getConfiguration().orientation;
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, AutoLoadingFragment.newInstance(query))
                    .commit();
        }
        intent.setAction(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (getResources().getConfiguration().orientation == orientation) {
            finish();
        }
    }
}
