package smartenforce.aty.personrepay;



import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParamsUtil {
    private static ParamsUtil paramsUtil=new ParamsUtil();
    private ParamsUtil(){}

    public static ParamsUtil getInstance(){
        return paramsUtil;
    }

    public String toXmlString(LinkedHashMap<String,String> map){
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value=map.get(key);
            sb.append("<"+key+">");
            sb.append(value);
            sb.append("</"+key+">");
        }

        sb.append("</xml>");

        return sb.toString();

    }

    public String genPackageSign(LinkedHashMap<String,String> map) {
        StringBuilder sb = new StringBuilder();
        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value=map.get(key);
            sb.append(key);
            sb.append('=');
            sb.append(value);
            sb.append('&');
        }

        sb.append("key=");
        sb.append(Constants.API_KEY);

        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }


    public String genAppSign(LinkedHashMap<String,String> map) {
        StringBuilder sb = new StringBuilder();

        for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value=map.get(key);
            sb.append(key);
            sb.append('=');
            sb.append(value);
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);

        sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion",e.toString());
        }
        return null;

    }


}
