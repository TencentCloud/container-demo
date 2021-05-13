package demo.tcloud.triblewood.qcbm.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sysinfo")
public class SystemInfoController {

    @GetMapping(value = "/backend", produces = {"application/json;charset=UTF-8"})
    public @ResponseBody
    Map<String, Object> queryUserFavorites() {

        Map<String, Object> sysInfoMap = new HashMap<>(1);
        sysInfoMap.put("backend", "dubbo");

        return sysInfoMap;
    }
}
