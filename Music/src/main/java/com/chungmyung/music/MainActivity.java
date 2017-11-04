package com.chungmyung.music;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chungmyung.music.util.ImageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1000;

    @BindView(R.id.song_list_view)
    ListView mSongListView;
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.artist_text)
    TextView mArtistText;
    @BindView(R.id.prev_button)
    ImageButton mPrevButton;
    @BindView(R.id.play_button)
    ImageButton mPlayButton;
    @BindView(R.id.next_button)
    ImageButton mNextButtonn;
    @BindView(R.id.picture_image_view)
    ImageView mPictureImageView;

    private MyMusicService mService;
    boolean mBound = false;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mPlayButton.setOnClickListener(this);

        //  runtime 권한 체크


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1000);
            return;
        }

        getSongList();

        mPlayButton.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mNextButtonn.setOnClickListener(this);
    }

    private void getSongList() {

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            MyMusicCursorAdapter adapter = new MyMusicCursorAdapter(MainActivity.this, cursor);
            mSongListView.setAdapter(adapter);

            mSongListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor currentCursor = (Cursor) parent.getAdapter().getItem(position);

                    Intent intent = new Intent(MainActivity.this, MyMusicService.class);
                    intent.setAction(MyMusicService.ACTION_PLAY);
                    intent.putExtra("position", currentCursor.getPosition());
                    startService(intent);
                }
            });
        }
    }

        // 권한요청 응답 처리.
        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getSongList();
                } else {
                    Toast.makeText(this, "권한 거절 ", Toast.LENGTH_SHORT).show();
                }
            }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_button:
                onPlayButtonClicked();
                break;
            case R.id.prev_button:
                onPrevButtonClicked();
                break;
            case R.id.next_button:
                onNextButtonClicked();
                break;
        }
    }

    @OnClick(R.id.next_button)
    public void onNextButtonClicked() {
        Intent intent = new Intent(this, MyMusicService.class);
        intent.setAction(MyMusicService.ACTION_NEXT);
        startService(intent);
    }

    @OnClick(R.id.prev_button)
    public void onPrevButtonClicked() {
        Intent intent = new Intent(this, MyMusicService.class);
        intent.setAction(MyMusicService.ACTION_PREV);
        startService(intent);
    }

    @OnClick(R.id.play_button)
    public void onPlayButtonClicked() {
        Intent intent = new Intent(this, MyMusicService.class);
        if (mService.getMediaPlayer().isPlaying()) {
            intent.setAction(MyMusicService.ACTION_PAUSE);
        } else {
            intent.setAction(MyMusicService.ACTION_PLAY);

        }
        startService(intent);
    }

    @Subscribe
    public void onUiChangeEvent(MyMusicService.UiChangeEvent event) {
        if (event.isPlaying) {
            ImageUtil.changeVectorDrawable(this, mPlayButton, R.drawable.ic_pause_circle_outline_black_24dp);
        } else {
            ImageUtil.changeVectorDrawable(this, mPlayButton, R.drawable.ic_play_circle_outline_black_24dp);
        }

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(this, event.uri);
            byte[] picture = mediaMetadataRetriever.getEmbeddedPicture();

            String artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String title = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            mArtistText.setText(artist);
            mTitleText.setText(title);

            if (picture != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                mPictureImageView.setImageBitmap(bitmap);
            } else {
                mPictureImageView.setImageResource(R.mipmap.ic_launcher);
            }

        } catch (Exception e) {
            Toast.makeText(mService, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        // 서비스 바인드
        Intent intent = new Intent(this, MyMusicService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        // 서비스 언바인드
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            mService = ((MyMusicService.MyMusicServiceBinder) service).getService();
            mBound = true;

            mService.updateUi();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // 강제 종료
            mBound = false;
        }
    };

}

