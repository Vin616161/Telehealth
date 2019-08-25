package com.wf.ew.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.exception.BusinessException;
import com.wf.ew.system.model.*;
import com.wf.ew.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@Api(value = "医生相关的接口", tags = "doctor")
@RestController
@RequestMapping("${api.version}/doctor")
public class DoctorController extends BaseController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorTimeService doctorTimeService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ChatRecordService chatRecordService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "web端查询医生的时间", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(value = "/queryTime", method = RequestMethod.GET)
    public PageResult<DoctorTime> getTime(Integer page, Integer limit, HttpServletRequest request) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<DoctorTime> doctorTimePage = new Page<>(page, limit);
        EntityWrapper<DoctorTime> wrapper = new EntityWrapper<>();
        wrapper.eq("doc_id", getLoginUserId(request)); //相当于where条件
        wrapper.orderBy("date", true);
        doctorTimeService.selectPage(doctorTimePage, wrapper);
        List<DoctorTime> timeList = doctorTimePage.getRecords();
        return new PageResult<>(timeList, doctorTimePage.getTotal());
    }

    @ApiOperation(value = "web端查询医生已被预约情况", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(value = "/queryAppoint", method = RequestMethod.GET)
    public PageResult<AppointWithPatInfo> getAppoint(Integer page, Integer limit, Integer type, HttpServletRequest request) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Appointment> appointmentPage = new Page<>(page, limit);
        EntityWrapper<Appointment> wrapper = new EntityWrapper<>();
        wrapper.eq("doc_id", getLoginUserId(request)); //相当于where条件
        wrapper.eq("type", type);
        wrapper.orderBy("apmt_time", true);
        appointmentService.selectPage(appointmentPage, wrapper);
        List<Appointment> appointmentList = appointmentPage.getRecords();
        // 关联查询user
        List<Integer> patIds = new ArrayList<>();
        for (Appointment one : appointmentList) {
            patIds.add(one.getPatId());
        }
        List<User> users = userService.selectList(new EntityWrapper().in("user_id", patIds));//查找出所有预约的病人信息
        List<AppointWithPatInfo> patInfos = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        for (int i = 0; i < appointmentList.size(); i++) {
            AppointWithPatInfo patInfo = new AppointWithPatInfo();
            patInfo.setId(appointmentList.get(i).getId());
            patInfo.setName(users.get(i).getTrueName());
            patInfo.setSex(users.get(i).getSex());
            cal.setTime(users.get(i).getBirthday());
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            int age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
            patInfo.setPatId(users.get(i).getUserId());
            patInfo.setAge(age);
            patInfo.setApmtTime(appointmentList.get(i).getApmtTime());
            patInfos.add(i, patInfo);
        }
        return new PageResult<>(patInfos, appointmentPage.getTotal());
    }

    @ApiOperation(value = "web端查询医生个人信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @RequestMapping(value = "/queryDocInfo", method = RequestMethod.GET)
    public DoctorWithUser getDocInfo(HttpServletRequest request) {
        DoctorWithUser doctor = new DoctorWithUser();
//        Doctor doctor1 = doctorService.selectById(getLoginUserId(request));
        User user = userService.selectById(getLoginUserId(request));
        Doctor doctor1 = doctorService.getByDocId(getLoginUserId(request));
        Clinic c = clinicService.selectById(doctor1.getCliId());

        String depart = departmentService.selectById(doctor1.getDepId()).getName();
        doctor.setEmail(user.getEmail());
        doctor.setClinic(c.getName());
        doctor.setAddress(c.getAddress());
        doctor.setDepartment(depart);
        doctor.setName(user.getTrueName());
        doctor.setPhone(user.getPhone());
        doctor.setSex(user.getSex());
        doctor.setUsername(user.getUsername());
        doctor.setIntroduction(doctor1.getIntroduction());
        return doctor;
    }

    @ApiOperation(value = "web端修改医生个人信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "nickName", value = "姓名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "clinic", value = "诊所名称", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "address", value = "诊所地址", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "department", value = "科室", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "电话", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "introduction", value = "个人简介", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PutMapping(value = "/updateDocInfo")
    public JsonResult updateDocInfo(String username, String sex, String nickName, String clinic, String address, String department,
                                    String phone, String email, String introduction, HttpServletRequest request) {
        if (department == null) {
            return JsonResult.error("科室不能为空！");
        }
        if (clinic == null) {
            return JsonResult.error("诊所不能为空！");
        }
        if (sex == null) {
            return JsonResult.error("性别不能为空！");
        }

        User user = userService.getByUsername(username);
        Integer docId = user.getUserId();
        Doctor doctor = doctorService.getByDocId(docId);
        if (sex != null) {
            user.setSex(sex);
        }
        if (nickName != null) {
            user.setTrueName(nickName);
            doctor.setName(nickName);
        }
        if (clinic != null && address != null) {
            List<Clinic> clinicList = clinicService.selectList(null);
            boolean flag = false;//标识医生单位是否在诊所表内存在
            for (Clinic c : clinicList) {
                if (c.getName().equals(clinic) && c.getAddress().equals(address)) {
                    doctor.setCliId(c.getId());
                    flag = true;
                    break;
                }
            }
            //医生单位在诊所表内不存在，要插入
            if (!flag) {
                Clinic clinic1 = new Clinic();
                clinic1.setAddress(address);
                clinic1.setName(clinic);
                if (!clinicService.insert(clinic1)) {
                    throw new BusinessException("添加诊所失败");
                }
                //更新医生表的诊所id
                Clinic clinic2 = clinicService.getByName(clinic);
                doctor.setCliId(clinic2.getId());
            }

        }
        if (department != null) {
            boolean flag = false;//标识科室是否在科室表内存在
            List<Department> departmentList = departmentService.selectList(null);
            for (Department d : departmentList) {
                if (d.getName().equals(department)) {
                    doctor.setDepId(d.getId());
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Department department1 = new Department();
                department1.setName(department);
                if (!departmentService.insert(department1)) {
                    throw new BusinessException("添加科室失败");
                }
                //更新医生表的科室id
                EntityWrapper<Department> wrapper1 = new EntityWrapper<>();
                wrapper1.eq("name", department);
                department1 = departmentService.selectOne(wrapper1);
                doctor.setCliId(department1.getId());
            }
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (introduction != null) {
            doctor.setIntroduction(introduction);
        }

        if (userService.updateById(user) && doctorService.updateById(doctor)) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }


    @ApiOperation(value = "app端查询医生个人信息", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docId", value = "医生id", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping(value = "/queryDocInfoForApp")
    public JsonResult getDocInfoForApp(Integer docId) {
        User user = userService.selectById(docId);
        Doctor doctor = doctorService.getByDocId(docId);
        String clinic = clinicService.selectById(doctor.getCliId()).getName();
        String depart = departmentService.selectById(doctor.getDepId()).getName();
        EntityWrapper<DoctorTime> timeEntityWrapper = new EntityWrapper<>();
        timeEntityWrapper.eq("doc_id",docId);
        timeEntityWrapper.eq("appointed", 0);
        List<DoctorTime> list = doctorTimeService.selectList(timeEntityWrapper);
        return JsonResult.ok("success")
                .put("name", doctor.getName())
                .put("info", doctor.getIntroduction())
                .put("attending", doctor.getAttending())
                .put("title", doctor.getTitle())
                .put("language", doctor.getLanguage())
                .put("sex", user.getSex())
                .put("clinic",clinic)
                .put("depart",depart)
                .put("time",list);
    }

    @ApiOperation(value = "app端查询全部医生，包含科室/病种信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping(value = "/queryAllDoc")
    public JsonResult getAllDoc(HttpServletRequest request) {
        List<DoctorWithDepart> list = new ArrayList<>();

        Department department = new Department();
        //查出所有医生
        EntityWrapper<Doctor> wrapper = new EntityWrapper<>();
        List<Doctor> doctors = doctorService.selectList(wrapper);
        //遍历医生列表，与科室表进行关联查询
        for (Doctor d : doctors) {
            department = departmentService.selectById(d.getDepId());
            DoctorWithDepart doctorWithDepart = new DoctorWithDepart();
            doctorWithDepart.setDepartName(department.getName());
            doctorWithDepart.setName(d.getName());
            doctorWithDepart.setDocId(d.getDocId());
            list.add(doctorWithDepart);
        }
        return JsonResult.ok("success").put("doctorList", list);
    }

    @ApiOperation(value = "app端根据条件筛选医生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "date", value = "日期", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "departments_id", value = "病种id", dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "预约类型", dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @GetMapping(value = "/queryDocListWithCondition")
    public PageResult<Doctor> getDocsVsCondition(Integer page, Integer limit, String departmentsId, String date,Integer type ) {
        //先检索时间
        EntityWrapper<DoctorTime> timeEntityWrapper = new EntityWrapper<>();
        if(date!=null&& !date.trim().isEmpty()){
            timeEntityWrapper.eq("date", date);
        }
        //1为线上，2为线下
        timeEntityWrapper.eq("type", type);
        //是否被预约
        timeEntityWrapper.eq("appointed", 0);
        List<DoctorTime> timeList = doctorTimeService.selectList(timeEntityWrapper);
        Set<Integer> docIds = new HashSet<>();
        for (DoctorTime dt : timeList) {
            docIds.add(dt.getDocId());
        }
        //此处wrapper的逻辑是如果集合为空就跳过该限制条件，要使条件生效 要添加一个不存在的id
        if(docIds.size()==0){
            docIds.add(-1);
        }
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<Doctor> doctorPage = new Page<>(page, limit);
        EntityWrapper<Doctor> wrapper = new EntityWrapper<>();
        //根据病种筛选
        if (departmentsId != null && !departmentsId.trim().isEmpty()) {
            wrapper.eq("dep_id", departmentsId);
        }
        wrapper.in("doc_id",docIds);
        doctorService.selectPage(doctorPage, wrapper);
        List<Doctor> userList = doctorPage.getRecords();
        return new PageResult<>(userList, doctorPage.getTotal());
    }

    @ApiOperation(value = "app端线上预约医生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departId", value = "病种/科室id", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "date", value = "精确到天的日期", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/appointOnline")
    public JsonResult appointOnline(Integer departId, String date, HttpServletRequest request) {
        List<DoctorWithTimeAndUser> list = new ArrayList<>();
        Doctor doctor = new Doctor();

        //将string型的日期转化为sql的Date型
        Date date1 = new java.sql.Date(MainController.strToDate(date).getTime());
        EntityWrapper<DoctorTime> wrapper = new EntityWrapper<>();
        EntityWrapper<DoctorTime> wrapper1 = new EntityWrapper<>();
        wrapper1.eq("date", date1);
        wrapper1.eq("type", 1);//线上预约类型
        wrapper1.eq("appointed", 0);//未被预约
        //查出该日期内可预约的医生列表
        wrapper.eq("date", date1);
        wrapper.eq("type", 1);//线上预约类型
        wrapper.eq("appointed", 0);//未被预约
        wrapper.setSqlSelect("distinct doc_id");
        List<DoctorTime> doctors = doctorTimeService.selectList(wrapper);
        for (DoctorTime dt : doctors) {
            doctor = doctorService.getByDocId(dt.getDocId());
            //查出列表中符合该科室/病种的医生
            if (doctor.getDepId() == departId) {
                User user = userService.selectById(doctor.getDocId());
                DoctorWithTimeAndUser doctorWithTimeAndUser = new DoctorWithTimeAndUser();
                doctorWithTimeAndUser.setId(doctor.getId());
                doctorWithTimeAndUser.setDocId(doctor.getDocId());
                doctorWithTimeAndUser.setName(doctor.getName());
                doctorWithTimeAndUser.setCliId(doctor.getCliId());
                doctorWithTimeAndUser.setDepId(departId);
                doctorWithTimeAndUser.setBirthday(new java.sql.Date(user.getBirthday().getTime()));
                doctorWithTimeAndUser.setAttending(doctor.getAttending());
                doctorWithTimeAndUser.setClinic(clinicService.selectById(doctor.getCliId()).getName());
                doctorWithTimeAndUser.setDepartment(departmentService.selectById(departId).getName());
                doctorWithTimeAndUser.setEmail(user.getEmail());
                doctorWithTimeAndUser.setNickName(user.getNickName());
                doctorWithTimeAndUser.setPhone(user.getPhone());
                doctorWithTimeAndUser.setSex(user.getSex());
                doctorWithTimeAndUser.setUsername(user.getUsername());
                doctorWithTimeAndUser.setIntroduction(doctor.getIntroduction());
                doctorWithTimeAndUser.setTitle(doctor.getTitle());
                //查出该医生的所有当天可预约时间
                wrapper1.eq("doc_id", doctor.getDocId());
                doctorWithTimeAndUser.setTimeList(doctorTimeService.getOnTimeByDocId(doctor.getDocId()));
                list.add(doctorWithTimeAndUser);
            }
        }
        return JsonResult.ok("success").put("appointOnline", list);
    }

    @ApiOperation(value = "app端线下预约医生")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departId", value = "病种/科室id（必填）", required = true, dataType = "Integer", paramType = "form"),
            @ApiImplicitParam(name = "date", value = "精确到天的日期（必填）", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "doctorName", value = "医生姓名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "clinicName", value = "诊所名称", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "sex", value = "医生性别（必填）", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "language", value = "语言（必填）", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "insurance", value = "保险", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/appointOffline")
    public JsonResult appointOffline(Integer departId, String date, String doctorName, String clinicName,
                                     String sex, String language, String insurance, HttpServletRequest request) {
        List<DoctorWithTimeAndUser> list = new ArrayList<>();

        //将string型的日期转化为sql的Date型
        Date date1 = null;
        if (date != null) {
            date1 = new java.sql.Date(MainController.strToDate(date).getTime());
        }

        EntityWrapper<DoctorTime> wrapper1 = new EntityWrapper<>();
        wrapper1.eq("date", date1);
        wrapper1.eq("type", 2);//线下预约类型
        wrapper1.eq("appointed", 0);//未被预约
        Integer clinicId = null;
        if (clinicName != null) {
            //查出clinicName对应的clinicId
            clinicId = clinicService.getByName(clinicName).getId();
        }

        //查出符合条件的医生
        List<Doctor> doctors = doctorService.getOffDoctor(departId, clinicId, sex, date1, doctorName, insurance, language);
        for (Doctor doctor : doctors) {
            User user = userService.selectById(doctor.getDocId());
            DoctorWithTimeAndUser doctorWithTimeAndUser = new DoctorWithTimeAndUser();
            doctorWithTimeAndUser.setId(doctor.getId());
            doctorWithTimeAndUser.setDocId(doctor.getDocId());
            doctorWithTimeAndUser.setName(doctorName);
            doctorWithTimeAndUser.setCliId(clinicId);
            doctorWithTimeAndUser.setDepId(departId);
            doctorWithTimeAndUser.setBirthday(new java.sql.Date(user.getBirthday().getTime()));
            doctorWithTimeAndUser.setAttending(doctor.getAttending());
            doctorWithTimeAndUser.setClinic(clinicService.selectById(doctor.getCliId()).getName());
            doctorWithTimeAndUser.setDepartment(departmentService.selectById(departId).getName());
            doctorWithTimeAndUser.setEmail(user.getEmail());
            doctorWithTimeAndUser.setNickName(user.getNickName());
            doctorWithTimeAndUser.setPhone(user.getPhone());
            doctorWithTimeAndUser.setSex(user.getSex());
            doctorWithTimeAndUser.setUsername(user.getUsername());
            doctorWithTimeAndUser.setIntroduction(doctor.getIntroduction());
            doctorWithTimeAndUser.setTitle(doctor.getTitle());
            //查出该医生的所有当天可预约时间
            wrapper1.eq("doc_id", doctor.getDocId());
            doctorWithTimeAndUser.setTimeList(doctorTimeService.getOffTimeByDocId(doctor.getDocId()));
            list.add(doctorWithTimeAndUser);
        }
        return JsonResult.ok("success").put("appointOffline", list);
    }


//    @ApiOperation(value = "医生上传头像图片")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "docId", value = "医生id（必填）", required = true, dataType = "Integer", paramType = "form"),
//            @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "form")
//    })
//    @RequestMapping(value = "/uploadPortrait", method = RequestMethod.POST)
//    public ResponseEntity<?> uploadPortrait(HttpServletRequest request, String s_rkbm, String type, @RequestParam(value = "file")MultipartFile[] files) {
//        Response response = new Response();
//    }

}
