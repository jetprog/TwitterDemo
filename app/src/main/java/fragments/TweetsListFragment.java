package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.JetTweets.R;
import com.codepath.apps.JetTweets.TweetsArrayAdapter;
import com.codepath.apps.JetTweets.TwitterClient;
import com.codepath.apps.JetTweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SET JETRO on 8/6/2016.
 */
public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;



    //inflate
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        //find the listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //connect adapter to thr listview
        lvTweets.setAdapter(aTweets);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create the arraylist
        tweets = new ArrayList<>();
        //create the adapter
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }
}
