package com.perasia.picgirls.utils;


import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatchImgUtil {
    private static final String TAG = CatchImgUtil.class.getSimpleName();

    public static List<String> getImgs(String baseUrl) {
        InputStreamReader reader = null;
        BufferedReader in = null;
        try {
            URL url = new URL(baseUrl);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "MSIE");
            connection.setConnectTimeout(10000);
            reader = new InputStreamReader(connection.getInputStream(), "utf-8");
            in = new BufferedReader(reader);
            String line = null; // line content
            int lineFlag = 0; //tag :has no data
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                lineFlag++;
            }
            if (lineFlag >= 1) {
                return getTextImageSrc(content.toString());
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static List<String> getTextImageSrc(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }

        String regex = "<\\s*[I|i][m|M][g|G]\\s+([^>]*)\\s*>";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(text);
        List<String> list = new ArrayList<>();
        while (ma.find()) { //if has pictures
            list.add(ma.group());
        }
        if (list.size() != 0) {// has pictures
            List<String> imgSrcList = null;
            String a = null;
            for (String s : list) {
                ma = Pattern.compile("[s|S][R|r][c|C]=[\"|'](.*?)[\"|']").matcher(s);
                if (ma.find()) {
                    a = ma.group();
                    if (imgSrcList == null)
                        imgSrcList = new ArrayList<String>();
                } else {
                    a = null;
                }
                if (a != null) {
                    a = a.replaceAll("[s|S][R|r][c|C]=[\"|']", "").replaceAll("[\"|']", "");
                    if (!TextUtils.isEmpty(a)) {
                        imgSrcList.add(a);
                    }
                }
            }
            if (imgSrcList != null && imgSrcList.size() != 0)
                return imgSrcList;
            else
                return null;
        } else {
            return null;
        }
    }

    public static void downloadPic(String downloadUrl, String fileName, String savePath) {

        try {
            URL url = new URL(downloadUrl);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            byte[] bytes = new byte[1024];
            int len;
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }

            OutputStream os = new FileOutputStream(sf.getPath() + File.separator + fileName);

            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }

            os.close();

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
