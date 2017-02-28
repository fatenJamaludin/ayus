package com.app.tbd.ui.Model.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 10/11/2016.
 */

public class TravellerInfoToBeFilter {

    List<TravellerDetailInfo> list;

    ArrayList<TravellerDetailInfo> array = new ArrayList<TravellerDetailInfo>();

    public ArrayList<TravellerDetailInfo> getArray() {
        return array;
    }

    public void setArray(ArrayList<TravellerDetailInfo> array) {
        this.array = array;
    }

    public List<TravellerDetailInfo> getList() {
        return list;
    }

    public void setList(ArrayList<TravellerDetailInfo> list) {
        this.list = list;
    }



}
