// DB Record
// Name: groovy:TEMPLATE_SB_<Name>
// EventType = P (Process)
// RuleType = S (JSR223ScriptingAPIs)
// Imports Here

// Enviroment variables
// A_Ctx: Context (Properties)
// A_Trx: Transaction (Trx)
// A_TrxName: Transaction Name (String)
// A_Record_ID: Record ID (Integer)
// A_AD_Client_ID: Client ID (Integer)
// A_AD_User_ID: Instance User ID (Integer)
// A_AD_PInstance_ID: Instance ID (Integer)
// A_Table_ID: Table ID (Integer)
// A_ProcessInfo: (ProcessInfo)

//  Get a Parameter
// Integer: A_ProcessInfo.getParameterAsInt("")
// String: A_ProcessInfo.getParameterAsString("")
// BigDecimal: A_ProcessInfo.getParameterAsBigDecimal("")
// Timestamp: A_ProcessInfo.getParameterAsTimestamp("")
// Object: A_ProcessInfo.getParameter("")

// Get Parameter To
// Integer: A_ProcessInfo.getParameterToAsInt("")
// String: A_ProcessInfo.getParameterToAsString("")
// BigDecimal: A_ProcessInfo.getParameterToAsBigDecimal("")
// Timestamp: A_ProcessInfo.getParameterToAsTimestamp("")
// Object: A_ProcessInfo.getParameterTo("")

// The throw exception should be used as result = "@Error@ <Error>"

// result: Result Message (String)

// Code Here

try {
  if(A_ProcessInfo != null) {
    if(A_ProcessInfo.getSelectionKeys() != null) {
      for (int keyId : A_ProcessInfo.getSelectionKeys()) {
        
      }
    }
  }
  result = "";
} catch (Exception e) {
  return "@Error@ " + e.getLocalizedMessage();
}