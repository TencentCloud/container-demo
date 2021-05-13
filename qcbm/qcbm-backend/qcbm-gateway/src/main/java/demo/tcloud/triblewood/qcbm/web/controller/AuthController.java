package demo.tcloud.triblewood.qcbm.web.controller;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.api.UserService;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.ResponseCode;
import demo.tcloud.triblewood.qcbm.common.UserInfoDto;
import demo.tcloud.triblewood.qcbm.web.entity.JwtUser;
import demo.tcloud.triblewood.qcbm.web.security.JwtTokenUtils;
import demo.tcloud.triblewood.qcbm.web.vo.SignInVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
//    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsServiceImpl;

    @DubboReference(version = "1.0.0")
    private UserService userService;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<SignInVO> createAuthenticationToken(@RequestBody UserInfoDto userInfo) throws Exception {

        logger.info("user login: {}", JSON.toJSONString(userInfo));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userInfo.getName(), userInfo.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final JwtUser jwtUser = (JwtUser) userDetailsServiceImpl.loadUserByUsername(userInfo.getName());

        final String jwtToken = JwtTokenUtils.createToken(jwtUser.getUsername(), jwtUser.getAuthorities().toString(), true);

        SignInVO vo = new SignInVO();
        vo.setId(jwtUser.getId());
        vo.setName(jwtUser.getUsername());
        vo.setJwt(jwtToken);

        return ResponseEntity.ok(vo);
    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/signup")
    public @ResponseBody
    Response<String> regist(@ApiParam(name="UserInfoDto",value="Json 格式的用户信息",required=true) @RequestBody UserInfoDto userInfoDto, HttpServletRequest req, HttpServletResponse resp) {

        logger.info("user registration: {}", JSON.toJSONString(userInfoDto));
        Response<String> userResp;

        try {
            userResp = userService.addUser(userInfoDto);
        }
        catch (Exception e) {
            logger.error("userService.addUser exception", e);
            userResp = new Response<>(ResponseCode.FAILURE);
            resp.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        return userResp;
    }

//    @ApiOperation("修改用户信息")
//    @PostMapping(value = "/user/modify")
//    public @ResponseBody Response<String> modifyUserInfo(@ApiParam(name="UserInfoDto",value="Json 格式的用户信息",required=true) @RequestBody UserInfoDto userInfoDto, HttpServletRequest req, HttpServletResponse resp) {
//
//        Response<String> serviceResp = userService.modifyUserInfo(userInfoDto);
//        if (serviceResp.getCode() == ResponseCode.FAILURE.getCode()) {
//            resp.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//
//        return serviceResp;
//    }

//
//    @RequestMapping(value="/logout", method= RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }
}
