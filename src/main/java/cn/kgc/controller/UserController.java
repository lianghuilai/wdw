package cn.kgc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	
	  @RequestMapping("/")
	  public String index(){
		  return "login";
	  }
	  
	  
	  @RequestMapping("/login")
	  public String login(String username,String password,Model model){
		  //创建一个shiro的Subject的对象，使用这个对象来完成用户的登录认证
		    Subject subject=SecurityUtils.getSubject(); 
		  //判断当前用户是否已经认证过，如果认证过不需要认证，如果没有认证进入if,去完成认证
		    if(!subject.isAuthenticated()){  //isAuthenticated():此方法就是判断当前用户是否认证
		    	//创建一个用户账号和密码的Token对象，并设置用户的账号和密码
		    	UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		    	//生成了Token令牌，那么我们需要去判断，是否符合要求,调用login方法进行判断
		    	//调用login后Shiro就会自动执行我们自定义的Realm中的方法
		    	try{
		    		  //例如账号不存在或密码错误，我们要根据不同的异常类型来判断用户登录的状态，给出友好的提示
		    		  subject.login(token);
		    	}catch(UnknownAccountException e){
		    		//进入catch里，表示用户账号错误，这个异常时我们在后台抛出的
		    		System.out.println("---------账号不存在");
		    	    model.addAttribute("errorMessage","账号不存在");
		    	    return "login";
		    	}catch(LockedAccountException e){
		    	   //进入catch里，表示用户的账号被锁定
		    		System.out.println("----------账号被锁定");
		    		model.addAttribute("errorMessage", "账号被锁定");
		    		return "login";
		    	}catch(IncorrectCredentialsException e){
		    		 //进入catch里，表示用户的密码不匹配
		    		 System.out.println("**********密码错误");
		    		 model.addAttribute("errorMessage","密码错误");
		    		 return "login";
		    	}
		    	
		    } 
		  
		  return "redirect:/success";
	  }
	  
	  @RequestMapping("/success")
	  public String success(){
		  return "success";
	  }
	  
	  @RequestMapping("/noPermission")
	  public String noPermission(){
		  return "noPermission";
	  }
	  
	  @RequestMapping("/user/test")
	  @ResponseBody
	  public String usetTset(){
		  return "这是userTest请求";
	  }
	  
	  @RequestMapping("/admin/test")
	  @ResponseBody
	  public String adminTset(){
		  return "这是adminTset请求";
	  }
	  
	  
	  //退出  注销
	  @RequestMapping("/logout")
	  public String logout(){
		  //创建一个shiro的subject对象，利用 这个对象的方法来完成退出
		  Subject subject=SecurityUtils.getSubject(); 
		  //调用退出方法，这个方法用于清空登录时的缓存信息
		  subject.logout();
		  return "redirect:/";
	  }
	  

}
