package com.example.bakingapp.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.model.RecipeStep;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.nio.BufferUnderflowException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    public RecipeStep selectedStep;
    @BindView(R.id.exoplayer)
    PlayerView mPlayerView;
    @BindView(R.id.step_instruction_tv)
    TextView mStepInstruction;
    @BindView(R.id.no_video_tv)
    TextView mNoVideo;
    private SimpleExoPlayer mPlayer;
    private long mCurrentPosition;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment DetailFragment.
     */


    public static DetailFragment newInstance(RecipeStep aSelectedStep){

        DetailFragment fragment = new DetailFragment();

        //set the bundle arguments for the fragment
        Bundle arguments = new Bundle();
        arguments.putParcelable("SELECTED_STEP",aSelectedStep);
        fragment.setArguments(arguments);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey("SELECTED_STEP")){
            //load the content specified by the fragment arguments
            selectedStep = getArguments().getParcelable("SELECTED_STEP");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this,rootView);

        if(selectedStep!=null){

            mStepInstruction.setText(selectedStep.getDescription());
        }

        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        if(Patterns.WEB_URL.matcher(selectedStep.getVideoUrl()).matches()){
            startPlayer();
        }else {
            mPlayerView.setVisibility(View.GONE);
            mNoVideo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        release();
    }

    private void startPlayer(){



            if(mPlayer!=null){
                return;
            }
            mPlayer = new SimpleExoPlayer.Builder(getContext()).build();
            mPlayerView.setPlayer(mPlayer);

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(),"BakingApp"));
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(selectedStep.getVideoUrl()));

            boolean isResuming = mCurrentPosition!=0;
            mPlayer.prepare(videoSource,isResuming,false);
            mPlayer.setPlayWhenReady(true);
            if(isResuming){
                mPlayer.seekTo(mCurrentPosition);
            }


    }

    private void release(){

        if(mPlayer==null){
            return;
        }

        mCurrentPosition = mPlayer.getCurrentPosition();
        mPlayer.release();
        mPlayer=null;

    }
}