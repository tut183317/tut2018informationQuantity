package s4.B183345; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 

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

public class InformationEstimator implements InformationEstimatorInterface {
    // Code to tet, *warning: This code condtains intentional problem*
    byte[] myTarget; // data to compute its information quantity
    byte[] mySpace; // Sample space to compute the probability
    FrequencerInterface myFrequencer; // Object for counting frequency
    double[] values;

    byte[] subBytes(byte[] x, int start, int end) {
        // corresponding to substring of String for byte[] ,
        // It is not implement in class library because internal structure of byte[]
        // requires copy.
        byte[] result = new byte[end - start];
        for (int i = 0; i < end - start; i++) {
            result[i] = x[start + i];
        }
        return result;
    }

    // IQ: information quantity for a count, -log2(count/sizeof(space))
    double iq(int freq) {
        return -Math.log10((double) freq / (double) mySpace.length) / Math.log10((double) 2.0);
    }

    double myiq(int n) {
        // nはtargetの文字数である．
        if (n <= 0) {
            return 0.0;
        }
        if (n == 1) {
	    double tmp = iq(myFrequencer.subByteFrequency(0, n));
	    if(Double.isInfinite(tmp)){
		tmp = Double.MAX_VALUE;
	    }
            return (values[n - 1] = tmp);
        }
        if (values[n - 1] > -1.0) {
            return values[n - 1];
        }

	double tmp1 = Double.MAX_VALUE;
	double tmp2;
        for (int i = n - 1; i >= 0; i--) {
            tmp2 = myiq(i) + iq(myFrequencer.subByteFrequency(i, n));
	    if(Double.isInfinite(tmp2)){
		tmp2 = Double.MAX_VALUE;
	    }
	    if(tmp1 > tmp2){
		tmp1 = tmp2;
	    }
        }
        return (values[n - 1] = tmp1);
    }

    public void setTarget(byte[] target) {
        myTarget = target;
        myFrequencer.setTarget(target);
    }

    public void setSpace(byte[] space) {
        myFrequencer = new Frequencer();
        mySpace = space;
        myFrequencer.setSpace(space);
    }

    public double estimation() {
	if(myTarget.length == 0 || myTarget == null){
	    return 0.0;
	}
	if(mySpace == null){
	    return Double.MAX_VALUE;
	}
        values = new double[myTarget.length];
        for (int i = 0; i < myTarget.length; i++) {
            values[i] = -1.0;
        }

	double tmp;
	tmp = myiq(myTarget.length);
	if(Double.isInfinite(tmp)){
	    tmp = Double.MAX_VALUE;
	}
        return (tmp);
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
