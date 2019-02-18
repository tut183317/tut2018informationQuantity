import javax.print.DocFlavor.STRING;

public class test{
    public static void main(String[] args) {
        String str = "Hi Ho Hi Ho";
        byte[] sbyte = str.getBytes();
        for(int i=0; i<sbyte.length; i++) {
            System.out.println(sbyte[i]);
        }
    }
}