package s4.B183301; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 

import java.lang.*;

import s4.specification.*;

import static s4.B183301.TestCase.InformationEstimatorTest;

/** What is imported from s4.specification
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
    byte[] mySpace;  // Sample space to compute the probability
    FrequencerInterface myFrequencer;  // Object for counting frequency

    byte[] subBytes(byte[] x, int start, int end) {
        // corresponding to substring of String for  byte[] ,
        // It is not implement in class library because internal structure of byte[] requires copy.
        byte[] result = new byte[end - start];
        for (int i = 0; i < end - start; i++) {
            result[i] = x[start + i];
        }
        return result;
    }

    static final double log2 = Math.log10(2.0);

    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    double iq(final int freq) {
        return -Math.log10((double) freq / (double) mySpace.length) / log2;
    }

    /**
     * Set the data for computing the information quantities.
     *
     * @param target the data
     */
    public void setTarget(final byte[] target) {
        myTarget = target;
    }

    /**
     * Set the data for sample space to computer probability.
     *
     * @param space the data
     */
    public void setSpace(final byte[] space) {
        myFrequencer = new Frequencer();
        mySpace = space;
        myFrequencer.setSpace(space);
    }

    /**
     * Estimate information quantity.
     * <p>
     * It returns 0.0 when the TARGET is not set or TARGET's length is zero;
     * It returns Double.MAX_VALUE when the true value is infinite, or SPACE is not set.
     * The behavior is undefined if the true value is finite but larger than Double.MAX_VALUE.
     * Note that this happens only when the SPACE is unreasonably large.
     * We will encounter other problem anyway.
     * Otherwise, it returns the estimation value of information quantity.
     * <p>
     * Information quantity I(S) of string S is defined as follows:
     * <p>
     * I(S) = - \sum_{i=0}^{N} log2 P(ci)
     * <p>
     * where, ci is a i-th character in string S,
     * N is the length of String S,
     * and P(c) is the probability of character c in string S.
     *
     * @return estimated information quantity
     */
    public double estimation() {
        if (myTarget == null || myTarget.length == 0)
            return 0.0;
        if (mySpace == null || mySpace.length == 0)
            return Double.MAX_VALUE;
        final var cash = new double[myTarget.length + 1];
        myFrequencer.setTarget(myTarget);
        for (int i = 1; i <= myTarget.length; i++){
            var min = Double.MAX_VALUE;
            //int freqx = myFrequencer.subByteFrequency(0, i);
            //var iqn = iq(freqx);
            //var min = iqn; ここのコメントアウトを外すなら下のfor loopは j > 0
            //コメントアウトを外すとMath.minが一回減るが,i=1の時のfreqxが0の時の対応がないと初めの一個だけないbyte列のとき遅い
            for (int j = i - 1; j >= 0; j--) {
                final var freq = myFrequencer.subByteFrequency(j, i);
                if (freq == 0){
                    if(j == i - 1)//一文字のfreqが0なら結果は必ずMAX_VALUE
                        return Double.MAX_VALUE;
                    break;
                }
                min = Math.min(min, iq(freq) + cash[j]);
            }
            cash[i] = min;
        }
        var value = cash[myTarget.length];
        return Double.isInfinite(value) ? Double.MAX_VALUE : value;
    }

    public static void main(String[] args) {
        try {
            InformationEstimatorInterface myObject;
            double value;
            System.out.println("checking s4.B183301.InformationEstimator");
            InformationEstimatorTest("3210321001230123","0");
            InformationEstimatorTest("3210321001230123","01");
            InformationEstimatorTest("3210321001230123","0123");
            InformationEstimatorTest("3210321001230123","00");
            InformationEstimatorTest("3210321001230123","0012");
        }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
        }
    }
}
				  
			       

	
    
