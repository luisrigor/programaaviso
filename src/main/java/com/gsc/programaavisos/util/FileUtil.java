package com.gsc.programaavisos.util;

import com.sc.commons.utils.ArrayTasks;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

public class FileUtil {

//    private ActionRequest request;
//    private Hashtable<String, Object> hstParameters;
//    private Hashtable<String, FileItem[]> hstFileItems;
//
//    private void processFormField(FileItem item) throws UnsupportedEncodingException {
//        FileItem
//        String field_name = item.getFieldName();
//        String characterEncoding = this.request.getCharacterEncoding() == null ? "" : this.request.getCharacterEncoding();
//        String value = "";
//        if (characterEncoding.equalsIgnoreCase("")) {
//            value = item.getString();
//        } else {
//            value = item.getString(this.request.getCharacterEncoding());
//        }
//
//        Object obj = this.hstParameters.get(field_name);
//        if (obj == null) {
//            this.hstParameters.put(field_name, value);
//        } else {
//            String[] old_value_array;
//            if (obj instanceof String) {
//                old_value_array = new String[]{(String)obj, value};
//                this.hstParameters.put(field_name, old_value_array);
//            } else if (obj instanceof String[]) {
//                old_value_array = (String[])((String[])obj);
//                String[] value_array = new String[old_value_array.length + 1];
//                System.arraycopy(old_value_array, 0, value_array, 0, old_value_array.length);
//                value_array[old_value_array.length] = value;
//                this.hstParameters.put(field_name, value_array);
//            }
//        }
//
//    }

//    private void processUploadedFile(FileItem oFileItem) {
//        if (oFileItem.getSize() > 0L) {
//            String field_name = oFileItem.getFieldName();
//            Object obj = this.hstParameters.get(field_name);
//            if (obj == null) {
//                this.hstParameters.put(field_name, oFileItem.getName());
//                this.hstFileItems.put(field_name, new FileItem[]{oFileItem});
//            } else {
//                String[] old_value_array;
//                if (obj instanceof String) {
//                    old_value_array = new String[]{(String)obj, oFileItem.getName()};
//                    this.hstParameters.put(field_name, old_value_array);
//                    FileItem[] oldFileItem = (FileItem[])this.hstFileItems.get(field_name);
//                    this.hstFileItems.put(field_name, new FileItem[]{oldFileItem[0], oFileItem});
//                } else if (obj instanceof String[]) {
//                    old_value_array = (String[])((String[])obj);
//                    String[] value_array = new String[old_value_array.length + 1];
//                    System.arraycopy(old_value_array, 0, value_array, 0, old_value_array.length);
//                    value_array[old_value_array.length] = oFileItem.getName();
//                    this.hstParameters.put(field_name, value_array);
//                    FileItem[] oldFileItem = (FileItem[])this.hstFileItems.get(field_name);
//                    oldFileItem = (FileItem[])((FileItem[]) ArrayTasks.Expand(oldFileItem, 1));
//                    oldFileItem[oldFileItem.length - 1] = oFileItem;
//                    this.hstFileItems.put(field_name, oldFileItem);
//                }
//            }
//        }
//
//    }
}
