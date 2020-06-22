package com.hawksjamesf.map.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import androidx.room.ColumnInfo;

import java.util.ArrayList;
import java.util.List;

public final class AppCellInfo implements Parcelable {
    public boolean isRegistered;
    public long bid;
    public long cdmalat;
    public long cdmalon;
    public int cgiage;
    public long cid;
    public long lac;
    public int mcc;
    public int mnc;
    public long nci;
    public long nid;
    public int pci;
    public String radio_type;
    public long rss;
    public long sid;
    public int tac;
    @ColumnInfo(name="is_cell_mock")
    public boolean isMockData;

    public static AppCellInfo convertSysCellLocation(CellLocation cellLocation) {
        AppCellInfo cellInfo2 = new AppCellInfo();
        if (cellLocation instanceof GsmCellLocation) {
//            cellInfo2.mcc = cellIdentity.getMcc();
//            cellInfo2.mnc = cellIdentity.getMnc();
            cellInfo2.lac = (long) ((GsmCellLocation) cellLocation).getLac();
            cellInfo2.cid = (long) ((GsmCellLocation) cellLocation).getCid();
//            cellInfo2.rss = (long) cellInfoGsm.getCellSignalStrength().getDbm();
            cellInfo2.radio_type = "gsm";
//            Log.d(LBS.TAG, "ollection app cell info GsmCellLocation-->" + cellInfo2.toString());
        } else if (cellLocation instanceof CdmaCellLocation) {
            cellInfo2.cdmalat = (long) ((CdmaCellLocation) cellLocation).getBaseStationLatitude();
            cellInfo2.cdmalon = (long) ((CdmaCellLocation) cellLocation).getBaseStationLongitude();
            cellInfo2.sid = (long) ((CdmaCellLocation) cellLocation).getSystemId();
            cellInfo2.nid = (long) ((CdmaCellLocation) cellLocation).getNetworkId();
            cellInfo2.bid = (long) ((CdmaCellLocation) cellLocation).getBaseStationId();
//            cellInfo2.rss = (long) cellLocation.getCellSignalStrength().getDbm();
            //            String[] mccMnc = getMccMnc();
//            cellInfo2.mcc = Integer.parseInt(mccMnc[0]);
//            cellInfo2.mnc = Integer.parseInt(mccMnc[1]);
            cellInfo2.radio_type = "cdma";
//            Log.d(LBS.TAG, "ollection app cell info CdmaCellLocation-->" + cellInfo2.toString());
        }
        return cellInfo2;

    }

    public static List<AppCellInfo> convertSysCellInfo(List<CellInfo> cellInfos) {
        ArrayList<AppCellInfo> appCellInfos = new ArrayList<>();
        if (cellInfos != null) {
            for (int i = 0; i < cellInfos.size(); i++) {
                CellInfo cellInfo = cellInfos.get(i);
                if (cellInfo != null && cellInfo.isRegistered()) {
                    appCellInfos.add(convertSysCellInfo(cellInfo));
                }
            }
        }
        return appCellInfos;
    }

    public static AppCellInfo convertSysCellInfo(CellInfo cellInfo) {
        AppCellInfo cellInfo2 = new AppCellInfo();
        cellInfo2.isRegistered = cellInfo.isRegistered();
        if (cellInfo instanceof CellInfoGsm) {
            CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
            CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
            cellInfo2.mcc = cellIdentity.getMcc();
            cellInfo2.mnc = cellIdentity.getMnc();
            cellInfo2.lac = (long) cellIdentity.getLac();
            cellInfo2.cid = (long) cellIdentity.getCid();
            cellInfo2.rss = (long) cellInfoGsm.getCellSignalStrength().getDbm();
            cellInfo2.radio_type = "gsm";
//            Log.d("", "collection app cell info gsm-->" + cellInfo2.toString());
        } else if (cellInfo instanceof CellInfoCdma) {
            CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
            CellIdentityCdma cellIdentity2 = cellInfoCdma.getCellIdentity();
            cellInfo2.cdmalat = (long) cellIdentity2.getLatitude();
            cellInfo2.cdmalon = (long) cellIdentity2.getLongitude();
            cellInfo2.sid = (long) cellIdentity2.getSystemId();
            cellInfo2.nid = (long) cellIdentity2.getNetworkId();
            cellInfo2.bid = (long) cellIdentity2.getBasestationId();
            cellInfo2.rss = (long) cellInfoCdma.getCellSignalStrength().getDbm();
//            String[] mccMnc = getMccMnc();
//            cellInfo2.mcc = Integer.parseInt(mccMnc[0]);
//            cellInfo2.mnc = Integer.parseInt(mccMnc[1]);
            cellInfo2.radio_type = "cdma";
//            Log.d(LBS.TAG, "collection app cell info cdma-->" + cellInfo2.toString());
        } else if (cellInfo instanceof CellInfoLte) {
            CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
            CellIdentityLte cellIdentity3 = cellInfoLte.getCellIdentity();
            cellInfo2.mcc = cellIdentity3.getMcc();
            cellInfo2.mnc = cellIdentity3.getMnc();
            cellInfo2.lac = (long) cellIdentity3.getTac();
            cellInfo2.cid = (long) cellIdentity3.getCi();
            cellInfo2.rss = (long) cellInfoLte.getCellSignalStrength().getDbm();
            cellInfo2.radio_type = "gsm_lte";
//            Log.d(LBS.TAG, "collection app cell info lte-->" + cellInfo2.toString());
        } else if (!(cellInfo instanceof CellInfoWcdma) || Build.VERSION.SDK_INT < 18) {
//            Log.d(LBS.TAG, "collection app cell  info unknown: " + cellInfo2.toString());
        } else {
            try {
                CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                CellIdentityWcdma cellIdentity4 = cellInfoWcdma.getCellIdentity();
                cellInfo2.mcc = cellIdentity4.getMcc();
                cellInfo2.mnc = cellIdentity4.getMnc();
                cellInfo2.lac = (long) cellIdentity4.getLac();
                cellInfo2.cid = (long) cellIdentity4.getCid();
                cellInfo2.radio_type = "gsm_wcdma";
                cellInfo2.rss = (long) cellInfoWcdma.getCellSignalStrength().getDbm();
                cellInfo2.cgiage = getCgiAgeInSecond(cellInfoWcdma);
                cellInfo2.rss = (long) cellInfoWcdma.getCellSignalStrength().getAsuLevel();
//                Log.d(LBS.TAG, "collection app cell info wcdma-->" + cellInfo2.toString());
            } catch (Exception e) {
//                Log.d(LBS.TAG, e.getMessage());
            }
        }
        return cellInfo2;
    }

    public static int getCgiAgeInSecond(CellInfo cellinfo) {
        return (int) ((SystemClock.elapsedRealtimeNanos() - cellinfo.getTimeStamp()) / 1000000000);
    }

    @Override
    public String toString() {
        return "\"appCellInfo\":{" +
                "\"isRegistered\":" + isRegistered +
                ", \"bid\":" + bid +
                ", \"cdmalat\":" + cdmalat +
                ", \"cdmalon\":" + cdmalon +
                ", \"cgiage\":" + cgiage +
                ", \"cid\":" + cid +
                ", \"lac\":" + lac +
                ", \"mcc\":" + mcc +
                ", \"mnc\":" + mnc +
                ", \"nci\":" + nci +
                ", \"nid\":" + nid +
                ", \"pci\":" + pci +
                ", \"radio_type\":\"" + radio_type +"\"" +
                ", \"rss\":" + rss +
                ", \"sid\":" + sid +
                ", \"tac\":" + tac +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isRegistered ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMockData ? (byte) 1 : (byte) 0);
        dest.writeLong(this.bid);
        dest.writeLong(this.cdmalat);
        dest.writeLong(this.cdmalon);
        dest.writeInt(this.cgiage);
        dest.writeLong(this.cid);
        dest.writeLong(this.lac);
        dest.writeInt(this.mcc);
        dest.writeInt(this.mnc);
        dest.writeLong(this.nci);
        dest.writeLong(this.nid);
        dest.writeInt(this.pci);
        dest.writeString(this.radio_type);
        dest.writeLong(this.rss);
        dest.writeLong(this.sid);
        dest.writeInt(this.tac);
    }

    public AppCellInfo() {
    }

    protected AppCellInfo(Parcel in) {
        this.isRegistered = in.readByte() != 0;
        this.isMockData = in.readByte() != 0;
        this.bid = in.readLong();
        this.cdmalat = in.readLong();
        this.cdmalon = in.readLong();
        this.cgiage = in.readInt();
        this.cid = in.readLong();
        this.lac = in.readLong();
        this.mcc = in.readInt();
        this.mnc = in.readInt();
        this.nci = in.readLong();
        this.nid = in.readLong();
        this.pci = in.readInt();
        this.radio_type = in.readString();
        this.rss = in.readLong();
        this.sid = in.readLong();
        this.tac = in.readInt();
    }

    public static final Parcelable.Creator<AppCellInfo> CREATOR = new Parcelable.Creator<AppCellInfo>() {
        @Override
        public AppCellInfo createFromParcel(Parcel source) {
            return new AppCellInfo(source);
        }

        @Override
        public AppCellInfo[] newArray(int size) {
            return new AppCellInfo[size];
        }
    };


}