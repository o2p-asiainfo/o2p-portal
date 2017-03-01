package com.ailk.o2p.portal.dao.price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.RsSysCellDef;
import com.ailk.eaap.op2.bo.TimeSegDef;
import com.ailk.eaap.op2.bo.TimeSegDtl;

@SuppressWarnings("all")
public interface IAttrSpecDao {
	public List<TimeSegDef> queryTimeSeqDef(Map map);
	public Integer countTimeSeqDef(Map map);
	public Integer addTimeSeqDef(TimeSegDef timeSeqDef);
	public Integer updateTimeSeqDef(TimeSegDef timeSeqDef);
	
	
	public List<TimeSegDtl> queryTimeSeqDtl(Map map);
	public Integer addTimeSeqDtl(TimeSegDtl timeSeqDtl);
	public Integer updateTimeSeqDtl(TimeSegDtl timeSeqDtl);
	
	public List<RsSysCellDef> queryRsSysCellDef(Map map);
	public Integer countRsSysCellDef(Map map);
	
	public Integer addPricngPlanAttr(PricngPlanAttr pricngPlanAttr);
	public Integer updatePricngPlanAttr(PricngPlanAttr pricngPlanAttr);
	public List<PricngPlanAttr> queryPricngPlanAttr(PricngPlanAttr pricngPlanAttr);
	public List<RsSysCellDef> selRsSysCellDef(Map<String, Object> map);
	/**
	 * 删除时间副表数据
	 * @param timeSegDef
	 */
	public void deleteTimeSeqDtl(TimeSegDef timeSegDef);
	/**
	 * 检查时间数据有没有被使用
	 * @param segId
	 * @return
	 */
	public Integer countDiscountPrice(Map paramMap);
	/**
	 * 删除时间数据
	 * @param segId
	 */
	public void deleteTimeSegDef(Map paramMap);
	/**
	 * 定价生失效时间
	 */
	public Integer addPricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle);
	public Integer updatePricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle);
	public PricePlanLifeCycle queryPricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle);
}
