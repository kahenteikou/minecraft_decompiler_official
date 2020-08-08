package io.github.kokkiemouse.minecraft_decompiler_official.lib;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MCDecompiler {
    public static Path get_minecraft_path(){
        Get_OS.OS_ENUM Now_OS=Get_OS.get_OS();
        switch (Now_OS){
            case LINUX: {
                String HomeDir_str=System.getProperty("user.home");
                Path homedir_path=Paths.get(HomeDir_str);
                return homedir_path.resolve(".minecraft");
            }
            case WINDOWS:{
                String appdata_path_str= System.getenv("APPDATA");
                Path appdata_path= Paths.get(appdata_path_str);
                return appdata_path.resolve(".minecraft");
            }
            case DARWIN:{
                String home_dir_str=System.getProperty("user.home");

                Path homedir_path=Paths.get(home_dir_str);
                return homedir_path.resolve("Library").resolve("Application Support").resolve("minecraft");
            }
            case UNKNOWN:{
                String HomeDir_str=System.getProperty("user.home");
                Path homedir_path=Paths.get(HomeDir_str);
                return homedir_path.resolve(".minecraft");
            }
            default:{

                String HomeDir_str=System.getProperty("user.home");
                Path homedir_path=Paths.get(HomeDir_str);
                return homedir_path.resolve(".minecraft");
            }


        }
    }
}
