/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.1.1
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package hardware.pelicano;

public class PelicanoControlClass {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected PelicanoControlClass(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PelicanoControlClass obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(PelicanoControlClass obj) {
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
        PelicanoControlJNI.delete_PelicanoControlClass(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setPortO(int value) {
    PelicanoControlJNI.PelicanoControlClass_PortO_set(swigCPtr, this, value);
  }

  public int getPortO() {
    return PelicanoControlJNI.PelicanoControlClass_PortO_get(swigCPtr, this);
  }

  public void setInsertedCoins(long value) {
    PelicanoControlJNI.PelicanoControlClass_InsertedCoins_set(swigCPtr, this, value);
  }

  public long getInsertedCoins() {
    return PelicanoControlJNI.PelicanoControlClass_InsertedCoins_get(swigCPtr, this);
  }

  public void setWarnToCritical(int value) {
    PelicanoControlJNI.PelicanoControlClass_WarnToCritical_set(swigCPtr, this, value);
  }

  public int getWarnToCritical() {
    return PelicanoControlJNI.PelicanoControlClass_WarnToCritical_get(swigCPtr, this);
  }

  public void setMaxCritical(int value) {
    PelicanoControlJNI.PelicanoControlClass_MaxCritical_set(swigCPtr, this, value);
  }

  public int getMaxCritical() {
    return PelicanoControlJNI.PelicanoControlClass_MaxCritical_get(swigCPtr, this);
  }

  public void setPath(String value) {
    PelicanoControlJNI.PelicanoControlClass_Path_set(swigCPtr, this, value);
  }

  public String getPath() {
    return PelicanoControlJNI.PelicanoControlClass_Path_get(swigCPtr, this);
  }

  public void setLogLvl(int value) {
    PelicanoControlJNI.PelicanoControlClass_LogLvl_set(swigCPtr, this, value);
  }

  public int getLogLvl() {
    return PelicanoControlJNI.PelicanoControlClass_LogLvl_get(swigCPtr, this);
  }

  public void setMaximumPorts(int value) {
    PelicanoControlJNI.PelicanoControlClass_MaximumPorts_set(swigCPtr, this, value);
  }

  public int getMaximumPorts() {
    return PelicanoControlJNI.PelicanoControlClass_MaximumPorts_get(swigCPtr, this);
  }

  public void setGlobals(GlobalVariables value) {
    PelicanoControlJNI.PelicanoControlClass_Globals_set(swigCPtr, this, GlobalVariables.getCPtr(value), value);
  }

  public GlobalVariables getGlobals() {
    long cPtr = PelicanoControlJNI.PelicanoControlClass_Globals_get(swigCPtr, this);
    return (cPtr == 0) ? null : new GlobalVariables(cPtr, false);
  }

  public PelicanoControlClass() {
    this(PelicanoControlJNI.new_PelicanoControlClass(), true);
  }

  public void InitLog() {
    PelicanoControlJNI.PelicanoControlClass_InitLog(swigCPtr, this);
  }

  public Response_t Connect() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_Connect(swigCPtr, this), true);
  }

  public Response_t CheckDevice() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_CheckDevice(swigCPtr, this), true);
  }

  public Response_t StartReader() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_StartReader(swigCPtr, this), true);
  }

  public CoinError_t GetCoin() {
    return new CoinError_t(PelicanoControlJNI.PelicanoControlClass_GetCoin(swigCPtr, this), true);
  }

  public CoinLost_t GetLostCoins() {
    return new CoinLost_t(PelicanoControlJNI.PelicanoControlClass_GetLostCoins(swigCPtr, this), true);
  }

  public Response_t ModifyChannels(int InhibitMask1, int InhibitMask2) {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_ModifyChannels(swigCPtr, this, InhibitMask1, InhibitMask2), true);
  }

  public Response_t StopReader() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_StopReader(swigCPtr, this), true);
  }

  public Response_t ResetDevice() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_ResetDevice(swigCPtr, this), true);
  }

  public Response_t CleanDevice() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_CleanDevice(swigCPtr, this), true);
  }

  public Response_t GetInsertedCoins() {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_GetInsertedCoins(swigCPtr, this), true);
  }

  public TestStatus_t TestStatus() {
    return new TestStatus_t(PelicanoControlJNI.PelicanoControlClass_TestStatus(swigCPtr, this), true);
  }

  public Response_t CheckCodes(int Check) {
    return new Response_t(PelicanoControlJNI.PelicanoControlClass_CheckCodes(swigCPtr, this, Check), true);
  }

}
