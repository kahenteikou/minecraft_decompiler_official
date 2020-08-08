package io.github.kokkiemouse.minecraft_decompiler_official.lib;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MCDecompiler {
    private Path work_dir;
    public void set_work_dir(Path workd){
        work_dir=workd;
    }
    public Path get_work_dir(){
        return work_dir;
    }
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
    public MCDecompiler(){
        work_dir=Paths.get( System.getProperty("user.dir"));
    }
    private void download_file(URL urlkun,Path file_path){
        int size=0;
        try(DataInputStream in=new DataInputStream(urlkun.openStream());
            DataOutputStream out=new DataOutputStream(new FileOutputStream(file_path.toFile()))){
            byte[] buf=new byte[8192];
            int len=0;
            while((len = in.read(buf)) != -1){
                out.write(buf,0,len);
                size += len;
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
