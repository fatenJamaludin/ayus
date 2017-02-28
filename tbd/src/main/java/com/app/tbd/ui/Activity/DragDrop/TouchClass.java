package com.app.tbd.ui.Activity.DragDrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Dell on 8/2/2016.
 */
public class TouchClass implements View.OnLongClickListener {

    @Override
    public boolean onLongClick(View view) {

        // ClipData data = ClipData.newPlainText("", "");

        ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData dragData = new ClipData(view.getTag().toString(), mimeTypes, item);

        Log.e("view.getTag()", view.getTag().toString());

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(dragData, shadowBuilder, view, 0);
        //view.setVisibility(View.INVISIBLE);

        return false;
    }

   /* public boolean onLongClick(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            //view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }*/

}
