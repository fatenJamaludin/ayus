package com.app.tbd;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Dell on 2/23/2016.
 */
public final class BusProvider {

    private static final Bus mbus = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance(){
        return mbus;
    }

    private BusProvider(){

    }
}
