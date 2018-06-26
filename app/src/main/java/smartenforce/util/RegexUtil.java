package smartenforce.util;


public class RegexUtil {

    // 判断是否符合身份证号码的规范
    public static boolean isIDCard(String IDCard) {
        if (IDCard != null) {
            String IDCardRegex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
            return IDCard.matches(IDCardRegex);
        }
        return false;
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "[1]\\d{10}";
        if (StringUtil.isEmptyString(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

}
