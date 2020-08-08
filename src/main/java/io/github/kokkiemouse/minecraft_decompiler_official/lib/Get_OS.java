package io.github.kokkiemouse.minecraft_decompiler_official.lib;

import org.apache.commons.lang3.SystemUtils;

public class Get_OS {
    /**
     * OS check enum
     * OS判定用enum
     */
    public static enum OS_ENUM{
        LINUX,
        DARWIN,
        WINDOWS,
        UNKNOWN
    }
    public static OS_ENUM get_OS(){
        if(SystemUtils.IS_OS_WINDOWS){
            return OS_ENUM.WINDOWS;
        }else if(SystemUtils.IS_OS_LINUX){
            return OS_ENUM.LINUX;
        }else if(SystemUtils.IS_OS_MAC){
            return OS_ENUM.DARWIN;
        }else{
            return OS_ENUM.UNKNOWN;
        }
    }
}
