package s4.B183323; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import java.util.Arrays;
import s4.specification.*;

public class InformationEstimator implements InformationEstimatorInterface{
    // Code to tet, *warning: This code condtains intentional problem*
    byte [] myTarget; // data to compute its information quantity
    byte [] mySpace;  // Sample space to compute the probability
    FrequencerInterface myFrequencer;  // Object for counting frequency

    boolean spaceready = false;
    boolean targetready = false;

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
    	if(myTarget.length > 0)
    		targetready = true;
    }

    public void setSpace(byte []space) {
		myFrequencer = new Frequencer();
		mySpace = space; myFrequencer.setSpace(space);
		if(mySpace.length > 0)
	    	spaceready = true;
    }

    public double estimation(){
    	double[] nparray = new double[myTarget.length+1];
    	double value = Double.MAX_VALUE; // value = mininimum of each "value1".
    	double ret = 0.0;
    	int start=0, end=0;

    	if(targetready == false){
    		return 0.0;
    	}
    	if(spaceready == false){
    		return Double.MAX_VALUE;
    	}

    	for(int i=0; i<myTarget.length; i++){
    		end = i+1;
    		if(i == 0){
    			myFrequencer.setTarget(subBytes(myTarget, start, end));
    			nparray[i] = iq(myFrequencer.frequency());
    		}
    		else{
    			for(int j=0; j<end; j++){
    				start = j;
    				myFrequencer.setTarget(subBytes(myTarget, start, end));
    				ret = iq(myFrequencer.frequency());
    				if(start != 0){
    					ret = nparray[j-1] + ret;
    				}
    				if(ret < value){
    					value = ret;
    				}
    			}
    			nparray[i] = value;
    		}
    	}

    	if(Double.isInfinite(nparray[myTarget.length-1])) {
    		return Double.MAX_VALUE;
    	}

		return nparray[myTarget.length-1];
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

	myObject.setTarget("230".getBytes());
	value = myObject.estimation();
	System.out.println(">230 "+value);

  	myObject.setTarget("321".getBytes());
	value = myObject.estimation();
	System.out.println(">321 "+value);

  	myObject.setTarget("01230".getBytes());
	value = myObject.estimation();
	System.out.println(">01230 "+value);

	myObject.setTarget("00".getBytes());
	value = myObject.estimation();
	System.out.println(">00 "+value);

    }
}
