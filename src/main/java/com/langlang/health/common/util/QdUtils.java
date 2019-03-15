package com.langlang.health.common.util;

import java.io.IOException;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.langlang.health.mobile.entity.HealthAppraiseData;
import com.langlang.health.mobile.entity.HealthDangerfilterData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Component
public class QdUtils {
	
    public static String url;

	@Value("${qingdao.url}")
	public void setUrl(String url) {
		QdUtils.url = url;
	}
	
	/**
	 *1.	上传运动步数接口
	 * @Method SportStepRecordsHandleProc   
	 * @Description 
	 * @param uid
	 * @param weight
	 * @param sportDate
	 * @param sportStr
	 * @return
	 * @throws Exception String
	 */
	public static String SportStepRecordsHandleProc(String uid, String weight, String sportDate, String sportStr) throws Exception{
		JSONObject postData = new JSONObject();
		postData.put("uid", uid);
		postData.put("weight", weight);
		postData.put("sportDate", sportDate);
		postData.put("sportStr", sportStr);
		System.out.println(postData.toString());
		String result = webTransInvoke("SportStepRecordsHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}
	/**
	 * 2.	获取运动处方和饮食处方接口
	 * @Method JSportsAndDietsHandleProc   
	 * @Description 
	 * @param uid
	 * @param height
	 * @param weight
	 * @param age
	 * @param sextype
	 * @param dieases
	 * @return
	 * @throws Exception boolean
	 */
	public static String JSportsAndDietsHandleProc(String uid, String height, String weight, Integer age, String sextype, String dieases) throws Exception{
		JSONObject postData = new JSONObject();
		postData.put("uid", uid);
		postData.put("height", height);
		postData.put("weight", weight);
		postData.put("age", age);
		postData.put("sextype", sextype);
		postData.put("dieases", dieases);
		System.out.println(postData.toString());
		String result = webTransInvoke("JSportsAndDietsHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}
	
	/**
	 *3.	上传医生接口
	 * @Method JDoctorHandleProc   
	 * @Description 
	 * @param orgname
	 * @param doctorname
	 * @param teamname
	 * @param tel
	 * @param doctorzz
	 * @param doctorzc
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws Exception String
	 */
	public static boolean JDoctorHandleProc(String orgname, String doctorname, String teamname, String tel, String doctorzz, String doctorzc, String loginname, String loginpass) throws Exception{
		JSONObject postData = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject doctor = new JSONObject();
		doctor.put("orgname", orgname);
		doctor.put("doctorname", doctorname);
		doctor.put("teamname", teamname);
		doctor.put("tel", tel);
		doctor.put("doctorzz", doctorzz);
		doctor.put("doctorzc", doctorzc);
		doctor.put("loginname", loginname);
		doctor.put("loginpass", loginpass);
		array.add(doctor);
		postData.put("doctors", array);
		System.out.println(postData.toString());
		return parsResult(webTransInvoke("JDoctorHandleProc", postData.toString()));
	}
	/**
	 * 4.	上传健康档案接口
	 * @Method JArchiveHandleProc   
	 * @Description 
	 * @param orgname
	 * @param doctorname
	 * @param teamname
	 * @param uid
	 * @param tel
	 * @param fullname
	 * @param idnumber
	 * @param sex
	 * @param birthday
	 * @param height
	 * @param weight
	 * @return
	 * @throws Exception boolean
	 */
	public static String JArchiveHandleProc(String orgname, String doctorname, String teamname, String uid, String tel, String fullname, String idnumber, String sex, String birthday, String height, String weight) throws Exception{
		JSONObject postData = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject archives = new JSONObject();
		archives.put("orgname", orgname);
		archives.put("doctorname", doctorname);
		archives.put("teamname", teamname);
		archives.put("uid", uid);
		archives.put("tel", tel);
		archives.put("fullname", fullname);
		archives.put("idnumber", idnumber);
		archives.put("builddate", DateUtil.format(new Date()));
		archives.put("height", height);
		archives.put("weight", weight);
		archives.put("sextype", sex);
		archives.put("age", "");
		archives.put("borndate", birthday);
		JSONObject dieases = new JSONObject();
		dieases.put("name", "高血压");
		dieases.put("year", "2016");
		JSONArray dieasesArray = new JSONArray();
		dieasesArray.add(dieases);
		archives.put("dieases", new JSONArray());
		
		JSONObject homedieases = new JSONObject();
		homedieases.put("name", "高血压");
		homedieases.put("relation", "父亲");
		JSONArray archivesArray = new JSONArray();
		archivesArray.add(homedieases);
		archives.put("homedieases", new JSONArray());
		array.add(archives);
		postData.put("archives",  array);
		System.out.println(postData.toString());
		String result = webTransInvoke("JArchiveHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}

	/**
	 * 6.	获取四大报告列表(筛查、分层、危险因素、中医体质辨识)接口
	 * @Method JGetReportHandleProc   
	 * @Description 
	 * @param uid
	 * @param reporttype
	 * @return
	 * @throws Exception String
	 */
	public static String JGetReportListHandleProc(String uid, ReportType reportType) throws Exception{
		JSONObject postData = new JSONObject();
		postData.put("uid", uid);
		postData.put("reporttype", reportType.name());
		System.out.println(postData.toString());
		String result = webTransInvoke("JGetReportListHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}
	/**
	 * 7.	获取四大报告 (筛查、分层、危险因素、中医体质辨识) +跟踪随访接口
	 * @Method JGetReportHandleProc   
	 * @Description 
	 * @param uid
	 * @param id
	 * @param reportType
	 * @return
	 * @throws Exception String
	 */
	public static String JGetReportHandleProc(String uid, String id, ReportType reportType) throws Exception{
		JSONObject postData = new JSONObject();
		postData.put("uid", uid);
		postData.put("id", id);
		postData.put("reporttype", reportType.name());
		String result = webTransInvoke("JGetReportHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}
	/**
	 * 异常事件推送
	 * @Method JGetAbnormalMsgHandleProc   
	 * @Description 
	 * @param list
	 * @return
	 * @throws Exception String
	 */
	/*public static String JGetAbnormalMsgHandleProc(List<AbnormalMsg> list) throws Exception{
		JSONObject postData = new JSONObject();
		postData.put("data", list);
		System.out.println(postData.toString());
		String result = webTransInvoke("JGetAbnormalMsgHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}*/
	/**
	 * 获取健康管理计划
	 * @Method JGetPlanInfoHandleProc   
	 * @Description 
	 * @param uid
	 * @return
	 * @throws Exception String
	 */
	public static String JGetPlanInfoHandleProc(String uid) throws Exception{
		JSONObject postData = new JSONObject();
		postData.put("uid", uid);
		System.out.println(postData.toString());
		String result = webTransInvoke("JGetPlanInfoHandleProc", postData.toString());
		if(parsResult(result)){
			return result;
		}
		return null;
	}
	/**
	 * 危险评估答题表
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String JAppraiseReportHandleProc(HealthAppraiseData healthAppraiseData) throws Exception{
		JSONObject postData = JSONObject.fromObject(healthAppraiseData);
		System.out.println(postData.toString());
		String result = webTransInvoke("JAppraiseReportHandleProc", postData.toString());
		return result;
	}
	/**
	 * 慢病答题表
	 * @param healthDangerfilterData
	 * @return
	 * @throws Exception
	 */
	public static String CommonDangerFilterHandleProc(HealthDangerfilterData healthDangerfilterData ) throws Exception{
		JSONObject postData = JSONObject.fromObject(healthDangerfilterData);
		postData.put("archiveid", postData.get("appid"));
		System.out.println(postData.toString());
		String result = webTransInvoke("CommonDangerFilterHandleProc", postData.toString());
		if(parsResultNew(result)){
			return result;
		}
		return null;
	}
	/**
	 * 小程序危险评估接口
	 * @param healthDangerfilterData
	 * @return
	 * @throws Exception
	 */
	public static String JAppraiseReportBeforeHandleProc(HealthAppraiseData healthAppraiseData) throws Exception{
		JSONObject postData = JSONObject.fromObject(healthAppraiseData);
		postData.discard("ID");
		String string = postData.toString();
		string = string.replace("appraiseid", "id");
		System.out.println(string);
		String result = webTransInvoke("JAppraiseReport_beforeHandleProc", string);
		if(parsResult(result)){
			return result;
		}
		return null;
	}
	/**
	 * post 请求
	 * @Method webTransInvoke   
	 * @Description 
	 * @param type
	 * @param jsonData
	 * @return String
	 */
	private static String webTransInvoke(String type, String jsonData){
		String result = null;
        String url = QdUtils.url + type;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建httppost.
            HttpPost httpPost = new HttpPost(url);
            //设置json数据对象
            StringEntity postentity = new StringEntity(jsonData,"UTF-8");
            postentity.setContentType("application/json");
            httpPost.setEntity(postentity);
            httpPost.setHeader("Accept","aplication/json");
            httpPost.addHeader("Content-Type","application/json;charset=UTF-8");
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                // 获取响应实体
                HttpEntity responseentity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (responseentity != null) {
                    // 打印响应内容长度
                    result = EntityUtils.toString(responseentity);
                    // 打印响应内容
                    System.out.println("应答数据内容: " + result);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
	
	private static boolean parsResult(String text){
		if(text!=null && !"".equals(text)){
			JSONObject result = JSONObject.fromObject(text);
			if("true".equals(result.getString("success"))){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	private static boolean parsResultNew(String text){
		if(text!=null && !"".equals(text)){
			JSONObject result = JSONObject.fromObject(text);
			if("true".equals(result.getString("flag"))){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	/*public static String getDiet(String datadiet){
		if(!UtilStr.isEmpty(datadiet)){
			JSONArray diet = JSONArray.fromObject(datadiet);
			for(int i=0; i<diet.size(); i++){
				String foodname = diet.getJSONObject(i).getString("foodname");
				double foodweight = diet.getJSONObject(i).getDouble("foodweight");
				String unit = "";
				foodname = foodname.replaceAll("（", "(").replaceAll("）", ")");
				if(foodname.indexOf("(")>-1){
					unit = foodname.substring(foodname.indexOf("("), foodname.length());
					diet.getJSONObject(i).put("foodname", foodname.substring(0, foodname.indexOf("(")));
					diet.getJSONObject(i).put("foodweight", foodweight + unit);
				}
				if(foodname.indexOf("谷类")>-1){
					diet.getJSONObject(i).put("listOptions", SysDietCache.getListOptions(SysDietCache.grainList, foodweight, unit));
				}else if(foodname.indexOf("蔬菜")>-1 || foodname.indexOf("水果")>-1 ){
					diet.getJSONObject(i).put("listOptions", SysDietCache.getListOptions(SysDietCache.kohlrabiList, foodweight, unit));
				}else if(foodname.indexOf("坚果")>-1 || foodname.indexOf("食用油")>-1 ){
					diet.getJSONObject(i).put("listOptions", SysDietCache.getListOptions(SysDietCache.fatList, foodweight, unit));
				}else if(foodname.indexOf("瘦猪肉")>-1 || foodname.indexOf("鱼虾")>-1 ){
					diet.getJSONObject(i).put("listOptions", SysDietCache.getListOptions(SysDietCache.meatList, foodweight, unit));
				}
			}
			return diet.toString();
		}
		return null;
	}*/
	
	
	public static void main(String[] args){
		try {
			System.out.println("返回结果:	");
//			System.out.println(QdUtils.JDoctorHandleProc("社康中心", "D1804161548E941", "D1804161548E941", "18653250728", "全科", "主治医师", "D1804161548E941", "1"));
//			System.out.println(QdUtils.JArchiveHandleProc("社康中心", "D1804161548E941", "D1804161548E941", "jingjuef7te1", "13040859064", "张红亮", "jingjuef7te1", "男", "1987-11-04", "170", "62"));
//			System.out.println(QdUtils.JSportsAndDietsHandleProc("langlang9l2n", "175", "80", 28, "男", ""));
			
//			String str = "1,7,38,68,30,90,86,64,27,76,70,65,22,26,55,34,29,25,78,30,83,64,65,62,52,72,53,58,15,43,92,75,97,86,78,18,64,49,33,71,35,29,42,33,16,77,46,40,78,82,74,52,28,76,25,48,18,60,27,27,78,58,77,33,42,90,36,26,25,46,68,91,30,57,63,59,88,48,30,28,84,34,47,42,82,51,15,23,53,40,75,13,70,93,53,12,50,44,56,71,72,87,13,68,16,44,79,24,31,35,73,67,39,14,81,53,45,10,26,35,49,40,55,82,34,29,55,74,15,32,86,35,91,58,81,41,42,21,63,42,77,44,15,74,88,93,21,38,35,88,19,31,92,36,92,26,24,62,21,70,12,52,88,43,99,90,60,11,78,27,71,59,65,68,26,38,68,35,92,67,51,56,62,66,34,58,57,96,49,29,50,98,75,98,85,96,40,44,45,26,96,86,45,99,76,98,22,82,70,69,18,13,95,37,42,72,83,32,55,48,45,14,53,78,94,84,50,97,54,13,34,10,85,21,94,94,11,65,97,96,32,50,44,86,44,68,34,19,60,20,47,16,46,79,54,77,89,59,86,69,45,34,73,68,81,13,72,85,88,5,65,25,64,98,96,27,91,50,57,93,30,18,19,51,20,14,71,21,62,83,99,40,90,90,75,93,47,12,81,89,12,40,28,91,94,79,44,39,29,79,34,94,55,59,97,44,90,99,99,57,46,41,15,44,48,50,14,26,44,72,99,87,65,42,59,40,56,12,72,37,98,79,44,16,62,58,46,39,47,94,35,49,92,16,39,50,13,77,55,45,61,75,77,18,46,13,90,44,28,50,90,75,50,89,61,27,88,49,4,17,71,48,69,75,94,49,23,91,63,74,75,62,52,22,60,83,43,55,88,83,20,29,55,93,31,29,67,22,18,78,13,68,19,47,22,48,88,25,31,65,34,40,18,46,82,73,59,66,53,70,84,74,63,34,60,36,56,36,17,90,50,30,74,52,98,80,52,89,36,18,28,88,36,18,92,19,47,70,38,41,79,40,31,30,46,88,75,84,35,89,88,26,28,19,60,77,21,18,38,98,78,36,41,65,91,46,85,59,47,76,12,11,63,60,55,48,23,36,69,13,16,35,56,23,48,82,73,24,83,60,61,81,89,58,69,96,62,54,62,71,59,30,76,27,26,33,94,58,13,94,77,14,75,67,46,48,96,72,36,79,96,33,12,39,71,89,9,7,61,45,82,21,90,61,12,51,50,17,10,45,80,55,94,90,57,75,37,42,79,28,78,25,54,71,27,69,19,62,24,18,59,38,42,14,68,12,79,35,13,56,84,21,81,62,93,25,61,82,30,53,46,1,1,0,0,0,0,0,0,0,64,56,68,21,72,40,42,62,37,95,61,34,94,16,72,48,60,87,41,82,52,40,75,30,39,22,94,29,60,32,76,35,54,56,74,28,57,92,35,31,75,94,34,88,31,10,94,92,52,48,65,80,93,48,11,17,11,57,28,18,90,92,43,43,56,75,72,49,80,48,98,46,52,48,70,57,37,60,83,87,10,25,56,62,65,54,21,22,93,48,22,96,77,16,44,61,52,12,53,62,82,18,34,1,1,1,1,1,1,1";
//			System.out.println(QdUtils.SportStepRecordsHandleProc("jingjuesa31", "80", "2018-08-15", str));
			
//			String result = QdUtils.JSportsAndDietsHandleProc("jingjuesa31", "175", "80", 88, "男", "糖尿病");
//			JSONObject jo = JSONObject.fromObject(result);
//			System.out.println(getDiet(jo.getString("datadiet")));
			
//			String result = QdUtils.JGetReportHandleProc("13761538626", ReportType.appraise);//-危险因素评估
//			String result = QdUtils.JGetReportHandleProc("13761538626", ReportType.cnappraise);//-中医体质辨识
//			String result = QdUtils.JGetReportListHandleProc("13761538626", ReportType.appraise);//-慢病高危筛查
			String result = QdUtils.JGetReportHandleProc("13761538626", "835877", ReportType.appraise);//-疾病危险分层
//			System.out.println(resultObj.getString("createdate"));
//			System.out.println(resultObj.getString("datasport"));
//			String result = QdUtils.JGetPlanInfoHandleProc("13901675417");
			System.out.println(result);
			/*List<AbnormalMsg> list = new ArrayList<AbnormalMsg>();
			String[] user = new String[]{"jingjue99v5","daxiang",};
			for(String uid : user){
				list.add(new AbnormalMsg(uid, "2018-10-16 13:48:00", "1", "心率(140)发生异常"));
			}
			JGetAbnormalMsgHandleProc(list);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	

}
