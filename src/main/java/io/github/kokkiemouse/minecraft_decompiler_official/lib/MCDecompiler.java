package io.github.kokkiemouse.minecraft_decompiler_official.lib;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MCDecompiler {
    public static enum MCJAR_TYPE{
        CLIENT,
        SERVER
    }
    private Path work_dir;
    public void set_work_dir(Path workd){
        work_dir=workd;
    }
    public Path get_work_dir(){
        return work_dir;
    }
    private String decompile_version;
    public String get_decompile_version(){
        return decompile_version;
    }
    public void set_decompile_version(String ver){
        decompile_version=ver;
    }
    private MCJAR_TYPE CLIENT_OR_SERVER;
    public MCJAR_TYPE get_CLIENT_OR_SERVER(){
        return CLIENT_OR_SERVER;
    }
    public void set_CLIENT_OR_SERVER(MCJAR_TYPE tkun){
        CLIENT_OR_SERVER=tkun;
    }
    public void get_version_manifest(){
        if(work_dir.resolve("versions").resolve(decompile_version).resolve("version.json").toFile().exists()){
            work_dir.resolve("versions").resolve(decompile_version).resolve("version.json").toFile().delete();
        }
        Path verPath=work_dir.resolve("versions").resolve(decompile_version).resolve("version.json");
        String ver_urlkun=get_ver_url(decompile_version);
        if(ver_urlkun.equals("error")){
            System.err.println("ERROR!");
            return;
        }
        try {
            URL ver_url = new URL(ver_urlkun);
            download_file(ver_url,verPath);
        }catch (MalformedURLException e){

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String get_ver_url(String ver_name){
        try {
            String ver_manifest_str = URL_to_str_download(new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json"));

            JSONObject manife_jo=new JSONObject(ver_manifest_str);
            JSONArray versions_ls=manife_jo.getJSONArray("versions");
            for(Object jo : versions_ls){
                JSONObject jo2=(JSONObject)jo;
                if(jo2.getString("id").equals(ver_name)){
                    return jo2.getString("url");
                }
            }

        }catch (IOException e){
            return "error";
        }
        return "error";

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
    /**
     * file Downloader
     * @param urlkun url
     * @param file_path file path
     * @throws IOException io error
     */
    private void download_file(URL urlkun,Path file_path) throws IOException {
        int size=0;
        DataInputStream in=new DataInputStream(urlkun.openStream());
        if(!file_path.toFile().getParentFile().exists()){
            Files.createDirectories(file_path.getParent());
        }
        DataOutputStream out=new DataOutputStream(new FileOutputStream(file_path.toFile()));
        byte[] buf=new byte[8192];
        int len=0;
        while((len = in.read(buf)) != -1){
            out.write(buf,0,len);
            size += len;
        }
        out.flush();
    }
    public static String URL_to_str_download(URL urlkun) throws IOException {
        InputStream istream=urlkun.openStream();
        InputStreamReader istreamreader=new InputStreamReader(istream);
        BufferedReader br=new BufferedReader(istreamreader);
        String line;
        String output_str = "";
        while((line = br.readLine()) != null){
            output_str += line ;
            output_str += "\n";
        }
        br.close();
        istreamreader.close();
        istream.close();
        return output_str;
    }
}
