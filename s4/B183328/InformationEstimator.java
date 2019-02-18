package s4.B183328; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
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

    boolean target_flag = false;
    boolean space_flag = false;

    byte [] subBytes(byte [] x, int start, int end) {
    	// corresponding to substring of String for  byte[] ,
    	// It is not implement in class library because internal structure of byte[] requires copy.
    	byte [] result = new byte[end - start];
    	for(int i = 0; i<end - start; i++) { result[i] = x[start + i]; };
    	return result;
    }

    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    //情報量を求めている．
    double iq(int freq) {
	     return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
    }

    public void setTarget(byte [] target) {
       myTarget = target;
       if(target.length>0){
         target_flag = true;
       }

     }
    public void setSpace(byte []space) {
    	myFrequencer = new Frequencer();
    	mySpace = space; myFrequencer.setSpace(space);
      if(space.length>0){
        space_flag = true;
      }
    }



    //ここを書き直す
    public double estimation(){

        double [] Iq = new double[myTarget.length+1];
        int start= 0;
        int end = 0;
        double value=Double.MAX_VALUE;
        double value_t=0;
        double result;

        if(target_flag == false){
          System.out.println("target-f = false");
          return 0.0;
        }

        if(space_flag == false){
          System.out.println("space-f = false");
          return Double.MAX_VALUE;

        }


        for(int i=0;i<myTarget.length;i++){
          value = Double.MAX_VALUE;
          end = i+1;
          //最初の一文字目
          if(i==0){
            //最初の一文字めの情報量
            myFrequencer.setTarget(subBytes(myTarget, start, end));
            result = myFrequencer.frequency();

            if(result == -1){
              System.out.println("result = -1");
               result = 0.0;
            }
            else if(result == 0){
              System.out.println("result = 0");
              result = Double.MAX_VALUE;
            }

            else{

              result = iq((int)result);
              // System.out.println("result = "+result);
            }

        		Iq[i] = result;
          }

          else{
            //一番小さい情報量を求める
            for(int j=0;j<end;j++){
              start=j;

              myFrequencer.setTarget(subBytes(myTarget, start, end));
              value_t = myFrequencer.frequency();
              if(value_t == -1){
                // System.out.println("result = -1");
                 value_t = 0.0;
              }
              else if(value_t == 0.0){
                // System.out.println("result = 0");
                value_t = Double.MAX_VALUE;
              }

              else{
                value_t = iq((int)value_t);
              }

                //全ての文字列（f(abc)みたいな）じゃないとき
                if(j!=0){
                  value_t = Iq[j-1]+value_t;
                }

                if(value_t<value){
                  value = value_t;
                }
              }
              Iq[i] = value;
            }
          }
          // System.out.println("Iq["+i+"]:"+Iq[i]);

        target_flag = false;
      	return Iq[myTarget.length-1];

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

      myObject.setTarget("1".getBytes());
    	value = myObject.estimation();
    	System.out.println(">1 "+value);

      myObject.setTarget("11".getBytes());
    	value = myObject.estimation();
    	System.out.println(">11 "+value);

      myObject.setTarget("".getBytes());
    	value = myObject.estimation();
    	System.out.println("> "+value);

      myObject.setTarget("9".getBytes());
    	value = myObject.estimation();
    	System.out.println(">9 "+value);
    }
}
