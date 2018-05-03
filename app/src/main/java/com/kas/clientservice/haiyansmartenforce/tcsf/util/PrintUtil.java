package com.kas.clientservice.haiyansmartenforce.tcsf.util;


import net.posprinter.utils.DataForSendToPrinterPos58;

import java.util.ArrayList;

/**
 * 打印格式
 */
public class PrintUtil {
    private ArrayList<byte[]> list = new ArrayList<byte[]>();

    /**
     * 构造一个打印数据
     *
     * @param header:大标题
     * @param header2：小标题
     * @param body：打印参数（组装好参数）；
     * @param foot：固定结尾
     */
    public PrintUtil(String header, String header2, String[] body, String[] foot) {
        list.clear();
        list.add(DataForSendToPrinterPos58.initializePrinter());
        list.add(DataForSendToPrinterPos58.printAndFeedForward(2));
        list.add(DataForSendToPrinterPos58.selectChineseCharModel());
        if (!StringUtil.isEmptyString(header)) {
            byte[] hd1 = StringUtil.str2bytes(header);
            list.add(DataForSendToPrinterPos58.selectAlignment(1));
            list.add(DataForSendToPrinterPos58.selectCharacterSize(1));

            list.add(hd1);
            list.add(DataForSendToPrinterPos58.printAndFeedLine());
            list.add(DataForSendToPrinterPos58.printAndFeedForward(1));

        }
        list.add(DataForSendToPrinterPos58.selectCharacterSize(0));
        if (!StringUtil.isEmptyString(header2)) {
            byte[] hd2 = StringUtil.str2bytes(header2);
            list.add(DataForSendToPrinterPos58.selectAlignment(1));
            list.add(hd2);
            list.add(DataForSendToPrinterPos58.printAndFeedLine());
            list.add(DataForSendToPrinterPos58.printAndFeedForward(1));
        }
        if (!StringUtil.isEmptyArry(body)) {
            list.add(DataForSendToPrinterPos58.printAndFeedLine());
            for (int i=0;i<body.length;i++){
                list.add(DataForSendToPrinterPos58.selectAlignment(0));
                list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
                byte[] bd = StringUtil.str2bytes(body[i]);
                list.add(bd);
                list.add(DataForSendToPrinterPos58.printAndFeedLine());
                list.add(DataForSendToPrinterPos58.printAndFeedForward(1));
            }


        }

        if (!StringUtil.isEmptyArry(foot)) {
            list.add(DataForSendToPrinterPos58.printAndFeedLine());
            for (int i=0;i<foot.length;i++){
                list.add(DataForSendToPrinterPos58.selectAlignment(0));
                list.add(DataForSendToPrinterPos58.selectOrCancelChineseCharUnderLineModel(0));
                byte[] ft = StringUtil.str2bytes(foot[i]);
                list.add(ft);
                list.add(DataForSendToPrinterPos58.printAndFeedLine());
                list.add(DataForSendToPrinterPos58.printAndFeedForward(1));
            }
             list.add(DataForSendToPrinterPos58.printAndFeedLine());
             list.add(DataForSendToPrinterPos58.printAndFeedLine());

        }



    }

    /**
     * 获取组装好参数
     * @return
     */
    public ArrayList<byte[]> getData() {
        return list;
    }

}
