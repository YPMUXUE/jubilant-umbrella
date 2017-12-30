package umbrella.bootstrap;

import umbrella.config.ConfigReader;
import umbrella.connection.proxy.config.ProxyConfig;
import umbrella.saver.config.SaverConfig;
import umbrella.task.config.TaskConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class GetStart {
    private static String isProxy;
    private static String userid;
    private static String configPath;

    static {
        try {
            isProxy="false";
            configPath=new File("").getCanonicalPath()+"\\Config.xml";
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args)throws Exception{
        print();
        readArgs(args);
        ConfigReader configReader=new ConfigReader();
        configReader.initConfig(configPath);
        SaverConfig saverConfig=new SaverConfig();
        System.out.println(saverConfig.toString());
        ProxyConfig proxyConfig=new ProxyConfig();
        System.out.println(proxyConfig.toString());
        TaskConfig taskConfig=new TaskConfig();
        System.out.println(taskConfig.toString());
    }

    private static void readArgs(String[] args) {
        if (args.length==0) {
            System.out.println("userid can not be null");
            System.exit(1);
        }else if (args.length==2){
            userid=args[0];
            isProxy=args[1];
        }else if(args.length==1){
            userid =args[0];
        }
    }
    private static void print() throws InterruptedException {
        String print="/*code is far away from bug with the animal protecting\n" +
                "    *  ┏┓　　　┏┓\n" +
                "    *┏┛┻━━━┛┻┓\n" +
                "    *┃　　　　　　　┃\n" +
                "    *┃　　　━　　　┃\n" +
                "    *┃　┳┛　┗┳　┃\n" +
                "    *┃　　　　　　　┃\n" +
                "    *┃　　　┻　　　┃\n" +
                "    *┃　　　　　　　┃\n" +
                "    *┗━┓　　　┏━┛\n" +
                "    *　　┃　　　┃神兽保佑\n" +
                "    *　　┃　　　┃代码无BUG！\n" +
                "    *　　┃　　　┗━━━┓\n" +
                "    *　　┃　　　　　　　┣┓\n" +
                "    *　　┃　　　　　　　┏┛\n" +
                "    *　　┗┓┓┏━┳┓┏┛\n" +
                "    *　　　┃┫┫　┃┫┫\n" +
                "    *　　　┗┻┛　┗┻┛\n" +
                "    *　　　\n" +
                "    */\n" +
                "\n" +
                "author：yp\n" +
                "link：https://github.com/YPMUXUE/jubilant-umbrella\n" +
                "\n";
        System.out.println(print);
        TimeUnit.SECONDS.sleep(3);
    }
}
