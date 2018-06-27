package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

/**
 * Created by DELL_Zjcoms02 on 2018/6/26.
 */

public class FilePath {
    String filepath;
    String filestate;
    String cudate;
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getFilestate() {
        return filestate;
    }
    public void setFilestate(String filestate) {
        this.filestate = filestate;
    }
    public String getCudate() {
        return cudate;
    }
    public void setCudate(String cudate) {
        this.cudate = cudate;
    }
    public FilePath(String filepath, String filestate, String cudate) {
        super();
        this.filepath = filepath;
        this.filestate = filestate;
        this.cudate = cudate;
    }
}
