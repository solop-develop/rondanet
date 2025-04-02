// DB Record
// Name: groovy:TEMPLATE_SB_UnassignOrderfromPS
// EventType = P (Process)
// RuleType = S (JSR223ScriptingAPIs)
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;

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

// Example

try {
  if(A_ProcessInfo != null) {
    if(A_ProcessInfo.getSelectionKeys() != null) {
      PO shipmentPlanning = MTable.get(A_Ctx, "FPLE01_ShipmentPlanning").getPO(A_Record_ID, A_TrxName);
      shipmentPlanning.set_ValueOfColumn("IsUpdateOrderLine", false);
      shipmentPlanning.saveEx();
      for (int keyId : A_ProcessInfo.getSelectionKeys()) {    
        MOrder order = new MOrder(A_Ctx, keyId, A_TrxName);
        MOrderLine[] orderLines = order.getLines();      
        if(orderLines != null) {        
          for (MOrderLine ol : orderLines) {
              PO planningLine = new Query(A_Ctx, "FPLE01_ShipmentPlanningLine", "C_Order_ID = ? AND C_OrderLine_ID = ?", A_TrxName).setParameters(ol.getC_Order_ID(), ol.getC_OrderLine_ID()).setClient_ID().first();
              if (planningLine != null){                 
                  planningLine.set_ValueOfColumn("IsUpdateOrderLine", false);
                  planningLine.set_ValueOfColumn("Processed", false);
                  planningLine.saveEx();
                  planningLine.deleteEx(true);
              }
          }
        }
      }
      //  Update Order by Default
      shipmentPlanning.set_ValueOfColumn("IsUpdateOrderLine", true);
      shipmentPlanning.saveEx();
    }
  }
  result = "";
} catch (Exception e) {
  return "@Error@ " + e.getLocalizedMessage();
}