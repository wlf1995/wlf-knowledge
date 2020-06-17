package com.test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        URL resource = Test.class.getClassLoader().getResource("");
        String path = resource.getPath()+ "签章授权证书模版.xml";

        Test test = new Test();
        List<String> items=new ArrayList<>();
        items.add("公司名称");
        items.add("张三");
        items.add("TDD");
        items.add("187XXXXXXX");
        List<String > data=new ArrayList<>();
        data.add("测试");
        data.add("王立方");
        data.add("涂多多");
        data.add("18272873489");
        test.changeFileText(path,"C:\\Users\\mayn\\Desktop\\签章授权证书模版.doc",items,data);
    }
    /*
     *
     * @Author:Rick
     * filepath 模板文件路径
     * tofilepath 要生成的文件路径
     * items word xml模板文件中的占位符
     * data  word xml文件要替换的数据
     */
    public boolean changeFileText(String filepath, String tofilepath, List<String> items, List<String > data){
        File file = new File(filepath);
        String line=null;
        InputStream is=null;
        FileOutputStream fos=null;
        try{
            is = new FileInputStream(file);
            StringBuffer  sb=new StringBuffer ();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while((line=reader.readLine()) != null){
                sb.append(line);
            }
            String result= String.valueOf(sb);

            for(int i=0;i<items.size();i++){
                /*
                 *
                 * 正则替换文件中的占位符
                 */
                result=result.replaceAll(items.get(i), data.get(i));
            }
            System.out.println(result);
            tofilepath=tofilepath.substring(0,tofilepath.indexOf("."))+".doc";
            File out=new File(tofilepath);
            fos=new FileOutputStream(out);
            fos.write(result.getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }finally{
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
