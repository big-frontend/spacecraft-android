package com.electrolytej.ad.util;

import com.electrolytej.util.NumberUtil;

public class ShakeTraceUtil {
    public static String reson(double checkAngleAccelerationX, double checkAngleAccelerationY, double checkAngleAccelerationZ, double checkDegreeX, double checkDegreeY, double checkDegreeZ,
                               double ax, double ay, double az, double degreeX, double degreeY, double degreeZ) {
        String areson = "加速度";
        if (Math.abs(ax) >= checkAngleAccelerationX) {
            areson += "x,";
        }
        if (Math.abs(ay) >= checkAngleAccelerationY) {
            areson += "y,";
        }
        if (Math.abs(az) >= checkAngleAccelerationZ) {
            areson += "z";
        }
        areson += "轴触发";
        String dreson = "角度";
        if (Math.abs(degreeX) >= checkDegreeX) {
            dreson += "x,";
        }
        if (Math.abs(degreeY) >= checkDegreeY) {
            dreson += "y,";
        }
        if (Math.abs(degreeZ) >= checkDegreeZ) {
            dreson += "z";
        }
        dreson += "轴触发";
        return areson + " " + dreson;
    }

    public static String accelerationTrace(double ax, double ay, double az) {
        StringBuilder sb = new StringBuilder();
        sb.append(NumberUtil.round(ax, 2))
                .append("|")
                .append(NumberUtil.round(ay, 2))
                .append("|")
                .append(NumberUtil.round(az, 2));
        return sb.toString();
    }

    public static String degreeTrace(double degreeX, double degreeY, double degreeZ) {
        return NumberUtil.round(degreeX, 2) + "|" + NumberUtil.round(degreeY, 2) + "|" + NumberUtil.round(degreeZ, 2);
    }
}
