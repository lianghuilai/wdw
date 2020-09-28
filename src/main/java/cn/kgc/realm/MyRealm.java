package cn.kgc.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

public class MyRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		/* Shiro的认证方法需要我们在这个方法里来获取用户信息 
		 * param:  authenticationToken: 用户登录时的Token令牌，这里就放着用户在浏览器中存放的账号和密码
		 * return: AuthenticationInfo:  这个对象返回以后Shiro会调用这个对象的一些方法来完成对密码的验证，
		 *                              密码是由Shiro进行验证是否合法
		 * 
		 * throws AuthenticationException：  如果验证失败首先Shiro会抛出异常，我们就可以抛出AuthenticationException异常，
		 *                                  我们可以根据异常的类型自己抛出异常，返回给用户特定的数据
		 *        
		 *        AuthenticationException异常的子类，
		 *                AccountException： 账号异常
		 *                UnknownAccountException：账号不存在异常
		 *                LockedAccountException
		 *                IncorrectCredentialsException
		 *                                  
		 * */
     	//将AuthenticationToken强制转换成UsernamePasswordToken
		UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
		//获取用户在浏览器输入的账号
		String username=token.getUsername();
		String dbusername=username;
		//正常情况下我们需要从数据库中获取账号的信息
		if(!"admin".equals(dbusername) && !"zhangsan".equals(dbusername)){  //判断用户账号是否正确
			throw new UnknownAccountException("账号错误");
		}
		if("zhangsan".equals(username)){
			throw new LockedAccountException("账号被锁定");
		}
		//定义一个密码，其实密码也应该来自数据库
		String dppassword="123456";
		//认证密码是否正确
		return new SimpleAuthenticationInfo(dbusername,dppassword,getName());
		
	}
	
	 public static void main(String[] args) {
		String pas="123456";
		//密码的加密
		/*SimpleHash()：参数
		 *      参数1：是加密算法 我们选择MD5加密
		 *      参数2：被加密的数据
		 *      参数3：加密时的盐值
		 *      参数4：加密的次数
		 *   
		 *     
		 *     14e1b600b1fd579f47433b88e8d85291
		 * */
		
		//Object obj=new SimpleHash("MD5",pas,"",1);
		String sss="e10adc3949ba59abbe56e057f20f883e";
		Object obj1=new SimpleHash("MD5",sss,"",1);
		Object obj2=new SimpleHash("MD5",pas,"",2);
		System.out.println(obj1);
		System.out.println(obj2);
	 }

	
}
