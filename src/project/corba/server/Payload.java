package corba.server;


/**
* corbaserver/Payload.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CorbaServer.idl
* Saturday, October 15, 2011 7:12:31 PM PDT
*/

public final class Payload implements org.omg.CORBA.portable.IDLEntity
{
  public double arrayOne[] = null;
  public double arrayTwo[] = null;
  public double arrayThree[] = null;
  public double arrayFour[] = null;
  public double arrayFive[] = null;

  public Payload ()
  {
  } // ctor

  public Payload (double[] _arrayOne, double[] _arrayTwo, double[] _arrayThree, double[] _arrayFour, double[] _arrayFive)
  {
    arrayOne = _arrayOne;
    arrayTwo = _arrayTwo;
    arrayThree = _arrayThree;
    arrayFour = _arrayFour;
    arrayFive = _arrayFive;
  } // ctor
  
  public void setOne(double[]arr){
	  arrayOne = arr;
  }
  
  public void setTwo(double[]arr){
	  arrayTwo = arr;
  }

  public void setThree(double[]arr){
	  arrayThree = arr;
  }

  public void setFour(double[]arr){
	  arrayFour = arr;
  }

  public void setFive(double[]arr){
	  arrayFive = arr;
  }
} // class Payload
