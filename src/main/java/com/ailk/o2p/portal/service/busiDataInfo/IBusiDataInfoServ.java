/** 
 * Project Name:o2p-portal-pro 
 * File Name:IBusiDataInfoServ.java 
 * Package Name:com.ailk.o2p.portal.service.busiDataInfo 
 * Date:2016年3月13日下午6:10:01 
 * Copyright (c) 2016, www.asiainfo.com All Rights Reserved. 
 * 
*/  
  
package com.ailk.o2p.portal.service.busiDataInfo;  

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.BusiDataInfo;

/** 
 * ClassName:IBusiDataInfoServ <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016年3月13日 下午6:10:01 <br/> 
 * @author   wushuzhen 
 * @version   
 * @since    JDK 1.7 
 * @see       
 */
public interface IBusiDataInfoServ {
	  public void insertDataToTable(BusiDataInfo busiDataInfo);
	  public BusiDataInfo qryBusiDataInfoByName(Map map) ;
	  public void updateBusiDataInfo(BusiDataInfo busiDataInfo) ;
//	  public List<BusiDataInfo> loadBusiDataInfo();
}
