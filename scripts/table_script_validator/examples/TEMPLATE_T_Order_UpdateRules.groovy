// DB Record
// Name: groovy:TEMPLATE_T_Order_UpdateRules
// EventType = T (ModelValidatorTableEvent)
// RuleType = S (JSR223ScriptingAPIs)
import org.compiere.model.MOrder;
import org.compiere.model.MBPartner;
import java.util.Optional;

// Enviroment variables
// A_Ctx: Context (Properties)
// A_PO: Persistence Object (PO)
// A_Type: Event Type (Integer)
// Event: Event (String)

// Example
try {
	MOrder order = (MOrder) A_PO;
	MBPartner businessPartner = MBPartner.get(order.getCtx(), order.getC_BPartner_ID());
	//	Payment Rule
	if(businessPartner.getPaymentRule() != null) {
		order.setPaymentRule(businessPartner.getPaymentRule());
	}
	//	Payment Term
	if(businessPartner.getC_PaymentTerm_ID() > 0) {
		order.setC_PaymentTerm_ID(businessPartner.getC_PaymentTerm_ID());
	}
	//	Delivery Rule
	if(businessPartner.getDeliveryRule() != null) {
		order.setDeliveryRule(businessPartner.getDeliveryRule());
	}
	//	Delivery Via Rule
	if(businessPartner.getDeliveryViaRule() != null) {
		order.setDeliveryViaRule(businessPartner.getDeliveryViaRule());
	}
	//	Invoice Rule
	if(businessPartner.getInvoiceRule() != null) {
		order.setInvoiceRule(businessPartner.getInvoiceRule());
	}
} catch(Exception e) {
	return "@Error@ " + e.getLocalizedMessage();
}