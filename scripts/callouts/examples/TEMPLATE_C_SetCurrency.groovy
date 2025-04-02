// DB Record
// Name: groovy:TEMPLATE_C_SetCurrency
// EventType = C (Callout)
// RuleType = S (JSR223ScriptingAPIs)
import org.compiere.model.MPriceList;

// Create it for field using this pattern: "@script:<Callout Code>" => "@script:groovy:TEMPLATE_C_SetCurrency"
// Enviroment variables
// A_WindowNo (Integer)
// A_Tab: Tab (GridTab)
// A_Field (GridField)
// A_Value (Object)
// A_OldValue (Object)
// A_Ctx: Context (Properties)
// result: Result Message (String)

// Example

if(A_Value != null) {
  int priceListId = (Integer) A_Value;
  MPriceList priceList = MPriceList.get(A_Ctx, priceListId, null);
  if(priceList != null) {
    A_Tab.setValue("C_Currency_ID", priceList.getC_Currency_ID());
  }
}
result = "";