package com.langlang.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.langlang.health.common.util.QdUtils;
import com.langlang.health.common.util.ReportType;


@RunWith(SpringRunner.class)
@SpringBootTest
public class QdutilTest {

	
	@Test 
	public void testQdutil() {
		System.out.println(QdUtils.url);
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
