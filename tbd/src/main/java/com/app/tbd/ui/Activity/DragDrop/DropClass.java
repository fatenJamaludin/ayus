package com.app.tbd.ui.Activity.DragDrop;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.tbd.R;
import com.app.tbd.base.BaseFragment;

/**
 * Created by Dell on 8/2/2016.
 */
public class DropClass implements View.OnDragListener {

    // Drawable enterShape =   ResourcesCompat.getDrawable(getResources(), R.drawable.tbd_icon, null);
    // Drawable normalShape =   ResourcesCompat.getDrawable(getResources(), R.drawable.tbd_icon, null);
    Activity act;

    public DropClass(Activity act) {
        this.act = act;
    }

    Drawable enterShape = act.getResources().getDrawable(R.drawable.shape_drop);
    Drawable normalShape = act.getResources().getDrawable(R.drawable.shape);

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                Log.e("DRAG ON", "Y");
                //myimage1.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.arrow_in_icon));

                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.e("DRAG ENTERED", "Y");
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.e("DRAG EXITED", "Y");
                v.setBackgroundDrawable(normalShape);
                break;
            case DragEvent.ACTION_DROP:

                Log.e("DRAG DROP", "Y");

                // Dropped, reassign View to ViewGroup
                View view = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);
                LinearLayout container = (LinearLayout) v;
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundDrawable(normalShape);
            default:
                break;
        }
        return true;
    }
}
