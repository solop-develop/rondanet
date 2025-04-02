CREATE OR REPLACE VIEW TEMPLATE_RV_FO_Summary AS
SELECT io.AD_Client_ID, io.AD_Org_ID, io.Created, io.CreatedBy, io.IsActive, io.Updated, io.UpdatedBy, io.UUID, p.Value, p.Name, iol.M_Product_ID, io.WM_InOutBound_ID, p.UnitsPerPallet,
  FLOOR(CASE WHEN COALESCE(p.UnitsPerPallet, 0) > 0 THEN SUM(iol.MovementQty) / p.UnitsPerPallet ELSE 0 END) AS QtyBatchs, 
  (SUM(iol.MovementQty) - (FLOOR(CASE WHEN COALESCE(p.UnitsPerPallet, 0) > 0 THEN SUM(iol.MovementQty) / p.UnitsPerPallet ELSE 0 END) * COALESCE(p.UnitsPerPallet, 0))) AS PickedQty, 
  SUM(iol.MovementQty) AS Qty
  FROM WM_InOutBound io
  INNER JOIN WM_InOutBoundLine iol ON(iol.WM_InOutBound_ID = io.WM_InOutBound_ID)
  INNER JOIN M_Product p ON(p.M_Product_ID = iol.M_Product_ID)
GROUP BY io.AD_Client_ID, io.AD_Org_ID, io.Created, io.CreatedBy, io.IsActive, io.Updated, io.UpdatedBy, io.UUID, p.Value, p.Name, iol.M_Product_ID, io.WM_InOutBound_ID, p.UnitsPerPallet
;