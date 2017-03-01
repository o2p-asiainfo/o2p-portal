package com.ailk.o2p.portal.service.price;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.eaap.op2.bo.MainData;
import com.ailk.eaap.op2.bo.MainDataType;
import com.ailk.eaap.op2.bo.PricePlanLifeCycle;
import com.ailk.eaap.op2.bo.PricngPlanAttr;
import com.ailk.eaap.op2.bo.RsSysCellDef;
import com.ailk.eaap.op2.bo.TimeSegDef;
import com.ailk.eaap.op2.bo.TimeSegDtl;
import com.ailk.eaap.op2.dao.MainDataDao;
import com.ailk.eaap.op2.dao.MainDataTypeDao;
import com.ailk.o2p.portal.dao.price.IAttrSpecDao;
@Service
@Transactional
public class AttrSpecServ implements IAttrSpecServ {
	@Autowired
	private IAttrSpecDao attrSpecDao;
	@Autowired
    private MainDataDao mainDataSqlDAO ;
	@Autowired
	private MainDataTypeDao mainDataTypeSqlDAO;
	
	public void setMainDataTypeSqlDAO(MainDataTypeDao mainDataTypeSqlDAO) {
		this.mainDataTypeSqlDAO = mainDataTypeSqlDAO;
	}
	public IAttrSpecDao getAttrSpecDao() {
		return attrSpecDao;
	}
	public void setAttrSpecDao(IAttrSpecDao attrSpecDao) {
		this.attrSpecDao = attrSpecDao;
	}
	public void setMainDataSqlDAO(MainDataDao mainDataSqlDAO) {
		this.mainDataSqlDAO = mainDataSqlDAO;
	}
	public MainDataDao getMainDataSqlDAO() {
		return mainDataSqlDAO;
	}
	public MainDataTypeDao getMainDataTypeSqlDAO() {
		return mainDataTypeSqlDAO;
	}
	public List<MainDataType> selectMainDataType(MainDataType mainDataType){
		return mainDataTypeSqlDAO.selectMainDataType(mainDataType) ;
	}
	public List<MainData> selectMainData(MainData mainData) {
		return mainDataSqlDAO.selectMainData(mainData) ;
	}

	public List<TimeSegDef> queryTimeSeqDef(Map map){
		return attrSpecDao.queryTimeSeqDef(map);
	}
	public Integer countTimeSeqDef(Map map){
		return attrSpecDao.countTimeSeqDef(map);
	}
	public Integer addTimeSeqDef(TimeSegDef timeSeqDef){
		return attrSpecDao.addTimeSeqDef(timeSeqDef);
	}
	public Integer updateTimeSeqDef(TimeSegDef timeSeqDef){
		return attrSpecDao.updateTimeSeqDef(timeSeqDef);
	}
	public List<RsSysCellDef> selRsSysCellDef(Map<String, Object> map){
		return attrSpecDao.selRsSysCellDef(map);
	}
	
	
	public List<TimeSegDtl> queryTimeSeqDtl(Map map){
		return attrSpecDao.queryTimeSeqDtl(map);
	}
	public Integer addTimeSeqDtl(TimeSegDtl timeSeqDtl){
		return attrSpecDao.addTimeSeqDtl(timeSeqDtl);
	}
	public Integer updateTimeSeqDtl(TimeSegDtl timeSeqDtl){
		return attrSpecDao.updateTimeSeqDtl(timeSeqDtl);
	}
	
	public List<RsSysCellDef> queryRsSysCellDef(Map map){
		return attrSpecDao.queryRsSysCellDef(map);
	}
	public Integer countRsSysCellDef(Map map){
		return attrSpecDao.countRsSysCellDef(map);
	}
	
	public Integer addPricngPlanAttr(PricngPlanAttr pricngPlanAttr){
		return attrSpecDao.addPricngPlanAttr(pricngPlanAttr);
	}
	public Integer updatePricngPlanAttr(PricngPlanAttr pricngPlanAttr){
		return attrSpecDao.updatePricngPlanAttr(pricngPlanAttr);
	}
	public List<PricngPlanAttr> queryPricngPlanAttr(PricngPlanAttr pricngPlanAttr){
		return attrSpecDao.queryPricngPlanAttr(pricngPlanAttr);
	}
	/**
	 * 删除时间副表数据
	 * @param timeSegDef
	 */
	@Override
	public void deleteTimeSeqDtl(TimeSegDef timeSegDef) {
		attrSpecDao.deleteTimeSeqDtl(timeSegDef);
	}
	/**
	 * 检查时间数据有没有被使用
	 * @param segId
	 * @return
	 */
	@Override
	public Integer countDiscountPrice(String segId) {
		Map paramMap=new HashMap(); 
		paramMap.put("segId", segId);
		return attrSpecDao.countDiscountPrice(paramMap);
	}
	/**
	 * 删除时间数据
	 * @param segId
	 */
	@Override
	public void deleteTimeSegDef(String segId) {
		Map paramMap=new HashMap(); 
		paramMap.put("segId", segId);
		attrSpecDao.deleteTimeSegDef(paramMap);
	}
	/**
	 * 定价生失效时间
	 */
	public Integer addPricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle){
		return attrSpecDao.addPricePlanLifeCycle(pricePlanLifeCycle);
	}
	public Integer updatePricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle){
		return attrSpecDao.updatePricePlanLifeCycle(pricePlanLifeCycle);
	}
	public PricePlanLifeCycle queryPricePlanLifeCycle(PricePlanLifeCycle pricePlanLifeCycle){
		return attrSpecDao.queryPricePlanLifeCycle(pricePlanLifeCycle);
	}
}

