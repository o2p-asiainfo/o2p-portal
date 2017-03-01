package com.ailk.o2p.portal.dao.apiStore;

import java.util.List;
import java.util.Map;

import com.ailk.eaap.op2.bo.Directory;

/**
 * @author yangbl
 * @since 2016/08/16
 */
@SuppressWarnings("all")
public interface IApiStoreDao {
	
	//apiStore成交量
	public int quryApiOfferDealCount(Map<String, String> param);
	//查询api名称、次价信息
	public List<Map> quryApiOfferMsg(Map<String, String> param);
	//查询特定目录下所有的apiOffer,prod_offer表字段logo_file_id表示ApiOffer的图片
	public List<Map> quryApiOfferList(Map<String, String> param);
	//查询api_offer的所属分类类型(左侧菜单)
	public String  quryApiOfferMenuType(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	//查询api_offer的服务提供商名称
	public String  quryApiOfferProviderName(com.ailk.eaap.op2.bo.Org org);
	//ApiOffer访问量入库
	public int insertApiOfferVisitorVolume(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	//ApiOffer分类
	public String quryApiOfferType(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	//最新的ApiOffer
	public List<Map> queryNewApiList(Map map);
	//查询api目录名称
	public String quryApiCataName(com.ailk.eaap.op2.bo.ProdOffer prodOffer);
	
	public List<Directory> queryApiCategoryList(Map map);
	
	public Integer updateVisitor(com.ailk.eaap.op2.bo.ItemCnt itemCnt);
	
	public List<Map> quryApiOfferListByCategory(Map<String, String> param);
	
	public List<Map> quryAllApiOfferList(Map<String, String> param);
	
	public List<Map> quryAllLimitApiOfferList(Map<String, String> param);
	
	public List<Map> queryNewLimitApiList(Map<String, String> param);
	
	public List<Map> quryApiOfferLimitList(Map<String, String> param); 
	
	public Integer queryApiOfferCnt(Map<String, Object> paraMap);
	
	public String querySeriveCode(com.ailk.eaap.op2.bo.Api api);
	
//	public List<Directory> queryDirList(com.ailk.eaap.op2.bo.Directory directory);
//	
//	public Integer queryFaDirId(com.ailk.eaap.op2.bo.Directory directory);
}
