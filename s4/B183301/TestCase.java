package s4.B183301; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.

import java.lang.*;
import java.util.ArrayList;

import s4.specification.*;
import java.util.function.Supplier;

/*
interface FrequencerInterface {     // This interface provides the design for frequency counter.
    void setTarget(byte[]  target); // set the data to search.
    void setSpace(byte[]  space);  // set the data to be searched target from.
    int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
                    //Otherwise, it return 0, when SPACE is not set or Space's length is zero
                    //Otherwise, get the frequency of TAGET in SPACE
    int subByteFrequency(int start, int end);
    // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
    // For the incorrect value of START or END, the behavior is undefined.
}
*/

/*
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


/**
 * Test Class for Frequencer and Estimator
 */
public class TestCase {

    /**
     * main for test
     */
    public static void main(String[] args) {
        testMain();
        subtest();
    }

    static void subtest(){
    {
            System.out.println("checking s4.B183301.Frequencer");
            FrequencerTest("Hi Ho Hi Ho", "H", 4);
            FrequencerTest(null, "Xx", 0);
            FrequencerTest("", "fefefe", 0);
            FrequencerTest("XXXXfefefrrehg9uewrj", null, -1);
            FrequencerTest("Xdwrjdfefedf", "", -1);
            FrequencerTest(null, null, -1);
            FrequencerTest("Hi Ho Hi Ho", "Hi", 2);
        }
        {
            System.out.println("checking s4.B183301.InformationEstimator");
            InformationEstimatorTest("HiaHic","Hi");
            InformationEstimatorTest("3210321001230123", "0");
            InformationEstimatorTest("3210321001230123", "1");
            InformationEstimatorTest("3210321001230123", "01");
            InformationEstimatorTest("3210321001230123", "2");
            InformationEstimatorTest("3210321001230123", "12");
            InformationEstimatorTest("3210321001230123", "21");
            InformationEstimatorTest("3210321001230123", "02");
            InformationEstimatorTest("3210321001230123", "012");
            InformationEstimatorTest("3210321001230123", "0123");
            InformationEstimatorTest("3210321001230123", "01234",Double.MAX_VALUE);
            InformationEstimatorTest("3210321001230123", "00");
            InformationEstimatorTest("3210321001230123", "3210");
            InformationEstimatorTest("3210321001230123", "ff",Double.MAX_VALUE);
            InformationEstimatorTest("3210321001230123", "3210321001230123");
            InformationEstimatorTest("3210321001230123", null, 0.0);
            InformationEstimatorTest("3210321001230123", "", 0.0);
            InformationEstimatorTest(null, "01", Double.MAX_VALUE);
            InformationEstimatorTest("","fefe",Double.MAX_VALUE);
            InformationEstimatorTest("g","f",Double.MAX_VALUE);
        }

    }

    static void testMain(){
        int c;
        c = 0;
        try {
            FrequencerInterface  myObject;
            int freq;
            c = 0;
            System.out.println("checking Frequencer");
            myObject = new Frequencer();
            freq = myObject.frequency();
            if(-1 != freq) { System.out.println("frequency() should return -1, when target is not set, but returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setTarget("".getBytes());
            freq = myObject.frequency();
            if(-1 != freq) { System.out.println("frequency() should return -1, when target is empty, but return "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAA".getBytes());
            if(-1 != freq) { System.out.println("frequency() for AAA should return -1, when target is  not set. But it returns  "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAA".getBytes());
            myObject.setTarget("".getBytes());
            freq = myObject.frequency();
            if(-1 != freq) { System.out.println("frequency() for AAA should return -1, when taget empty string. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setTarget("A".getBytes());
            freq = myObject.frequency();
            if(0 != freq) { System.out.println("frequency() for not set, should return 0, when taget is not empty. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("".getBytes());
            myObject.setTarget("A".getBytes());
            freq = myObject.frequency();
            if(0 != freq) { System.out.println("frequency() for empty space, should return 0, when taget is not empty. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAA".getBytes());
            myObject.setTarget("A".getBytes());
            freq = myObject.frequency();
            if(3 != freq) { System.out.println("frequency() for AAA, should return 3, when taget is A. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAA".getBytes());
            myObject.setTarget("AA".getBytes());
            freq = myObject.frequency();
            if(2 != freq) { System.out.println("frequency() for AAA, should return 2, when taget is AA. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAA".getBytes());
            myObject.setTarget("AAA".getBytes());
            freq = myObject.frequency();
            if(1 != freq) { System.out.println("frequency() for AAA, should return 1, when taget is AAA. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAA".getBytes());
            myObject.setTarget("AAAA".getBytes());
            freq = myObject.frequency();
            if(0 != freq) { System.out.println("frequency() for AAA, should return 0, when taget is AAAA. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("Hi Ho Hi Ho".getBytes());
            myObject.setTarget("H".getBytes());
            freq = myObject.frequency();
            if(4 != freq) {System.out.println("frequency() for Hi_Ho_Hi_Ho, should return 4, when taget is H. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("Hi Ho Hi Ho".getBytes());
            myObject.setTarget("Ho".getBytes());
            freq = myObject.frequency();
            if(2 != freq) {System.out.println("frequency() for Hi_Ho_Hi_Ho, should return 2, when taget is Ho. But it returns "+freq); c++; }
            /* please note subByteFreqency(0,0) is considered illeagal specification, and you should not include this case */
            myObject = new Frequencer();
            myObject.setSpace("AAAB".getBytes());
            myObject.setTarget("AAAAB".getBytes());
            freq = myObject.subByteFrequency(0,1);
            if(3 != freq) { System.out.println("SubBytefrequency() for AAAB, should return 3, when taget is AAAAB[0:1]. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAAB".getBytes());
            myObject.setTarget("AAAAB".getBytes());
            freq = myObject.subByteFrequency(1,2);
            if(3 != freq) { System.out.println("SubBytefrequency() for AAAB, should return 3, when taget is AAAAB[1:2]. But it returns "+freq); c++; }
            if(2 == freq) { System.out.println("You might be confused by the intentional error in sample code.");   }
            myObject = new Frequencer();
            myObject.setSpace("AAAB".getBytes());
            myObject.setTarget("AAAAB".getBytes());
            freq = myObject.subByteFrequency(1,3);
            if(2 != freq) { System.out.println("SubBytefrequency() for AAAB, should return 2, when taget is AAAAB[1:3]. But it returns "+freq); c++; }
            myObject = new Frequencer();
            myObject.setSpace("AAAB".getBytes());
            myObject.setTarget("AAAAB".getBytes());
            freq = myObject.subByteFrequency(4,5);
            if(1 != freq) {
                System.out.println("SubBytefrequency() for AAAB, should return 1, when taget is AAAAB[4:5]. But it returns "+freq); c++;
            }
        }
        catch(Exception e) {
            System.out.println("Exception occurred in Frequencer Object: STOP");
        }

        try {
            InformationEstimatorInterface myObject;
            double value;
            System.out.println("checking s4.B183301.InformationEstimator");
            myObject = new InformationEstimator();
            myObject.setSpace("3210321001230123".getBytes());
            myObject.setTarget("0".getBytes());
            value = myObject.estimation();
            if((value < 1.9999) || (2.0001 <value)) { System.out.println("IQ for 0 in 3210321001230123 should be 2.0. But it returns "+value); c++; }
            myObject.setTarget("01".getBytes());
            value = myObject.estimation();
            if((value < 2.9999) || (3.0001 <value)) { System.out.println("IQ for 01 in 3210321001230123 should be 3.0. But it returns "+value); c++; }
            myObject.setTarget("0123".getBytes());
            value = myObject.estimation();
            if((value < 2.9999) || (3.0001 <value)) { System.out.println("IQ for 0123 in 3210321001230123 should be 3.0. But it returns "+value); c++; }
            myObject.setTarget("00".getBytes());
            value = myObject.estimation();
            if((value < 3.9999) || (4.0001 <value)) { System.out.println("IQ for 00 in 3210321001230123 should be 4.0. But it returns "+value); c++; }
        }
        catch(Exception e) {
            System.out.println("Exception occurred: STOP");
        }
        if(c == 0) { System.out.println("TestCase OK"); }
    }

    /**
     * Testing Estimator without expected value
     *
     * @param space  Space Data
     * @param target Target Data
     */
    static void InformationEstimatorTest(String space, String target) {
        InformationEstimatorTest(space, target, null);
    }

    /**
     * Testing Estimator with expected value
     *
     * @param space    Space Data
     * @param target   Target Data
     * @param expected expected value for estimated value
     */
    static void InformationEstimatorTest(String space, String target, Double expected) {
        InformationEstimatorInterfaceTest(()-> new s4.B183301.InformationEstimator(),space,target,expected);
    }

    /**
     * Testing Estimator with expected value
     * @param supplier Factory of InformationEstimator
     * @param space    Space Data
     * @param target   Target Data
     * @param expected expected value for estimated value
     */
    static void InformationEstimatorInterfaceTest(Supplier<InformationEstimatorInterface> supplier, String space, String target, Double expected){
        var myObject = supplier.get();
        try {
            if (space != null)
                myObject.setSpace(space.getBytes());
            if (target != null)
                myObject.setTarget(target.getBytes());
            double value = myObject.estimation();
            System.out.printf(">%s %s %s\n", space, target, value);
            if(expected != null){
                if (expected == value) {
                    if(expected == Double.MAX_VALUE){
                        System.out.println("Success expected: Double.MAXVALUE actual: Double.MAXVALUE\n");
                    }else {
                        System.out.printf("Success expected: %f actual: %f\n", expected, value);
                    }
                } else {
                    if(expected == Double.MAX_VALUE){
                        System.out.printf("Failed expected: Double.MAXVALUE actual: %f\n",value);
                    }else {
                        System.out.printf("Failed expected: %f actual: %f\n", expected, value);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed with Error " + myObject.toString() + " " + space);
            e.printStackTrace();
        }
    }

    /**
     * Testing Frequencer
     *
     * @param space    Space Data
     * @param target   Target Data
     * @param expected expected value for estimated value
     */
    static void FrequencerTest(String space, String target, int expected) {
        FrequencerInterfaceTest(()->new Frequencer(),space,target,expected);
    }

    /**
     * Testing Frequencer
     * @param supplier Factory of FrequencerInterface
     * @param space    Space Data
     * @param target   Target Data
     * @param expected expected value for estimated value
     */
    static void FrequencerInterfaceTest(Supplier<FrequencerInterface> supplier, String space, String target, int expected) {
        try {
            var myObject = supplier.get();
            int freq;
            if (space != null) myObject.setSpace(space.getBytes());
            if (target != null) myObject.setTarget(target.getBytes());
            freq = myObject.frequency();
            System.out.printf("\"%s in \"%s\" appears %d times. \n", space, target, freq);
            if (expected == freq) {
                System.out.printf("Success expected: %d actual; %d\n",expected,freq);
            } else {
                System.out.printf("Failed expected: %d actual; %d\n",expected,freq);
            }
        } catch (Exception e) {
            System.out.printf("Failed with Error space: %s target: %s expect: %d \n", space, target, expected);
            e.printStackTrace();
        }
    }

    /**
     * Testing SubFrequencer
     */
    static void SubFrequencerTest(String space, String target, int expected, int start, int end) {
        try {
            int freq;
            var myObject = new s4.B183301.Frequencer();
            if (space != null) myObject.setSpace(space.getBytes());
            if (target != null) myObject.setTarget(target.getBytes());
            freq = myObject.subByteFrequency(start, end);
            System.out.printf("\"%s\" in \"%s\" appears %d times. ", space, target, freq);
            if (expected == freq) {
                System.out.printf("Success expected: %d actual: %d\n",expected,freq);
            } else {
                System.out.printf("Failed expected: %d actual: %d\n",expected,freq);
            }
        } catch (Exception e) {
            System.out.printf("Failed with Error space: %s target: %s expect: %d \n", space, target, expected);
            e.printStackTrace();
        }
    }
}	    
	    
