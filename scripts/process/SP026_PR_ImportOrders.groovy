import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;

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

try {

    String clientCheck = " AND o.AD_Client_ID=" + A_ProcessInfo.getParameterAsInt("AD_Client_ID");
    //	for user
    if(A_ProcessInfo.getParameterAsInt("AD_User_ID") != 0) {
        clientCheck += " AND o.CreatedBy = " + A_ProcessInfo.getParameterAsInt("AD_User_ID");
    }

    //BP from GLN
    String sql = "UPDATE I_Order o SET C_BPartner_ID = bpi.BP_ID, BPartnerValue = bpi.BP_Value, C_BPartner_Location_ID = bpi.BP_Location_ID, BillTo_ID = bpi.BP_Location_ID " +
            "FROM (SELECT bp.C_BPartner_ID AS BP_ID, bp.Value AS BP_Value, BPL.C_BPartner_Location_ID AS BP_Location_ID, bpl.SP026_GLN AS GLN, " +
            "bp.AD_Client_ID, bpl.IsShipTo, bpl.IsDefaultShipping, bpl.IsBillTo " +
            "FROM C_BPartner_Location bpl " +
            "INNER JOIN C_BPartner bp ON (bp.C_BPartner_ID = bpl.C_BPartner_ID)) AS bpi " +
            "WHERE bpi.GLN = o.SP026_GLN " +
            "AND o.I_IsImported <> 'Y' " +
            "AND o.AD_Client_ID = bpi.AD_Client_ID " +
            "AND bpi.IsShipTo = 'Y' " + clientCheck;
    DB.executeUpdateEx(sql, A_TrxName);

    sql = "UPDATE I_Order o SET " +
            "M_PriceList_ID = CASE WHEN o.M_PriceList_ID IS NULL THEN bp.M_PriceList_ID ELSE o.M_PriceList_ID END, " +
            "C_PaymentTerm_ID = CASE WHEN o.C_PaymentTerm_ID IS NULL THEN bp.C_PaymentTerm_ID ELSE o.C_PaymentTerm_ID END " +
            "FROM C_BPartner bp " +
            "WHERE o.I_IsImported <> 'Y' " +
            "AND o.C_BPartner_ID = bp.C_BPartner_ID ";
    DB.executeUpdateEx(sql, A_TrxName);
    if (A_Trx != null) {
        A_Trx.commit(true);
    }
    ProcessInfo info = org.eevolution.services.dsl.ProcessBuilder.create(A_Ctx)
            .process("Import_Order")
            .withParameter("AD_Client_ID", A_ProcessInfo.getParameterAsInt("AD_Client_ID"))
            .withParameter("AD_Org_ID", A_ProcessInfo.getParameterAsInt("AD_Org_ID"))
            .withParameter("AD_User_ID", A_ProcessInfo.getParameterAsInt("AD_User_ID"))
            .withParameter("DocAction", A_ProcessInfo.getParameterAsString("DocAction"))
            .withParameter("DeleteOldImported", A_ProcessInfo.getParameterAsBoolean("DeleteOldImported"))
            .withoutTransactionClose()
            .execute(A_TrxName);

    if(info.isError()){
        throw new AdempiereException(info.getSummary());
    }

    result = "";

} catch(Exception e) {
    return "@Error@ " + e.getLocalizedMessage();
}