package com.gsc.programaavisos.util;

import com.gsc.programaavisos.dto.FilterBean;
import org.apache.tomcat.util.http.fileupload.FileItem;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

public class PAUtil {

    public static Date getDateFromFilter(FilterBean filter){
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.YEAR, filter.getFromYear());
        calStart.set(Calendar.MONTH, filter.getFromMonth() - 1);
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        return new Date (calStart.getTimeInMillis());
    }

    public static String getUserStamp(String userName){
        return userName.split("\\|\\|")[0]+"||"+userName.split("\\|\\|")[1];
    }

    public static FileItem getFileItem(String field_name) {
        HashMap<String, FileItem[]> hstFileItems=new HashMap<>();
        return hstFileItems.containsKey(field_name) ? ((FileItem[])hstFileItems.get(field_name))[0] : null;
    }


}
