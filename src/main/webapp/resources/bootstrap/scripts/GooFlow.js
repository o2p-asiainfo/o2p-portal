//定义一个区域图类：

function GooFlow(bgDiv,property){
	if (navigator.userAgent.indexOf("MSIE 8.0")>0||navigator.userAgent.indexOf("MSIE 7.0")>0||navigator.userAgent.indexOf("MSIE 6.0")>0)
		GooFlow.prototype.useSVG="";
	else	GooFlow.prototype.useSVG="1";
	//初始化区域图的对象
	this.$id=bgDiv.attr("id");
	this.$bgDiv=bgDiv;//最父框架的DIV
	this.$bgDiv.addClass("GooFlow");
	var width=property.width;
	var height=property.height;
	this.$bgDiv.css({width:width+"px",height:height+"px"});
	this.$tool=null;//左侧工具栏对象
	this.$head=null;//顶部标签及工具栏按钮
	this.$title="newFlow_1";//流程图的名称
	this.$nodeRemark={};//每一种结点或按钮的说明文字,JSON格式,key为类名,value为用户自定义文字说明
	this.$nowType="";    //"cursor";//当前要绘制的对象类型
	this.$nodeLeft=5;       //左节点的left用来判断是左边的表格还是右边的表格
	this.$nodeRight=400;   //左节点的left用来判断是左边的表格还是右边的表格
	this.$flag=0;  //右边表格的拖动的开关
	this.$lineData={};
	this.$lineCount=0;
	this.$nodeData={};
	this.$nodeRightData={};//右边节点数据
	this.$nodeCount=0;
	this.$areaData={};
	this.$areaCount=0;
	this.$lineDom={};
	this.$nodeDom={};
	this.$areaDom={};
	this.$max=property.initNum||1;//计算默认ID值的起始SEQUENCE
	this.$focus="";//当前被选定的结点/转换线ID,如果没选中或者工作区被清空,则为""
	this.$focusnode="";
	this.$innerLine={};
	this.$cursor="default";//鼠标指针在工作区内的样式
	this.$editable=true;//工作区是否可编辑
	this.$deletedItem={};//在流程图的编辑操作中被删除掉的元素ID集合,元素ID为KEY,元素类型(node,line.area)为VALUE
	var headHeight=0;
	this.$tabCou = 0;
	this.isRepeat = false;
	this.repeadId;
	this.$isIEflag=false;
	this.$addRecordflag=false;
	this.$leftIndex=-1;
	this.$leftId="";
	this.$rightIndex=-1;
	this.$rightId="";
	//=============================
	this.mark;
	this.width = 270;
	this.height = 25;
	this.top = 1;
	this.left = 0;
	this.type = "complex mix";
	this.alt = true;
	this.lintype;
	this.linID={};
	this.$nowType="direct";
	this.$bgDiv.append("<div class='GooFlow_work'  style='width:"+(width)+"px;height:"+(height)+"px;'><div class='GooFlow_work_inner clearfix'><div class='GooFlow_line_move' style='display:none;'></div><div class='baseversion'></div></div></div>");
	this.$workArea=$(".GooFlow_work_inner");
	this.$draw=null;//画矢量线条的容器
	//height = height>1000?height:(height+1700);
	this.initDraw("draw_"+this.$id,(width),(height));
//	this.loadData();
//	this.addData();
//	this.loadLine();
	this.$group=null;
	//if(property.haveGroup)
	//	this.initGroup(width,height);
	if(this.$editable){		
	  //划线时用的绑定
	  this.$workArea.mousemove({inthis:this},function(e){
		var This=e.data.inthis;
		var lineStart=$(this).data("lineStart");
		if(!lineStart)return;
		var ev=mousePosition(e),t=getElCoordinate(this);
		var X,Y;
		X=ev.x-t.left+this.parentNode.scrollLeft;
		Y=ev.y-t.top+this.parentNode.scrollTop;
		var line=document.getElementById("GooFlow_tmp_line");
		if(line==null){return;}
		if(GooFlow.prototype.useSVG!=""){
			line.childNodes[0].setAttribute("d","M "+lineStart.x+" "+lineStart.y+" L "+X+" "+Y);
			line.childNodes[1].setAttribute("d","M "+lineStart.x+" "+lineStart.y+" L "+X+" "+Y);
			if(line.childNodes[1].getAttribute("marker-end")=="url(\"#arrow2\")")
				line.childNodes[1].setAttribute("marker-end","url(#arrow3)");
			else line.childNodes[1].setAttribute("marker-end","url(#arrow2)");
		}
		else line.points.value=lineStart.x+","+lineStart.y+" "+X+","+Y;
	  });
	  this.$workArea.mouseup({inthis:this},function(e){
	  	var This=e.data.inthis;
		if(This.$rightIndex!=-1){
			var nstr;
			if($("#"+This.$rightId).hasClass("par"))nstr='';
			else nstr='<dd class="assign">Assign</dd>';
			var rtop=$("#"+This.$rightId).offset().top+$(".GooFlow_work").scrollTop()-$("#UniversalAdapterDemo").offset().top;
			$('<div class="operate  single-opline"><div class="op-select"><div class="por"><div class="sl"></div><span>Operate</span><dl><dd class="remove">Remove</dd><dd class="other">Other</dd>'+nstr+'</dl></div></div></div>').prependTo(This.$workArea).css("top",rtop+3+"px").attr({"id":"op"+This.$rightId,"to":This.$rightId,"from":""}).css("z-index","201").siblings(".operate").css("z-index","197");
			}
		This.$rightIndex=-1;
		This.$rightId="";
	  	var lineStart=This.$workArea.data("lineStart");
		$(this).css("cursor","auto").removeData("lineStart");
		var tmp=document.getElementById("GooFlow_tmp_line");
		if(tmp)This.$draw.removeChild(tmp);
	  });
	  //为了结点而增加的一些集体delegate绑定
	  this.initWorkForNode();
	}
}

////////////////全局变量区域
var repeadJson={};//存放重复操作的记录
var repeadAction={};//存放动作记录


GooFlow.prototype={
	useSVG:"",
	getSvgMarker:function(id,color){
		var m=document.createElementNS("http://www.w3.org/2000/svg","marker");
		m.setAttribute("id",id);
		m.setAttribute("viewBox","0 0 6 6");
		m.setAttribute("refX",5);
		m.setAttribute("refY",3);
		m.setAttribute("markerUnits","strokeWidth");
		m.setAttribute("markerWidth",6);
		m.setAttribute("markerHeight",6);
		m.setAttribute("orient","auto");
		var path=document.createElementNS("http://www.w3.org/2000/svg","path");
		path.setAttribute("d","M 0 0 L 6 3 L 0 6 z");
		path.setAttribute("fill",color);
		path.setAttribute("stroke-width",0);
		m.appendChild(path);
		return m;
	},
	initDraw:function(id,width,height){
		var elem;
		if(GooFlow.prototype.useSVG!=""){
			this.$draw=document.createElementNS("http://www.w3.org/2000/svg","svg");//可创建带有指定命名空间的元素节点
			this.$workArea.prepend(this.$draw);
			var defs=document.createElementNS("http://www.w3.org/2000/svg","defs");
			this.$draw.appendChild(defs);
			defs.appendChild(GooFlow.prototype.getSvgMarker("arrow1","#15428B"));
			defs.appendChild(GooFlow.prototype.getSvgMarker("arrow2","#ff3300"));
			defs.appendChild(GooFlow.prototype.getSvgMarker("arrow3","#ff3300"));
		}
		else{
			this.$draw = document.createElement("v:group");
			this.$draw.coordsize = width+","+height;
			this.$workArea.prepend("<div class='GooFlow_work_vml' style='position:relative;width:"+width+"px;height:"+(height+700)+"px'></div>");
			this.$workArea.children("div")[0].insertBefore(this.$draw,null);
		}
		this.$draw.id = id;
		this.$draw.style.width = width + "px";
		this.$draw.style.height = +height + "px";
		//绑定连线的点击选中以及双击编辑事件
		var tmpClk=null;
		if(GooFlow.prototype.useSVG!="")  tmpClk="g";
		else  tmpClk="PolyLine";
		if(this.$editable){
		$(this.$draw).delegate(tmpClk,"click",{inthis:this},function(e){
			e.data.inthis.focusItem(this.id,true);
		});
		}
	},
	loadLine:function (linejson){
//		var linejson={
//		 "lines": {
//        			"R3T1L1": {
//            		"type": "sl",
//            		"from": "T1L1",
//            		"to": "R3",
//            		"name": "",
//            		"alt": true,
//					"operate":"Move"
//        		}
//    		}
//		}
		var linelist=linejson.lines;
		$.each(linelist,function(key,val){
			if(val.from==""){
				var nstr;
				if($("#"+val.to).hasClass("par"))nstr='';
			    else nstr='<dd class="assign">Assign</dd>';
				$('<div class="operate single-opline relation fade-model"><div class="op-select"><div class="por"><div class="sl"></div><span>'+val.operate+'</span><dl><dd class="remove">Remove</dd><dd class="other">Other</dd>'+nstr+'</dl></div></div></div>').prependTo($(".GooFlow_work_inner")).css("top",(val.to.substring(1)*31+15-31)+"px").attr({"id":key,"to":val.to,"from":""});
				$("#"+val.to).addClass("checked");
				}
			else{
				var lid=val.from;
				var rid=val.to;
				drawConcenectLine(lid,rid,"1");
				$("#"+val.from).addClass("checked");	
				$("#"+val.to).addClass("checked");	
				}
			});
		reSize();
		},
	initWorkForNode:function(){
		this.$workArea.delegate("ul li","click",{inthis:this},function(e){
			$(this).addClass("act").siblings().removeClass("act");	
		});
		this.$workArea.delegate("ul.r li","click",{inthis:this},function(e){
			var This=e.data.inthis;
			if($(this).hasClass("checked")){
			var rid=$(this).attr("id");
			lineRelation(rid);
			nodeChecked(lid,mid,rid);
			}
			else if(This.$rightIndex!=-1){$("ul.l li.act").removeClass("act")}
		});
		this.$workArea.delegate("ul li","mousedown",{inthis:this},function(e){
			var This=e.data.inthis;
			This.$workArea.find(".operate").each(function(){
				if(!$(this).hasClass("relation")){$(this).remove()}
				else{$(this).addClass("fade-model")}
				})
			This.$rightIndex=-1;
			$("ul.r li.act").removeClass("act");
			$(this).addClass("act").siblings().removeClass("act");	
			if($(this).attr("id").indexOf('L')>-1){This.$leftIndex=$(this).index();This.$leftId=$(this).attr("id");}
			else{
				if($(this).hasClass("checked")){This.$rightIndex=-1}
				else{This.$rightIndex=$(this).index();This.$rightId=$(this).attr("id");}}
			var ev=mousePosition(e),t=getElCoordinate(This.$workArea[0]);
			var X,Y;
			X=ev.x-t.left+This.$workArea[0].parentNode.scrollLeft;
			Y=ev.y-t.top+This.$workArea[0].parentNode.scrollTop;
			This.$workArea.data("lineStart",{"x":X,"y":Y,"id":this.id}).css("cursor","crosshair");
			var line=GooFlow.prototype.drawLine("GooFlow_tmp_line",[X,Y],[X,Y],true,true);
			This.$draw.appendChild(line);
			This.$flag=0;				
		});
		this.$workArea.delegate("div.operate dd","click",function(){
		var prevOperate=$(this).parent().prev().html();
		$(this).parent().prev().html($(this).html());
		var curCss=$(this).attr("class");
		var curId=$(this).closest('.operate').attr("id");
		var leftnodeId=$("#"+curId).attr("from");
		var rightnodeId=$("#"+curId).attr("to");
		$("#"+leftnodeId).removeClass("act");
		$("#"+rightnodeId).removeClass("act");
		if($("#"+curId).attr("from")==""){leftnodeId=""}
		if($("#"+curId).hasClass("relation")){
			if(curCss=="remove"){
				remove(leftnodeId,curId,rightnodeId,prevOperate);
			}
			else{
				if(curCss=="move"){move(leftnodeId,curId,rightnodeId);}
				else if(curCss=="update"){update(leftnodeId,curId,rightnodeId);}
				else if(curCss=="other"){other(leftnodeId,curId,rightnodeId);}
				else if(curCss=="assign"){assign(leftnodeId,curId,rightnodeId);}
				else if(curCss=="rowToColumn"){rowToColumn(leftnodeId,curId,rightnodeId);}
				else if(curCss=="columnToRow"){columnToRow(leftnodeId,curId,rightnodeId);}
			}
		}
		else{
			$(this).parent().prev().html($(this).html());
			if(curCss=="remove"){
				$("#"+leftnodeId).removeClass("checked");
				$("#"+rightnodeId).removeClass("checked");
				$("#"+curId).remove();
			}
			else{
				$("#"+curId).addClass("relation fade-model")
				$("#"+rightnodeId).addClass("checked");	
				$("#"+leftnodeId).addClass("checked");	
				if(curCss=="move"){move(leftnodeId,curId,rightnodeId);}
				else if(curCss=="update"){update(leftnodeId,curId,rightnodeId);}
				else if(curCss=="other"){other(leftnodeId,curId,rightnodeId);}
				else if(curCss=="assign"){assign(leftnodeId,curId,rightnodeId);}
				else if(curCss=="rowToColumn"){rowToColumn(leftnodeId,curId,rightnodeId);}
				else if(curCss=="columnToRow"){columnToRow(leftnodeId,curId,rightnodeId);}
			}
		}
		
		});
		//绑定连线时确定结束点
		this.$workArea.delegate("ul.r li","mouseup",{inthis:this},function(e){		
			var This=e.data.inthis;
			if(This.$leftIndex!="-1"){
			$(this).addClass("act").siblings().removeClass("act");	
			if($(this).hasClass("checked")){
				///alert(nodeBeUsed);
				///This.$leftIndex="-1";
				///This.$leftId="";
				///return;
			}
			This.$rightIndex=$(this).index();
			This.$rightId=$(this).attr("id");
			//var newid="R"+(This.$rightIndex+1)+"L"+(This.$leftIndex+1);
			drawConcenectLine(This.$leftId,This.$rightId)
			This.$leftIndex="-1";
			This.$rightIndex="-1";
			}
			var tmp=document.getElementById("GooFlow_tmp_line");
			if(tmp)This.$draw.removeChild(tmp);
			reSize();
			e.stopPropagation();
		});
		this.$workArea.delegate("ul.l .head","click",{inthis:this},function(e){		
			var This=e.data.inthis;
			$(this).parent().toggleClass("contract");
			$(this).find(".title").toggleClass("t");
			$(".connect-line").each(function(index, element) {
                var from=$(this).attr("from");
				var to=$(this).attr("to");
				var operate=$(this).find(".op-select span").html();
				$(this).remove();
				drawConcenectLine(from,to,"1",operate);
            });
			reHeight();
			reSize();
		});
		this.$workArea.delegate("ul.l .head .close","click",{inthis:this},function(e){
			removeVersion($(this).closest("ul").attr("id"));
			e.stopPropagation();
		});
		this.$workArea.delegate("ul.l .head .flag label","click",{inthis:this},function(e){
			changeVType($(this).closest("ul").attr("id"),$(this).find("input").val());
			e.stopPropagation();
		});
		this.$workArea.delegate("ul.l .head .check","click",{inthis:this},function(e){
			e.stopPropagation();//chagneVType($(this).closest("ul").attr("id"));
		});
		this.$workArea.delegate("ul.l .basicid","click",{inthis:this},function(e){
			$("#ProtocolId").attr("releId",$(this).closest("ul").attr("id"));
			$('#dataidienty').modal('show');
			e.stopPropagation();
			updateMessageFlow($(this).closest("ul").attr("id"));//修改消息流
		});
		this.$workArea.delegate("ul.l .head .check","change",{inthis:this},function(e){
				var versionId=$(this).closest("ul").attr("id");
				if($(this).find("input").is(":checked")){
					changeVType(versionId,"XX");
				}
				else{
					$(this).next().css("visibility","visible");
				 	changeVType(versionId,"t");
				}
				
		});
	},
	drawLine:function(id,sp,ep,mark,dash){
		var line;
		if(GooFlow.prototype.useSVG!=""){
			line=document.createElementNS("http://www.w3.org/2000/svg","g");
			var hi=document.createElementNS("http://www.w3.org/2000/svg","path");
			var path=document.createElementNS("http://www.w3.org/2000/svg","path");
			if(id!="")	line.setAttribute("id",id);
			line.setAttribute("from",sp[0]+","+sp[1]);
			line.setAttribute("to",ep[0]+","+ep[1]);
			hi.setAttribute("visibility","hidden");
			hi.setAttribute("stroke-width",9);
			hi.setAttribute("fill","none");
			hi.setAttribute("stroke","white");
			hi.setAttribute("d","M "+sp[0]+" "+sp[1]+" L "+ep[0]+" "+ep[1]);
			hi.setAttribute("pointer-events","stroke");
			path.setAttribute("d","M "+sp[0]+" "+sp[1]+" L "+ep[0]+" "+ep[1]);
			path.setAttribute("stroke-width",1.4);
			path.setAttribute("stroke-linecap","round");
			path.setAttribute("fill","none");
			if(dash)	path.setAttribute("style", "stroke-dasharray:6,5");
			if(mark){
				path.setAttribute("stroke","#ff3300");
				path.setAttribute("marker-end","url(#arrow2)");
			}
			else{
				path.setAttribute("stroke","#5068AE");
				path.setAttribute("marker-end","url(#arrow1)");
			}
			line.appendChild(hi);
			line.appendChild(path);
			line.style.cursor="crosshair";
			if(id!=""&&id!="GooFlow_tmp_line"){
				var text=document.createElementNS("http://www.w3.org/2000/svg","text");
				//text.textContent=id;
				line.appendChild(text);
				var x=(ep[0]+sp[0])/2;
				var y=(ep[1]+sp[1])/2;
				text.setAttribute("text-anchor","middle");
				text.setAttribute("x",x);
				text.setAttribute("y",y);
				line.style.cursor="pointer";
				text.style.cursor="text";
			}
		}else{
			line=document.createElement("v:polyline");
			if(id!="")	line.id=id;
			//line.style.position="absolute";
			line.points.value=sp[0]+","+sp[1]+" "+ep[0]+","+ep[1];
			line.setAttribute("fromTo",sp[0]+","+sp[1]+","+ep[0]+","+ep[1]);
			line.strokeWeight="1.2";
			line.stroke.EndArrow="Block";
			line.style.cursor="crosshair";
			if(id!=""&&id!="GooFlow_tmp_line"){
				var text=document.createElement("div");
				//text.innerHTML=id;
				line.appendChild(text);
				var x=(ep[0]-sp[0])/2;
				var y=(ep[1]-sp[1])/2;
				if(x<0) x=x*-1;
				if(y<0) y=y*-1;
				text.style.left=x+"px";
				text.style.top=y-6+"px";
				line.style.cursor="pointer";
			}
			if(dash)line.stroke.dashstyle="Dash";
			if(mark)line.strokeColor="#ff3300";
			else	line.strokeColor="#5068AE";
		}
		return line;
	},
}
function getElCoordinate(dom) {
  var t = dom.offsetTop;
  var l = dom.offsetLeft;
  dom=dom.offsetParent;
  while (dom) {
    t += dom.offsetTop;
    l += dom.offsetLeft;
	dom=dom.offsetParent;
  }; return {
    top: t,
    left: l
  };
}
function mousePosition(ev){
	if(!ev) ev=window.event;
    if(ev.pageX || ev.pageY){
        return {x:ev.pageX, y:ev.pageY};
    }
    return {
        x:ev.clientX + document.documentElement.scrollLeft - document.body.clientLeft,
        y:ev.clientY + document.documentElement.scrollTop  - document.body.clientTop
    };
}
	function reSize(){
	var opsnumber=$(".GooFlow_work .connect-line").length;
	$(".GooFlow_work .connect-line").each(function(index){
		$(this).find(".left").width((20+parseInt(235/opsnumber*(index+1)))+"px");
		$(this).find(".right").width((337-parseInt(235/opsnumber*(index+1)))+"px");
		$(this).find(".right").css("right","-"+(337-parseInt(235/opsnumber*(index+1)))+"px");
		if($(this).hasClass("fade-model")){$(this).css("z-index",200-index);}
		else{$(this).css("z-index",201);}
		});
	$(".GooFlow_work .single-opline").css("z-index","199");
	}
	function clearStatus(){
		$(".GooFlow_work_inner .operate:not(.fade-model)").addClass("fade-model").css("z-index",189);
		}
	function addParentnode(e,e1){
		var operate;
		if(e1==null){
			operate="Other";
		}
		else{operate=e1;}
		var objectid=e;
		var curid="op"+e;
		var rtop=$("#"+objectid).offset().top+$(".GooFlow_work").scrollTop()-$("#UniversalAdapterDemo").offset().top;
		$('<div class="operate single-opline relation fade-model"><div class="op-select"><div class="por"><div class="sl"></div><span>'+operate+'</span><dl><dd class="remove">Remove</dd><dd class="other">Other</dd></dl></div></div></div>').prependTo($(".GooFlow_work_inner")).css("top",(rtop+5+"px")).attr({"id":curid,"to":objectid,"from":""});
		$("#"+objectid).addClass("checked");
		}
	function lineRelation(e){
			clearStatus();
		    var rid=e;
			var lid="";
			var mid="";
			$(".GooFlow_work_inner .fade-model").each(function(index){
				if($(this).attr('to')==rid&&$(this).attr('from')!=""){
					lid=$(this).attr('from');
					$("#"+lid).addClass("act").siblings().removeClass("act");
					$(this).removeClass("fade-model").css("z-index","201");
					mid=$(this).attr("id");
					}
				else if($(this).attr('id')=="op"+rid){
					$(this).removeClass("fade-model").css("z-index","201");
					mid=$(this).attr("id");
					$("ul.l li").removeClass("act")
					}
				})
				$("#"+lid).addClass("act").siblings().removeClass("act");
				$("#"+rid).addClass("act").siblings().removeClass("act");
				nodeChecked(lid,mid,rid);
		}
	//字符过长溢出处理
	function drawConcenectLine(fromid,toid,rd,e1){
			var operate;
			if(e1==null){
				operate="operate";
			}
			else{operate=e1;}
			var ltop=$("#"+fromid).offset().top+$(".GooFlow_work").scrollTop()-$("#UniversalAdapterDemo").offset().top;
			var rtop=$("#"+toid).offset().top+$(".GooFlow_work").scrollTop()-$("#UniversalAdapterDemo").offset().top;
			var n=Math.abs(ltop-rtop);
			newid=toid+fromid;
			var newop;
			if(rd!=null){newop=$('<div class="operate connect-line relation fade-model"><div class="t-b"><div class="left"><div class="right"></div><div class="op-select"><div class="por"><span>'+operate+'</span><dl><dd class="remove">Remove</dd><dd class="update">Update</dd><dd class="move">Move</dd><dd class="rowToColumn" title="Row to Column">R to C</dd><dd class="columnToRow"  title="Column to Row">C to R</dd></dl></dl></div></div></div></div></div>').css({"height":n+"px","z-index":"201"}).attr({"id":newid,"from":fromid,"to":toid});}
			else{newop=$('<div class="operate connect-line"><div class="t-b"><div class="left"><div class="right"></div><div class="op-select"><div class="por"><span>operate</span><dl><dd class="remove">Remove</dd><dd class="update">Update</dd><dd class="move">Move</dd><dd class="rowToColumn" title="Row to Column">R to C</dd><dd class="columnToRow"  title="Column to Row">C to R</dd></dl></div></div></div></div></div>').css({"height":n+"px","z-index":"201"}).attr({"id":newid,"from":fromid,"to":toid});}
			
			$(".GooFlow_work_inner").append(newop);
			if(ltop>=rtop)newop.find(".t-b").attr("class","b-t").css({"top":rtop-24+"px",});
			else{newop.css({"top":ltop+16+"px"})}
		}
	function resizeStr(){
		$(".GooFlow_work ul em").each(function(){
		if($(this).html().length>=26){
			$(this).html($(this).html().substring(0,25)+"…");
			}
		})
		}
	//删除节点节接口，三个参数分别是左节点ID， 中间操作项id，右节点,左节点存在null情况。
	function remove(lid,mid,rid,prevop){
		if(confirm(title[5])){
			$("#"+lid).removeClass("checked");
			$("#"+rid).removeClass("checked");
			$("#"+mid).remove();//删除线操作
			var srcId = null;
			if("Move" == prevop || "Update" == prevop){
				if("" != lid){
					srcId = $('#'+lid).attr('nodeDescId');//左节点ID
				}
			}
			var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
			delTableTr(lid,rid,prevop);
			//清空数据
			delLineToDate(srcId,tarId);
		}
		}
	//move操作节点
	function move(lid,mid,rid){
		var srcId = $('#'+lid).attr('nodeDescId');//左节点ID
		var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
		var state = isExitLine(srcId,tarId,"M");
		if("EXIT" == state){
			alert(title[6]);
		}else if("USED" == state){
			alert(nodeUsed);
			removeLine(lid,rid,mid);
		}else if("NOT_IN_LIST" == state){
			alert(srcDel);
			removeLine(lid,rid,mid);
		}else if("UPDATE" == state){
			if(confirm(title[7])){
				$("#"+mid).addClass("relation fade-model").find("span").html("Move");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"M");
				changeTableTr(lid,rid,"Move");
			}
		}else if("NO_DATA" == state){
			//添加线的数据
			addLineToDate(srcId,tarId,"M");
			//添加表格记录
			addTableTr(lid,rid,'Move');
			//添加上级节点
			autoAddParentNode(mid,rid);
		}
		}
	//更新节点操作接口mm
	function update(lid,mid,rid){
		var srcId = $('#'+lid).attr('nodeDescId');//左节点ID
		var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
		var fromval = $('#'+lid).attr('name');//源节点名称
		var tpath = $('#'+rid).attr('path');//目标节点path
		var spath = $('#'+lid).attr('path');//源节点path
		var state = isExitLine(srcId,tarId,"U");
		if("EXIT" == state){
			if(confirm(title[8])){
				$("#"+mid).addClass("relation fade-model").find("span").html("Update");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"U");
				//打开操作窗口
				openWind(fromval,spath,tpath,tarId,'U');
			}
		}else if("USED" == state){
			alert(nodeUsed);
			removeLine(lid,rid,mid);
		}else if("NOT_IN_LIST" == state){
			alert(srcDel);
			removeLine(lid,rid,mid);
		}else if("UPDATE" == state){
			if(confirm(title[9])){
				$("#"+mid).addClass("relation fade-model").find("span").html("Update");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"U");
				changeTableTr(lid,rid,"Update");
				//打开操作窗口
				openWind(fromval,spath,tpath,tarId,'U');
			}
		}else if("NO_DATA" == state){
			//修改线的数据
			addLineToDate(srcId,tarId,"U");
			//添加表格记录
			addTableTr(lid,rid,'Update');
			//添加上级节点
			autoAddParentNode(mid,rid);
			//打开操作窗口
			openWind(fromval,spath,tpath,tarId,'U');
		}
	}
	function other(lid,mid,rid){
		var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
		var state = isExitLine('',tarId,"R");
		if("EXIT" == state){
			alert(title[10]);
		}else if("USED" == state){
			alert(nodeUsed);
			removeLine(lid,rid,mid);
		}else if("UPDATE" == state){
			if(confirm(title[11])){
				$("#"+mid).addClass("relation fade-model").find("span").html("Other");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//添加线记录
				addLineToDate(null,tarId,"R");
				changeTableTr(lid,rid,"Other");
			}
		}else if("NO_DATA" == state){
			//添加线记录
			addLineToDate(null,tarId,"R");
			//添加表格记录
			addTableTr(lid,rid,'Other');
			//添加上级节点
			autoAddParentNode(mid,rid);
		}
	}
	function assign(lid,mid,rid){
		var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
		var tpath = $('#'+rid).attr('path');//右节点ID
		var state = isExitLine('',tarId,"A");
		if("EXIT" == state){
			alert(title[12]);
		}else if("USED" == state){
			alert(nodeUsed);
			removeLine(lid,rid,mid);
		}else if("UPDATE" == state){
			if(confirm(title[13])){
				$("#"+mid).addClass("relation fade-model").find("span").html("Assign");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//添加线记录
				addLineToDate(null,tarId,"A");
				changeTableTr(lid,rid,"Assign");
				//打开操作窗口
				openWind('','',tpath,tarId,'A');
			}
		}else if("NO_DATA" == state){
			//添加线记录
			addLineToDate(null,tarId,"A");
			addTableTr(lid,rid,'Assign');
			//添加上级节点
			autoAddParentNode(mid,rid);
			//打开操作窗口
			openWind('','',tpath,tarId,'A');
		}
	}

	//纵转横Z
	var lSelId;
	var rSelId;
	function rowToColumn(lid,mid,rid){
		lSelId = lid;
		var srcId = $('#'+lid).attr('nodeDescId');//左节点ID
		var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
		var fromval = $('#'+lid).attr('name');//源节点名称
		var tName = $('#'+rid).attr('name');//目标节点名称
		var tpath = $('#'+rid).attr('path');//目标节点path
		var spath = $('#'+lid).attr('path');//源节点path
		var state = isExitLine(srcId,tarId,"Z");
		if("EXIT" == state){
			if(confirm(title[14])){	//纵转横操作已经存在,确定要继续操作吗?
				$("#"+mid).addClass("relation fade-model").find("span").html("R to C");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"Z");
				//打开操作窗口
				openWindRToC(fromval,spath,tName,tarId,'Z');
			}
		}else if("USED" == state){
			alert(nodeUsed);		//目标节点已经被使用
			removeLine(lid,rid,mid);
		}else if("NOT_IN_LIST" == state){
			alert(srcDel);			//数据异常:源节点协议可能已经被删除
			removeLine(lid,rid,mid);
		}else if("UPDATE" == state){
			if(confirm(title[15])){		//确定要修改为“纵转横”操作吗?
				$("#"+mid).addClass("relation fade-model").find("span").html("R to C");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"Z");
				changeTableTr(lid,rid,"R to C");
				//打开操作窗口
				openWindRToC(fromval,spath,tName,tarId,'Z');
				$("#conNodeValue").val("");
			}
		}else if("NO_DATA" == state){
			//修改线的数据
			addLineToDate(srcId,tarId,"Z");
			//添加表格记录
			addTableTr(lid,rid,'R to C');
			//添加上级节点
			autoAddParentNode(mid,rid);
			//打开操作窗口
			openWindRToC(fromval,spath,tName,tarId,'Z');
		}
	}
	
	//横转纵H
	function columnToRow(lid,mid,rid){
		rSelId = rid;
		var srcId = $('#'+lid).attr('nodeDescId');//左节点ID
		var tarId = $('#'+rid).attr('nodeDescId');//右节点ID
		var fromval = $('#'+lid).attr('name');//源节点名称
		var tpath = $('#'+rid).attr('path');//目标节点path
		var spath = $('#'+lid).attr('path');//源节点path
		var state = isExitLine(srcId,tarId,"H");
		if("EXIT" == state){
			if(confirm(title[16])){	//横转纵操作已经存在,确定要继续操作吗?
				$("#"+mid).addClass("relation fade-model").find("span").html("C to R");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"H");
				//打开操作窗口
				openWindCToR(fromval,spath,tpath,tarId,'H');
			}
		}else if("USED" == state){
			///alert(nodeUsed);		//目标节点已经被使用  ?
			///removeLine(lid,rid,mid);
		}else if("NOT_IN_LIST" == state){
			alert(srcDel);			//数据异常:源节点协议可能已经被删除
			removeLine(lid,rid,mid);
		}else if("UPDATE" == state){
			if(confirm(title[17])){		//确定要修改为“横转纵”操作吗?
				$("#"+mid).addClass("relation fade-model").find("span").html("C to R");
				$("#"+rid).addClass("checked");	
				$("#"+lid).addClass("checked");
				//修改线的数据
				addLineToDate(srcId,tarId,"H");
				changeTableTr(lid,rid,"C to R");
				//打开操作窗口
				openWindCToR(fromval,spath,tpath,tarId,'H');
			}
		}else if("NO_DATA" == state){
			//添加线的数据
			addLineToDate(srcId,tarId,"H");
			//添加表格记录
			addTableTr(lid,rid,'C to R');
			//添加上级节点
			autoAddParentNode(mid,rid);
			//打开操作窗口
			openWindCToR(fromval,spath,tpath,tarId,'H');
		}
	}
	
	function chooseSrcBrothersNodePathNode(){
		var e="l";
		var lid = lSelId;
		$("#sTolistL").html("");
		var nodedescid			=$(".GooFlow_work_inner ul."+e+" li[id='"+lid+"']").attr("nodedescid");
		var nodedescidpath	=$(".GooFlow_work_inner ul."+e+" li[id='"+lid+"']").attr("nodedescidpath");
		var pNodedescidpath = nodedescidpath.replace(nodedescid,"");
		for(var j=0; j<$(".GooFlow_work_inner ul."+e+" li").length; j++){
			var opid=$(".GooFlow_work_inner ul."+e+" li").eq(j).attr("id");
			var iNodedescid			=$(".GooFlow_work_inner ul."+e+" li").eq(j).attr("nodedescid");
			var iNodedescidpath	=$(".GooFlow_work_inner ul."+e+" li").eq(j).attr("nodedescidpath");
			var iPNodedescidpath = iNodedescidpath.replace(iNodedescid,"");
			if(pNodedescidpath == iPNodedescidpath && nodedescid != iNodedescid){
				$("#sTolistL").append($("#"+opid).clone(true));
			}
		}
		$("#sTolistL li").click(function(){
			$(this).addClass("act").siblings().removeClass("act");
		});
		$("#lChooseBrothersNodePathModal").modal("show");
	}

	function chooseTarBrothersNodePathNode(srcNodeDescId){
		$('#pageSrcNodeDescId').val(srcNodeDescId);	//源节点ID
		var e="r"
		var lid = rSelId;
		$("#sTolistR").html("");
		var nodedescid			=$(".GooFlow_work_inner ul."+e+" li[id='"+lid+"']").attr("nodedescid");
		var nodedescidpath	=$(".GooFlow_work_inner ul."+e+" li[id='"+lid+"']").attr("nodedescidpath");
		var pNodedescidpath = nodedescidpath.replace(nodedescid,"");
		for(var j=0; j<$(".GooFlow_work_inner ul."+e+" li").length; j++){
			var opid=$(".GooFlow_work_inner ul."+e+" li").eq(j).attr("id");
			var iNodedescid			=$(".GooFlow_work_inner ul."+e+" li").eq(j).attr("nodedescid");
			var iNodedescidpath	=$(".GooFlow_work_inner ul."+e+" li").eq(j).attr("nodedescidpath");
			var iPNodedescidpath = iNodedescidpath.replace(iNodedescid,"");
			if(pNodedescidpath == iPNodedescidpath && nodedescid != iNodedescid){
				$("#sTolistR").append($("#"+opid).clone(true));
			}
		}
		$("#sTolistR li").click(function(){
			$(this).addClass("act").siblings().removeClass("act");
		});
		$("#rChooseBrothersNodePathModal").modal("show");
	}
	

	function openWindRToC(sname,spath,tName,tarNodeDescId,operator){
		$('#leftNodeName').html(sname);
		$('#rightNodeName').html(tName);
		$('#pageTarNodeDescId').val(tarNodeDescId);
		//取得节点取值要求
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/getNodeValAdaReq.shtml",
		    dataType:'json',
		    data:{pageTarNodeDescId:tarNodeDescId,pageContractAdapterId:$('#pageContractAdapterId').val()},
			success:function(msg){
				if('NO_DATA' == msg.result){
					//说明第一次配置
					$("#conNodeValue").val("");
				    $('#rToCConfigModal').modal('show');//显示窗口
				    
					$("#conTable").hide();
					$("#conAssignmentConditionText").hide();
					$("#conAssignmentConditionText").text("");
					$("#conAssignmentCondition").hide();
					$("#conAssignmentCondition").val("");
				}else{
					//说明有数据
					var nodeReqId = msg.nodeReqId;
					$('#pageNodeReqId').val(nodeReqId);
					//var valueWay = msg.valueWay;
					var valueExp = msg.valueExp;
					//var valueScript = msg.valueScript;
					var triggerExp = msg.triggerExp;
					triggerExp = triggerExp.replace('src_node_val=="','');
					triggerExp = triggerExp.replace('"','');
					triggerExp = triggerExp.replace(/(^\s*)|(\s*$)/g,'');
					if(triggerExp==""){
						$("#conTable").hide();
						$("#conAssignmentConditionText").hide();
						$("#conAssignmentConditionText").text("");
						$("#conAssignmentCondition").hide();
						$("#conAssignmentCondition").val("");
					}else{
						$("#conTable").show();
						$("#conAssignmentCondition").hide();
						$("#conAssignmentCondition").val(triggerExp);
						$("#conAssignmentConditionText").show();
						$("#conAssignmentConditionText").text(triggerExp);
					}
					$('#conNodeValue').val(valueExp);
					$('#rToCConfigModal').modal('show');//显示窗口
				}
	          }
	     });
	}

	function openWindCToR(sname,spath,tpath,tarNodeDescId,operator){
		$('#pageTarNodeDescId').val(tarNodeDescId);
		$('#cToRConfigModal').modal('show');//显示窗口
		loadCToRLinesData()
	}
	
	

	 function loadCToRLinesData(){
		$.each($('#cToRLinesTable>tbody>tr'), function(){
			$(this).remove();		//移除已加载的行
		});
		 var pageContractAdapterId = $('#pageContractAdapterId').val();	//协议转化ID
		 var pageTarNodeDescId = $('#pageTarNodeDescId').val();				//目标节点ID
		 $.ajax({
				type: "POST",
				async:true,
			    url: "../newadapter/loadCToRLinesData.shtml",
			    dataType:'json',
			    data:{pageContractAdapterId:pageContractAdapterId,pageTarNodeDescId:pageTarNodeDescId},
				success:function(msg){
					$.each(msg.all,function(key,val) {
						loadRToCLinesTable(key,val);		//在列表中加载此目标节点已配的横转纵数据
					});
	           }
	      });
	 }
	 
	 function loadRToCLinesTable(rN,row){
			var trValue = "<tr>" +
			"<td>"+(rN+1)+"</td>" +
			"<td><div style=\"width:130px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis\" title=\""+row.SRC_NODE_NAME+"\">"+row.SRC_NODE_NAME+"</div></td>" +
			"<td><div style=\"width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis\" title=\""+row.SRC_NODE_PATH+"\">"+row.SRC_NODE_PATH+"</div></td>" +
			"<td><div style=\"width:130px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis\" title=\""+row.TAR_NODE_NAME+"\">"+row.TAR_NODE_NAME+"</div></td>" +
			"<td><div style=\"width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis\" title=\""+row.TAR_NODE_PATH+"\">"+row.TAR_NODE_PATH+"</div></td>" +
			"<td style=\"border-right-width:0;\"><div style=\"width:180px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis\" title=\""+row.VALUE_EXPRESS+"\">"+
				(row.VALUE_EXPRESS==null?"&nbsp;":row.VALUE_EXPRESS)+
			"</div></td>";
			if(row.VALUE_EXPRESS==null){
				trValue += "<td style=\"border-left-width:0;\"><button type=\"button\"  onclick=\"chooseTarBrothersNodePathNode('"+row.SRC_NODE_DESC_ID+"')\" class=\"btn btn-primary\"  style=\"height:22px; line-height:22px; padding:0 6px;\">Select</button></td>";
			}else{
				trValue += "<td style=\"border-left-width:0;\"><button type=\"button\"  onclick=\"chooseTarBrothersNodePathNode('"+row.SRC_NODE_DESC_ID+"')\" class=\"btn btn-default\"  style=\"height:22px; line-height:22px; padding:0 6px;\">Change</button></td>";
			}
			trValue += "</tr>";
			$("#cToRLinesTable>tbody").append(trValue);
	}
	 
	
	/**
	 * 删除线操作
	 * @param lid
	 * @param rid
	 * @param mid
	 */
	function removeLine(lid,rid,mid){
		$("#"+lid).removeClass("checked");
		$("#"+rid).removeClass("checked");
		$("#"+mid).remove();//删除线操作
	}
	/**
	 * 打开窗口操作
	 * @param sname 源节点名称
	 * @param spath 源节点路径
	 * @param tpath 目标节点路径
	 * @param tarNodeDescId 目标节点ID
	 * @param operator 操作类别:U、A
	 */
	function openWind(sname,spath,tpath,tarNodeDescId,operator){
		$('#showNodeName').val(sname);
		$('#showNodePath').val(spath);
		$('#pageNodePath').val(tpath);
		$('#pageTarNodeDescId').val(tarNodeDescId);
		if('A' == operator){
			$('#chooseTab2').find('li').eq(0).find('a').click();//设置Tab选中
			$('#chooseTab2').find('li').eq(0).show();
			$('#chooseTab2').find('li').eq(1).hide();
			$('#chooseTab2').find('li').eq(2).hide();
		}else{
			$('#chooseTab2').find('li').eq(0).show();
			$('#chooseTab2').find('li').eq(1).show();
			$('#chooseTab2').find('li').eq(2).show();
		}
		//取得节点取值要求
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/getNodeValAdaReq.shtml",
		    dataType:'json',
		    data:{pageTarNodeDescId:tarNodeDescId,pageContractAdapterId:$('#pageContractAdapterId').val()},
			success:function(msg){
				if('NO_DATA' == msg.result){//说明第一次配置
					$('#chooseTab2').find('li').eq(0).find('a').click();//设置Tab选中
				    $('#chooseTemplate').modal('show');//显示窗口
				}else{//说明有数据
					var nodeReqId = msg.nodeReqId;
					$('#pageNodeReqId').val(nodeReqId);
					var valueWay = msg.valueWay;
					var valueExp = msg.valueExp;
					var valueScript = msg.valueScript;
					var triggerExp = msg.triggerExp;
					$('#pageAssignmentCondition').val(triggerExp);
					if('2' == valueWay){//固定值
						$('#pageNodeValue').val(valueExp);
						$('#pageJavaBean').val('');
					}else if('3' == valueWay){//Map方式
						$('#chooseTab2').find('li').eq(2).find('a').click();//设置Tab选中
						$('#pageConsMapCDFinal').val(valueExp);//设置变量类型隐藏域
						$('#pageConsMapCD').val(valueExp);//设置变量类型显示
						$('#pageConsMapName').val(getVarMapTypeName(valueExp));
						oTable2.api().ajax.url('../newadapter/getValableMap.shtml?pageConsMapCD='+valueExp).load();
					}else if('4' == valueWay){//JavaBean
						$('#pageNodeValue').val('');
						$('#pageJavaBean').val(valueScript);
						$('#chooseTab2').find('li').eq(1).find('a').click();//设置Tab选中
					}
					$('#chooseTemplate').modal('show');//显示窗口
				}
	          }
	     });
		
	}
	/**
	 * 得到常量映射类型名称
	 */
	function getVarMapTypeName(consMapCD){
		var result = '';
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/getVarMapTypeName.shtml",
		    dataType:'json',
		    data:{pageConsMapCD:consMapCD},
			success:function(msg){ 
				result = msg.result;
	          }
	     });
		return result;
	}
	/**
	 * 选中线时效果
	 * @param lid
	 * @param mud
	 * @param rid
	 */
	function nodeChecked(lid,mud,rid){
		var tarNodeDescID = $('#'+rid).attr('nodeDescId');
		var trId = tarNodeDescID+'trValue';
		changeRowColor();
		$('#'+trId).addClass("cur");
	}
	/**
	 * 删协议操作
	 * @param e:协议ID
	 * action:XX为复选框,R 为R操作,T为T操作
	 */
	function removeVersion(e,action){
		if(checkLeftTableRecords(e+'table')>0){
			alert(notDelSrc);
		}else{
			//删除协议操作
			$("#"+e).remove();
			$(".connect-line").each(function(index, element) {
	            var from=$(this).attr("from");
				var to=$(this).attr("to");
				$(this).remove();
				drawConcenectLine(from,to,"1");
	        });
			reSize();	
			//清除表格记录
			$('#'+e+'table').remove();
			//清除协议记录
			$.ajax({
				type: "POST",
				async:false,
			    url: "../newadapter/delContractRecords.shtml",
			    dataType:'json',
			    data:{pageTarTcpCtrFId:e,pageEndpointId:$('#pageEndpointId').val(),pageContractAdapterId:$('#pageContractAdapterId').val()},
				success:function(msg){
		          }
		     });
		}
	}
	/**
	 * 查看源协议是否有拉线
	 * @param tableId
	 */
	function checkLeftTableRecords(tableId){
		var num = 0;
		$.each($('#'+tableId+'>tbody>tr'), function () {
			num++;
		});
		return num;
	}
	/**
	 * 协议类型转换
	 * @param e:协议ID
	 */
	function changeVType(e,action){
		var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
		if('r' == action){
			var type = isExitAction(e,'R');
			if('EXIT' == type){
				alert(risExist);
				var actionValue = getActionValue(e,pageContractAdapterId);
				if('R' == actionValue){
					$("#"+e).find(".head .flag").find("input[value='r']").attr("checked",true);
				}else{
					$("#"+e).find(".head .flag").find("input[value='t']").attr("checked",true);
				}
			}else{
				var result = changeAction(e,'R',pageContractAdapterId);
				if('success' == result){
					$("#"+e).find(".head .check").next().css("visibility","visible");
					$("#"+e).find(".head .flag").find("input[value='r']").attr("checked",true);
					$("#"+e).find(".head").css("background","#690e02");
				}else{
					alert(dataError);
				}
			}
		}else if('XX' == action){
			var type = isExitAction(e,'XX');
			if('EXIT' == type){
				alert(intoProExist);
				$("#"+e).find(".head .check input").attr("checked",false);
			}else{
				var result = changeAction(e,'XX',pageContractAdapterId);
				if('success' == result){
					$("#"+e).find(".head .check").next().css("visibility","hidden");
					$("#"+e).find(".head").css("background","#FF8C00");
				}else{
					alert(dataError);
				}
			}
		}else{
			var result = changeAction(e,'T',pageContractAdapterId);
			if('success' == result){
				$("#"+e).find(".head .check").next().css("visibility","visible");
				$("#"+e).find(".head .flag").find("input[value='t']").attr("checked",true);
				$("#"+e).find(".head").css("background","#ab5a03");
			}else{
				alert(dataError);
			}
		}
	}
	/**
	 * 
	 * @param pageSrcTcpCtrFId源协议格式ID
	 * @param operator 动作类型：R、T、XX
	 * @param pageContractAdapterId协议转化ID
	 */
	function changeAction(pageSrcTcpCtrFId,operator,pageContractAdapterId){
		var result = '';
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/changeToAction.shtml",
		    dataType:'json',
		    data:{pageSrcTcpCtrFId:pageSrcTcpCtrFId,operator:operator,pageContractAdapterId:pageContractAdapterId,pageEndpointId:$('#pageEndpointId').val()},
			success:function(msg){
				result = msg.result;
           }
      });
		return result;
	}
	/**
	 * @param pageSrcTcpCtrFId源协议格式ID
	 * @param pageContractAdapterId协议转化ID
	 */
	function getActionValue(pageSrcTcpCtrFId,pageContractAdapterId){
		var result = '';
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/getActionValue.shtml",
		    dataType:'json',
		    data:{pageSrcTcpCtrFId:pageSrcTcpCtrFId,pageContractAdapterId:pageContractAdapterId},
			success:function(msg){
				result = msg.result;
           }
      });
		return result;
	}
	/**
	 * 删除表格行记录
	 * @param lid
	 * @param rid
	 */
	function delTableTr(lid,rid,action){
		var str= new Array();   
		var tcp_ctr_f_id = '';
		var srcNodeDescID = '';
		if('Update' == action || 'Move' == action || 'R to C' == action || 'C to R' == action){
			str = lid.split("L");
			tcp_ctr_f_id = str[0];
			srcName = $('#'+lid).attr('name');
			srcNodeDescID = $('#'+lid).attr('nodeDescId');
		}else{ 
			str = rid.split("R");
			tcp_ctr_f_id = str[0]+'Righ';
		}
		var tarNodeDescID = $('#'+rid).attr('nodeDescId');
		var trId = srcNodeDescID+tarNodeDescID+'trValue';
		$('#'+trId).remove();
		//表格记录重新排列
		var num = 1;
		$.each($('#'+tcp_ctr_f_id+'table>tbody>tr'), function () {
			$(this).find('td').eq(0).text(num++);
		});
	}
	/**
	 * 添加表格记录
	 */
	function addTableTr(lid,rid,action){
		var tcp_ctr_f_id = '';
		var srcName = '';
		var srcNodeDescID = '';
		var str= new Array();   
		if('Update' == action || 'Move' == action || 'R to C' == action || 'C to R' == action){
			str = lid.split("L");
			tcp_ctr_f_id = str[0];
			srcName = $('#'+lid).attr('name');
			srcNodeDescID = $('#'+lid).attr('nodeDescId');
		}else{ 
			str = rid.split("R");
			tcp_ctr_f_id = str[0]+'Righ';
		}
		var tarName = $('#'+rid).attr('name');
		var tarNodeDescID = $('#'+rid).attr('nodeDescId');
		var num = 1;
		$.each($('#'+tcp_ctr_f_id+'table>tbody>tr'), function () {
			num++;
		});
		var trId = srcNodeDescID+tarNodeDescID+'trValue';
		var trValue = "<tr id="+trId+" name='tid' value='"+tarNodeDescID+"' onclick=\"clickRow('"+rid+"')\">"
			        + '<td>'+num+'</td>'
			        + '<td>'+srcName+'</td>'
			        + '<td>'+tarName+'</td>'
			        + '<td>'+action+'</td>';
		if('Update' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWind('"+$('#'+lid).attr('name')+"','"+$('#'+lid).attr('path')+"','"+$('#'+rid).attr('path')+"','"+tarNodeDescID+"','U')\"    data-toggle='modal'>Operation</a></td></tr>";
		}else if('Assign' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWind('','','"+$('#'+rid).attr('path')+"','"+tarNodeDescID+"','A')\"    data-toggle='modal'>Operation</a></td></tr>";
		}else if('R to C' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWindRToC('"+$('#'+lid).attr('name')+"','"+$('#'+lid).attr('path')+"','"+$('#'+rid).attr('name')+"','"+tarNodeDescID+"','Z')\"    data-toggle='modal'>Operation</a></td></tr>";
		}else if('C to R' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWindCToR('"+$('#'+lid).attr('name')+"','"+$('#'+lid).attr('path')+"','"+$('#'+rid).attr('path')+"','"+tarNodeDescID+"','H')\"    data-toggle='modal'>Operation</a></td></tr>";
		}else{
			trValue = trValue + '<td></td></tr>';
		}
		$('#'+tcp_ctr_f_id+'table').append(trValue);
		//清空表格选中样式
		changeRowColor();
		//设置当前行选中
		$('#'+trId).addClass("cur");
	}
	/**
	 * 修改表格记录
	 */
	function changeTableTr(lid,rid,action){
		var tcp_ctr_f_id = '';
		var srcName = '';
		var srcNodeDescID = '';
		var str= new Array();   
		if('Update' == action || 'Move' == action || 'R to C' == action || 'C to R' == action){
			str = lid.split("L");
			tcp_ctr_f_id = str[0];
			srcName = $('#'+lid).attr('name');
			srcNodeDescID = $('#'+lid).attr('nodeDescId');
		}else{ 
			str = rid.split("R");
			tcp_ctr_f_id = str[0]+'Righ';
		}
		var tarName = $('#'+rid).attr('name');
		var tarNodeDescID = $('#'+rid).attr('nodeDescId');
		var trId = srcNodeDescID+tarNodeDescID+'trValue';
		var trValue = "<tr id="+trId+" name='tid' value='"+tarNodeDescID+"' onclick=\"clickRow('"+rid+"')\">"
			        + '<td></td>'
			        + '<td>'+srcName+'</td>'
			        + '<td>'+tarName+'</td>'
			        + '<td>'+action+'</td>';
		if('Update' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWind('"+$('#'+lid).attr('name')+"','"+$('#'+lid).attr('path')+"','"+$('#'+rid).attr('path')+"','"+tarNodeDescID+"','U')\"     data-toggle='modal'>Operation</a></td></tr>";
		}else if('Assign' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWind('','','"+$('#'+rid).attr('path')+"','"+tarNodeDescID+"','A')\"     data-toggle='modal'>Operation</a></td></tr>";
		}else if('R to C' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWindRToC('"+$('#'+lid).attr('name')+"','"+$('#'+lid).attr('path')+"','"+$('#'+rid).attr('name')+"','"+tarNodeDescID+"','Z')\"     data-toggle='modal'>Operation</a></td></tr>";
		}else if('C to R' == action){
			trValue = trValue + "<td><a onclick=\"javascript:openWindCToR('"+$('#'+lid).attr('name')+"','"+$('#'+lid).attr('path')+"','"+$('#'+rid).attr('path')+"','"+tarNodeDescID+"','H')\"     data-toggle='modal'>Operation</a></td></tr>";
		}else{
			trValue = trValue + '<td></td></tr>';
		}
		$('#'+trId).replaceWith(trValue);
		//表格记录重新排列
		var num = 1;
		$.each($('#'+tcp_ctr_f_id+'table>tbody>tr'), function () {
			$(this).find('td').eq(0).text(num++);
		});
		//清空表格选中样式
		changeRowColor();
		//设置当前行选中
		$('#'+trId).addClass("cur");
	}
	/**
	 * 设置记录选中方法
	 * @param to 目标节点ID
	 */
	function clickRow(to){
		lineRelation(to);//设置树节点选中
	}
	/**
	 * 清空表格选中样式
	 */
	function changeRowColor(){
		$.each($('#FlowTable>table>tbody>tr'), function () {
			$(this).removeClass("cur");
		});
	}
	/**
	 * 自动添加父节点
	 * @param currentId:目标节点ID
	 */
	function autoAddParentNode(mud,rid){
		var nodeDescIdPath = $('#'+rid).attr('nodeDescIdPath');
		var nodeDescIdArray = nodeDescIdPath.split('/');
		for (var i=1;i<nodeDescIdArray.length - 1;i++) {
			if (nodeDescIdArray[i] != "") {
				$.each($nodeRightData, function (key, value) {
					  if (value.nodeDescId == nodeDescIdArray[i]) {
						  var state = isExitLine(null,nodeDescIdArray[i],'R');
						  if("NO_DATA" == state){
							  addParentnode(key);//添加父节点的线操作
							  //添加线记录
							  addLineToDate(null,nodeDescIdArray[i],"R");
							  //添加上级节点的列表记录
							  addTableTr('',key,'Other');
						  }
					  }
				});
			}
		}
		nodeChecked("",mud,rid);//设置当前操作记录选中
	}
	/**
	 * 加载节点数据
	 * @param my_object
	 */
	function loadData(my_object){
		dadaobj=my_object;
		var josnObj=my_object.nodes.RightNode;
		if(josnObj!=null){
			this.$nodeRightData=my_object.nodes.RightNode;//给右边节点复值
			$(".GooFlow_work_inner").append("<ul class='r'></ul>");
			$.each(josnObj,function(key,val){
				var ns=val.childCount>0?"par":"child";
				var lli='<li id="'+key+'" class="'+ns+'" name="'+val.name+'" nodeType="'+val. nodeType+'" nodeNum="'+val.nodeNum+'"  dept="'+val.dept+'" path="'+val.path+'"  nodeDescIdPath="'+val.nodeDescIdPath+'" childCount="'+val.childCount+'" nodeDescId="'+val.nodeDescId+'"><span class="d-t"><em style="margin-left:'+val.dept*5+"px"+'" >'+val.name+'</em></span><span class="d-s"><b>'+val.nodeNum+'</b>'+val.nodeType+'</span><div class="tips">'+val.name+'<br>'+val.description+'</div></li>';
				$("ul.r").append(lli);
			});
		}
		
		josnObj=my_object.nodes.LeftNode;
		if(josnObj!=null){
			$.each(josnObj,function(key,val){
				josnObjList=josnObj[key];
				var id=key;
				$(".baseversion").append("<ul class='l' id='"+id+"'></ul>");
				$("#"+id).append("<div class='head'><span class='title' tip='"+josnObjList.versionName+"'>"+subStr(josnObjList.versionName)+"</span><span class='check' tip='"+josnObjList.checkbox+"'><label><input type='checkbox' >Into</label></span><span class='flag'><span class='basicid' tip='"+josnObjList.inputId+"'><img src='../resources/bootstrap/img/pencil.png'></span></span><span class='close'>X</span></div>");
				//$("#"+id).append("<div class='head'><span class='title' tip='"+josnObjList.versionName+"'>"+subStr(josnObjList.versionName)+"</span><span class='check' tip='"+josnObjList.checkbox+"'><label><input type='checkbox' >Into</label></span><span class='flag'><span class='basicid' tip='"+josnObjList.inputId+"'><img src='../resources/bootstrap/img/pencil.png'></span><label  tip='"+josnObjList.actionR+"'><input type='radio' name='"+id+"' value='r'>R</label><label  tip='"+josnObjList.actionT+"'><input type='radio' name='"+id+"' value='t' checked='checked'>T</label></span><span class='close'>X</span></div>");
				//$("#"+id).append("<div class='head'><span class='title' tip='"+josnObjList.versionName+"'>"+subStr(josnObjList.versionName)+"</span></div>");
				
				if(josnObj[key].flags=="false"){
					$("#"+id).find(".head").css("background","#FF8C00").find(".flag").css("visibility","hidden");
					$("#"+id).find("input:checkbox").attr("checked","checked");
					}
				else if(josnObj[key].flags=="T"){
					$("#"+id).find(".head").css("background","#ab5a03").find(".flag").find("input[value='t']").attr("checked","checked");
					}
				else if(josnObj[key].flags=="R"){
					$("#"+id).find(".head").css("background","#690e02").find(".flag").find("input[value='r']").attr("checked","checked");
					}
				
				
				var nodeList=josnObjList.nodeList;
				$.each(nodeList,function(key,val){
					var ns=val.childCount>0?"par":"child";
					var lli='<li id="'+key+'" class="'+ns+'" name="'+val.name+'" nodeType="'+val. nodeType+'" nodeNum="'+val.nodeNum+'"  dept="'+val.dept+'" path="'+val.path+'"  nodeDescIdPath="'+val.nodeDescIdPath+'" childCount="'+val.childCount+'" nodeDescId="'+val.nodeDescId+'"><span class="d-t"><em style="margin-left:'+val.dept*5+"px"+'" >'+val.name+'</em></span><span class="d-s"><b>'+val.nodeNum+'</b>'+val.nodeType+'</span><div class="tips">'+val.name+'<br>'+val.description+'</div></li>';
					$("#"+id).append(lli);
				});
				
			});
		}
		reHeight();
		//限制字符长度
		resizeStr();
	}
	/**
	 * 加载线数据
	 * @param linejson
	 */
	function loadLine(linejson){
		var linelist=linejson.lines;
		$.each(linelist,function(key,val){
			var operate=val.operate;
			var rid=val.to;
			if(val.from==""){
				addParentnode(rid,operate);
				}
			else{
				var lid=val.from;
				drawConcenectLine(lid,rid,"1",operate);
				$("#"+val.from).addClass("checked");	
				$("#"+val.to).addClass("checked");	
				}
			});
		reSize();
	}
	/**
	 * 协议名显示
	 * @param e
	 * @returns
	 */
	function subStr(e){
		if(e.length>=10){
			return(e.substring(0,9)+"…");
			}
     	else{return(e);}
	}
	/**
	 * 判断线是否存在
	 * @param srcId 左节点ID
	 * @param tarId 右节点ID
	 * @param operator 操作类别:M、R、A、U
	 * @returns :EXIT数据已经存在,NOT_IN_LIST源目标节点不在列表中,USED 目标节点已经被使用,UPDATE更新操作,NO_DATA数据不存在,
	 */
	function isExitLine(srcId,tarId,operator){
		var result = '';
		var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/isExitLine.shtml",
		    dataType:'json',
		    data:{operator:operator,pageContractAdapterId:pageContractAdapterId,pageSrcNodeDescId:srcId,pageTarNodeDescId:tarId},
			success:function(msg){ 
				result = msg.result;
	          }
	     });
		return result;
	}
	/**
	 * 判断动作类型是否已经存在
	 * @param e
	 * @param operator:XX,R,T
	 * @returns :EXIT存在,NO_DATA不存在数据
	 */
	function isExitAction(e,operator){
		var result = '';
		var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/isExitOperator.shtml",
		    dataType:'json',
		    data:{operator:operator,pageContractAdapterId:pageContractAdapterId},
			success:function(msg){ 
				result = msg.result;
	          }
	     });
		return result;
	}
	
	 //添加线到数据库srcId:源节点ID,tarId:目标节点ID,typeV:节点操作类型
	function addLineToDate(srcId,tarId,typeV){
		var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/saveNodeDescMap.shtml",
		    dataType:'json',
		    data:{pageSrcNodeDescId:srcId,pageTarNodeDescId:tarId,operator:typeV,pageContractAdapterId:pageContractAdapterId},
			success:function(msg){
           }
      });
	}
	//删除线数据库srcId:源节点ID,tarId:目标节点ID
	function delLineToDate(srcId,tarId){
		var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
		 $.ajax({
				type: "POST",
				async:false,
			    url: "../newadapter/delNodeDescMap.shtml",
			    dataType:'json',
			    data:{pageSrcNodeDescId:srcId,pageTarNodeDescId:tarId,pageContractAdapterId:pageContractAdapterId},
				success:function(msg){
	           }
	      });
	}
	/**
	 * 修改消息流ID
	 */
	function updateMessageFlow(contractFormatId){
		var pageContractAdapterId = $('#pageContractAdapterId').val();//协议转化ID
		$.ajax({
			type: "POST",
			async:false,
		    url: "../newadapter/showMessageFlowId.shtml",
		    dataType:'json',
		    data:{pageSrcTcpCtrFId:contractFormatId,pageContractAdapterId:pageContractAdapterId},
			success:function(msg){
				//$('#ProtocolId').val(msg.messageFlowId);
				$('#ProtocolId').select2("val", msg.messageFlowId);
           }
      });
	}
	function reHeight(){
		var n=$(".baseversion ul.l").length;
		var i=0;
		for(var c=0;c<n;c++){
			i+=$(".baseversion ul.l").eq(c).height()+49;
			}
		var j=$("ul.r").height();
		var nm=i>j?i:j;
		$(".GooFlow_work").height(nm+20+"px");
		$("#draw_UniversalAdapterDemo").height(nm+20+"px");
		$(".GooFlow_work_inner").height(nm+20+"px");		
	}
	