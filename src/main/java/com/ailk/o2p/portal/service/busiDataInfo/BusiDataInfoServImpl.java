/** 
 * Project Name:o2p-portal-pro 
 * File Name:BusiDataInfoServImpl.java 
 * Package Name:com.ailk.o2p.portal.service.busiDataInfo 
 * Date:2016年3月13日下午6:10:19 
 * Copyright (c) 2016, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.service.busiDataInfo;  

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailk.eaap.op2.bo.BusiDataInfo;
import com.ailk.eaap.op2.dao.BusiDataInfoDao;
import com.ailk.eaap.op2.util.SpringContextUtil;

/** 
 * ClassName:BusiDataInfoServImpl <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016年3月13日 下午6:10:19 <br/> 
 * @author   wushuzhen 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
@Service("busiDataInfoServ")
public class BusiDataInfoServImpl implements IBusiDataInfoServ{
    @Autowired
	private BusiDataInfoDao  busiDataInfoDao;

	@Override
	public void insertDataToTable(BusiDataInfo busiDataInfo) {
		// TODO Auto-generated method stub
		busiDataInfoDao.insertDataToTable(busiDataInfo);
	}

	@Override
	public BusiDataInfo qryBusiDataInfoByName(Map map) {
		// TODO Auto-generated method stub
		 
		return busiDataInfoDao.qryBusiDataInfoByName(map);
	}
	
//	public List<BusiDataInfo> loadBusiDataInfo(){
//		return busiDataInfoDao.loadBusiDataInfo();
//	}

	@Override
	public void updateBusiDataInfo(BusiDataInfo busiDataInfo) {
		// TODO Auto-generated method stub
		busiDataInfoDao.updateBusiDataInfo(busiDataInfo);
	}

	public BusiDataInfoDao getBusiDataInfoDao() {
		if(busiDataInfoDao==null){
			busiDataInfoDao=(BusiDataInfoDao) SpringContextUtil.getBean("busiDataInfoDao");
		}
		return busiDataInfoDao;
	}

	public void setBusiDataInfoDao(BusiDataInfoDao busiDataInfoDao) {
		this.busiDataInfoDao = busiDataInfoDao;
	}
	
}
