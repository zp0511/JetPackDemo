# JetPackDemo
jetpack组件学习- navigation
         
    第一步：添加依赖
    
    1.自己模块的build.gradle中添加：
    ext.navigationVersion = "2.0.0"
    dependencies {
        //... 
        implementation "androidx.navigation:navigation-fragment-ktx:$rootProject.navigationVersion"
        implementation "androidx.navigation:navigation-ui-ktx:$rootProject.navigationVersion"
    }
    2.safeArgs:用来传递参数的插件,自行选择添加。在项目的build.gradle中添加依赖
    ext.navigationVersion = "2.0.0"
    buildscript {
        ext.navigationVersion = "2.0.0"
        dependencies {
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
        }
    }
    在模块的build.gradle中添加plugin
    apply plugin: 'kotlin-android-extensions'
    apply plugin: 'androidx.navigation.safeargs'
    例如：
    <fragment
            android:id="@+id/register"
            android:name="com.demo.jetpack.fragment.RegisterFragment"
            android:label="RegisterFragment"
            tools:layout="@layout/fragment_register">
            /*最好build下,避免在类中使用时找不到生成的方法*/
        <argument
                android:name="parameter"//参数名
                android:defaultValue="kchome"//值
                app:argType="string"/>//类型
    </fragment>
 
    第二步：创建navigation导航
    
    1.名词解释
    Navigation Graph（这是一个新的资源文件，用户在可视化界面可以看出他能够到达的Destination(用户能够到达的屏幕界面)，以及流程关系，相当于各个
    fragment的xml融合到navigation中）
    NavHostFragment（当前fragment容器）如：
    <fragment
            android:name="androidx.navigation.fragment.NavHostFragment"
            app:navGraph="@navigation/login_navigation"
            app:defaultNavHost="true"
            android:id="@+id/my_host_nav_fragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
    NavController：（导航的控制者，用来控制跳转传值，相当于切换fragment）
    2.创建navigation目录
    在目录中新建navigation.xml文件
    <navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            app:startDestination="@id/welcome"//开始目标，也就是最开始显示的哪个页面
            android:id="@+id/login_navigation">
        <fragment
                android:id="@+id/login"
                tools:layout="@layout/fragment_login"
                android:name="com.demo.jetpack.fragment.LoginFragment"//指明类fragment，全路径
                android:label="LoginFragment"/>
        /*表示首先显示欢迎页，欢迎页有两个动作（action）也就是不同的页面*/
        <fragment
               android:name="com.demo.jetpack.fragment.WelComeFragment"
               android:label="WelComeFragment"
               android:id="@+id/welcome"
               tools:layout="@layout/fragment_welcome">
               <action
                       android:id="@+id/action_welcome_login"
                       app:destination="@id/login"/>
               <action
                       android:id="@+id/action_welcome_register"
                       app:destination="@id/register"/>
        </fragment>
        <fragment
                android:id="@+id/register"
                android:name="com.demo.jetpack.fragment.RegisterFragment"
                android:label="RegisterFragment"
                tools:layout="@layout/fragment_register">
        /*跳转至注册页面可携带的参数*/
           <argument
                   android:name="parameter"
                   android:defaultValue="kchome"
                   app:argType="string"/>
        </fragment>
    </navigation>

    第三步：建立NavHostFragment
    
    创建一个新的LoginActivity，在activity_login.xml文件中
    <androidx.constraintlayout.widget.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <fragment
                android:name="androidx.navigation.fragment.NavHostFragment"//值必须是androidx.navigation.fragment.NavHostFragment，声明这是一个NavHostFragment
                app:navGraph="@navigation/login_navigation"//存放的是第二步建好导航的资源文件，也就是确定了Navigation Graph
                app:defaultNavHost="true"//与系统的返回按钮相关联
                android:id="@+id/my_host_nav_fragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>
    </androidx.constraintlayout.widget.ConstraintLayout>
    
    第四步：界面跳转以及传值取值
    
    button3.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key", "value")
            findNavController().navigate(R.id.login, bundle)
        }
    button2.setOnClickListener {
            val direction =
                WelComeFragmentDirections
                    .actionWelcomeRegister()
                    .setParameter("kchome")
            findNavController().navigate(direction)
        }
        
    val arg: RegisterFragmentArgs by navArgs()
    tvRegister.text = arg.parameter
