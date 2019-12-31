/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2019. All rights reserved.
 */

package com.reactlibrary.hms.utils;

public class CheckUtils {
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * Check whether the character string is an integer.
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check whether the character string is a floating point number.
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains(".")) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkIsEdit(String string) {
        return ((string.length() == 0) || (string.isEmpty()) || (string == null) || ("".equals(string)));
    }

    public static boolean checkIsRight(String string) {
        return isNumber(string);
    }
}
