package com.example.marwa.tweetme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TweetActivity extends AppCompatActivity {

    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // LinearLayoutManager
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Add Item Decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        /* fetching the username from LoginActivity */
        String username = getIntent().getStringExtra("username");

        /* setting up a UserTimeline */
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(username)
                .build();

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this)
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

        // If there is a list, hide TextView
        TextView empty = (TextView) findViewById(R.id.emptyList);
        if (adapter != null) {
            empty.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        } else {
            empty.setVisibility(View.VISIBLE);
        }

        // Initialize the SearchView
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        // Initialize the Toolbar
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set Toolbar as Actionbar
        setSupportActionBar(mainToolbar);
        // Color of the title in the Toolbar
        mainToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Search for keyword
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query(query)
                        .build();

                final TweetTimelineRecyclerViewAdapter adapterForSearch =
                        new TweetTimelineRecyclerViewAdapter.Builder(getApplicationContext())
                                .setTimeline(searchTimeline)
                                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                                .build();

                recyclerView.setAdapter(adapterForSearch);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Compose Tweets
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TwitterSession session = TwitterCore.getInstance().getSessionManager()
                        .getActiveSession();
                //Uri uri = Uri.parse("android.resource://com.example.marwa.tweetme/drawable/twitter");
                final Intent intent = new ComposerActivity.Builder(TweetActivity.this)
                        .session(session)
                        .text("Write Your Tweets Here")
                        //.image(uri)
                        .hashtags("#twitter")
                        .createIntent();
                startActivity(intent);
            }
        });


    }

    /**
     * Create the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
    
}
