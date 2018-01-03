package umbrella.bootstrap;

public class ParameterFactory {
    private static InputParameter parameter;
    public static InputParameter getInputParameter(){
        return parameter;
    }
    static void setInputParameter(InputParameter p){
        parameter=p;
    }
}
