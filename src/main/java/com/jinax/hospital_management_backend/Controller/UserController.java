package com.jinax.hospital_management_backend.Controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jinax.hospital_management_backend.Component.MyUserDetails;
import com.jinax.hospital_management_backend.Entity.User;
import com.jinax.hospital_management_backend.Service.LoginService;
import com.jinax.hospital_management_backend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chara
 */
@Api(tags = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final UserService userService;
    private final LoginService loginService;
    private final UserDetailsService userDetailsService;

    public UserController(UserService userService, LoginService loginService, UserDetailsService userDetailsService) {
        this.userService = userService;
        this.loginService = loginService;
        this.userDetailsService = userDetailsService;
    }

    @ApiOperation("获取用户信息")
    @ResponseBody
    @GetMapping("/{id}")
    public Map<String, String> getUser(@PathVariable("id") long userId){
        LOGGER.info("get user, user Id is: {}",userId);
        User user = userService.findById(userId);
        Map<String,String> result = new HashMap<>();
        result.put("name",user.getName());
        result.put("districtId",user.getDistrictId() + "");
        return result;
    }

    @ApiOperation("获取自己的信息")
    @ResponseBody
    @GetMapping("/self")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public User getSelfInfo(){
        MyUserDetails details = (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = new User();
        user.setId(details.getId());
        if(user.getDistrictId() != null){
            user.setDistrictId(details.getDistrictId());
        }

        user.setIdentification(details.getUsername());
        user.setName(details.getRealUsername());
        user.setDistrictId(details.getDistrictId());
        user.setRole(details.getRole());
        return user;
    }

    @ApiOperation("登录")
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("identification") String identification,@RequestParam("password") String password){
        LOGGER.info("get user, identification Id is: {}, pw is: {}",identification,password);
        String token = loginService.login(identification, password);
        if (token == null) {
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(identification);
        tokenMap.put("id",userDetails.getId() + "");
        return new ResponseEntity<Map<String, String>>(tokenMap, HttpStatus.OK);
    }

}
