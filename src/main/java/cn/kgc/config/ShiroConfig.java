package cn.kgc.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.kgc.realm.MyRealm;

@Configuration   //(.xml  <beans> <bean></bean>  </beans>)
public class ShiroConfig {
	
	   //配置Shiro的安全管理器
	   @Bean
	   public DefaultWebSecurityManager  securityManager(Realm realm){
		   //创建DefaultWebSecurityManager对象
		   DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		   //设置一个Realm，这个 Realm是最终 用于完成我们认证号和授权操作的对象
		   securityManager.setRealm(realm);
		   return securityManager;
	   } 
	     
	   
	   
	   //配置一个自定义的Realm的bean，最终将使用这个bean返回的对象来完成我们的认证和授权
	   @Bean
	   public Realm myRealm(){
		   MyRealm realm=new MyRealm();
		   return realm;
	   }
	   
	   
	   //配置一个Shiro的过滤器bean,这个bean将配置Shiro相关的一个拦截规则
	   //什么样的请求可以访问，什么样的请求不可以访问等等
	   @Bean
	   public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
		   //创建shiro的拦截器，用于拦截我们的请求
		   ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
		   //设置安全管理，设置安全管理的同时就会指定某个Realm,用来完成权限分配
		   shiroFilter.setSecurityManager(securityManager);
		   
		   //用于设置一个登录请求地址,通过这个url转向登录界面
		   shiroFilter.setLoginUrl("/");
		   
		   //登录成功后访问的地址
		   shiroFilter.setSuccessUrl("/success");
		   
		   
		   //访问这个路径，表示用户没有操作权限
		   shiroFilter.setUnauthorizedUrl("/noPermission");
		   
		   //定义一个Map集合，在这个集合里存放的就是规则，么样的请求可以访问，什么样的请求不可以访问等等
		   Map<String,String> map=new LinkedHashMap<String,String>();
		   map.put("/login","anon");  //这个请求不需要登录
		   //authc:表示对应的请求，必须进行登录，只有登录才能访问
		   
		   map.put("/admin/**","authc");
		   map.put("/user/**","authc");
		  // map.put("/**","authc");    如果你写了这行代码，那么一定是放在最后
		   shiroFilter.setFilterChainDefinitionMap(map); 
		   return shiroFilter;
	   }
	   
	   

}
