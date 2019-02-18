package s4.B183306; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
import java.lang.*;
import s4.specification.*;

/* What is imported from s4.specification
package s4.specification;
public interface InformationEstimatorInterface{
    void setTarget(byte target[]); // set the data for computing the information quantities
    void setSpace(byte space[]); // set data for sample space to computer probability
    double estimation(); // It returns 0.0 when the target is not set or Target's length is zero;
// It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
// The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
// Note that this happens only when the space is unreasonably large. We will encounter other problem anyway.
// Otherwise, estimation of information quantity, 
}                        
*/

public class InformationEstimator implements InformationEstimatorInterface{
    // Code to tet, *warning: This code condtains intentional problem*
    byte [] myTarget; // data to compute its information quantity
    byte [] mySpace;  // Sample space to compute the probability
    FrequencerInterface myFrequencer;  // Object for counting frequency
    boolean t_flag = false; //ターゲットがセットされている時:1 されていない時:0
    boolean s_flag = false; //スペースがセットされている時:1 されていない時:0

    byte [] subBytes(byte [] x, int start, int end) {
        // corresponding to substring of String for  byte[] ,
        // It is not implement in class library because internal structure of byte[] requires copy.
        byte [] result = new byte[end - start];
        for(int i = 0; i<end - start; i++) { result[i] = x[start + i]; };
        return result;
    }

    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    double iq(int freq) {
        return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
    }

    public void setTarget(byte [] target) {
        myTarget = target;
        if(myTarget.length > 0){
            t_flag = true;
        }
    }
    public void setSpace(byte []space) { 
        myFrequencer = new Frequencer();
        mySpace = space; myFrequencer.setSpace(space);
        if(mySpace.length > 0){
            s_flag = true;
        }
    }

    public double estimation(){
        if(t_flag == false || s_flag == false){
            return 0.0;
        }
        //valueの値を保存する配列
        double[] value_store_array = new double[myTarget.length+1];
        value_store_array[0] = 0.0;
        //myTargetをセット
        myFrequencer.setTarget(myTarget);
        
        for(int i = 1; i < myTarget.length; i++){
            double value = Double.MAX_VALUE;
            for(int j = 0; j < i; j++){
                int frequence = myFrequencer.subByteFrequency(i,j);
                if(frequence == 0){
                    break;
                }
                double value1 = value_store_array[j] + iq(frequence);
                if(value > value1){
                    value = value1;
                }
            }
            value_store_array[i] = value;
        }
        return value_store_array[value_store_array.length];
    }

    public static void main(String[] args) {
        InformationEstimator myObject;
        double value;
        myObject = new InformationEstimator();
        myObject.setSpace("3210321001230123".getBytes());
        myObject.setTarget("0".getBytes());
        value = myObject.estimation();
        System.out.println(">0 "+value);
        myObject.setTarget("01".getBytes());
        value = myObject.estimation();
        System.out.println(">01 "+value);
        myObject.setTarget("0123".getBytes());
        value = myObject.estimation();
        System.out.println(">0123 "+value);
        myObject.setTarget("00".getBytes());
        value = myObject.estimation();
        System.out.println(">00 "+value);
    }
}
				  
			       

	
    
