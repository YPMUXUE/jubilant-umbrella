package umbrella.bootstrap;

import umbrella.config.ConfigReader;
import umbrella.connection.proxy.config.ProxyConfig;
import umbrella.producer.Start;
import umbrella.producer.pixiv.PixivStart;
import umbrella.saver.config.SaverConfig;
import umbrella.task.Receiver;
import umbrella.task.config.TaskConfig;
import umbrella.task.impl.ReceiverImpl;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GetStart {
    private static String configPath;

    static {
        try {
            configPath=new File("").getCanonicalPath()+"\\Config.xml";
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args)throws Exception{
        print();
        args=new String[]{"490219","true"};
        ConfigReader configReader=new ConfigReader();
        configReader.initConfig(configPath);

        InputParameter parameter=readArgs(args);

        Start start=new PixivStart();
        start.start(parameter);
        Receiver receiver=new ReceiverImpl();
        start.save(receiver);
        SaverConfig saverConfig=new SaverConfig();
        System.out.println(saverConfig.toString());
        ProxyConfig proxyConfig=new ProxyConfig();
        System.out.println(proxyConfig.toString());
        TaskConfig taskConfig=new TaskConfig();
        System.out.println(taskConfig.toString());
    }

    private static InputParameter readArgs(String[] args) {
        InputParameter parameter=new InputParameter();
        if (args.length==0) {
            System.out.println("userid can not be null");
            System.exit(1);
        }else if (args.length==2){
            parameter.setUserid(args[0]);
            parameter.setProxy(args[1]);
        }else if(args.length==1){
            parameter.setUserid(args[0]);
        }
        System.out.println("sessionValue:");
        Scanner scanner=new Scanner(System.in);
        parameter.setSessionValue(scanner.nextLine());
        return parameter;
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
