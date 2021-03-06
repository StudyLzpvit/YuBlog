package cn.net.wangshifu.controller;

import cn.net.wangshifu.dao.UserDao;
import cn.net.wangshifu.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;


@Controller
public class
LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            HttpServletRequest request,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "notlogin", required = false) String notlogin,
            @RequestParam(value = "relogin", required = false) String relogin
    ) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            HttpSession session = request.getSession();
            Exception reason = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (reason.getMessage() != null) {
                model.addObject("error", reason.getMessage());
            } else {
                model.addObject("error", "请输入正确的用户名和密码");
            }
        }
        if (logout != null) {
            model.addObject("msg", "你已经成功退出");
        }
        if (notlogin != null) {
            model.addObject("msg", "请先登录账号");
        }
        if (relogin != null) {
            model.addObject("msg", "密码修改成功，请重新登录");
        }

        ResourceBundle rb = ResourceBundle.getBundle("bloginfo".trim());
        String blogName = rb.getString("blog.name");
        model.addObject("blogName", blogName);
        String blogLoginSlogen = rb.getString("blog.login.slogen");
        String blogLoginMinorSlogen = rb.getString("blog.login.minor_slogen");
        String blogLoginSencondMinorSlogen = rb.getString("blog.login.second_minor_slogen");
        model.addObject("blogLoginSlogen", blogLoginSlogen);
        model.addObject("blogLoginMinorSlogen", blogLoginMinorSlogen);
        model.addObject("blogLoginSencondMinorSlogen", blogLoginSencondMinorSlogen);


        model.setViewName("login.html");
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register_page() {
        ModelAndView model = new ModelAndView();

        ResourceBundle rb = ResourceBundle.getBundle("bloginfo".trim());
        String blogName = rb.getString("blog.name");
        String blogRegisterSlogen = rb.getString("blog.register.slogen");
        String blogRegisterMinorSlogen = rb.getString("blog.register.minor_slogen");
        String blogRegisterSencondMinorSlogen = rb.getString("blog.register.second_minor_slogen");
        model.addObject("blogName", blogName);
        model.addObject("blogRegisterSlogen", blogRegisterSlogen);
        model.addObject("blogRegisterMinorSlogen", blogRegisterMinorSlogen);
        model.addObject("blogRegisterSencondMinorSlogen", blogRegisterSencondMinorSlogen);
        model.setViewName("register.html");
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("user-name") String username, @RequestParam("pwd") String password) {
        ApplicationContext context = null;
        context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserDao userDao = (UserDao) context.getBean("userDao");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userDao.addUser(user);
        ModelAndView model = new ModelAndView();
        model.setViewName("register_success.html");
        model.addObject("id", user.getId());
        model.addObject("username", user.getUsername());

        return model;

    }
}
