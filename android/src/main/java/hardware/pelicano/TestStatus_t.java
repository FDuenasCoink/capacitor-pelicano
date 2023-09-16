/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package hardware.pelicano;

public class TestStatus_t {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected TestStatus_t(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(TestStatus_t obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(TestStatus_t obj) {
    long ptr = 0;
    if (obj != null) {
      if (!obj.swigCMemOwn)
        throw new RuntimeException("Cannot release ownership as memory is not owned");
      ptr = obj.swigCPtr;
      obj.swigCMemOwn = false;
      obj.delete();
    }
    return ptr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        PelicanoControlJNI.delete_TestStatus_t(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setVersion(String value) {
    PelicanoControlJNI.TestStatus_t_Version_set(swigCPtr, this, value);
  }

  public String getVersion() {
    return PelicanoControlJNI.TestStatus_t_Version_get(swigCPtr, this);
  }

  public void setDevice(int value) {
    PelicanoControlJNI.TestStatus_t_Device_set(swigCPtr, this, value);
  }

  public int getDevice() {
    return PelicanoControlJNI.TestStatus_t_Device_get(swigCPtr, this);
  }

  public void setErrorType(int value) {
    PelicanoControlJNI.TestStatus_t_ErrorType_set(swigCPtr, this, value);
  }

  public int getErrorType() {
    return PelicanoControlJNI.TestStatus_t_ErrorType_get(swigCPtr, this);
  }

  public void setErrorCode(int value) {
    PelicanoControlJNI.TestStatus_t_ErrorCode_set(swigCPtr, this, value);
  }

  public int getErrorCode() {
    return PelicanoControlJNI.TestStatus_t_ErrorCode_get(swigCPtr, this);
  }

  public void setMessage(String value) {
    PelicanoControlJNI.TestStatus_t_Message_set(swigCPtr, this, value);
  }

  public String getMessage() {
    return PelicanoControlJNI.TestStatus_t_Message_get(swigCPtr, this);
  }

  public void setAditionalInfo(String value) {
    PelicanoControlJNI.TestStatus_t_AditionalInfo_set(swigCPtr, this, value);
  }

  public String getAditionalInfo() {
    return PelicanoControlJNI.TestStatus_t_AditionalInfo_get(swigCPtr, this);
  }

  public void setPriority(int value) {
    PelicanoControlJNI.TestStatus_t_Priority_set(swigCPtr, this, value);
  }

  public int getPriority() {
    return PelicanoControlJNI.TestStatus_t_Priority_get(swigCPtr, this);
  }

  public TestStatus_t() {
    this(PelicanoControlJNI.new_TestStatus_t(), true);
  }

}
