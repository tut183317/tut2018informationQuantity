package s4.B183364; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
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

  byte [] subBytes(byte [] x, int start, int end) {
	  // corresponding to substring of String for  byte[] ,
    // It is not implement in class library because internal structure of byte[] requires copy.
    int range = end - start;
    byte [] result = new byte[range];
    for(int i = 0; i < range; i++) { result[i] = x[start + i]; };
    return result;
  }

  // IQ: information quantity for a count,  -log2(count/sizeof(space))
  double iq(int freq) {
    return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
  }

  public void setTarget(byte [] target) { myTarget = target;}
  public void setSpace(byte []space) { 
    myFrequencer = new Frequencer();
    mySpace = space; myFrequencer.setSpace(space); 
  }

  public double estimation(){
    if(myTarget == null || myTarget.length == 0) return 0.0;
    if(mySpace == null || mySpace.length == 0) return Double.MAX_VALUE;

    double[] iqSave = new double[myTarget.length];
    double value;
    double tmp_value;
    myFrequencer.setTarget(myTarget);

    for(int i=0; i < myTarget.length; i++){
      value = 0.0;
      for(int j=0; j <= i; j++){
        if(j == 0){
          value = iq(myFrequencer.subByteFrequency(j,i+1));
        } else {
          tmp_value = iqSave[j-1] + iq(myFrequencer.subByteFrequency(j,i+1));
          value = (value < tmp_value) ? value : tmp_value;
          // value = Math.min(value, iqSave[j-1] + iq(myFrequencer.subByteFrequency(j,i+1)));
        }
      }
      iqSave[i] = value;
    }
    double result = iqSave[myTarget.length - 1];
    return Double.isInfinite(result) ? Double.MAX_VALUE : result;
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
				  
			       

	
    
