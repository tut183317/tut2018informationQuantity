package s4.B183329; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
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
    boolean targetReady = false;
    boolean spaceReady = false;
    FrequencerInterface myFrequencer;  // Object for counting frequency

    byte [] subBytes(byte [] x, int start, int end) {
	// corresponding to substring of String for  byte[] ,
	// It is not implement in class library because internal structure of byte[] requires copy.
	byte [] result = new byte[end - start];
	for(int i = 0; i<end - start; i++) { result[i] = x[start + i]; };
	return result;
    }

    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    double iq(int freq) {
        if(freq == 0){
            return Double.MAX_VALUE;
        }else{
            return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
        }
    }

    public void setTarget(byte [] target) {
        myTarget = target;
        if(myTarget.length>0) targetReady = true;
    }
    public void setSpace(byte []space) { 
        myFrequencer = new Frequencer();
        mySpace = space; myFrequencer.setSpace(space);
        if(mySpace.length>0) spaceReady = true;
    }

    public double estimation(){
        //Targetとspaceの異常を検出
        if(targetReady == false) return 0;
        if(spaceReady == false) return Double.MAX_VALUE;
        
        int i=0;
        double IQ[] = new double[myTarget.length+1]; //IQ[N]は、先頭からN文字目までの情報量
        double f[] = new double[myTarget.length-1]; //再帰定義に使うf
        double min_can[] = new double[myTarget.length-1]; //minの候補
        double min = Double.MAX_VALUE; //情報量の最小値
        double origin = -1; //分割しないときの情報量
        //(IQ[N]=N文字目までの情報量)とするため、IQ[0]は使用しない
        IQ[0] = -1;

        //Targetをセット(以降はsubByteFrequencyで部分文字列を探索する)
        myFrequencer.setTarget(myTarget);
        //IQ[1]のときは無分割
        IQ[1] = iq(myFrequencer.subByteFrequency(0,1));
        
        //IQ[N+1]を求めるためのループ
        for(i=2;i<myTarget.length+1;i++){
            //無分割のときの情報量をとりあえず最小値とする
            min = iq(myFrequencer.subByteFrequency(0, i));
            //分割のパターンいろいろ
            for(int j=0;j<i-1;j++){
                f[j] = iq(myFrequencer.subByteFrequency(i-j-1, i));
                min_can[j] = IQ[i-j-1] + f[j];
                //情報量の最小値を更新
                if(min > min_can[j]){
                    min = min_can[j];
                }
            }
            //情報量が最小になるときの分割はIQ[i]
            IQ[i] = min;
        }
        //Targetの情報量を返す
        return IQ[myTarget.length];
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
				  
			       

	
    
