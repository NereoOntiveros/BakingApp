package com.example.bakingapp.view;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailStepFragment extends Fragment {

    private RecipeStep selectedStep;
    @BindView(R.id.exoplayer)
    PlayerView mPlayerView;
    @BindView(R.id.step_instruction_tv)
    TextView mStepInstruction;
    @BindView(R.id.iv_thumbnail)
    ImageView mThumbnail;
    private SimpleExoPlayer mPlayer;
    private long mCurrentPosition;

    public DetailStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment DetailStepFragment.
     */


    public static DetailStepFragment newInstance(RecipeStep aSelectedStep){

        DetailStepFragment fragment = new DetailStepFragment();

        //set the bundle arguments for the fragment
        Bundle arguments = new Bundle();
        arguments.putParcelable("SELECTED_STEP",aSelectedStep);
        fragment.setArguments(arguments);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments()!= null && getArguments().containsKey("SELECTED_STEP")){
            //load the content specified by the fragment arguments
            selectedStep = getArguments().getParcelable("SELECTED_STEP");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

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
            startPlayer(selectedStep.getVideoUrl());
        }else {
            mPlayerView.setVisibility(View.GONE);
            loadThumbnail();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        release();
    }

    private void startPlayer(String videoUrl){



            if(mPlayer!=null){
                return;
            }
            mPlayer = new SimpleExoPlayer.Builder(getContext()).build();
            mPlayerView.setPlayer(mPlayer);

            DataSource.Factory dataSourceFactory =
                    new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(),"BakingApp"));
            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));

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

    private void loadThumbnail(){
        if(Patterns.WEB_URL.matcher(selectedStep.getThumbnailURL()).matches()){
            String stringPattern = ".*mp4*.";
            Pattern pattern = Pattern.compile(stringPattern);
            Matcher matcher = pattern.matcher(selectedStep.getThumbnailURL());

            if(matcher.matches()){
                mPlayerView.setVisibility(View.VISIBLE);
                startPlayer(selectedStep.getThumbnailURL());
            }else {
                Picasso.get()
                        .load(selectedStep.getThumbnailURL())
                        .resize(480,362)
                        .placeholder(R.drawable.baking_placeholder)
                        .error(R.drawable.not_found_image).into(mThumbnail);
            }

        }else {
            Picasso.get().load(R.drawable.not_found_image).resize(480,362).into(mThumbnail);
        }

    }
}