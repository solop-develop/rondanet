// DB Record
// Name: groovy:TEMPLATE_PR_ProcessCommercialRelation
// EventType = P (Process)
// RuleType = S (JSR223ScriptingAPIs)
import java.util.List;
import org.compiere.model.Query;
import org.compiere.model.PO;
import org.compiere.model.MTable;
import org.compiere.model.MProject;
import java.sql.Timestamp;

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

int relationId = A_Record_ID;
//  Validate Record ID (Order)
if(relationId <= 0) {
  return "@Error@ @FPLE01_CommercialRelation_ID@ @IsMandatory@";
}
//	Validate Lines
try {
	PO commercialRelation = MTable.get(A_Ctx, "FPLE01_CommercialRelation").getPO(relationId, A_TrxName);
	//	Close Project
	int projectId = new Query(A_Ctx, "C_Project", "FPLE01_CommercialRelation_ID = ?", A_TrxName).setParameters(relationId).setClient_ID().firstId();
	if(projectId > 0) {
		MProject project = new MProject(A_Ctx, projectId, A_TrxName);
		project.set_ValueOfColumn("Processed", true);
		project.saveEx();
	}
	//	Update Commercial Relation
	commercialRelation.set_ValueOfColumn("IsClosed", true);
	commercialRelation.set_ValueOfColumn("CloseDate", new Timestamp(System.currentTimeMillis()));
	commercialRelation.saveEx();
	result = "";
} catch(Exception e) {
	return "@Error@ " + e.getLocalizedMessage();
}