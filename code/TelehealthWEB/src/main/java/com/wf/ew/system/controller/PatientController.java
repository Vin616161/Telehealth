package com.wf.ew.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.*;
import com.wf.ew.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import static com.wf.ew.system.controller.MainController.strToDate;

@Api(value = "患者相关的接口", tags = "patient")
@RestController
@RequestMapping("${api.version}/patient")
public class PatientController extends BaseController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatFileService patFileService;
    @Autowired
    private UserService userService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private TestIndexService testIndexService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DepartmentQuestionsService departmentQuestionsService;

    @ApiOperation(value = "查询患者基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patId", value = "患者id", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/baseInfo/{id}")
    public PatientWithUser getPatInfo(@PathVariable("id")Integer patId) {
        PatientWithUser patientWithUser = new PatientWithUser();
        User user = userService.selectById(patId);
        Patient patient = patientService.getByPatId(patId);
        //常去药房
        String pharmacy = pharmacyService.selectById(patient.getPharmacyId()).getName();
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(user.getBirthday());
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            }
        }
        patientWithUser.setRealName(user.getTrueName());
        patientWithUser.setAge(age);
        patientWithUser.setSex(user.getSex());
        patientWithUser.setPhone(user.getPhone());
        patientWithUser.setAllergicHistory(patient.getAllergicHistory());
        patientWithUser.setMedicare(patient.getMedicare());
        patientWithUser.setPharmacy(pharmacy);
        return patientWithUser;
    }

    @ResponseBody
    @ApiOperation(value = "查询所有病种/科室")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping(value = "/queryDiseases")
    public JsonResult getAllDiseases() {
        EntityWrapper<Department> wrapper = new EntityWrapper<>();
        List<Department> departments = departmentService.selectList(wrapper);
        return JsonResult.ok("success").put("diseaseList", departments);
    }

    @ResponseBody
    @ApiOperation(value = "健康问答，获取问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "科室/病种id", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/queryQSOfDiseases")
    public JsonResult getQSOfDiseases(Integer departmentId) {
        EntityWrapper<DepartmentQuestions> wrapper = new EntityWrapper<>();
        wrapper.eq("department_id", departmentId);
        wrapper.orderBy("question_no", true);
        List<DepartmentQuestions> questions = departmentQuestionsService.selectList(wrapper);
        return JsonResult.ok("success").put("QAList", questions);
    }

    @ApiOperation(value = "网页端查询患者所有检测指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "patId", value = "患者id", dataType = "Integer",required = true, paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(value = "/testIndex", method = RequestMethod.GET)
    public PageResult<TestIndex> getTIForWeb(Integer page, Integer limit, Integer patId) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<TestIndex> testIndexPage = new Page<>(page, limit);
        EntityWrapper<TestIndex> wrapper = new EntityWrapper<>();
        wrapper.eq("pat_id", patId); //相当于where条件
        wrapper.orderBy("record_time", false);
        testIndexService.selectPage(testIndexPage, wrapper);
        List<TestIndex> testIndexList = testIndexPage.getRecords();
        return new PageResult<>(testIndexList, testIndexPage.getTotal());
    }

    @ApiOperation(value = "app端查询患者所有检测指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patId", value = "患者id", dataType = "Integer",required = true, paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/getAllTIForAPP")
    public JsonResult getAllTIForAPP(Integer page, Integer limit, Integer patId) {
        EntityWrapper<TestIndex> wrapper = new EntityWrapper<>();
        wrapper.orderBy("record_time", false);
        List<TestIndex> list = testIndexService.selectList(wrapper);
        return JsonResult.ok("success").put("TIList", list);
    }

    @ApiOperation(value = "app端查询患者最新一条检测指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patId", value = "患者id", dataType = "Integer",required = true, paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/getLatestTIForAPP")
    public JsonResult getLatestTIForAPP(Integer patId) {
        //返回用户最后一次体检信息列表，有可能最新一条只有血压一个项目，其他项目为空，
        // 那就继续往下查询其他项目不为空的和最新一条拼接成一条完整的
        EntityWrapper<TestIndex> wrapper = new EntityWrapper<>();
        wrapper.orderBy("record_time", false);
        List<TestIndex> list = testIndexService.selectList(wrapper);
        TestIndex testIndex = list.get(0);
        for (int i = 0; i < list.size(); i++){
            if (testIndex.getBloodPressure() == null ){
                testIndex.setBloodPressure(list.get(i).getBloodPressure());
            }
            if (testIndex.getBloodOxygen() == null){
                testIndex.setBloodOxygen(list.get(i).getBloodOxygen());
            }
            if (testIndex.getHeartRate() == null){
                testIndex.setHeartRate(list.get(i).getHeartRate());
            }
            if (String.valueOf(testIndex.getTemperature()) == null){
                testIndex.setTemperature(list.get(i).getTemperature());
            }
            if (String.valueOf(testIndex.getHeight()) == null){
                testIndex.setHeight(list.get(i).getHeight());
            }
            if (String.valueOf(testIndex.getWeight()) == null){
                testIndex.setWeight(list.get(i).getWeight());
            }
        }
        return JsonResult.ok("success").put("TI", testIndex);
    }

    @ResponseBody
    @ApiOperation(value = "app端上传患者体检报告/检测指标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patId", value = "患者id", dataType = "Integer",required = true, paramType = "form"),
            @ApiImplicitParam(name = "height", value = "身高", dataType = "String",required = true, paramType = "form"),
            @ApiImplicitParam(name = "weight", value = "体重", dataType = "String",required = true, paramType = "form"),
            @ApiImplicitParam(name = "temperature", value = "体温", dataType = "String",required = true, paramType = "form"),
            @ApiImplicitParam(name = "bloodPressure", value = "血压", dataType = "String",required = true, paramType = "form"),
            @ApiImplicitParam(name = "bloodOxygen", value = "血氧", dataType = "Integer",required = true, paramType = "form"),
            @ApiImplicitParam(name = "heartRate", value = "心率", dataType = "Integer",required = true, paramType = "form"),
            @ApiImplicitParam(name = "recordTime", value = "上传时间", dataType = "String",required = true, paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uploadTestIndex")
    public JsonResult updateIndex(Integer patId, String height, String weight, String temperature, String bloodPressure,
                                  Integer bloodOxygen, Integer heartRate, String recordTime, HttpServletRequest servletRequest){
        TestIndex testIndex = new TestIndex();
        testIndex.setPatId(patId);
        testIndex.setHeight(Double.valueOf(height));
        testIndex.setWeight(Double.valueOf(weight));
        testIndex.setTemperature(Double.valueOf(temperature));
        testIndex.setBloodPressure(bloodPressure);
        testIndex.setBloodOxygen(bloodOxygen);
        testIndex.setHeartRate(heartRate);
        testIndex.setRecordTime(strToDate(recordTime));
        if (testIndexService.insert(testIndex)){
            return JsonResult.ok("上传成功");
        }else {
            return JsonResult.error("上传失败");
        }
    }

    @ApiOperation(value = "保存用户上传的图片文件")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "patId", value = "患者id", required = true, dataType = "Integer", paramType = "form"),
//            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
//    })
    @RequestMapping(value = "/saveFile/{id}/{access_token}",method = RequestMethod.POST)
    public Object addFile(MultipartFile file, HttpServletRequest request)throws Exception {

        String prefix="";
        String dateStr="";
        //保存上传
        OutputStream out = null;
        InputStream fileInput=null;
        try {
            if(file!=null){
                String originalName = file.getOriginalFilename();
                prefix=originalName.substring(originalName.lastIndexOf(".")+1);
                dateStr = new Date().toString();
                String filepath = request.getServletContext().getRealPath("/static")  + dateStr + "." + prefix;
                filepath = filepath.replace("\\", "/");
                File files=new File(filepath);
                //打印查看上传路径
                System.out.println(filepath);
                if(!files.getParentFile().exists()){
                    files.getParentFile().mkdirs();
                }
                file.transferTo(files);
            }
        }catch (Exception e){
        }finally{
            try {
                if(out!=null){
                    out.close();
                }
                if(fileInput!=null){
                    fileInput.close();
                }
            } catch (IOException e) {
            }
        }

        Map<String,Object> map2=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("data",map2);
        map2.put("src","../../../static" + dateStr + "." + prefix);
        return map;
    }
}
