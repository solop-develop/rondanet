// DB Record
// Name: groovy:TEMPLATE_D_Process_Shipment
// EventType = D (ModelValidatorDocumentEvent)
// RuleType = S (JSR223ScriptingAPIs)
import org.compiere.model.MOrder;
import org.compiere.util.DB;

// Enviroment variables
// A_Ctx: Context (Properties)
// A_PO: Persistence Object (PO)
// A_Type: Event Type (Integer)
// Event: Event (String)

// Example
MOrder order = A_PO;
try {
  if(!order.isSOTrx()) {
    return "";
  }
  int updates = DB.executeUpdate("UPDATE FPLE01_ShipmentPlanningLine SET Processed = 'Y' WHERE C_Order_ID = ?", order.getC_Order_ID(), order.get_TrxName());
  result = "";
} catch(Exception e) {
	return "@Error@ " + e.getLocalizedMessage();
}