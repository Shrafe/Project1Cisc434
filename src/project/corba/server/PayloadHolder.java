package corba.server;

/**
* corbaserver/PayloadHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CorbaServer.idl
* Saturday, October 15, 2011 7:12:31 PM PDT
*/

public final class PayloadHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.server.Payload value = null;

  public PayloadHolder ()
  {
  }

  public PayloadHolder (corba.server.Payload initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.server.PayloadHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.server.PayloadHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.server.PayloadHelper.type ();
  }

}
