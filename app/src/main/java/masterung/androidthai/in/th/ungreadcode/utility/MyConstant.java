package masterung.androidthai.in.th.ungreadcode.utility;

/**
 * Created by masterung on 22/3/2018 AD.
 */

public class MyConstant {

    //About FTP
    private String hostString = "ftp.swiftcodingthai.com";
    private int portAnInt = 21;
    private String userString = "cent@swiftcodingthai.com";
    private String passwordString = "Abc12345";



    //    About URL
    private String urlEditMessageWhereCode = "http://tscore.ms.ac.th/ms_student/EditChildByCode.php";
    private String urlGetMessageWhereCode = "http://tscore.ms.ac.th/ms_student/getMessageWhereCode.php";
    private String urlEditStatusWhereIDuser = "http://tscore.ms.ac.th/ms_student/EditStatusByUser.php";
    private String urlGetChildWhereIdUser = "http://tscore.ms.ac.th/ms_student/getChildWhereIdUser.php";
    private String urlAddChildString = "http://tscore.ms.ac.th/ms_student/AddChild.php";
    private String urlGetAllUserString = "http://tscore.ms.ac.th/ms_student/getAllUser.php";
    private String urlPostUserString = "http://tscore.ms.ac.th/ms_student/postUser.php";

    //    About Array
    private String[] columnMessageStrings = new String[]{"id", "idUser", "Code",
            "NameChild", "ImageChild", "Status", "DateMessage", "Message"};
    private String[] loginStrings = new String[]{"id", "Name", "User", "Password"};

    public String getHostString() {
        return hostString;
    }

    public int getPortAnInt() {
        return portAnInt;
    }

    public String getUserString() {
        return userString;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public String getUrlEditMessageWhereCode() {
        return urlEditMessageWhereCode;
    }

    public String getUrlGetMessageWhereCode() {
        return urlGetMessageWhereCode;
    }

    public String getUrlEditStatusWhereIDuser() {
        return urlEditStatusWhereIDuser;
    }

    public String[] getColumnMessageStrings() {
        return columnMessageStrings;
    }

    public String getUrlGetChildWhereIdUser() {
        return urlGetChildWhereIdUser;
    }

    public String getUrlAddChildString() {
        return urlAddChildString;
    }

    public String[] getLoginStrings() {
        return loginStrings;
    }

    public String getUrlGetAllUserString() {
        return urlGetAllUserString;
    }

    public String getUrlPostUserString() {
        return urlPostUserString;
    }
}   // Main Class
