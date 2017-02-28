package com.app.tbd.ui.Activity.BookingFlight.Add;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.tbd.R;
import com.app.tbd.ui.Activity.BookingFlight.PassengerInfoFragment;
import com.app.tbd.ui.Model.JSON.PerRowObj;
import com.app.tbd.ui.Model.JSON.TravellerInfo;
import com.app.tbd.ui.Model.Receive.SeatInfoReceive;
import com.app.tbd.utils.ExpandAbleGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.View.GONE;

public class SeatAdapter2 extends BaseAdapter {

    private final Activity context;
    private String departureAirport;
    private String arrivalAirport;
    private String flightClass;
    private Integer selected_position = -1;
    private String flightWay;
    private Boolean active = false;
    TravellerInfo travellerInfo;
    SeatInfoReceive seatInfoReceive;
    int totalPassenger = 0;
    Boolean traveller1;
    SeatTabFragment frag;
    int y, yLowest, a;
    int segment;
    String equipmentType;

    ArrayList<ArrayList<PerRowObj>> perColum2;

    public SeatAdapter2(Activity context, String equipmentType, SeatInfoReceive seatInfoReceive, ArrayList<ArrayList<PerRowObj>> perColum2, SeatTabFragment frag, int segment) {
        this.context = context;
        this.seatInfoReceive = seatInfoReceive;
        this.frag = frag;
        this.y = y;
        this.yLowest = yLowest;
        this.perColum2 = perColum2;
        this.segment = segment;
        this.equipmentType = equipmentType;
    }

    @Override
    public int getCount() {

        return perColum2.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {


        @InjectView(R.id.txtRowID)
        TextView txtRowID;

        @InjectView(R.id.txtToilet9)
        ImageView txtToilet9;

        @InjectView(R.id.seatRow1)
        LinearLayout seatRow1;

        @InjectView(R.id.seatRow2)
        LinearLayout seatRow2;

        @InjectView(R.id.seatRow3)
        LinearLayout seatRow3;

        @InjectView(R.id.seatRow4)
        LinearLayout seatRow4;

        @InjectView(R.id.seatRow5)
        LinearLayout seatRow5;

        @InjectView(R.id.seatRow6)
        LinearLayout seatRow6;

        @InjectView(R.id.seatRow7)
        LinearLayout seatRow7;

        @InjectView(R.id.seatRow8)
        LinearLayout seatRow8;

        @InjectView(R.id.seatRow9)
        LinearLayout seatRow9;

        @InjectView(R.id.emptySpace)
        LinearLayout emptySpace;

        @InjectView(R.id.extraSeat)
        LinearLayout extraSeat;

        @InjectView(R.id.seatLayout)
        LinearLayout seatLayout;

        ////
        @InjectView(R.id.txtA)
        TextView txtA;

        @InjectView(R.id.txtB)
        TextView txtB;

        @InjectView(R.id.txtC)
        TextView txtC;

        @InjectView(R.id.txtD)
        TextView txtD;

        @InjectView(R.id.txtE)
        TextView txtE;

        @InjectView(R.id.txtF)
        TextView txtF;

        @InjectView(R.id.txtG)
        TextView txtG;

        @InjectView(R.id.txtH)
        TextView txtH;

        @InjectView(R.id.txtI)
        TextView txtI;

        @InjectView(R.id.txtToilet)
        ImageView txtToilet;

        @InjectView(R.id.seatRow1Image)
        ImageView seatRow1Image;

        @InjectView(R.id.seatRow2Image)
        ImageView seatRow2Image;

        @InjectView(R.id.seatRow3Image)
        ImageView seatRow3Image;

        @InjectView(R.id.seatRow4Image)
        ImageView seatRow4Image;

        @InjectView(R.id.seatRow5Image)
        ImageView seatRow5Image;

        @InjectView(R.id.seatRow6Image)
        ImageView seatRow6Image;

        @InjectView(R.id.seatRow7Image)
        ImageView seatRow7Image;

        @InjectView(R.id.seatRow8Image)
        ImageView seatRow8Image;

        @InjectView(R.id.seatRow9Image)
        ImageView seatRow9Image;

        //restricted
        @InjectView(R.id.txtRestricted1)
        ImageView txtRestricted1;

        @InjectView(R.id.txtRestricted2)
        ImageView txtRestricted2;

        @InjectView(R.id.txtRestricted3)
        ImageView txtRestricted3;

        @InjectView(R.id.txtRestricted4)
        ImageView txtRestricted4;

        @InjectView(R.id.txtRestricted5)
        ImageView txtRestricted5;

        @InjectView(R.id.txtRestricted7)
        ImageView txtRestricted7;

        @InjectView(R.id.txtRestricted8)
        ImageView txtRestricted8;

        @InjectView(R.id.txtRestricted9)
        ImageView txtRestricted9;

        @InjectView(R.id.txtRestricted6)
        ImageView txtRestricted6;
        //

        @InjectView(R.id.txtExit1)
        TextView txtExit1;

        @InjectView(R.id.txtExit9)
        TextView txtExit9;

        @InjectView(R.id.txtPassengerName1)
        TextView txtPassengerName1;

        @InjectView(R.id.txtPassengerName2)
        TextView txtPassengerName2;

        @InjectView(R.id.txtPassengerName3)
        TextView txtPassengerName3;

        @InjectView(R.id.txtPassengerName4)
        TextView txtPassengerName4;

        @InjectView(R.id.txtPassengerName5)
        TextView txtPassengerName5;

        @InjectView(R.id.txtPassengerName6)
        TextView txtPassengerName6;

        @InjectView(R.id.txtPassengerName7)
        TextView txtPassengerName7;

        @InjectView(R.id.txtPassengerName8)
        TextView txtPassengerName8;

        @InjectView(R.id.txtPassengerName9)
        TextView txtPassengerName9;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.seat_row_layout, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }


        if (perColum2.get(position).get(0).getSeatType().equals("EMPTY")) {
            vh.emptySpace.setVisibility(View.GONE);
        } else {
            a++;
            vh.emptySpace.setVisibility(View.VISIBLE);
            vh.txtRowID.setText(perColum2.get(position).get(0).getRowID());

            try {

                //check seat type
                final String type = perColum2.get(position).get(0).getSeatType();
                final String seatDesignator = perColum2.get(position).get(0).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(0).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(0).getSeatGroup());

                //check colour per group
                vh.seatRow1.setVisibility(View.VISIBLE);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(0).getSeatGroup());
                vh.seatRow1.setBackground(colorCode);


                if (perColum2.get(position).get(0).getSelectedBy() == 99) {
                    vh.seatRow1Image.setVisibility(View.GONE);
                    vh.txtPassengerName1.setVisibility(View.GONE);
                    vh.txtPassengerName1.setText("");
                    vh.seatRow1.setClickable(true);
                    vh.txtRestricted1.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(0).getSelectedBy() == 999) {
                    vh.seatRow1Image.setVisibility(View.VISIBLE);
                    vh.seatRow1.setClickable(false);
                    vh.txtPassengerName1.setVisibility(View.GONE);
                    vh.txtPassengerName1.setText("");
                    vh.txtRestricted1.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(0).getSelectedBy() == 9) {
                    vh.txtRestricted1.setVisibility(View.VISIBLE);
                    vh.seatRow1Image.setVisibility(View.GONE);
                    vh.seatRow1.setClickable(false);
                    vh.txtPassengerName1.setVisibility(View.GONE);
                    vh.txtPassengerName1.setText("");
                    vh.seatRow1.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName1.setVisibility(View.VISIBLE);
                    vh.txtPassengerName1.setText(perColum2.get(position).get(0).getSelectedByName());
                    vh.seatRow1.setClickable(true);
                }

                //vh.txtA.setText(seatDesignator);
                vh.seatRow1.setTag(seatDesignator);

                vh.seatRow1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(0).getSelectedBy() != 999 && perColum2.get(position).get(0).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 0, seatCompartment);
                        }
                    }
                });

                if (type.equals("EX")) {
                    vh.txtExit1.setVisibility(View.VISIBLE);
                    vh.seatRow1Image.setVisibility(View.GONE);
                    vh.seatRow1.setClickable(false);
                    //Drawable colorCode = checkColourCode("99");
                    //vh.seatRow1.setBackground(colorCode);

                }
                if (type.equals("ES")) {
                    vh.seatRow1.setVisibility(View.INVISIBLE);
                    vh.seatRow1.setClickable(false);
                }
                if (type.equals("NS")) {
                    vh.txtExit1.setVisibility(View.GONE);
                }
                if (type.equals("LR")) {
                    vh.txtA.setVisibility(View.VISIBLE);
                    vh.txtExit1.setVisibility(View.GONE);
                } else {
                    vh.txtA.setVisibility(View.GONE);
                }
                if (type.equals("LV")) {
                    vh.txtToilet.setVisibility(View.VISIBLE);
                    vh.seatRow1.setClickable(false);
                } else {
                    vh.txtToilet.setVisibility(View.GONE);
                }


                //else {
                //    vh.txtExit1.setVisibility(View.GONE);
                //}

                /*if (type.equals("Reserved")) {
                    vh.seatRow1Image.setVisibility(View.VISIBLE);
                }

                if (type.equals("Empty_Space") || type.equals("WG")) {
                    vh.seatRow1.setVisibility(View.INVISIBLE);
                } else {
                    vh.seatRow1.setVisibility(View.VISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtA.setVisibility(View.VISIBLE);
                } else {
                    vh.txtA.setVisibility(View.GONE);
                }

                if (type.equals("EX")) {
                    vh.txtExit1.setVisibility(View.VISIBLE);
                } else {
                    vh.txtExit1.setVisibility(View.GONE);
                }

                if (type.equals("LV")) {
                    //vh.seatRow1Image.setVisibility(View.VISIBLE);
                    //vh.seatRow1Image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_lavatory));
                }*/

            } catch (Exception e) {
                vh.seatRow1.setVisibility(View.INVISIBLE);
            }

            try {

                final String type = perColum2.get(position).get(1).getSeatType();
                final String seatDesignator = perColum2.get(position).get(1).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(1).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(1).getSeatGroup());

                //vh.seatRow2.setText(seatDesignator);
                vh.seatRow2.setTag(seatDesignator);
                vh.seatRow2.setVisibility(View.VISIBLE);
                //not selected
                Drawable colorCode = checkColourCode(perColum2.get(position).get(1).getSeatGroup());
                vh.seatRow2.setBackground(colorCode);


                if (perColum2.get(position).get(1).getSelectedBy() == 99) {
                    vh.seatRow2Image.setVisibility(View.GONE);
                    vh.txtPassengerName2.setVisibility(View.GONE);
                    vh.txtPassengerName2.setText("");
                    vh.seatRow2.setClickable(true);
                    vh.txtRestricted2.setVisibility(View.GONE);

                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(1).getSelectedBy() == 9) {
                    vh.txtRestricted2.setVisibility(View.VISIBLE);
                    vh.seatRow2Image.setVisibility(View.GONE);
                    vh.seatRow2.setClickable(false);
                    vh.txtPassengerName2.setVisibility(View.GONE);
                    vh.txtPassengerName2.setText("");
                    vh.seatRow2.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(1).getSelectedBy() == 999) {
                    vh.seatRow2Image.setVisibility(View.VISIBLE);
                    vh.seatRow2.setClickable(false);
                    vh.txtPassengerName2.setVisibility(View.GONE);
                    vh.txtPassengerName2.setText("");
                    vh.txtRestricted2.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName2.setVisibility(View.VISIBLE);
                    vh.txtPassengerName2.setText(perColum2.get(position).get(1).getSelectedByName());
                    vh.seatRow2.setClickable(true);
                }

                vh.seatRow2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(1).getSelectedBy() != 999 && perColum2.get(position).get(1).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 1, seatCompartment);
                        }
                    }
                });

                if (type.equals("ES")) {
                    vh.seatRow2.setVisibility(View.INVISIBLE);
                }
                if (type.equals("LR")) {
                    vh.txtB.setVisibility(View.VISIBLE);
                } else {
                    vh.txtB.setVisibility(View.GONE);
                }

                /*if (type.equals("Reserved")) {
                    vh.seatRow2Image.setVisibility(View.VISIBLE);
                }

                if (type.equals("Empty_Space") || type.equals("WG")) {
                    vh.seatRow2.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtB.setVisibility(View.VISIBLE);
                } else {
                    vh.txtB.setVisibility(View.GONE);
                }*/


            } catch (Exception e) {
                vh.seatRow2.setVisibility(View.INVISIBLE);
            }

            try {

                final String type = perColum2.get(position).get(2).getSeatType();
                final String seatDesignator = perColum2.get(position).get(2).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(2).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(2).getSeatGroup());

                //vh.seatRow3.setText(seatDesignator);
                vh.seatRow3.setTag(seatDesignator);
                vh.seatRow3.setVisibility(View.VISIBLE);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(2).getSeatGroup());
                vh.seatRow3.setBackground(colorCode);

                //check colour per group
                if (perColum2.get(position).get(2).getSelectedBy() == 99) {
                    vh.seatRow3Image.setVisibility(View.GONE);
                    vh.txtPassengerName3.setVisibility(View.GONE);
                    vh.txtPassengerName3.setText("");
                    vh.seatRow3.setClickable(true);
                    vh.txtRestricted3.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(2).getSelectedBy() == 9) {
                    vh.txtRestricted3.setVisibility(View.VISIBLE);
                    vh.seatRow3Image.setVisibility(View.GONE);
                    vh.seatRow3.setClickable(false);
                    vh.txtPassengerName3.setVisibility(View.GONE);
                    vh.txtPassengerName3.setText("");
                    vh.seatRow3.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(2).getSelectedBy() == 999) {
                    vh.seatRow3Image.setVisibility(View.VISIBLE);
                    vh.seatRow3.setClickable(false);
                    vh.txtPassengerName3.setVisibility(View.GONE);
                    vh.txtPassengerName3.setText("");
                    vh.txtRestricted3.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName3.setVisibility(View.VISIBLE);
                    vh.txtPassengerName3.setText(perColum2.get(position).get(2).getSelectedByName());
                    vh.seatRow3.setClickable(true);
                }

                vh.seatRow3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(2).getSelectedBy() != 999 && perColum2.get(position).get(2).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 2, seatCompartment);
                        }
                    }
                });

                if (type.equals("ES")) {
                    vh.seatRow3.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtC.setVisibility(View.VISIBLE);
                } else {
                    vh.txtC.setVisibility(View.GONE);
                }

                //vh.seatRow1Image.setImageDrawable();
                /*if (type.equals("Reserved")) {
                    vh.seatRow3Image.setVisibility(View.VISIBLE);
                }

                if (type.equals("Empty_Space") || type.equals("WG")) {
                    vh.seatRow3.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtC.setVisibility(View.VISIBLE);
                } else {
                    vh.txtC.setVisibility(View.GONE);
                }*/

            } catch (Exception e) {
                vh.seatRow3.setVisibility(View.INVISIBLE);
            }

            try {

                final String type = perColum2.get(position).get(3).getSeatType();
                final String seatDesignator = perColum2.get(position).get(3).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(3).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(3).getSeatGroup());

                //vh.seatRow4.setText(seatDesignator);
                vh.seatRow4.setTag(seatDesignator);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(3).getSeatGroup());
                vh.seatRow4.setBackground(colorCode);


                //check colour per group
                if (perColum2.get(position).get(3).getSelectedBy() == 99) {
                    vh.seatRow4Image.setVisibility(View.GONE);
                    vh.txtPassengerName4.setVisibility(View.GONE);
                    vh.txtPassengerName4.setText("");
                    vh.seatRow4.setClickable(true);
                    vh.txtRestricted4.setVisibility(View.GONE);

                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(3).getSelectedBy() == 9) {
                    vh.txtRestricted4.setVisibility(View.VISIBLE);
                    vh.seatRow4Image.setVisibility(View.GONE);
                    vh.seatRow4.setClickable(false);
                    vh.seatRow4.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    vh.txtPassengerName4.setVisibility(View.GONE);
                    vh.txtPassengerName4.setText("");
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(3).getSelectedBy() == 999) {
                    vh.seatRow4Image.setVisibility(View.VISIBLE);
                    vh.seatRow4.setClickable(false);
                    vh.txtPassengerName4.setVisibility(View.GONE);
                    vh.txtPassengerName4.setText("");
                    vh.txtRestricted4.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName4.setVisibility(View.VISIBLE);
                    vh.txtPassengerName4.setText(perColum2.get(position).get(3).getSelectedByName());
                    vh.seatRow4.setClickable(true);
                }

                vh.seatRow4.setVisibility(View.VISIBLE);

                vh.seatRow4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(3).getSelectedBy() != 999 && perColum2.get(position).get(3).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 3, seatCompartment);
                        }
                    }
                });

                if (type.equals("ES")) {
                    vh.seatRow4.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtD.setVisibility(View.VISIBLE);
                } else {
                    vh.txtD.setVisibility(View.GONE);
                }
                //vh.seatRow1Image.setImageDrawable();
                /*if (type.equals("Reserved")) {
                    vh.seatRow4Image.setVisibility(View.VISIBLE);
                }

                if (type.equals("Empty_Space") || type.equals("WG")) {
                    vh.seatRow4.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtD.setVisibility(View.VISIBLE);
                } else {
                    vh.txtD.setVisibility(View.GONE);
                }*/


            } catch (Exception e) {
                vh.seatRow4.setVisibility(View.INVISIBLE);

            }

            try {

                final String type = perColum2.get(position).get(4).getSeatType();
                final String seatDesignator = perColum2.get(position).get(4).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(4).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(4).getSeatGroup());

                //vh.seatRow5.setText(seatDesignator);
                vh.seatRow5.setTag(seatDesignator);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(4).getSeatGroup());
                vh.seatRow5.setBackground(colorCode);


                //check colour per group
                if (perColum2.get(position).get(4).getSelectedBy() == 99) {
                    vh.seatRow5Image.setVisibility(View.GONE);
                    vh.txtPassengerName5.setVisibility(View.GONE);
                    vh.txtPassengerName5.setText("");
                    vh.seatRow5.setClickable(true);
                    vh.txtRestricted5.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(4).getSelectedBy() == 9) {
                    vh.txtRestricted5.setVisibility(View.VISIBLE);
                    vh.seatRow5Image.setVisibility(View.GONE);
                    vh.seatRow5.setClickable(false);
                    vh.txtPassengerName5.setVisibility(View.GONE);
                    vh.txtPassengerName5.setText("");
                    vh.seatRow5.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(4).getSelectedBy() == 999) {
                    vh.seatRow5Image.setVisibility(View.VISIBLE);
                    vh.seatRow5.setClickable(false);
                    vh.txtPassengerName5.setVisibility(View.GONE);
                    vh.txtPassengerName5.setText("");
                    vh.txtRestricted5.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName5.setVisibility(View.VISIBLE);
                    vh.txtPassengerName5.setText(perColum2.get(position).get(4).getSelectedByName());
                    vh.seatRow5.setClickable(true);
                }

                vh.seatRow5.setVisibility(View.VISIBLE);
                vh.seatRow5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(4).getSelectedBy() != 999 && perColum2.get(position).get(4).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 4, seatCompartment);
                        }
                    }
                });

                if (type.equals("ES")) {
                    vh.seatRow5.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtE.setVisibility(View.VISIBLE);
                } else {
                    vh.txtE.setVisibility(View.GONE);
                }
                //vh.seatRow1Image.setImageDrawable();
                /*if (type.equals("Reserved")) {
                    vh.seatRow5Image.setVisibility(View.VISIBLE);
                }
                if (type.equals("Empty_Space") || type.equals("WG")) {
                    vh.seatRow5.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtE.setVisibility(View.VISIBLE);
                } else {
                    vh.txtE.setVisibility(View.GONE);
                }*/


            } catch (Exception e) {
                vh.seatRow5.setVisibility(View.INVISIBLE);
            }


            try {

                final String type = perColum2.get(position).get(5).getSeatType();
                final String seatDesignator = perColum2.get(position).get(5).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(5).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(5).getSeatGroup());

                // vh.seatRow6.setText(seatDesignator);
                vh.seatRow6.setTag(seatDesignator);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(5).getSeatGroup());
                vh.seatRow6.setBackground(colorCode);


                //check colour per group
                if (perColum2.get(position).get(5).getSelectedBy() == 99) {
                    vh.seatRow6Image.setVisibility(View.GONE);
                    vh.txtPassengerName6.setVisibility(View.GONE);
                    vh.txtPassengerName6.setText("");
                    vh.seatRow6.setClickable(true);
                    vh.txtRestricted6.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(5).getSelectedBy() == 9) {
                    vh.txtRestricted6.setVisibility(View.VISIBLE);
                    vh.seatRow6Image.setVisibility(View.GONE);
                    vh.seatRow6.setClickable(false);
                    vh.seatRow6.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    vh.txtPassengerName6.setVisibility(View.GONE);
                    vh.txtPassengerName6.setText("");
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(5).getSelectedBy() == 999) {
                    vh.seatRow6Image.setVisibility(View.VISIBLE);
                    vh.seatRow6.setClickable(false);
                    vh.txtRestricted6.setVisibility(View.GONE);
                    vh.txtPassengerName6.setVisibility(View.GONE);
                    vh.txtPassengerName6.setText("");
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName6.setVisibility(View.VISIBLE);
                    vh.txtPassengerName6.setText(perColum2.get(position).get(5).getSelectedByName());
                    vh.seatRow6.setClickable(true);
                }

                vh.seatRow6.setVisibility(View.VISIBLE);

                vh.seatRow6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(5).getSelectedBy() != 999 && perColum2.get(position).get(5).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 5, seatCompartment);
                        }
                    }
                });

                if (type.equals("ES")) {
                    vh.seatRow6.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtF.setVisibility(View.VISIBLE);
                } else {
                    vh.txtF.setVisibility(View.GONE);
                }


            } catch (Exception e) {
                vh.seatRow6.setVisibility(View.INVISIBLE);
            }

            vh.extraSeat.setVisibility(View.VISIBLE);

            try {
                final String type = perColum2.get(position).get(6).getSeatType();
                final String seatDesignator = perColum2.get(position).get(6).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(6).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(6).getSeatGroup());

                // vh.seatRow6.setText(seatDesignator);
                vh.seatRow7.setTag(seatDesignator);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(6).getSeatGroup());
                vh.seatRow7.setBackground(colorCode);


                //check colour per group
                if (perColum2.get(position).get(6).getSelectedBy() == 99) {
                    vh.seatRow7Image.setVisibility(View.GONE);
                    vh.txtPassengerName7.setVisibility(View.GONE);
                    vh.txtPassengerName7.setText("");
                    vh.seatRow7.setClickable(true);
                    vh.txtRestricted7.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(6).getSelectedBy() == 9) {
                    vh.txtRestricted7.setVisibility(View.VISIBLE);
                    vh.seatRow7Image.setVisibility(View.GONE);
                    vh.seatRow7.setClickable(false);
                    vh.seatRow7.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    vh.txtPassengerName7.setVisibility(View.GONE);
                    vh.txtPassengerName7.setText("");
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(6).getSelectedBy() == 999) {
                    vh.seatRow7Image.setVisibility(View.VISIBLE);
                    vh.seatRow7.setClickable(false);
                    vh.txtPassengerName7.setVisibility(View.GONE);
                    vh.txtPassengerName7.setText("");
                    vh.txtRestricted7.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName7.setVisibility(View.VISIBLE);
                    vh.txtPassengerName7.setText(perColum2.get(position).get(6).getSelectedByName());
                    vh.seatRow7.setClickable(true);
                }

                vh.seatRow7.setVisibility(View.VISIBLE);

                vh.seatRow7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(6).getSelectedBy() != 999 && perColum2.get(position).get(6).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 6, seatCompartment);
                        }
                    }
                });


                if (type.equals("ES")) {
                    vh.seatRow7.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtG.setVisibility(View.VISIBLE);
                } else {
                    vh.txtG.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                vh.seatRow7.setVisibility(View.INVISIBLE);
            }


            try {
                final String type = perColum2.get(position).get(7).getSeatType();
                final String seatDesignator = perColum2.get(position).get(7).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(7).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(7).getSeatGroup());

                // vh.seatRow6.setText(seatDesignator);
                vh.seatRow8.setTag(seatDesignator);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(7).getSeatGroup());
                vh.seatRow8.setBackground(colorCode);


                //check colour per group
                if (perColum2.get(position).get(7).getSelectedBy() == 99) {
                    vh.seatRow8Image.setVisibility(View.GONE);
                    vh.txtPassengerName8.setVisibility(View.GONE);
                    vh.txtPassengerName8.setText("");
                    vh.seatRow8.setClickable(true);
                    vh.txtRestricted8.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(7).getSelectedBy() == 9) {
                    vh.txtRestricted8.setVisibility(View.VISIBLE);
                    vh.seatRow8Image.setVisibility(View.GONE);
                    vh.seatRow8.setClickable(false);
                    vh.seatRow8.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    vh.txtPassengerName8.setVisibility(View.GONE);
                    vh.txtPassengerName8.setText("");
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(7).getSelectedBy() == 999) {
                    vh.seatRow8Image.setVisibility(View.VISIBLE);
                    vh.seatRow8.setClickable(false);
                    vh.txtPassengerName8.setVisibility(View.GONE);
                    vh.txtPassengerName8.setText("");
                    vh.txtRestricted8.setVisibility(View.GONE);

                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName8.setVisibility(View.VISIBLE);
                    vh.txtPassengerName8.setText(perColum2.get(position).get(7).getSelectedByName());
                    vh.seatRow8.setClickable(true);

                }

                vh.seatRow8.setVisibility(View.VISIBLE);

                vh.seatRow8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(7).getSelectedBy() != 999 && perColum2.get(position).get(7).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 7, seatCompartment);
                        }
                    }
                });


                if (type.equals("ES")) {
                    vh.seatRow8.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtH.setVisibility(View.VISIBLE);
                } else {
                    vh.txtH.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                vh.seatRow8.setVisibility(View.INVISIBLE);
            }


            try {
                final String type = perColum2.get(position).get(8).getSeatType();
                final String seatDesignator = perColum2.get(position).get(8).getSeatDesignator();
                final String seatCompartment = perColum2.get(position).get(8).getCompartment();
                final String seatPts = checkPTS(perColum2.get(position).get(8).getSeatGroup());

                // vh.seatRow6.setText(seatDesignator);
                vh.seatRow9.setTag(seatDesignator);

                Drawable colorCode = checkColourCode(perColum2.get(position).get(8).getSeatGroup());
                vh.seatRow9.setBackground(colorCode);

                //check colour per group
                if (perColum2.get(position).get(8).getSelectedBy() == 99) {
                    vh.seatRow9Image.setVisibility(View.GONE);
                    vh.txtPassengerName9.setVisibility(View.GONE);
                    vh.txtPassengerName9.setText("");
                    vh.seatRow9.setClickable(true);
                    vh.txtRestricted9.setVisibility(View.GONE);
                    //selected by passenger (reserved)
                } else if (perColum2.get(position).get(8).getSelectedBy() == 9) {
                    vh.txtRestricted9.setVisibility(View.VISIBLE);
                    vh.seatRow9Image.setVisibility(View.GONE);
                    vh.seatRow9.setClickable(false);
                    vh.seatRow9.setBackground(ContextCompat.getDrawable(context, R.color.white));
                    vh.txtPassengerName9.setVisibility(View.GONE);
                    vh.txtPassengerName9.setText("");
                    //selected by passenger (new)
                } else if (perColum2.get(position).get(8).getSelectedBy() == 999) {
                    vh.seatRow9Image.setVisibility(View.VISIBLE);
                    vh.seatRow9.setClickable(false);
                    vh.txtPassengerName9.setVisibility(View.GONE);
                    vh.txtPassengerName9.setText("");
                    vh.txtRestricted9.setVisibility(View.GONE);
                    //selected by passenger (new)
                } else {
                    vh.txtPassengerName9.setVisibility(View.VISIBLE);
                    vh.txtPassengerName9.setText(perColum2.get(position).get(8).getSelectedByName());
                    vh.seatRow9.setClickable(true);
                }


                vh.seatRow9.setVisibility(View.VISIBLE);

                vh.seatRow9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (perColum2.get(position).get(8).getSelectedBy() != 999 && perColum2.get(position).get(8).getSelectedBy() != 9 && !type.equals("EX") && !type.equals("LV")) {
                            frag.openUserSeatImage(seatDesignator, "2", seatPts, segment, position, 8, seatCompartment);
                        }
                    }
                });

                if (type.equals("EX")) {
                    vh.txtExit9.setVisibility(View.VISIBLE);
                    vh.seatRow9Image.setVisibility(View.GONE);
                } else {
                    vh.txtExit9.setVisibility(View.GONE);
                }
                if (type.equals("ES")) {
                    vh.seatRow9.setVisibility(View.INVISIBLE);
                }

                if (type.equals("LR")) {
                    vh.txtI.setVisibility(View.VISIBLE);
                } else {
                    vh.txtI.setVisibility(View.GONE);
                }

                if (type.equals("LV")) {
                    vh.txtToilet9.setVisibility(View.VISIBLE);
                } else {
                    vh.txtToilet9.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                vh.seatRow9.setVisibility(View.INVISIBLE);
            }

        }

        return view;

    }

    public Drawable checkColourCode(String group) {

        Drawable colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_1);

        if (group.equals("1")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_1);
        } else if (group.equals("2")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_2);
        } else if (group.equals("3")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_3);
        } else if (group.equals("4")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_4);
        } else if (group.equals("5")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_5);
        } else if (group.equals("6")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_6);
        } else if (group.equals("7")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_7);
        } else if (group.equals("8")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_8);
        } else if (group.equals("9")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_9);
        } else if (group.equals("10")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_colour_1);
        } else if (group.equals("88")) {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_follow_background);
        } else if (group.equals("X")) {
            colorCode = ContextCompat.getDrawable(context, R.color.white);
        } else {
            colorCode = ContextCompat.getDrawable(context, R.drawable.seat_group_follow_background);
        }


        return colorCode;

    }

    public String checkPTS(String seatGroup) {

        String pts = "NA";

        for (int f = 0; f < seatInfoReceive.getSeatGroupPassengerFee().size(); f++) {
            if (seatGroup.equals(seatInfoReceive.getSeatGroupPassengerFee().get(f).getSeatGroup())) {
                pts = seatInfoReceive.getSeatGroupPassengerFee().get(f).getQuotedPoints();
                break;
            }
        }

        return pts;
    }


    public void updateSeatAdapter(int y, int x, int travellerPosition, String action, String name) {

        //need to remove previous selected seat for this user
        for (int b = 0; b < perColum2.size(); b++) {
            for (int c = 0; c < perColum2.get(b).size(); c++) {
                if (perColum2.get(b).get(c).getSelectedBy() == travellerPosition) {
                    perColum2.get(b).get(c).setSelectedBy(99);
                    break;
                }
            }
        }

        if (action.equals("Remove")) {
            perColum2.get(y).get(x).setSelectedBy(99);
        } else {
            if (perColum2.get(y).get(x).getSeatAvailability().equals("Reserved")) {
                perColum2.get(y).get(x).setSelectedBy(999);
                Log.e("Reserved", "Y");
            } else {
                perColum2.get(y).get(x).setSelectedBy(travellerPosition);
                perColum2.get(y).get(x).setSelectedByName(name);
            }
        }
        notifyDataSetChanged();
    }


   /* public void updateTravellerInfo(TravellerInfo obj) {
        travellerInfo = obj;
        notifyDataSetChanged();
    }

    public void notifyError(TravellerInfo obj) {
        travellerInfo = obj;
        notifyDataSetChanged();
    }

    public void invalidateSelected() {
        selected_position = -1;
        notifyDataSetChanged();
    }

    public TravellerInfo returnTravellerInfo() {
        return travellerInfo;
    } */
}
