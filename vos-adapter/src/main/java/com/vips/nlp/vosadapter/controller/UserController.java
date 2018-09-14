package com.vips.nlp.vosadapter.controller;


import com.vips.nlp.vosadapter.vo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/users")     // 通过这里配置使下面的映射都在/users下
@Log4j2
public class UserController {
    // 创建线程安全的Map
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
    static {
        users.put(0L, new User(){
            {
                setAge(100);
                setName("test");
                setId(1L);
            }
        });
    }

    @ApiOperation(value="获取用户列表", notes="获取所有用户列表信息")
    @GetMapping(value="/")
    public List<User> getUserList() {
        List<User> r = new ArrayList<User>(users.values());
        log.info("GET");
        int i = 0;
        while (i<10000000){
            System.gc();
            log.info("gc..");
            i++;
        }
        return r;
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping(value="/")
    public String postUser(@ModelAttribute User user) {
        log.info("POST, id:{}", user.getId());
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @GetMapping(value="/{id}")
    public User getUser(@PathVariable Long id) {
        log.info("GET, id:{}",id);
        return users.get(id);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @PutMapping(value="/{id}")
    public String putUser(@PathVariable Long id, @ModelAttribute User user) {
        log.info("PUT, id:{}", id);
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @DeleteMapping(value="/{id}")
    public String deleteUser(Long id) {
        log.info("DELETE, id:{}", id);
        users.remove(id);
        return "success";
    }

}