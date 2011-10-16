package corba.server;


/**
* corbaserver/arrayHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CorbaServer.idl
* Saturday, October 15, 2011 7:12:31 PM PDT
*/

abstract public class arrayHelper
{
  private static String  _id = "IDL:corbaserver/array:1.0";

  public static void insert (org.omg.CORBA.Any a, double[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static double[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
      __typeCode = org.omg.CORBA.ORB.init ().create_array_tc (1000, __typeCode );
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (corba.server.arrayHelper.id (), "array", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static double[] read (org.omg.CORBA.portable.InputStream istream)
  {
    double value[] = null;
    value = new double[1000];
    for (int _o0 = 0;_o0 < (1000); ++_o0)
    {
      value[_o0] = istream.read_double ();
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, double[] value)
  {
    if (value.length != (1000))
      throw new org.omg.CORBA.MARSHAL (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    for (int _i0 = 0;_i0 < (1000); ++_i0)
    {
      ostream.write_double (value[_i0]);
    }
  }

}
