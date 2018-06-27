package com.kas.clientservice.haiyansmartenforce.Module.XieTongList;

import java.util.List;

/**
 * Created by DELL_Zjcoms02 on 2018/6/26.
 */

public class XIGuanBean {

    /**
     * State : true
     * ErrorCode : null
     * ErrorMsg : null
     * Page : null
     * Rtn : [{"id":21,"stepcode":"4","DictionCode":"结果反馈","short_sentence":"请追加派遣","text_sentence":"此问题涉及其他多个部门，请追加派遣相关部门"},{"id":20,"stepcode":"4","DictionCode":"结果反馈","short_sentence":"已按要求处理完毕","text_sentence":"问题已经处理完毕，请核查结案"}]
     * total : 0
     */

    public boolean State;
    public String ErrorCode;
    public String ErrorMsg;
    public String Page;
    public int total;
    public List<RtnBean> Rtn;

    public static class RtnBean {
        /**
         * id : 21
         * stepcode : 4
         * DictionCode : 结果反馈
         * short_sentence : 请追加派遣
         * text_sentence : 此问题涉及其他多个部门，请追加派遣相关部门
         */

        public int id;
        public String stepcode;
        public String DictionCode;
        public String short_sentence;
        public String text_sentence;
    }
}
