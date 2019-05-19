package com.wf.ew.system.controller;

import com.wangfan.endecrypt.utils.EndecryptUtils;
import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.exception.BusinessException;
import com.wf.ew.system.model.Patient;
import com.wf.ew.system.model.User;
import com.wf.ew.system.model.UserRole;
import com.wf.ew.system.service.*;
import io.swagger.annotations.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wf.jwtp.provider.Token;
import org.wf.jwtp.provider.TokenStore;
import springfox.documentation.annotations.ApiIgnore;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(value = "个人相关接口", tags = "main")
@Controller
public class MainController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserRoleService userRoleService;

    @ApiIgnore
    @RequestMapping({"/", "/index"})
    public String index() {
        return "redirect:index.html";
    }

    @ResponseBody
    @ApiOperation(value = "获取个人信息")
    @ApiImplicitParam(name = "access_token", value = "令牌", required = true, dataType = "String", paramType = "query")
    @GetMapping("${api.version}/userInfo")
    public JsonResult userInfo(HttpServletRequest request) {
        return JsonResult.ok().put("user", userService.selectById(getLoginUserId(request)));
    }

    @ResponseBody
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form")
    })

    @PostMapping("${api.version}/login")
    public JsonResult login(String username, String password, HttpServletRequest request) {
        User user = userService.getByUsername(username);
        if (user == null) {
            return JsonResult.error("账号不存在");
        } else if (!user.getPassword().equals(password) && !user.getPassword().equals(EndecryptUtils.encrytMd5(password))) {
            return JsonResult.error("密码错误");
        }
        String[] roles = arrayToString(userRoleService.getRoleIds(user.getUserId()));
        String[] permissions = listToArray(authoritiesService.listByUserId(user.getUserId()));
        Token token = tokenStore.createNewToken(String.valueOf(user.getUserId()), permissions, roles);
        return JsonResult.ok("登录成功").put("access_token", token.getAccessToken());
    }

    @ResponseBody
    @ApiOperation(value = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form"),
//            @ApiImplicitParam(name = "nickName", value = "昵称", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "trueName", value = "真实姓名", required = true, dataType = "String", paramType = "form"),
//            @ApiImplicitParam(name = "idCard", value = "驾驶证号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "gender", value = "性别", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "birthday", value = "出生日期", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "tel", value = "电话", required = true, dataType = "String", paramType = "form"),
//            @ApiImplicitParam(name = "email", value = "邮箱", required = true, dataType = "String", paramType = "form"),
//            @ApiImplicitParam(name = "medicare", value = "保险", required = true, dataType = "String", paramType = "form"),
//            @ApiImplicitParam(name = "allergic", value = "过敏史", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "address", value = "药房地址", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "postalCode", value = "邮编", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "emergency_people", value = "紧急联系人", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "emergency_tel", value = "紧急联系人电话", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping("${api.version}/register")
    public JsonResult register(String username, String password, String trueName, String gender, String birthday,
                               String tel, String address, String postalCode,String emergency_people,
                               String emergency_tel,HttpServletRequest request) {

        //注册涉及三个表的插入：sys_user, sys_role_user, patients

        User user = new User();
        if (username != null){
            user.setUsername(username);

        }else {
            return JsonResult.error("注册失败，账号不能为空");
        }
        if (password != null){
            user.setPassword(EndecryptUtils.encrytMd5(password));//将用户密码md5加密后存入数据库
        }else {
            return JsonResult.error("注册失败，密码不能为空");
        }
        if (trueName != null){
            user.setTrueName(trueName);
        }else {
            return JsonResult.error("注册失败，姓名不能为空");
        }
//        user.setNickName(nickName);
//        user.setIdCard(idCard);
        if (gender != null){
            user.setSex(gender);
        }else {
            return JsonResult.error("注册失败，性别不能为空");
        }
        if (birthday != null){
            user.setBirthday(strToDate(birthday));
        }else {
            return JsonResult.error("注册失败，出生日期不能为空");
        }
        if (tel != null){
            user.setPhone(tel);
        }else {
            return JsonResult.error("注册失败，电话不能为空");
        }
//        user.setEmail(email);
        user.setState(null);
        user.setEmailVerified(null);
        user.setPostalCode(postalCode);
        if (userService.insert(user)){
            UserRole userRole = new UserRole();
            userRole.setRoleId(4);//4代表用户角色是病人
            userRole.setUserId(user.getUserId());
            if (!userRoleService.insert(userRole)) {
                throw new BusinessException("注册失败");
            }
            Patient patient = new Patient();
            patient.setPatId(user.getUserId());
            patient.setName(trueName);
//            patient.setAllergicHistory(allergic);
//            patient.setMedicare(medicare);
            patient.setAddress(address);
            patient.setEmergencyPeople(emergency_people);
            patient.setEmergencyTel(emergency_tel);
            if (patientService.insert(patient)){
                return JsonResult.ok("注册成功");
            }
        }
        return JsonResult.error("注册失败");
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    private String[] listToArray(List<String> list) {
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }

    private String[] arrayToString(Object[] objs) {
        String[] strs = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            strs[i] = String.valueOf(objs[i]);
        }
        return strs;
    }

}
