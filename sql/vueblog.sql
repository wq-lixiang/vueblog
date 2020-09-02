/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.15 : Database - vueblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`vueblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `vueblog`;

/*Table structure for table `m_blog` */

DROP TABLE IF EXISTS `m_blog`;

CREATE TABLE `m_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '注解id',
  `description` varchar(255) DEFAULT NULL COMMENT '介绍',
  `content` longtext COMMENT '博客内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `flag` varchar(255) DEFAULT NULL COMMENT '是否原创',
  `first_picture` varchar(10000) DEFAULT NULL COMMENT '图片地址',
  `views` int(11) DEFAULT '0' COMMENT '浏览次数',
  `type_id` bigint(20) DEFAULT NULL COMMENT '类型',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `recommend` bit(1) DEFAULT NULL COMMENT '是否推荐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

/*Data for the table `m_blog` */

insert  into `m_blog`(`id`,`description`,`content`,`create_time`,`update_time`,`flag`,`first_picture`,`views`,`type_id`,`user_id`,`recommend`) values (39,'Spring控制反转(IOC)和依赖注入(DI)','Spring学习01：控制反转【IOC】与依赖注入【DI】\n\n程序的耦合和解耦\n耦合：程序间的依赖关系.在开发中，应该做到解决编译期依赖，即编译期不依赖，运行时才依赖\n解耦的思路：使用反射来创建对象，而避免使用new关键字，并通过读取配置文件来获取要创建的对象全限定类名.\n\n下面以两个列子来说明如何解耦.\n\n解耦实例1：JDBC驱动注册\nJDBC操作中注册时，我们不适用DriverManager的register方法，而采用Class.forName(“驱动类全类名”)的方式.\n\n\n解耦实例2: UI层,Service层,Dao层的调用\n在web层中有，service层，DAO层之间有严重的前后调用的关系.\n\npublic class MyServiceImpl implements IMyService {\n\n    private IMyDao myDao = new MyDaoImpl();	// 业务层要调用持久层的接口和实现类\n\n    public void myService(){\n        myDao.serviceProcess();\n    }\n}\n\n业务层依赖持久层的接口和实现类，若编译时不存在没有持久层实现类，则编译将不能通过，这构成了编译期依赖\n\n解决耦合的思想：工厂模式解耦\n在实际开发中可以把三层的对象的全类名都使用配置文件保存起来，当启动服务器应用加载的时候，创建这些对象的实例并保存在容器中，在获取对象时，不适用new的方式，而是直接从容器中获取，这就是工厂设计模式。\n\n使用SpringIOC解决程序耦合	\n简单实例\n1.准备工作：创建MEAVN项目，并准备三层接口类和实现类\n创建meavn项目,配置其pom.xml如下：\n\n创建三层接口类和实现类的结构如下，模拟一个保存账户的服务.\n\n2.配置bean：在类的跟路径下的resource目录下创建bean.xml文件，把对象的创建交给spring来管理.\n每个 <bean>标签对应一个类，其class属性为该类的全类名，id属性为改类的id，在spring配置中，id获取类的对象\n\n <?xml version=\"1.0\" encoding=\"UTF-8\"?>\n <beans xmlns=\"http://www.springframework.org/schema/beans\"\n       xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n       xmlns:context=\"http://www.springframework.org/schema/context\"\n       xmlns:aop=\"http://www.springframework.org/schema/aop\"\n       xmlns:tx=\"http://www.springframework.org/schema/tx\"\n       xmlns:mvc=\"http://www.springframework.org/schema/mvc\"\n       xsi:schemaLocation=\"http://www.springframework.org/schema/beans\n        http://www.springframework.org/schema/beans/spring-beans.xsd\n        http://www.springframework.org/schema/context\n        http://www.springframework.org/schema/context/spring-context.xsd\n        http://www.springframework.org/schema/aop\n        http://www.springframework.org/schema/aop/spring-aop.xsd\n        http://www.springframework.org/schema/tx\n        http://www.springframework.org/schema/tx/spring-tx.xsd\n        http://www.springframework.org/schema/mvc\n        http://www.springframework.org/schema/mvc/spring-mvc.xsd\" default-lazy-init=\"true\">\n \n     <!--把对象的创建交给spring来管理-->\n     <bean id=\"accountService\" class=\"com.itheima.service.impl.AccountServiceImpl\"></bean>\n     <bean id=\"accountDao\" class=\"com.itheima.dao.impl.AccountDaoImpl\"></bean>\n </beans>\n\n3.在表现层文件Client.java中通过容器创建对象.通过核心容器的getBean()方法获取具体对象.\n\n我们常用的容器有三种：\nClassPathXmlApplication，FileSystemXmlApplicationContext，AnnotationConfigApplicationContext\n1.ClassPathXmlApplicationContext：它是从类的根路径下加载配置文件\n2.FileSystemXmlApplicationContext：它是从磁盘路径上加载配置文件\n3.AnnotationConfigApplicationContext：读取注解创建容器\n\n使用XML配置文件实现IOC\n使用配置文件实现IOC，要将托管给Spring的类写进bean.xml配置文件中.\n\nbean标签\n作用：配置托管给Spring对象，默认情况下调用类的无参构造函数，若如果没有无参构造函数则不能创建成功\n属性：\nid：指定对象在容器中的标识，将其作为参数传入getBean()方法可以获取获取对应对象\nclass：指定类的全类名，默认情况下调用无参构造函数\nscope：指定对象的作用范围，可选值如下\nsingleton:单例对象，默认值\nprototype：多例对象\nrequest：将对象存入到web项目的request域中\nsession：将对象存入到web项目的session域中\nglobal session：将对象存入到web项目集群的session域中，若不存在集群，则global Session相当于session\n init-method：指定类中的初始化方法名称，在对象创建成功之后执行\ndestroy-method：指定类中的销毁方法名称，对prototype多例对象没有作用，因为多利对象的销毁时机不受容器控制\n\nbean的作用范围和生命周期\n单例对象:scope=\"singleton\"\n作用范围：每个应用只有一个该对象的实例，它的作用范围就是整个应用\n生命周期：单例对象的创建与销毁和容器的创建与销毁时机一致\n对象出生：当应用加载，创建容器时，对象就被创建\n对象活着：只要容器存在，对象一直活着\n对象死亡：当应用卸载，销毁容器时，对象就被销毁\n2.多例对象：scope=“prototype”\n作用范围：每次访问对象时，都会重新创建对象的实例.\n生命周期：多例对象的创建与销毁时机\n对象的出生：当使用对象时，创建新的对象实例\n对象活着：只要对象在使用中，就一直活着\n对象死亡：当对象长时间不用时，被java的垃圾回收器回收了\n\n实例化Bean的三种方式\n1.使用默认无参构造函数创建对象：默认情况下会根据无参构造函数来创建类对象，若Bean类中没有默认无参构造函数，将会创建失败.\n<bean id=\"accountDAO\" class=\"shop.lixiang.Dao.impl.AccountDaoImp\"></bean>\n\n2.使用静态工厂的方法创建对象：\n创建静态工厂如下：\n\n使用StaticFactory类中的静态方法createAccountService创建对象，涉及到bean标签的属性\nid属性：指定对象在容器中的标识，用于从容器中获取对象\nclass属性：指定静态工厂的全类名\nfactory-method属性：指定生产对象的静态方法\n\n其实，类的构造函数也是静态方法，因此默认无参构造函数也可以看作一种静态工厂方法\n\n3.使用实例工厂的方法创建对象\n\n创建实例工厂如下：\n\n先创建实例工厂对象instanceFactory，通过调用其createAccountService()方法创建对象，涉及到bean标签的属性：\nfactory-bean属性：指定实例工厂的id\nfactory-method属性：指定实例工厂中生产对象的方法\n\n\n\n依赖注入\n\n依赖注入的概念\n依赖注入（Dependency Injection）是spring框架核心ioc的具体实现.\n\n通过控制反转，我们把创建对象托管给了spring，但是代码中不可能消除所有依赖，列如：业务层仍然会调用调用持久层的方法，因此业务层中应包含持久层的实现类对象.\n我们等待框架通过配置的方式将持久层对象传入业务层，而不是直接在代码中new某个具体的持久层实现类，这种方式称之为依赖注入\n\n依赖注入的方法\n因为我们是通过反射的方式来创建属性对象的，而不是使用new关键字，因此我们要指定创建出对象各字段的取值.\n\n使用构造函数注入\n通过类默认的构造函数来给创建类的字段赋值，相当于调用类的构造方法.\n\n涉及的标签：<constructor-arg>用来定义构造函数的参数，其属性可大致分为两类：\n寻找要赋值给的字段\nindex：指定参数在构造函数参数列表的索引位置\ntype:指定参数在构造函数中的数据类型\nname:指定参数在构造函数中的变量名，最常用的属性\n指定赋给字段的值\nvalue：给基本数据类型和String类型赋值\nref：给其它Bean类型的字段赋值，ref属性的值应为配置文件中配置的Bean的id\n\n\n\n使用set方法注入(更常用)\n在类中提供需要注入成员属性的set方法，创建对象只调用要赋值属性的set方法.\n涉及的标签：<property>,用来定义要调用set方法的成员，其主要属性可大致分为两类：\n指定要调用set方法赋值的成员字段\nname：要调用set方法赋值的成员字段\n指定赋给字段的值\nvalue：给基本数据类型和String类型赋值\nref：给其他Bean类型的字段赋值，ref属性的值应为配置文件中配置的Bean的id\n\n\n\n注入集合字段\n集合字段及其对应的标签按照集合的结构分为两类：相同结构的集合标签之间可以互相替换.\n只有键的结构：\n数组字段：<array>标签表示集合，<value>标签表示集合内的成员\nList字段：<list>标签表示集合,<value>标签表示集合内的成员\nSet字段:<set>标签表示集合，<value>标签表示集合内的成员.	\n其中<array><List><Set>标签之间可以互相替换使用.\n\n2.键值对的结构：\na. Map字段：<map>标签表示集合，<entry>标签表示集合的键值对，其key属性表示键，				  			value属性表示值.\nb. Properties字段：<props>标签表示集合，<prop>标签表示键值对，其key属性表示键，标签内的内容表示值.\n其中<map>,<props>标签之间，<entry>，<prop>标签之间可以互相替换使用.\n\n下面使用set方式注入各种集合字段\n\n\n\n使用注解实现IOC\n使用注解实现IOC，要将注解写在类的定义中\n\n常用注解\n用于创建注解对象的注解\n这些注解的作用相当于 bean.xml中的<bean> 标签\n@Component：把当前类对象存入spring容器中，其属性如下；\nvalue：用于指定当前类的id.不写时默认值是当前类名，且首字母改小写\n@Controller：将当前表现层对象存入spring容器中\n@Service：将当前业务层对象存入spring容器中\n@Repository：将当前持久层对象存入spring容器中\n@Controller；@Service;@Repository注解的作用和属性与@Component是一模一样的，可以相互替代，它们的作用是使三层对象的分别更加清晰\n\n用于注入数据的注解\n\n这些注解的作用相当于bean.xml中的\n<property>标签\n@Autowired：自动按照成员变量类型注入.\n1. 注入过程\n当spring容器中有且只有一个对象的类型与要注入的类型相同时，注入该对象\n当spring容器中有多个对象类型与要注入的类型相同时，使用要注入的变量名为bean的id，在spring容器查找，找到则注入该对象.找不到则报错 .\n2. 出现位置：既可以在变量上，也可以在方法上\n3. 细节：使用注解注入时，set方法可以省略\n2. @Qualifer：在自动按照类型注入的基础之上，再按照bean的id注入基础之上，再按照bean的\n    id注入.\n出现位置：既可以在变量上，也可以在方法上.注入变量时不能独立使用，必须和@Autowire一起使用；注入方法时可以独立使用.\n属性：\nvalue：指定bean的id\n\n\n3. @Resource：直接按照bean的id注入，它可以独立使用，独立使用时相当于同时使用\n@Autowired和@Qualifier两个注解.\n1.属性\n1.name：指定bean的id\n4. @Value：注入基本数据类型和String类型数据\n1.属性：\n1.value：用于指定数据的值，可以使用el表达式（${表达式}）\n\n用于改变作用范围的注解\n\n这些注解的作用相当于bean.xml中的 <bean>标签的scope属性.\n@Scope：指定bean的作用范围\n属性：\nvalue：用于指定作用范围的取值，\"singleton\",\"prototype\",\"request\",\"session\",\"globalsession\"\n\n和生命周期相关的注解\n\n这些注解的作用相当于bean.xml中的<bean>标签的init-method和destroy-method属性\n@PostConstruct：用于指定初始化方法\n@PreDestory：用于指定销毁方法\n\nspring的半注解配置和纯注解配置\nspring的注解配置可以与xml配置并存，也可以只使用注解配置\n				  \n半注解配置\n在半注解配置下，spring容器任然使用ClassPathXmlApplicationContext类从xml文件中读取IOC配置，同时在xml文件中告知spring容器时要扫描的包\n\n列如，使用半注解模式时，上述简单实例中的bean.xml内容如下：\n\n然后将spring注解加在类的定义中.\n\n纯注解配置\n在纯注解配置下，我们用配置类替代bean.xml，spring容器使用AnnotationApplicationContext类从Spring配置类中读取IOC配置\n\n纯注解配置下的注解\n@Configuration：用于指定当前类是一个Spring配置类，当创建容器时会从该类上加载注解，获取容器时需要使用 AnnotationApplicationContext(有@Configuration注解的类.class)\n@ComponentScan：指定spring在初始化容器时要扫描的包，作用和bean.xml文件中<context:component-scan base-package=\"要扫描的包名\"/>是一样的.其属下如下：\nbasePackages：用于指定要扫描的包，是value属性的别名\n@Bean：该注解只能写在方法上，表明使用此方法创建一个对象，并放入spring容器，其属下如下\nname：指定此方法创建出的bean对象的id\n细节：使用注解配置方法时，如果方法有参数，Spring框架会到容器中查找有没有可用的bean对象，查找的方式与@Autowired注解一样的。\n@PropertySource：用于加载properties配置文件中配置。例如配置数据源时，可以把连接数据库的信息写到properties配置文件中，就可以使用此注解指定properties配置文件的位置，其属性如下\nvalue：用于指定properties文件位置.如果是在类路径下，需要写上CLASSPATH\n@Import：用于导入其他配置类.当我们使用@Import注解之后，有@Import注解的类就是父配置类，而导入的都是子配置类.其属性如下：\nvalue：用于指定其他配置类的字节码\n实例：使用纯注解配置实现数据库CRUD\n1. 项目结构：其中包shop.lixiang存放业务代码，包config存放配置类.dao选用DBUtils和C3p0\n\n2.包shop.lixiang存放业务代码，其中dao层实现类和service层实现类的代码如下：\ndao层实现类\n\nservice层实现类一样\n\n3.在config存放配置类，其中配置类代码如下：\n其中SpringConf类为主配置类，内容如下；\n\n其中JDBCConfig类为JDBC配置类，内容如下：\n\n','2020-08-31 15:43:03','2020-09-01 12:51:59','原创','https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',25,12,1,''),(47,'如何撩妹','# 洗洗睡吧','2020-09-01 21:48:35','2020-09-01 21:48:35','原创','https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',0,7,1,''),(76,'如何赚钱','如何赚钱','2020-09-02 13:52:56','2020-09-02 17:43:05','原创','https://wuqing-zero.oss-cn-beijing.aliyuncs.com/20200902135254.jpg',0,7,1,'\0'),(77,'如何撩学姐','要微信','2020-09-02 13:54:13','2020-09-02 17:43:21','原创','https://wuqing-zero.oss-cn-beijing.aliyuncs.com/20200902135411.jpg',0,27,1,'\0');

/*Table structure for table `m_blog_tag` */

DROP TABLE IF EXISTS `m_blog_tag`;

CREATE TABLE `m_blog_tag` (
  `blog_id` bigint(20) NOT NULL,
  `tag_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `m_blog_tag` */

insert  into `m_blog_tag`(`blog_id`,`tag_id`) values (39,2),(39,3),(47,2),(47,3),(54,2),(54,3),(64,3),(64,13),(65,3),(65,13),(66,3),(66,13),(67,2),(67,3),(68,2),(68,3),(69,2),(69,3),(71,3),(71,13),(76,2),(76,3),(77,2),(77,3);

/*Table structure for table `m_tag` */

DROP TABLE IF EXISTS `m_tag`;

CREATE TABLE `m_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `m_tag` */

insert  into `m_tag`(`id`,`name`) values (2,'保安日记'),(3,'反击日记'),(13,'舔狗日记');

/*Table structure for table `m_type` */

DROP TABLE IF EXISTS `m_type`;

CREATE TABLE `m_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Data for the table `m_type` */

insert  into `m_type`(`id`,`name`) values (7,'CSS'),(11,'mybatis'),(12,'springboot'),(27,'HTML5');

/*Table structure for table `m_users` */

DROP TABLE IF EXISTS `m_users`;

CREATE TABLE `m_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `m_users` */

insert  into `m_users`(`id`,`username`,`password`) values (1,'zero','200088lx');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
