/*package com.metech.tbd.drawer;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.metech.tbd.R;
import com.metech.tbd.base.BaseBaseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class NavigationDrawerAdapter extends BaseBaseAdapter<DrawerItem>
{

	private static class ViewHolder
	{
		public TextView text;
		public ImageView icon;
        public View background;

        public ImageView dpProfile;
        public TextView regDate;
        public TextView fullName;
        public TextView userLoc;

        public ViewHolder()
		{
		}
	}

	Random r = new Random(new Date().getTime());
	ArrayList<View> rowList = new ArrayList<View>();
	private NavigationDrawerFragment navigationDrawerFragment;
   // SharedPrefManager prefer;


	public static class DrawerViewType
	{
		public static Integer[] layouts = { R.layout.drawer_menu, R.layout.drawer_header, R.layout.drawer_static_menu, R.layout.drawer_header_closebtn,R.layout.drawer_profile };
		public final static int MENU = layouts[0];
		public final static int HEADER = layouts[1];
		public final static int STATIC_MENU = layouts[2];
		public final static int HEADER_CLOSEBTN = layouts[3];
        public final static int DRAWER_PROFILE = layouts[4];

    }

	public NavigationDrawerAdapter(Activity activity, NavigationDrawerFragment navigationDrawerFragment)
	{
		super(activity);
		this.navigationDrawerFragment = navigationDrawerFragment;
        //prefer = new SharedPrefManager(activity);

    }

	@Override
	public int getViewTypeCount()
	{
		return DrawerViewType.layouts.length;
	}

	ArrayList<ViewHolder> viewHolder = new ArrayList<ViewHolder>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

        String profileImage = "n/a";
        String registerDate = "n/a";
        String userFullName = "n/a";
        String userLocation = "n/a";


        try
        {
           // HashMap<String, String> init = prefer.getDrawerProfile();
           // profileImage = init.get(SharedPrefManager.PROFILE_IMAGE);
           // registerDate = init.get(SharedPrefManager.REGISTER_DATE);
           // userFullName = init.get(SharedPrefManager.USERNAME);
            //userLocation = init.get(SharedPrefManager.USER_LOCATION);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        ViewHolder holder = new ViewHolder();
		final DrawerItem drawerObj = getItem(position);

		int layoutRes = drawerObj.getLayoutRes();

		convertView = getLayoutInflater().inflate(layoutRes, parent, false);
		aq.recycle(convertView);

		if (layoutRes == DrawerViewType.MENU)
		{
			holder.text = aq.id(R.id.text1).getTextView();
			//aq.id(holder.text).typeface(Typeface.createFromAsset(getActivity().getAssets(), App.FONT_HELVETICA_NEUE)).textSize(18);

		}
		else if (layoutRes == DrawerViewType.HEADER)
		{
			holder.background = aq.id(R.id.vrsm__drawer_item_bg).getView();
			holder.text = aq.id(R.id.text1).getTextView();
			//aq.id(holder.text).typeface(Typeface.createFromAsset(getActivity().getAssets(), App.FONT_HELVETICA_NEUE)).textSize(24);

		}
		else if (layoutRes == DrawerViewType.HEADER_CLOSEBTN)
		{
			holder.background = aq.id(R.id.vrsm__drawer_item_bg).getView();
			holder.text = aq.id(R.id.text1).getTextView();
			//aq.id(holder.text).typeface(Typeface.createFromAsset(getActivity().getAssets(), App.FONT_HELVETICA_NEUE)).textSize(24);
			aq.id(R.id.txtCloseBtn).clicked(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					navigationDrawerFragment.closeDrawer();
				}
			});

		}
		else if (layoutRes == DrawerViewType.STATIC_MENU)
		{
			holder.text = aq.id(R.id.text1).getTextView();
			holder.icon = aq.id(R.id.imageView1).getImageView();
			//aq.id(holder.text).typeface(Typeface.createFromAsset(getActivity().getAssets(), App.FONT_HELVETICA_NEUE)).textSize(18);
		}
        else if (layoutRes == DrawerViewType.DRAWER_PROFILE)
        {
            holder.dpProfile = aq.id(R.id.drawerProfilePic).getImageView();
            holder.regDate = aq.id(R.id.txtUserRegisterDate).getTextView();
            holder.fullName = aq.id(R.id.txtUserFullName).getTextView();
            holder.userLoc = aq.id(R.id.txtUserLocation).getTextView();

            try
            {
                if(profileImage != "n/a") {
                    final String fullUrl = profileImage.replace("&amp;", "&");
                }
                else
                {
                    //another image
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



           aq.id(holder.regDate).text("Member since: " + registerDate);
           aq.id(holder.userLoc).text("Location: " + userLocation);
           aq.id(holder.fullName).text(userFullName);

        }

        aq.id(holder.text).text(drawerObj.getTitle());
		aq.id(holder.icon).image(drawerObj.getIconId());
		aq.id(holder.background).backgroundColor(drawerObj.getBackgroundColor());

		return convertView;
	}
}
*/