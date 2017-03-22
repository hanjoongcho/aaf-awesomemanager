package me.blog.korn123.awesomemanager.help;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.blog.korn123.awesomemanager.R;

/**
 * Created by CHO HANJOONG on 2016-10-20.
 */

public class ScreenSlidePageFragment extends Fragment {

    int index;

    public ScreenSlidePageFragment() {

    }

    @SuppressLint("ValidFragment")
    public ScreenSlidePageFragment(int index) {
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_image, container, false);

        int resourceId = 0;
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        TextView titleView = (TextView) rootView.findViewById(R.id.infoTitle);
        TextView close = (TextView) rootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        titleView.setText("User Guide " + (index + 1) + " / 46");
        switch (index) {
            case 0:
                resourceId = R.drawable.help1;
                break;
            case 1:
                resourceId = R.drawable.help2;
                break;
            case 2:
                resourceId = R.drawable.help3;
                break;
            case 3:
                resourceId = R.drawable.help4;
                break;
            case 4:
                resourceId = R.drawable.help5;
                break;
            case 5:
                resourceId = R.drawable.help6;
                break;
            case 6:
                resourceId = R.drawable.help7;
                break;
            case 7:
                resourceId = R.drawable.help8;
                break;
            case 8:
                resourceId = R.drawable.help9;
                break;
            case 9:
                resourceId = R.drawable.help10;
                break;
            case 10:
                resourceId = R.drawable.help11;
                break;
            case 11:
                resourceId = R.drawable.help12;
                break;
            case 12:
                resourceId = R.drawable.help13;
                break;
            case 13:
                resourceId = R.drawable.help14;
                break;
            case 14:
                resourceId = R.drawable.help15;
                break;
            case 15:
                resourceId = R.drawable.help16;
                break;
            case 16:
                resourceId = R.drawable.help17;
                break;
            case 17:
                resourceId = R.drawable.help18;
                break;
            case 18:
                resourceId = R.drawable.help19;
                break;
            case 19:
                resourceId = R.drawable.help20;
                break;
            case 20:
                resourceId = R.drawable.help21;
                break;
            case 21:
                resourceId = R.drawable.help22;
                break;
            case 22:
                resourceId = R.drawable.help23;
                break;
            case 23:
                resourceId = R.drawable.help24;
                break;
            case 24:
                resourceId = R.drawable.help25;
                break;
            case 25:
                resourceId = R.drawable.help26;
                break;
            case 26:
                resourceId = R.drawable.help27;
                break;
            case 27:
                resourceId = R.drawable.help28;
                break;
            case 28:
                resourceId = R.drawable.help29;
                break;
            case 29:
                resourceId = R.drawable.help30;
                break;
            case 30:
                resourceId = R.drawable.help31;
                break;
            case 31:
                resourceId = R.drawable.help32;
                break;
            case 32:
                resourceId = R.drawable.help33;
                break;
            case 33:
                resourceId = R.drawable.help34;
                break;
            case 34:
                resourceId = R.drawable.help35;
                break;
            case 35:
                resourceId = R.drawable.help36;
                break;
            case 36:
                resourceId = R.drawable.help37;
                break;
            case 37:
                resourceId = R.drawable.help38;
                break;
            case 38:
                resourceId = R.drawable.help39;
                break;
            case 39:
                resourceId = R.drawable.help40;
                break;
            case 40:
                resourceId = R.drawable.help41;
                break;
            case 41:
                resourceId = R.drawable.help42;
                break;
            case 42:
                resourceId = R.drawable.help43;
                break;
            case 43:
                resourceId = R.drawable.help44;
                break;
            case 44:
                resourceId = R.drawable.help45;
                break;
            case 45:
                resourceId = R.drawable.help46;
                break;
        }
        new ThumbnailTask(getActivity(), imageView).execute(String.valueOf(resourceId));
        return rootView;
    }

    private static class ThumbnailTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private Activity activity;

        public ThumbnailTask(Activity activity, ImageView imageView) {
            this.imageView = imageView;
            this.activity = activity;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            int resourceId = Integer.parseInt(params[0]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resourceId);
//            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), android.R.drawable.ic_menu_camera);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            final TransitionDrawable td =
                    new TransitionDrawable(new Drawable[] {
                            new ColorDrawable(Color.TRANSPARENT),
                            new BitmapDrawable(activity.getResources(), bitmap)
                    });
            imageView.setImageDrawable(td);
            td.startTransition(500);
        }

    }

}