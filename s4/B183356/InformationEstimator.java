package s4.B183356; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
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
		byte [] result = new byte[end - start];
		for(int i = 0; i<end - start; i++) { 
			result[i] = x[start + i]; 
		};
		return result;
	}
	
    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    double iq(int freq) {
		return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
	}
/*
	double FindMinimumValue(double [] num_list){
		double min = num_list[0];
		for(int index = 1; index < num_list.length; index++){
			min = Math.min(min, num_list[index]);
		}
		return min;
	}
	
	double subiq(int n){
		double [] num = null;
		if(n<=0) return 0.0;
		if(n==1) return(num[n-1] = iq(myFrequencer.subByteFrequency(0, 1)));
		if(num[n-1] >= 0) return num[n-1];

		double [] num1 = new double[n];
		for(int index = 0; index < num1.length; index++){
			num1[index] = subiq(index) + iq(myFrequencer.subByteFrequency(index, n));
		}
		return(num[n-1] = FindMinimumValue(num1));
	}

	public double estimation(){
		double [] value = new double[myTarget.length];
		for(int index = 0; index<myTarget.length; index++){
			value[index] = -1.0;
		}
		return(subiq(myTarget.length));
	}
	*/

	public void setTarget(byte [] target) { myTarget = target; }
    public void setSpace(byte []space) { 
		myFrequencer = new Frequencer();
		mySpace = space; 
		myFrequencer.setSpace(space); 
	}

	
	public double estimation(){
		myFrequencer.setTarget(myTarget);
		double value, value1;
		double [] myEstimation = new double [myTarget.length + 1];
		myEstimation[0] = 0.0;

		for(int n=1; n<=myTarget.length; n++){
			value = Double.MAX_VALUE; // value = mininimum of each "value1".
			for(int start=n-1; start>=0; start--) {
				int freq = myFrequencer.subByteFrequency(start, n);
				value1 = myEstimation[start]+iq(freq);
				if(value>value1) value = value1;
			}
			myEstimation[n] = value;
		}
		return myEstimation[myTarget.length];
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
				  
			       

	
    
