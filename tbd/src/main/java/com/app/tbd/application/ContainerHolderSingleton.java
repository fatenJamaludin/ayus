package com.app.tbd.application;

import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Created by me-techmacprouser2 on 05/11/2016.
 */

public class ContainerHolderSingleton {

    private static ContainerHolder containerHolder;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {
    }

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }
}
