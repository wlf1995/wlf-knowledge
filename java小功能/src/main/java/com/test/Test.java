package com.test;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
/*
    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://guozhenglaw.mall.vip.webportal.top/cn/ajax/news_h.jsp?cmd=get&_TOKEN=bcfcff85d059f951b085a786e0160e16";
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("pageno","1");
        map.add("pageSize","600");
        map.add("titleOrder","normal");
        map.add("authorOrder","normal");
        map.add("dateOrder","normal");
        map.add("topOrder","normal");
        map.add("groupIdSort","-1");
        map.add("levelSort","-2");
        map.add("formSort","-3");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, request, String.class);
        String body = stringResponseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(body);
//        [1,2,3,5,4,6,7,8,9,]
        //法律新闻,热点新闻,典型案例,法律知识,法律政策,律师文苑,客户手册,国征文化,国征动态
        String fenleis[]=new String[]{"法律新闻","热点新闻","典型案例","法律政策","法律知识","律师文苑","客户手册","国征文化","国征动态"};
        JSONArray newsList = jsonObject.getJSONArray("newsList");
        for(int i=0;i<newsList.size();i++){
            JSONObject news = newsList.getJSONObject(i);
            JSONArray jsonArray = news.getJSONArray("groupIds");
            List<String >fen=new ArrayList<>();
            for(int j=0;j<jsonArray.size();j++){
                Integer integer = jsonArray.getInteger(j);
                fen.add(fenleis[integer]);
            }
            news.put("groupNames",fen);
        }
        System.out.println(newsList);
        for(int i=0;i<newsList.size();i++){
            JSONObject news = newsList.getJSONObject(i);
            String url1 = "https://guozhenglaw.mall.vip.webportal.top/manage_v2/newsEdit.jsp?fromManage=true&id="+news.getString("id")+"&popupID=89";
            HttpHeaders headers1 = new HttpHeaders();
            headers1.set("Cookie", "_cliid=qtwsavfLmr1ypu9c; grayUrl=; loginCacct=guozhenglaw; loginSacct=guozhenglawyer; loginUseSacct=1; _FSESSIONID=8gkaoXRZxJRwU1MN; loginSign=f9pD1_9hf4Fg6Mo00PlhO6XdzBgNvYHqxCuEFUV59KnGjrz4E4FDasDbHXs_1GrA5lHvDXBQCnLgR-A5CWcG5QLbYhu3RXr9gt5O7I4iI-A; _jzmFirstLogin=false; _whereToPortal_=login; _preUrl=; _wafSiteType=2");
            //请求
            HttpEntity<JSONObject> entity = new HttpEntity<>(null, headers1);
            ResponseEntity<String> result = null;
            result = restTemplate.exchange(url1, HttpMethod.GET, entity, String.class);
            String body1 = result.getBody();
            if(news.getString("id").equals("398")){
                System.out.println(body1);
            }
            String substring = body1.substring(body1.indexOf("pageData")+10, body1.indexOf("flag = ")-1);
            System.out.println(substring);
            news.put("pageData",substring);
        }
        File file=new File("C:\\Users\\mayn\\Desktop\\文章导出.txt");
        FileWriter fileWriter=new FileWriter(file);
        fileWriter.write(newsList.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }*/
}
