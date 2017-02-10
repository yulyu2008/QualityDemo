#1.前言:
在我们平时项目开发中,经常会写一些不严谨的代码或者一些比较低级的错误代码,但是这些错误往往很难被发现,这样就导致了我们的项目中会隐藏了很多影响性能甚至是导致闪退的错误代码,于是许多响应的检测工具就出现了.在这里我就介绍一下我比较常用的几个检测工具吧

#[项目DEMO源码](https://github.com/yulyu2008/QualityDemo)

#2.FindBugs
顾名思义,FindBugs是一个寻找bug的工具,更具体的说FindBugs是一个静态检测java代码的工具,可以找到代码中的一些潜在bug,比如说NullPointerException,或者是一些流或者数据库没有关闭的问题.

##2.1作用
检测范围 :

- 常见代码错误，序列化错误
- 可能导致错误的代码，如空指针引用
- 国际化相关问题：如错误的字符串转换
- 可能受到的恶意攻击，如访问权限修饰符的定义等
- 多线程的正确性：如多线程编程时常见的同步，线程调度问题。
- 运行时性能问题：如由变量定义，方法调用导致的代码低效问题
##2.2使用方法
#####FindBugs在Android studio里面有个插件 正常安卓即可(不会安装插件的自己google)

![这里写图片描述](http://img.blog.csdn.net/20170210170252600?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


#####安装好插件之后重启一下Android studio,在底部会有个红色的图标(不同版本可能位置不同),如果你出现了这个图标,那么恭喜你完成了第一步
![这里写图片描述](http://img.blog.csdn.net/20170210170643524?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#####点击左侧第五个按钮可以开始检测

![这里写图片描述](http://img.blog.csdn.net/20170210170925244?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



#####检测结果显示如下

- 当创建流的时候发生了异常  那么inputStream就可能为空 接下来直接调用inputStream就可能产生空指针异常
![这里写图片描述](http://img.blog.csdn.net/20170210172157593?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- 这里使用了io流,但是没有做释放
![这里写图片描述](http://img.blog.csdn.net/20170210192533233?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


##2.3设置
#####在setting里面还可以对FindBugs做一些设置(这里就不做详细的介绍了)
![image](http://img.blog.csdn.net/20170210192721918?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

##2.4自定义规则
#####FindBugs可以在gradle里面做自定义任务
#####(注意需要在gradle里面添加 apply plugin:'findbugs')
![这里写图片描述](http://img.blog.csdn.net/20170210193228154?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170210193347371?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


#####在这里配置了规则findbugs-filter.xml  以及报告生成的路径findbugs.xml

	task findbugs(type: FindBugs, dependsOn: "assembleDebug") {
	    ignoreFailures = false
	    effort = "max"
	    reportLevel = "high"
	    excludeFilter = new File("$configDir/findbugs/findbugs-filter.xml")//这里是自定义的规则
	    classes = files("${project.rootDir}/app/build/intermediates/classes")
	
	    source 'src'
	    include '**/*.java'
	    exclude '**/gen/**'
	
	    reports {
	        xml.enabled = false
	        html.enabled = true
	        xml {
	            destination "$reportsDir/findbugs/findbugs.xml"  //这里是报告产生的路径
	        }
	        html {
	            destination "$reportsDir/findbugs/findbugs.html"  //这里是报告产生的路径
	        }
	    }
	
	    classpath = files()
	}

#####运行自定义任务(找到任务,双击即可)
![这里写图片描述](http://img.blog.csdn.net/20170210193614469?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



#3.PMD
#####PMD和FindBugs的作用差不多,但是他们俩的检测方法不同,所以同时使用能达到互补.
##3.1作用
#####检测范围 :

- 可能的bug——空的try/catch/finally/switch块。
- 无用代码(Dead code)：无用的本地变量，方法参数和私有方法。
- 空的if/while语句。
- 过度复杂的表达式——不必要的if语句，本来可以用while循环但是却用了for循环。
- 可优化的代码：浪费性能的String/StringBuffer的使用。

##3.2使用方法
#####PMD在Android studio也有个插件,直接安装即可(注意搜索的关键字用QAPlug - PMD)
![这里写图片描述](http://img.blog.csdn.net/20170210193941974?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

##3.2自定义规则
#####(注意需要在gradle里面添加 apply plugin: 'pmd')
![这里写图片描述](http://img.blog.csdn.net/20170210193228154?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



	task pmd(type: Pmd) {
	    ignoreFailures = false
	    ruleSetFiles = files("$configDir/pmd/pmd-ruleset.xml") //这里是自定义的规则
	    ruleSets = []
	
	    source 'src'
	    include '**/*.java'
	    exclude '**/gen/**'
	
	    reports {
	        xml.enabled = false
	        html.enabled = true
	        xml {
	            destination "$reportsDir/pmd/pmd.xml"  //这里是报告产生的路径
	        }
	        html {
	            destination "$reportsDir/pmd/pmd.html"  //这里是报告产生的路径
	        }
	    }
	}
#####在这里配置了规则pmd-ruleset.xml  以及报告生成的路径 pmd.html

![这里写图片描述](http://img.blog.csdn.net/20170210194154925)



#####运行自定义任务(跟FindBugs一样,找到任务,双击即可)

![这里写图片描述](http://img.blog.csdn.net/20170210194323332)




#4.CheckStyles

#####CheckStyles是用来检测java代码规范性的

##4.1作用
#####检测范围 :

- 注解
- javadoc注释
- 命名规范
- 文件头
- 导入包规范
- 尺寸设置
- 空格
- 正则表达式
- 修饰符
- 代码块
- 编码问题
- 类设计问题
- 重复、度量以及一些杂项

##4.2使用方法
#####安装插件 CheckStyles
![这里写图片描述](http://img.blog.csdn.net/20170210194537521)

##4.3设置
![这里写图片描述](http://img.blog.csdn.net/20170210194607927)

**TreatCheckstyle errors as warnings  如果勾上的话,检测到错误时只是会以警告的形式提示**

**下面是勾上时的提示方式(以警告的形式)**

![这里写图片描述](http://img.blog.csdn.net/20170210194737288)

**下面是不勾时的提示方式(以报错的形式)**
![这里写图片描述](http://img.blog.csdn.net/20170210194935473)



**下面是选择官方默认的检测规则(也可以自己定义,具体这里就不做详解了)**
![这里写图片描述](http://img.blog.csdn.net/20170210195148867)


##4.4通过gradle运行
#####(注意需要在gradle里面添加 apply plugin: 'checkstyle')
![这里写图片描述](http://img.blog.csdn.net/20170210193228154?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXVseXU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

	task checkstyle(type: Checkstyle) {
	    configFile file("$configDir/checkstyle/checkstyle.xml") //这里是自定义的规则
	    configProperties.checkstyleSuppressionsPath = file("$configDir/checkstyle/suppressions.xml").absolutePath  //这里是自定义的规则
	    source 'src'
	    include '**/*.java'
	    exclude '**/gen/**'
	    classpath = files()
	}

![这里写图片描述](http://img.blog.csdn.net/20170210195248899)



#5.Demo源码
##[Demo链接](https://github.com/yulyu2008/QualityDemo) 
## https://github.com/yulyu2008/QualityDemo

**注意Demo里面的task都抽取到了config目录下的quality.gradle中,所以需要在项目的build.gradle添加**

**apply from: '../config/quality.gradle'**


Demo是在他人的基础上修改的,由于已经无法找到源头,所以在这里声明一下

