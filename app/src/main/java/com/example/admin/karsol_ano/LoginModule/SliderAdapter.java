package com.example.admin.karsol_ano.LoginModule;

/**
 * Created by Admin on 04-10-2017.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.karsol_ano.R;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter
{


        private ArrayList<Integer> images;
        private LayoutInflater inflater;
        private Context context;

        public SliderAdapter(Context context, ArrayList<Integer> images) {
            this.context = context;
            this.images=images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View myImageLayout = inflater.inflate(R.layout.slide, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.imageslide);
            myImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }

