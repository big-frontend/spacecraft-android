package com.hawksjamesf.common;

import android.os.Bundle;
import android.os.IBinder;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import org.parceler.Parcel;

import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jul/01/2019  Mon
 *
 *
 *
 * Parcelable listParcelable = Parcels.wrap(new ArrayList<AndroidParcelable2>());
 * Parcelable mapParcelable = Parcels.wrap(new HashMap<String, AndroidParcelable2>());
 *
 * AndroidParcelable2 example = Parcels.unwrap(getIntent().getParcelableExtra("AndroidParcelable2"));
 *
 * Bundle bundle = new Bundle();
 * bundle.putParcelable("AndroidParcelable2", Parcels.wrap(androidParcelable2));
 */
@Parcel
public class AndroidParcelable2 {
    public byte arg0;
    public char arg1;
    public String arg2;
    public boolean arg3;
    public int arg4;
    public short arg5;
    public long arg6;
    public float arg7;
    public double arg8;
    public AndroidParcelable arg9;//Parcelable,Serializable,@Parcel
    public IBinder arg10;
    public Bundle arg11;
    public SparseArray arg12;
    public SparseBooleanArray arg13;
    //List, ArrayList and LinkedList
    public List arg15;
    //Map, HashMap, LinkedHashMap, SortedMap, and TreeMap
    public Map arg16;
    //Set, HashSet, SortedSet, TreeSet, LinkedHashSet
    public Set arg17;
    //Array
    int[] arg18;

}
