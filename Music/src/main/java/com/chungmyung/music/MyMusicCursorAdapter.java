package com.chungmyung.music;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2017-10-11.
 */

public class MyMusicCursorAdapter extends CursorAdapter {
    public static final String TAG = MyMusicCursorAdapter.class.getSimpleName();

    public MyMusicCursorAdapter(Context context, Cursor c) {
        super(context, c, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.title = (TextView) view.findViewById(R.id.title_text);
        viewHolder.artist = (TextView) view.findViewById(R.id.artist_text);

        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // 그림이 데이터 베이스에서 실제로 몇번째 옆에 있는지
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        viewHolder.mTitleTextView.setText(title);
        String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
        viewHolder.mArtistTextView.setText(artist);

        final Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));

        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(context, uri);
            byte[] picture = mediaMetadataRetriever.getEmbeddedPicture();

            if (picture != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                viewHolder.mImageView.setImageBitmap(bitmap);
            } else {
                viewHolder.mImageView.setImageResource(R.mipmap.ic_launcher);
            }
        } catch (Exception e) {
            Log.d(TAG, "bindView: " + cursor.getPosition());
        }
    }

    static class ViewHolder {
        @BindView(R.id.image_view)
        ImageView mImageView;
        @BindView(R.id.title_text_view)
        TextView mTitleTextView;
        @BindView(R.id.artist_text_view)
        TextView mArtistTextView;
        public TextView title;
        public TextView artist;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}

