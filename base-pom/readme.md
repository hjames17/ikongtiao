## 简单介绍
base-pom 是最顶层的pom，里面定义了spring依赖的jar包，一些常用的jar包，但都是通过dependencyManagement管理的，所以如果知识简单继承该pom，不回引入任何jar，这里制定了，通用jar包的版本号。

base-core继承base-pom，并切加入了包依赖，不过所有的包都是optional的，所以依赖该pom的项目，并不会引入base-core依赖的jar包。base-core是底层架构的核心。要把代码放到这里面要慎重考虑，要足够通用才放到这里面来。这里面包含一些常用的工具：
		
	访问数据的CommonDao;
	全局异常定义Exception;		
	通用的filter(跨域，签名);
	分页查询工具类PageList;
	全局返回结果封装AjaxResult;
	IP地址工具类，IpUtils;
	uuid的获取;
	一些常用加密方法，Base64，MD5，Sha1;
	对象与json的转化，Jackson;
	异步线程，ThreadExecutor;
	网络请求方法,HttpExecutor;
	异步线程和网络请求，都可以使用Utils.get(HttpExecutor.class)...的方式调用;

parent-pom继承base-pom，并且添加了所有base-pom定义的jar包，并且依赖了base-core,所以后面所有项目只要继承该pom就基本都会有基本的jar包，不用自己再次添加依赖。

ikongtiao-pom继承parent-pom，然后聚合了自己的所有子模块。他的所有自模块都继承该pom，然后环境配置文件都配置到该项目中，env目录下面，可以根据不同环境建立不同的conf文件。所有根据环境不同，配置不同的参数都配置到这里。然后自项目中的conf文件使用占位符方式来获取对应的值。

ikongtiao-tool,继承ikongtiao-pom的最底层的jar包，里面定义一些项目的全局枚举，全局的工具类，只有该项目才用到的工具类，放到这里，如果后面项目多了，都需要这些工具类，可以考虑这些工具类重构到底层的base-core里面去。

ikongtiao-dao,继承ikongtiao-pom，依赖ikongtiao-tool,该模块为数据访问层，entity，dto都定义到这里。

ikongtiao-wechat,继承ikongtiao-pom，模块比较独立，没有依赖其他项目模块，该层时微信相关的累，发送消息，微信支付放到这里。

ikongtiao-biz,继承ikongtiao-pom，依赖ikongtiao-dao,和ikongtiao-wechat,业务处理层。项目的配置文件通常配置在该层，包括访问数据库的配置，缓存(ehcache)的配置，内存redis的配置，日志采集的配置，支付的配置，xml配置中的占位符都是从该项目的conf目录下的conf文件中填充的数据，所以一般在ikongtiao-pom中不同环境的不同配置，在这里都会统一，这里还会加上一些不根据环境变更的配置，供xml文件使用。供程序@value和properties使用。

ikongtiao-web,继承ikongtiao-pom,依赖ikongtiao-biz,项目的web层，对外提供接口，需要配置web.xml,context.xml,servlet.xml文件。

## 开发规则

所有对外接口返回json数据的都使用AjaxResult，业务异常都使用AjaxException类，异常内容都使用枚举，枚举实现base-core的ErrorMessage。
web成可以使用biz层，也可以直接使用dao层，但仅限于，不需要业务逻辑处理的简单操作，如果有逻辑，就需要service层。
所有代码记得加注释，格式化，清除多余导入。

