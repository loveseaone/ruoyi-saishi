package com.ruoyi.wx.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ruoyi.activity.domain.ActivityAthletes;
import com.ruoyi.activity.domain.ActivityTeams;
import com.ruoyi.activity.service.IActivityAthletesService;
import com.ruoyi.activity.service.IActivityTeamsService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.wx.config.WxConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信端登录认证接口控制器
 *
 * @author ruoyi
 */
@Api("微信端登录认证接口")
@RestController
@RequestMapping("/wx/auth")
public class WxAuthController extends BaseController {

    @Autowired
    private IActivityAthletesService activityAthletesService;

    @Autowired
    private IActivityTeamsService activityTeamsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private RedisCache redisCache;

    /**
     * 微信登录（通过code获取openID并返回token）
     */
    @ApiOperation("微信登录（通过code获取openID并返回token）")
    @ApiImplicitParam(name = "code", value = "微信授权码", dataType = "String", paramType = "query")
    @Anonymous
    @PostMapping("/loginByCode")
    public AjaxResult loginByCode(@RequestParam String code) {
        try {
            // 通过code换取openid
            String openid = getOpenidByCode(code);
            if (openid == null || openid.isEmpty()) {
                return AjaxResult.error(400, "微信登录失败，请重试");
            }

            // 创建虚拟用户，ID为10000
            SysUser sysUser = new SysUser();
            sysUser.setUserId(10000L);
            sysUser.setUserName("微信用户");
            sysUser.setPhonenumber("13800138000");
            //设置角色 admin
            sysUser.setRoles(getDefaultRoles());

            // 创建LoginUser对象
            LoginUser loginUser = new LoginUser();
            loginUser.setUserId(sysUser.getUserId());
            loginUser.setUser(sysUser);
            loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));

            // 生成token
            String token = tokenService.createToken(loginUser);

            // 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("openid", openid);

            return AjaxResult.success(result);
        } catch (Exception e) {
            logger.error("微信登录时发生错误", e);
            return AjaxResult.error("登录失败，请稍后重试");
        }
    }

    /**
     * 通过姓名、手机号、队伍ID登录
     */
    @ApiOperation("通过姓名、手机号、队伍ID登录")
    @ApiImplicitParam(name = "loginInfo", value = "登录信息", dataType = "Map", dataTypeClass = Map.class)
    @Anonymous
    @PostMapping("/loginByInfo")
    public AjaxResult loginByInfo(@ApiParam("登录信息") @RequestBody Map<String, Object> loginInfo) {
        try {
            // 获取登录信息
            String name = (String) loginInfo.get("name");
            String phone = (String) loginInfo.get("phone");
            Long teamId = Long.valueOf(loginInfo.get("teamId").toString());

            // 验证参数
            if (name == null || name.isEmpty() || phone == null || phone.isEmpty() || teamId == null) {
                return AjaxResult.error(400, "姓名、手机号和队伍ID不能为空");
            }

            // 查询运动员信息
            ActivityAthletes query = new ActivityAthletes();
            query.setName(name);
            query.setPhone(phone);
            query.setTeamId(teamId);
            List<ActivityAthletes> athletesList = activityAthletesService.selectActivityAthletesList(query);

            // 验证是否存在匹配的记录
            if (athletesList.isEmpty()) {
                return AjaxResult.error(1001, "身份信息不匹配，请检查姓名、手机号或所属队伍");
            }

            ActivityAthletes athlete = athletesList.get(0);

            // 验证角色是否为参赛人员（队员、领队、教练、工会人员）
            String role = athlete.getRole();
            if (!("男队员".equals(role) || "女队员".equals(role) || "领队".equals(role)
                    || "教练".equals(role) || "工会人员".equals(role))) {
                return AjaxResult.error(1001, "身份信息不匹配，请检查姓名、手机号或所属队伍");
            }

//            String token = loginService.login("app01", "app01@2025");
//            // 构造返回结果，仅返回token
//            Map<String, Object> result = new HashMap<>();
//            result.put("token", token);
            // 先尝试从缓存中获取公共账号token
            String tokenKey = "login_tokens:app01";
            String token = redisCache.getCacheObject(tokenKey);
            if (token == null || token.isEmpty()) {
                // 如果缓存中没有有效token，则生成新的token
                token = loginService.login("app01", "app01@2025");
                // 将新生成的token存入缓存，设置30分钟过期时间（与默认token过期时间一致）
                redisCache.setCacheObject(tokenKey, token, 4320, TimeUnit.MINUTES);
            }

            // 构造返回结果，仅返回token
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);

            // 返回用户信息
            result.put("userInfo",  athlete);
            return AjaxResult.success(result);
        } catch (Exception e) {
            logger.error("登录时发生错误", e);
            return AjaxResult.error("登录失败，请稍后重试");
        }
    }

    /**
     * 2.1 身份绑定（首次登录）
     */
    @ApiOperation("身份绑定（首次登录）")
    @ApiImplicitParam(name = "bindInfo", value = "绑定信息", dataType = "Map", dataTypeClass = Map.class)
    @Anonymous
    @PostMapping("/bind")
    public AjaxResult bind(@RequestBody Map<String, Object> bindInfo) {
        try {
            // 获取code并换取openid
            String code = (String) bindInfo.get("code");
            if (code == null || code.isEmpty()) {
                return AjaxResult.error(400, "微信授权码不能为空");
            }
            
            String openid = getOpenidByCode(code);
            if (openid == null || openid.isEmpty()) {
                return AjaxResult.error(400, "微信登录失败，请重试");
            }

            // 获取绑定信息
            String name = (String) bindInfo.get("name");
            String phone = (String) bindInfo.get("phone");
            Long teamId = Long.valueOf(bindInfo.get("teamId").toString());

            // 验证参数
            if (name == null || name.isEmpty() || phone == null || phone.isEmpty() || teamId == null) {
                return AjaxResult.error(400, "姓名、手机号和队伍信息不能为空");
            }

            // 查询运动员信息
            ActivityAthletes query = new ActivityAthletes();
            query.setName(name);
            query.setPhone(phone);
            query.setTeamId(teamId);
            List<ActivityAthletes> athletesList = activityAthletesService.selectActivityAthletesList(query);

            // 验证是否存在匹配的记录
            if (athletesList.isEmpty()) {
                return AjaxResult.error(1001, "身份信息不匹配，请检查姓名、手机号或所属队伍");
            }

            ActivityAthletes athlete = athletesList.get(0);
            
            // 验证角色是否为参赛人员（队员、领队、教练、工会人员）
            String role = athlete.getRole();
            if (!("男队员".equals(role) || "女队员".equals(role) || "领队".equals(role) 
                    || "教练".equals(role) || "工会人员".equals(role))) {
                return AjaxResult.error(1001, "身份信息不匹配，请检查姓名、手机号或所属队伍");
            }

            // 检查该openid是否已绑定其他身份
            ActivityAthletes openidQuery = new ActivityAthletes();
            openidQuery.setOpenid(openid);
            List<ActivityAthletes> openidAthletes = activityAthletesService.selectActivityAthletesList(openidQuery);
            if (!openidAthletes.isEmpty()) {
                return AjaxResult.error(400, "该微信已绑定其他身份");
            }

            // 更新运动员的openid
            athlete.setOpenid(openid);
            activityAthletesService.updateActivityAthletes(athlete);

            // 生成正式JWT Token
            String token = createLoginToken(athlete);

            // 构造返回用户信息
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", buildUserInfo(athlete));

            return AjaxResult.success(result);
        } catch (Exception e) {
            logger.error("绑定身份时发生错误", e);
            return AjaxResult.error("绑定失败，请稍后重试");
        }
    }

    /**
     * 2.2 已绑定用户快速登录
     */
    @ApiOperation("已绑定用户快速登录")
    @ApiImplicitParam(name = "loginInfo", value = "登录信息", dataType = "Map", dataTypeClass = Map.class)
    @Anonymous
    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String, Object> loginInfo) {
        try {
            String code = (String) loginInfo.get("code");
            if (code == null || code.isEmpty()) {
                return AjaxResult.error(400, "微信授权码不能为空");
            }

            // 通过code换取openid
            String openid = getOpenidByCode(code);
            if (openid == null || openid.isEmpty()) {
                return AjaxResult.error(400, "微信登录失败，请重试");
            }

            // 查询绑定记录
            ActivityAthletes query = new ActivityAthletes();
            query.setOpenid(openid);
            List<ActivityAthletes> athletesList = activityAthletesService.selectActivityAthletesList(query);

            if (athletesList.isEmpty()) {
                return AjaxResult.error(1002, "未绑定身份，请先完成身份认证");
            }

            ActivityAthletes athlete = athletesList.get(0);

            // 生成正式JWT Token
            String token = createLoginToken(athlete);

            // 构造返回用户信息
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", buildUserInfo(athlete));

            return AjaxResult.success(result);
        } catch (Exception e) {
            logger.error("登录时发生错误", e);
            return AjaxResult.error("登录失败，请稍后重试");
        }
    }

    /**
     * 通过微信code获取openid的方法
     * 调用微信API: https://api.weixin.qq.com/sns/jscode2session
     * 
     * @param code 微信临时授权码
     * @return openid
     */
    private String getOpenidByCode(String code) {
        try {
            // 构造请求参数
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("appid", wxConfig.getAppid());
            paramMap.put("secret", wxConfig.getSecret());
            paramMap.put("js_code", code);
            paramMap.put("grant_type", "authorization_code");
            //打印请求参数
            logger.info("微信接口请求参数: {}", paramMap);
            
            // 发送HTTP请求调用微信接口
            String url = "https://api.weixin.qq.com/sns/jscode2session";
            String result = HttpUtil.get(url, paramMap);
            
            // 解析返回结果
            if (JSONUtil.isJsonObj(result)) {
                Map<String, Object> resultMap = JSONUtil.toBean(result, Map.class);
                if (resultMap.containsKey("openid")) {
                    return (String) resultMap.get("openid");
                }
                // 如果返回错误信息
                if (resultMap.containsKey("errcode")) {
                    logger.error("微信接口返回错误: errcode={}, errmsg={}", 
                        resultMap.get("errcode"), resultMap.get("errmsg"));
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("调用微信接口获取openid时发生错误", e);
            return null;
        }
    }

    /**
     * 创建登录token
     * 
     * @param athlete 运动员信息
     * @return token
     */
    private String createLoginToken(ActivityAthletes athlete) {
        // 创建SysUser对象
        SysUser sysUser = new SysUser();
        sysUser.setUserId(athlete.getAthleteId());
        sysUser.setUserName(athlete.getName());
        sysUser.setPhonenumber(athlete.getPhone());
        //设置角色
        sysUser.setRoles(getDefaultRoles());
        // 创建LoginUser对象
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(sysUser.getUserId());
        loginUser.setUser(sysUser);
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));

        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 构造用户信息
     * 
     * @param athlete 运动员信息
     * @return 用户信息
     */
    private Map<String, Object> buildUserInfo(ActivityAthletes athlete) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", athlete.getAthleteId());
        user.put("name", athlete.getName());
        
        // 手机号脱敏
        String phone = athlete.getPhone();
        if (phone != null && !phone.isEmpty()) {
            phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
        user.put("phone", phone);
        
        // 获取队伍名称
        ActivityTeams team = activityTeamsService.selectActivityTeamsByTeamId(athlete.getTeamId());
        if (team != null) {
            user.put("teamName", team.getTeamName());
        }
        
        user.put("role", getDefaultRoles().get(0));
        
        return user;
    }
    private List<SysRole> getDefaultRoles() {
        SysRole role = new SysRole();
        role.setRoleId(1L);
        role.setRoleName("超级管理员");
        role.setRoleKey("admin");
        role.setDataScope("1");
        role.setMenuCheckStrictly(true);
        role.setDeptCheckStrictly(true);
        role.setStatus("0");
        role.setDelFlag("0");
        role.setFlag(true);
        List<SysRole> roles = new ArrayList<>();
        roles.add(role);
        return roles;
    }
}