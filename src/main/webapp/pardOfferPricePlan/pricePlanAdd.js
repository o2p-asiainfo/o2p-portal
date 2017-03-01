var PricePlanAdd = function() {
    var handleSelectZoneModal = function(selector) {
        var zonesDT = $(selector).find("#zonesDT").dataTable({
            "processing": true,
            "serverSide": true,
            "pagingType": "bootstrap_full_number",
            "searching": false,
            "ajax": "../pardOfferPricePlan/getZoneSpecList.shtml",
            "order": [],
            "autoWidth": false,
            "columns": [{
                "data": "INDEX"
            }, {
                "data": "OP_ID"
            }, {
                "data": "OP_NAME"
            }, {
                "data": "LONGITUDE"
            }, {
                "data": "LATITUDE"
            }, {
                "data": "OP_DESC"
            }],
            "columnDefs": [{
                "orderable": false,
                "targets": [0, 1, 2, 3, 4, 5],
                "width": "25px"
            }, {
                "visible": true,
                "targets": [0, 1, 2, 3, 4, 5]
            }],
            "initComplete": function() {
                $(selector).find("#zonesDT tbody").find('input[type="checkbox"]:checked').each(function() {
                    $(this).closest('tr').addClass('active');
                })
            },
            "drawCallback": function() {
                App.ajaxInit();
            }
        });
        $(selector).find("#zonesDT_wrapper .dataTables_filter input").addClass("form-control input-small input-inline"); // modify table search input
        $(selector).find("#zonesDT_wrapper .dataTables_length select").addClass("form-control input-small"); // modify table per page dropdown
        //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
        $(selector).find('.zoneOK').on('click', function() {
            $(this).closest("#selectZoneModal").modal("hide");
            var idArr = [];
            var nameArr = [];
            $(this).closest("#selectZoneModal").find("tbody input[type='checkbox']:checked").each(function(i, o) {
                idArr.push(zonesDT.fnGetData(zonesDT.$('tr.active').get(i)).OP_ID);
                nameArr.push(zonesDT.fnGetData(zonesDT.$('tr.active').get(i)).OP_NAME);
            });
            $(selector).find("#zoneDiv").find("#defaultVal").val(idArr.join("/"));
            $(selector).find("#zoneDiv").find("#defaultValName").val(nameArr.join("/"));
        });
        $(selector).find(".zoneCancel").click(function() {
            $(this).closest("#selectZoneModal").modal('hide');
        });
        $(selector).find("#zoneModal").click(function() {
            $(selector).find("#selectZoneModal").modal('show');
        });
        $(selector).find("#zoneQuery").click(function() {
            var radius = $(this).closest("#selectZoneModal").find('#radius').val();
            var lat = $(this).closest("#selectZoneModal").find('#lat').val();
            var lng = $(this).closest("#selectZoneModal").find('#lng').val();
            var nameOrId = $(this).closest("#selectZoneModal").find('#nameOrId').val();
            var url = APP_PATH + "/pardOfferPricePlan/getZoneSpecList.shtml?lat=" + lat + "&lng=" + lng + "&nameOrId=" + nameOrId + "&radius=" + radius;
            zonesDT.api().ajax.url(url).load();
        });
    }
    var handleJstree = function(selector) {
        var prodOfferId = $("#prodOfferId").val();
        var relIds = $(selector).find("#relIds").val();
        $.ajax({
            async: false,
            type: "POST",
            url: APP_PATH + "/pardOffer/getPardOffertProdTree.shtml",
            dataType: "json",
            data: {
                prodIds: prodOfferId,
                idvalue: relIds
            },
            success: function(data) {
                if (data.code == "0000") {
                    $(selector + ' .pp').jstree({
                        'plugins': ["checkbox", "types"],
                        'core': {
                            'data': data.desc
                        },
                        "types": {
                            "default": {
                                "icon": "fa fa-folder icon-warning icon-lg"
                            },
                            "file": {
                                "icon": "fa fa-file icon-warning icon-lg"
                            }
                        }
                    });
                    $(selector + ' .pp').on('changed.jstree', function(e, data) {
                        var prodOfferId = $("#prodOfferId").val();
                        prodOfferId = "A" + prodOfferId;
                        var checkTextAry = [];
                        var checkIdAry = [];
                        for (i = 0, j = data.selected.length; i < j; i++) {
                            if (data.instance.get_node(data.selected[i]).id != prodOfferId) {
                                checkTextAry.push(data.instance.get_node(data.selected[i]).text);
                                checkIdAry.push(data.instance.get_node(data.selected[i]).id);
                            }
                        }
                        $(selector).find("#jstreeTextString").val(checkTextAry.join(","));
                        $(selector).find("#relIds").val(checkIdAry.join(","));
                    });
                } else {
                    toastr.error(data.desc);
                }
            }
        });
    }
    var handlePOJstree = function(selector, formName) {
        var thisForm = $(selector).closest("form[id=" + formName + "]");
        var baseItemTypeObj = $(thisForm).find("select[id=baseItemType]");
        var baseItemType = baseItemTypeObj.val();
        var itemNamesObj = $(thisForm).find("input[id=itemNames]");
        var itemIdsObj = $(thisForm).find("#itemIds");
        $(thisForm).find("div[id=poJstreeDiv]").hide();
        $(thisForm).find("div[id=loadingImg_tree]").show();
        $.ajax({
            async: true,
            type: "POST",
            url: APP_PATH + "/pardOfferPricePlan/getPricingSubjectTree.shtml",
            data: {
                baseItemType: baseItemType,
                itemIds: itemIdsObj.val()
            },
            success: function(data) {
                $(thisForm).find("div[id=poJstreeDiv]").show();
                $(thisForm).find("div[id=loadingImg_tree]").hide();
                if (data.code == "0000") {
                    var tree = $(selector).jstree({
                        'plugins': ["checkbox", "types"],
                        'core': {
                            'data': data.desc
                        },
                        "types": {
                            "default": {
                                "icon": "fa fa-folder icon-warning icon-lg"
                            },
                            "file": {
                                "icon": "fa fa-file icon-warning icon-lg"
                            }
                        }
                    });
                    $(selector).on('changed.jstree', function(e, data) {
                        var checkTextAry = [];
                        var checkIdAry = [];
                        for (i = 0, j = data.selected.length; i < j; i++) {
                            checkTextAry.push(data.instance.get_node(data.selected[i]).text);
                            checkIdAry.push(data.instance.get_node(data.selected[i]).id);
                        }
                        itemNamesObj.val(checkTextAry.join(","));
                        itemIdsObj.val(checkIdAry.join(","));
                    });
                } else {
                    toastr.error(data.desc);
                }
            },
            dataType: "json",
            error: function(xhr, textStatus, errorThrown) {}
        });
    }
    var handleRatingUnitType = function(selector) {
            var baseItemTypeObj = $(selector).find("select[id=baseItemType]");
            var baseItemTypeText = $(baseItemTypeObj).find("option:selected").text();
            var ratingUnitTypeObj = $(selector).find("select[id=ratingUnitType]");
            $(selector).find("select[id=ratingUnitType]").hide();
            $(selector).find("div[id=loadingImg_unit]").show();
            var defValue = $(ratingUnitTypeObj).attr('def-value');
            $.ajax({
                async: true,
                type: "POST",
                url: APP_PATH + "/pardOfferPricePlan/showRatingUnitTypeList.shtml",
                data: {
                    baseItemType: baseItemTypeText
                },
                success: function(data) {
                    $(selector).find("div[id=loadingImg_unit]").hide();
                    $(selector).find("select[id=ratingUnitType]").show();
                    if (data.code == "0000") {
                        ratingUnitTypeObj.find("option").remove();
                        var selList = data.desc
                        if (selList != null && selList.length > 0) {
                            for (var i = 0; i < selList.length; i++) {
                                var _key = selList[i].key;
                                var _value = selList[i].value;
                                ratingUnitTypeObj.append("<option value='" + _key + "'  " + (defValue == _key ? 'selected' : '') + ">" + _value + "</option>");
                            }
                        }
                    } else {
                        toastr.error(data.desc);
                    }
                    $(selector + ' .unitArea').html($(ratingUnitTypeObj).find('option:selected').text());
                },
                dataType: "json"
            });
        }
        //控制offset tyep 选择为offset时 显示输入框
    var handleOffsetType = function(selector) {
            var $offsetType = $(selector).find('.offsetType');
            $(selector).on('change', '.offsetType', function() {
                if ($offsetType.val() == 1) {
                    $(selector).find('.offset').show();
                } else {
                    $(selector).find('.offset').hide();
                }
            })
            if ($offsetType.val() == 1) {
                $(selector).find('.offset').show();
            } else {
                $(selector).find('.offset').hide();
            }
        }
        //控制 EFFECTIVE TIME 当单位选择为fixed expiry date时 input变为日期控件
    var handleEffectiveTime = function(selector) {
            var $unit = $(selector).find('.unit');
            var $effectiveTime1 = $(selector).find('.effectiveTime1');
            var $effectiveTime2 = $(selector).find('.effectiveTime2');
            $(selector).on('change', '.unit', function() {
                if ($unit.val() == 11) {
                    $effectiveTime1.hide();
                    $effectiveTime2.show();
                } else {
                    $effectiveTime1.show();
                    $effectiveTime2.hide();
                }
            })
            if ($unit.val() == 10) {
                $effectiveTime1.hide();
                $effectiveTime2.show();
            } else {
                $effectiveTime1.show();
                $effectiveTime2.hide();
            }
        }
        //Recurring Charge 选择 Subject：
    var handleSelectPriceItemModal = function(selector) {
            var priceItemDT = $(selector).find("#priceItemDT").dataTable({
                "processing": true,
                "serverSide": true,
                "pagingType": "bootstrap_full_number",
                "searching": false,
                "ajax": "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + $(selector).find("#selectPriceItemModal").find("#itemIdOrName").val() + "&itemType=" + $(selector).find("#selectPriceItemModal").find("#itemType").val() + "&itemTypes=" + $(selector).find("#selectPriceItemModal").find("#itemTypes").val(),
                "ordering": false,
                "autoWidth": false,
                "columns": [{
                    "data": "INDEX"
                }, {
                    "data": "ITEM_ID"
                }, {
                    "data": "ITEM_NAME"
                }, {
                    "data": "ITEM_TYPE_NAME"
                }, {
                    "data": "DESCRIPTION"
                }],
                "columnDefs": [{
                    "orderable": false,
                    "targets": 0,
                    "width": "37px"
                }, {
                    "visible": true,
                    "targets": [0, 1, 2, 3, 4]
                }],
                "initComplete": function() {
                    $(selector).find("#priceItemDT tbody").find('input[type="radio"]:checked').each(function() {
                        //$(this).closest('tr').addClass('active');
                    })
                },
                "drawCallback": function() {
                    App.ajaxInit();
                }
            });
            jQuery('#priceItemDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
            jQuery('#priceItemDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
            $(selector).find('.priceItemOK').on('click', function() {
                var itemId = "";
                var itemName = "";
                $(this).closest("#selectPriceItemModal").find("#priceItemDT tbody input[type='radio']:checked").each(function(i, o) {
                    var objTr = $(this).closest('tr');
                    itemId = $(objTr).children('td').eq(1).html();
                    itemName = $(objTr).children('td').eq(2).html();
                    //itemId        = priceItemDT.fnGetData($(this).closest('tr')).ITEM_ID;
                    //itemName  = priceItemDT.fnGetData($(this).closest('tr')).ITEM_NAME;
                });
                $(selector).find("#itemId").val(itemId);
                $(selector).find("#itemName").val(itemName);
                $(this).closest("#selectPriceItemModal").modal("hide");
            });
            $(selector).find(".priceItemCancel").click(function() {
                $(this).closest("#selectPriceItemModal").find(".close").click();
            });
            $(selector).find("#opener_odrPrice").click(function() {
                $(selector).find("#selectPriceItemModal").modal('show');
            });
            $(selector).find("#priceItemQuery").click(function() {
                var url = "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + $(this).closest("#selectPriceItemModal").find("#itemIdOrName").val() + "&itemType=" + $(this).closest("#selectPriceItemModal").find("#itemType").val() + "&itemTypes=" + $(this).closest("#selectPriceItemModal").find("#itemTypes").val();
                priceItemDT.api().ajax.url(url).load();
            });
        }
        //One-Time Charge  选择 Service Type：
    var handleSelectPriceItem4Modal = function(selector) {
            var priceItem4DT = $(selector).find('#priceItem4DT').dataTable({
                "processing": true,
                "serverSide": true,
                "pagingType": "bootstrap_full_number",
                "searching": false,
                "ajax": "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + $(selector).find("#selectPriceItem4Modal").find("#itemIdOrName").val() + "&itemType=" + $(selector).find("#selectPriceItem4Modal").find("#itemType").val() + "&itemTypes=4",
                "order": [],
                "autoWidth": false,
                "columns": [{
                    "data": "INDEX"
                }, {
                    "data": "ITEM_ID"
                }, {
                    "data": "ITEM_NAME"
                }, {
                    "data": "ITEM_TYPE_NAME"
                }, {
                    "data": "DESCRIPTION"
                }],
                "columnDefs": [{
                    "orderable": false,
                    "targets": [0, 1, 2, 3, 4],
                    "width": "25px"
                }, {
                    "visible": true,
                    "targets": [0, 1, 2, 3, 4]
                }],
                "initComplete": function() {
                    App.ajaxInit();
                    $(selector).find('#priceItem4DT tbody').find('input[type="radio"]:checked').each(function() {
                        //$(this).closest('tr').addClass('active');
                    })
                }
            });
            jQuery('#priceItem4DT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
            jQuery('#priceItem4DT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
            $(selector).find('.priceItem4OK').bind('click', function() {
                var _Names = "";
                var _Ids = "";
                $(this).closest("#selectPriceItem4Modal").find("#priceItem4DT tbody input[type='radio']:checked").each(function(i, objCk) {
                    var objTr = $(this).closest('tr');
                    if (i == 0) {
                        _Names = $(objTr).children('td').eq(2).html();
                        _Ids = $(this).val();
                    }
                    //else{
                    //      _Names += ","+$(objTr).children('td').eq(2).html();
                    //      _Ids += ","+$(this).val();
                    //}
                });
                $(selector).find("#businessItemName").val(_Names);
                $(selector).find("#businessItem").val(_Ids);
                $(selector).find('#selectPriceItem4Modal').modal('hide');
            });
            $(selector).find(".priceItem4Cancel").click(function() {
                $(this).closest("#selectPriceItem4Modal").find(".close").click();
            });
            $(selector).find("#opener_odrPrice4").click(function() {
                $(selector).find("#selectPriceItem4Modal").modal('show');
            });
            $(selector).find("#priceItem4Query").click(function() {
                var itemIdOrName = $(this).closest("#selectPriceItem4Modal").find("#itemIdOrName").val();
                var itemType = $(this).closest("#selectPriceItem4Modal").find("#itemType").val();
                var url = "../pardOfferPricePlan/" + "getPriceItemList.shtml?itemIdOrName=" + itemIdOrName + "&itemType=" + itemType + "&itemTypes=4";
                priceItem4DT.api().ajax.url(url).load();
            });
        }
        //One-Time Charge  选择Subject：
    var handleSelectPriceItem3Modal = function(selector) {
            var priceItem3DT = $(selector).find('#priceItem3DT').dataTable({
                "processing": true,
                "serverSide": true,
                "pagingType": "bootstrap_full_number",
                "searching": false,
                "ajax": "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + $(selector).find("#selectPriceItem3Modal").find("#itemIdOrName").val() + "&itemType=" + $(selector).find("#selectPriceItem3Modal").find("#itemType").val() + "&itemTypes=3",
                "order": [],
                "autoWidth": false,
                "columns": [{
                    "data": "INDEX"
                }, {
                    "data": "ITEM_ID"
                }, {
                    "data": "ITEM_NAME"
                }, {
                    "data": "ITEM_TYPE_NAME"
                }, {
                    "data": "DESCRIPTION"
                }],
                "columnDefs": [{
                    "orderable": false,
                    "targets": [0, 1, 2, 3, 4],
                    "width": "25px"
                }, {
                    "visible": true,
                    "targets": [0, 1, 2, 3, 4]
                }],
                "initComplete": function() {
                    App.ajaxInit();
                    $(selector).find('#priceItem3DT tbody').find('input[type="radio"]:checked').each(function() {
                        //$(this).closest('tr').addClass('active');
                    })
                }
            });
            jQuery('#priceItem3DT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
            jQuery('#priceItem3DT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
            $(selector).find('.priceItem3OK').bind('click', function() {
                var _Names = "";
                var _Ids = "";
                $(this).closest("#selectPriceItem3Modal").find("#priceItem3DT tbody input[type='radio']:checked").each(function(i, objCk) {
                    var objTr = $(this).closest('tr');
                    if (i == 0) {
                        _Names = $(objTr).children('td').eq(2).html();
                        _Ids = $(this).val();
                    }
                    //else{
                    //      _Names += ","+$(objTr).children('td').eq(2).html();
                    //      _Ids += ","+$(this).val();
                    //}
                });
                $(selector).find("#oneTimeItemName").val(_Names);
                $(selector).find("#oneTimeItem").val(_Ids);
                $(selector).find('#selectPriceItem3Modal').modal('hide');
            });
            $(selector).find(".priceItem3Cancel").click(function() {
                $(this).closest("#selectPriceItem3Modal").find(".close").click();
            });
            $(selector).find("#opener_odrPrice3").click(function() {
                $(selector).find("#selectPriceItem3Modal").modal('show');
            });
            $(selector).find("#priceItem3Query").click(function() {
                var itemIdOrName = $(this).closest("#selectPriceItem3Modal").find("#itemIdOrName").val();
                var itemType = $(this).closest("#selectPriceItem3Modal").find("#itemType").val();
                var url = "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + itemIdOrName + "&itemType=" + itemType + "&itemTypes=3";
                priceItem3DT.api().ajax.url(url).load();
            });
        }
        //Billing Discount 选择 Reference Subject/Contrast Subject：
    var setId = null;
    var setName = null;
    var handleSelectPriceItem23Modal = function(selector) {
            var priceItem23DT = $(selector).find('#priceItem23DT').dataTable({
                "processing": true,
                "serverSide": true,
                "pagingType": "bootstrap_full_number",
                "searching": false,
                "ajax": "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + $(selector).find("#selectPriceItem23Modal").find("#itemIdOrName").val() + "&itemType=" + $(selector).find("#selectPriceItem23Modal").find("#itemType").val() + "&itemTypes=2,3",
                "order": [],
                "autoWidth": false,
                "columns": [{
                    "data": "INDEX"
                }, {
                    "data": "ITEM_ID"
                }, {
                    "data": "ITEM_NAME"
                }, {
                    "data": "ITEM_TYPE_NAME"
                }, {
                    "data": "DESCRIPTION"
                }],
                "columnDefs": [{
                    "orderable": false,
                    "targets": [0, 1, 2, 3, 4],
                    "width": "25px"
                }, {
                    "visible": true,
                    "targets": [0, 1, 2, 3, 4]
                }],
                "initComplete": function() {
                    App.ajaxInit();
                    $(selector).find('#priceItem23DT tbody').find('input[type="radio"]:checked').each(function() {
                        //$(this).closest('tr').addClass('active');
                    })
                }
            });
            jQuery('#priceItem23DT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
            jQuery('#priceItem23DT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
            //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
            $(selector).find('.priceItem23OK').bind('click', function() {
                var _Names = "";
                var _Ids = "";
                $(this).closest("#selectPriceItem23Modal").find("#priceItem23DT tbody input[type='radio']:checked").each(function(i, objCk) {
                    var objTr = $(this).closest('tr');
                    if (i == 0) {
                        _Names = $(objTr).children('td').eq(2).html();
                        _Ids = $(this).val();
                    }
                    //else{
                    //      _Names += ","+$(objTr).children('td').eq(2).html();
                    //      _Ids += ","+$(this).val();
                    //}
                });
                $(selector).find("#" + setName).val(_Names);
                $(selector).find("#" + setId).val(_Ids);
                $(selector).find('#selectPriceItem23Modal').modal('hide');
            });
            $(selector).find(".priceItem23Cancel").click(function() {
                $(this).closest('#selectPriceItem23Modal').find(".close").click();
            });
            $(selector).find(".opener_odrPrice23").click(function() {
                setId = $(this).attr("setId");
                setName = $(this).attr("setName");
                $(selector).find("#selectPriceItem23Modal").find("#itemIdOrName").val("");
                $(selector).find("#selectPriceItem23Modal").find("#itemType").val("");
                $(selector).find("#selectPriceItem23Modal").modal('show');
                $(selector).find("#itemType").val("");
                $(selector).find("#itemIdOrName").val("")
                $(selector).find("#priceItem23Query").click();
            });
            $(selector).find("#priceItem23Query").click(function() {
                var url = "../pardOfferPricePlan/getPriceItemList.shtml?itemIdOrName=" + $(this).closest("#selectPriceItem23Modal").find("#itemIdOrName").val() + "&itemType=" + $(this).closest("#selectPriceItem23Modal").find("#itemType").val() + "&itemTypes=2,3";
                priceItem23DT.api().ajax.url(url).load();
            });
        }
        //Rating Discount 选择 Time Range：
    var handleSelectTimeRangeModal = function(selector) {
        var timeRangeDT = $(selector).find('#timeRangeDT').dataTable({
            "processing": true,
            "serverSide": true,
            "pagingType": "bootstrap_full_number",
            "searching": false,
            "ajax": "../pardOfferPricePlan/getTimeRangeList.shtml",
            "order": [],
            "autoWidth": false,
            "columns": [{
                "data": "INDEX"
            }, {
                "data": "SEG_NAME"
            }, {
                "data": "DATE_MODE"
            }, {
                "data": "TIME_MODE"
            }, {
                "data": "DESCRIPTION"
            }],
            "columnDefs": [{
                "orderable": false,
                "targets": [0, 1, 2, 3, 4],
                "width": "25px"
            }, {
                "visible": true,
                "targets": [0, 1, 2, 3, 4]
            }],
            "initComplete": function() {
                App.ajaxInit();
                $(selector).find('#timeRangeDT tbody').find('input[type="radio"]:checked').each(function() {
                    //$(this).closest('tr').addClass('active');
                })
            }
        });
        jQuery('#timeRangeDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#timeRangeDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
        //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
        $(selector).find('.timeOK').bind('click', function() {
            var _Names = "";
            var _Ids = "";
            $(this).closest("#selectTimeRangeModal").find("#timeRangeDT tbody input[type='radio']:checked").each(function(i, objCk) {
                var objTr = $(this).closest('tr');
                if (i == 0) {
                    _Names = $(objTr).children('td').eq(1).html();
                    _Ids = $(this).val();
                } else {
                    _Names += "," + $(objTr).children('td').eq(1).html();
                    _Ids += "," + $(this).val();
                }
            });
            $(selector).find("#segName").val(_Names);
            $(selector).find("#segId").val(_Ids);
            $(selector).find('#selectTimeRangeModal').modal('hide');
        });
        $(selector).find(".timeCancel").click(function() {
            $(this).closest("#selectTimeRangeModal").find(".close").click();
        });
        $(selector).find("#timeModal").click(function() {
            $(selector).find("#selectTimeRangeModal").modal('show');
        });
        $(selector).find("#timeQuery").click(function() {
            var url = "../pardOfferPricePlan/getTimeRangeList.shtml?idOrName=" + $(this).closest("div").find("#idOrName").val() + 
            "&dateMode=" + $(this).closest("div").find("#dateMode").val() + "&timeMode=" + $(this).closest("div").find("#timeMode").val();
            timeRangeDT.api().ajax.url(url).load();
        });
    }
    
    //Free Resource 选择 Free Resource Item：
    var handleSelectFreeResourceItemModal = function(selector) {
        var timeRangeDT = $(selector).find('#freeResourceItemDT').dataTable({
            "processing": true,
            "serverSide": true,
            "pagingType": "bootstrap_full_number",
            "searching": false,
            "ajax": "../pardOfferPricePlan/getFreeResourceItemList.shtml",
            "order": [],
            "autoWidth": false,
            "columns": [{
                "data": "INDEX"
            }, {
                "data": "ITEM_ID"
            }, {
                "data": "ITEM_NAME"
            }, {
                "data": "UNIT"
            }],
            "columnDefs": [{
                "orderable": false,
                "targets": [0, 1, 2, 3],
                "width": "25px"
            }, {
                "visible": true,
                "targets": [0, 1, 2, 3]
            }],
            "initComplete": function() {
                App.ajaxInit();
                $(selector).find('#freeResourceItemDT tbody').find('input[type="radio"]:checked').each(function() {
                    //$(this).closest('tr').addClass('active');
                })
            }
        });
        jQuery('#freeResourceItemDT_wrapper .dataTables_filter input').addClass("form-control input-small input-inline"); // modify table search input
        jQuery('#freeResourceItemDT_wrapper .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown
        //选择确认后，复制选择项到对应位置（modal上th的顺序，需要和目标位置上的TH对应，否则会错乱）
        $(selector).find('.timeOK').bind('click', function() {
            var _Names = "";
            var _Ids = "";
            $(this).closest("#selectFreeResourceItemModal").find("#freeResourceItemDT tbody input[type='radio']:checked").each(function(i, objCk) {
                var objTr = $(this).closest('tr');
                if (i == 0) {
                    _Names = $(objTr).children('td').eq(2).html();
                    _Ids = $(this).val();
                } else {
                    _Names += "," + $(objTr).children('td').eq(2).html();
                    _Ids += "," + $(this).val();
                }
            });
            $(selector).find("#freeresItemName").val(_Names);
            $(selector).find("#freeresItem").val(_Ids);
            $(selector).find('#selectFreeResourceItemModal').modal('hide');
        });
        $(selector).find(".timeCancel").click(function() {
            $(this).closest("#selectFreeResourceItemModal").find(".close").click();
        });
        $(selector).find("#timeModal").click(function() {
            $(selector).find("#selectFreeResourceItemModal").modal('show');
        });
        $(selector).find("#timeQuery").click(function() {
            var url = "../pardOfferPricePlan/getFreeResourceItemList.shtml?idOrName=" + $(this).closest("div").find("#idOrName").val();
            freeResourceItemDT.api().ajax.url(url).load();
        });
    }
    
    
    var setDefValue = function(selector, pSelector, draw) {
            var pricingName = $(pSelector).find("#pricingName").val();
            var defVal = pricingName + "_" + _priceTypeName + "_" + draw;
            $(selector).find("#priceName").val(defVal);
            var pricingInfoId = $(pSelector).find("#pricingInfoId").val();
            $(selector).find("#priPricingInfoId").val(pricingInfoId);
        }
        /////////////////////////////////  
    var addTabMenu = function(selector) {
        var tpl = '<ul class="nav nav-tabs fix tabs-content-ajax"></ul> <div class="tab-content"></div>';
        $(selector).on('click', 'a[data-action="addTabMenu"]', function() {
                //防止多次添加模板
                var addTpl = $(selector + ' .tabsArea').attr('data-addtpl');
                if (addTpl == 'false') {
                    $(selector + ' .tabsArea').html(tpl).attr('data-addtpl', 'true');
                }
                var $this = $(this);
                var target = $this.data('target');
                var type = $this.data('type');
                var draw = $this.attr('data-draw'); //数字表示重复添加的次数，如果是undefined表示不允许重复添加同一个
                var title = $this.text();
                var url = $this.data('url');
                var container = $this.data('container');
                var $container = $(selector + ' ' + container);
                //有设置改属性的，表明只能添加一次
                if ($this.attr('data-unique') == 'true' && draw == '1') {
                    toastr.error("This property has already been added."); ///这个属性已经添加过了
                    return
                }
                //添加一个tabMenu
                $container.find('.nav li').removeClass('active');
                //创建一个a标签,先判断是否允许重复添加 
                if (draw != undefined) {
                    target = target + '_' + draw;
                    draw = parseInt(draw) + 1;
                    $this.attr({
                        'data-draw': draw
                    });
                }
                var a = $('<a data-toggle="tab">').attr({
                    'data-url': url,
                    'href': target
                }).html(title);
                var li = $('<li class="active">').append(a); //创建一个li标签
                li.append('<input type="checkbox" value="' + target + '" name="select">');
                //根据data-url上的值加载html
                a.one('click', function(event) {
                    var $this = $(this);
                    var requestURL = $(this).data('url');
                    if (!requestURL) {
                        return
                    }
                    var id = $(this).attr('href');
                    $(id).load(requestURL, function() {
                        if (type == 0) {
                            //basic tariff :
                            handlePOJstree(id + ' .poShu', "basicTariffForm");
                            handleChargeTable(id);
                            handleChargeTableAdd(id);
                            handleRatingUnitType(id);
                            basicTariff_InitDefValue(id);
                            tab2ChilFormValidate(id, "basicTariffForm");
                            $('.dropdown-toggle2').dropdownHover();
                        } else if (type == 7) {
                            //Recurring charge :
                            handleSelectPriceItemModal(id);
                            tab2ChilFormValidate(id, "recurringChargeForm");
                        } else if (type == 5) {
                            //Rating Discount :
                            handleSelectTimeRangeModal(id);
                            ratingDiscount_InitDefValue(id);
                            handlePOJstree(id + ' .poShu', "ratingDiscountForm");
                            tab2ChilFormValidate(id, "ratingDiscountForm");
                            $('.dropdown-toggle2').dropdownHover();
                        } else if (type == 9) {
                            //One-time charge :
                            handleSelectPriceItem4Modal(id);
                            handleSelectPriceItem3Modal(id);
                            tab2ChilFormValidate(id, "oneTimeChargeForm");
                        } else if (type == 8) {
                            //Billing discount :
                            handleSelectPriceItem23Modal(id);
                            billingDiscount_InitDefValue(id);
                            handleBillDisChargeTableAddRow(id);
                            tab2ChilFormValidate(id, "billingDiscountForm");
                        }else if (type == 3) {
                            //Free Resource :
                        	freeResourceInit(id);
                        	handleUserStateJstree(id + ' .poShu', "freeResourceForm");
                        	handleSelectFreeResourceItemModal(id);
                            tab2ChilFormValidate(id, "freeResourceForm");
                            $('.dropdown-toggle2').dropdownHover();
                        }
                        handleDatePickers(id + ' .date-picker');
                        setDefValue(id, selector, draw);
                        App.ajaxInit();
                    })
                });
                $container.find('.nav').append(li);
                //添加一个tab-content
                $container.find('.tab-pane').removeClass('active');
                var div = $('<div class="tab-pane active">').attr('id', target.substring(1)).html('<img src="' + APP_PATH + '/resources/img/input-spinner.gif" alt="">')
                $container.find('.tab-content').append(div);
                a.click();
            })
            //控制选择按钮
        $(selector).on('click', '.btn-select', function() {
                var $this = $(this);
                $this.closest('.portlet').find('.nav-tabs').toggleClass('select');
            })
            //控制删除按钮（删除Charge Information）
        $(selector).on('click', '.btn-delete', function() {
            var $this = $(this);
            var $selected = $this.closest('.portlet').find('.nav-tabs input[type="checkbox"]:checked');
            var selected = $selected.size();
            if (selected != 0) {
                bootbox.confirm(_deleteConfirm, function(result) {
                    if (result == true) {
                        $selected.each(function() {
                            var $$this = $(this);
                            var target = $$this.val();
                            var priceId = $$this.closest('li').attr("priceId");
                            if (priceId == undefined) {
                                priceId = $(target).find("input[id=priceId]").val();
                            }
                            //数据库中删除：
                            if (priceId != undefined && priceId != "") {
                                $.ajax({
                                    async: false,
                                    type: "POST",
                                    url: APP_PATH + "/pardOfferPricePlan/deletePrice.shtml",
                                    data: {
                                        priceId: priceId
                                    },
                                    success: function(data) {
                                        if (data.code == "0000") {
                                            //删除成功
                                            delTab($$this, target); //界面上删除
                                        } else {
                                            //删除失败
                                            alert(data.desc);
                                        }
                                    },
                                    dataType: "json",
                                    error: function(xhr, textStatus, errorThrown) {
                                        //删除失败
                                    }
                                });
                            } else {
                                delTab($$this, target); //未保存的界面上删除
                            }
                        });
                    }
                });
            }
            //界面上删除:
            delTab = function(obj, target) {
                var length = target.length;
                var index = target.charAt(length - 3);
                var $a = $this.closest('.dropdown').find('.dropdown-menu li').eq(index).children('a');
                $a.attr('data-draw', parseInt($a.attr('data-draw')) - 1);
                $(target).remove();
                obj.closest('li').remove();
                //全删了：
                var tabNum = $this.closest('.portlet').find('.nav-tabs input').size();
                if (tabNum == 0) {
                    $this.closest('.portlet').find('.portlet-body').html('<div class="well">' + _noChargeInformation + '</div>');
                    $this.closest('.dropdown').find('.dropdown-menu li a').each(function() {
                        $(this).attr('data-draw', '0');
                    })
                    $this.closest('.portlet').children('.portlet-body').attr('data-addtpl', 'false');
                }
                $this.closest('.portlet').find('.nav-tabs li:first a').click();
            }
        });
        //编辑页面初始化显示第一页：
        var tab2Chils = $(selector).find(".tab2Chil_TabUL").find("a");
        if (tab2Chils.length > 0) {
            tab2Chils[0].click();
        }
    }
    var basicTariff_InitDefValue = function(selector) {
        var baseItemTypeObj = $(selector).find("select[id=baseItemType]");
        baseItemTypeObj.change(function() {
            $(selector + ' .poShu').jstree('destroy'); //先清空树
            handlePOJstree(selector + ' .poShu', 'basicTariffForm'); //再重新加载树
            handleRatingUnitType(selector);
        });
    }
    
   var regValue = function (obj){
    	var val = $(obj).val();
    	if(/^\d*(\d|(\.{0,1}[1-9]{0,2}))$/.test(val)){
    		$(obj).val(val);
    	}else{
    		$(obj).val('');
    	}
    }
    var ratingDiscount_InitDefValue = function(selector) {
        var baseItemTypeObj = $(selector).find("select[id=baseItemType]");
        baseItemTypeObj.change(function() {
            $(selector + ' .poShu').jstree('destroy'); //先清空树
            handlePOJstree(selector + ' .poShu', 'ratingDiscountForm'); //再重新加载树
            handleRatingUnitType(selector);
        });
        var discountTypeObj = $(selector).find("select[id=discountType]");
        discountTypeObj.change(function() {
            if ($(this).val() == "0") {
                $(selector).find("#fixedTab").hide();
                $(selector).find("#percentyTab").show();
                $(selector).find("#feeTr").show();
            } else if ($(this).val() == "1") {
                $(selector).find("#fixedTab").show();
                $(selector).find("#percentyTab").hide();
                $(selector).find("#feeTr").show();
            } else if ($(this).val() == "2") {
                $(selector).find("#fixedTab").hide();
                $(selector).find("#percentyTab").hide();
                $(selector).find("#feeTr").hide();
            }
        });
        if (discountTypeObj.val() == "0") {
            $(selector).find("#fixedTab").hide();
            $(selector).find("#percentyTab").show();
            $(selector).find("#feeTr").show();
        } else if (discountTypeObj.val() == "1") {
            $(selector).find("#fixedTab").show();
            $(selector).find("#percentyTab").hide();
            $(selector).find("#feeTr").show();
        } else if (discountTypeObj.val() == "2") {
            $(selector).find("#fixedTab").hide();
            $(selector).find("#percentyTab").hide();
            $(selector).find("#feeTr").hide();
        }
    }
    var billingDiscount_InitDefValue = function(selector) {
            var promTypeObj = $(selector).find("select[id=promType]");
            promTypeObj.change(function() {
                if ($(this).val() == 1) {
                    $(selector).find("#detailTab").show();
                    $(selector).find("#detailTab1").hide();
                } else {
                    $(selector).find("#detailTab1").show();
                    $(selector).find("#detailTab").hide();
                }
            });
            if (promTypeObj.val() == 1) {
                $(selector).find("#detailTab").show();
                $(selector).find("#detailTab1").hide();
            } else {
                $(selector).find("#detailTab1").show();
                $(selector).find("#detailTab").hide();
            }
            $(selector).on('change', '#currencyUnitType', function() {
                var currency = $(this).find('option:selected').text();
                $(selector).find('.currencyArea').html(currency);
            });
            var currencyUnitTypeObj = $(selector).find("select[id=currencyUnitType]");
            var currency = $(currencyUnitTypeObj).find('option:selected').text();
            $(selector).find('.currencyArea').html(currency);
        }
        //控制 Charge Information中basic tariff面板内的charge表格的联动效果
    var handleChargeTable = function(selector) {
        $(selector).on('change', '#ratingUnitType', function() {
            var unit = $(this).find('option:selected').text();
            $(selector + ' .unitArea').html(unit);
        })
        $(selector + ' input[name="ratingUnitVal"]').focusout(function() {
            var ratingUnitVal = $(this).val();
            $(selector + ' .numArea').html(ratingUnitVal);
        })
        $(selector).on('click', 'input[name="rateType"]', function() {
            var rateType = $(this).val();
            if (rateType == 1) {
                $(selector).find("#simpleFeeTab").show();
                $(selector).find("#ladderFeeTab").hide();
                inputV = "1";
            } else if (rateType == 2) {
                $(selector).find("#simpleFeeTab").hide();
                $(selector).find("#ladderFeeTab").show();
                inputV = "2";
            }
        })
        $(selector).on('change', '#currencyUnitType', function() {
                var currency = $(this).find('option:selected').text();
                $('.currencyArea').html(currency);
            })
            //初始化
        $(selector + ' .unitArea').html($(selector + ' #ratingUnitType').find('option:selected').text());
        $(selector + ' .currencyArea').html($(selector + ' #currencyUnitType').find('option:selected').text());
        $(selector + ' .numArea').html($(selector + ' input[name="ratingUnitVal"]').val() ? $(selector + ' input[name="ratingUnitVal"]').val() : 1); //??
        var rateType = $(selector + ' input[name="rateType"]:checked').val();
        if (rateType == 1) {
            $(selector).find("#simpleFeeTab").show();
            $(selector).find("#ladderFeeTab").hide();
        } else if (rateType == 2) {
            $(selector).find("#simpleFeeTab").hide();
            $(selector).find("#ladderFeeTab").show();
        }
    }
    
    //免费资源初始化
    var freeResourceInit = function(selector) {
    	//Free Resource Cycle 切换时隐藏/显示Free Resource Period：
    	$(selector).on('change', 'select[name="cycleRefMode"]', function() {
    		var cycleRefMode = $(this).val();
    		if (cycleRefMode =="0" || cycleRefMode =="1") {
    			$(selector).find("#freeResourcePeriodDiv").hide();
    			$(selector).find("#freeResourcePeriod").val("");
    		} else{
    			$(selector).find("#freeResourcePeriodDiv").show();
    		}
    	});
    	//Amount Distribution Type 切换：
    	$(selector).on('click', 'input[name="allowanceType"]', function() {
    		var allowanceType = $(this).val();
    		if (allowanceType == 1) {
    			$(selector).find("#freeSourceAmountDiv").show();
    			$(selector).find("#cycleRangeDiv").hide();
    			$(selector).find("#amount").show();
    			$(selector).find("#measureId").show();
    			$(selector).find("#unlimitamount").hide();
    		} else if (allowanceType == 2) {
    			$(selector).find("#freeSourceAmountDiv").show();
    			$(selector).find("#cycleRangeDiv").hide();
    			$(selector).find("#amount").hide();
    			$(selector).find("#measureId").hide();
    			$(selector).find("#unlimitamount").show();
    			$(selector).find("#amount").val("");
    			$(selector).find("#measureId").val("");
    		}else if (allowanceType == 3) {
    			$(selector).find("#freeSourceAmountDiv").hide();
    			$(selector).find("#cycleRangeDiv").show();
    		}
    	});
    	//Cycle Range表格添加加一行记录：
        $(selector).find(".addCycleRangeBut").bind('click', function() {
            var tableObj = $(selector).find("table[id=cycleRangeTable]");
            var len = $(selector).find("table[id=cycleRangeTable]>tbody>tr").length;
            var val = $(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val == "-1") {
                toastr.error(_endValTip);
                $(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            if(val == ""){
            	toastr.error(_endValNotNUll);
                $(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            var num1 = parseInt($(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=startval]").val());
            var num2 = parseInt($(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").val());
            if (num1 >= num2) {
                toastr.error(_greater);
                $(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            var val = $(selector).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val == undefined) {
                val = 1;
            }
            var newTr   = "<tr><td>" + (len + 1) + "</td>";
            newTr+= "<td>";
            newTr+= "	<div class='input-group input-xs'>";
            newTr+= "		<input type='text' value='" + val + "'  id='startval' name='startval'  class='form-control' maxlength='9'  onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"  style=\"width:110px;\"/>";
            newTr+= "		<span class='input-group-addon'>To</span>";
            newTr+= "		<input type='text' value='-1' id='endVal'  name='endVal'  class='form-control' maxlength='9'  onclick=\"select()\" onkeyup=\"this.value=this.value.match('^-?[0-9]*$')\" onafterpaste=\"this.value=this.value.match('^-?[0-9]*$')\"   style=\"width:110px;\">";
            newTr+= "	</div>";
            newTr+= "</td>";
            newTr+= "<td align=\"center\"><input type=\"checkbox\" class=\"form-control\" name=\"unlimited\"  id=\"unlimited\"  onclick=\"unlimitedIsSelect(this)\" /></td>";
            newTr+= "<td>";
            newTr+= "	<input type=\"text\"  value=\"\"  class=\"form-control input-xs\"  id=\"quantity_amount\" name=\"quantity_amount\"  placeholder=\"\"  maxlength=\"9\" onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"  style=\"width:100px;\"/>";
            newTr+= "    <select style=\"width:100px;\" id=\"quantity_measureId\" name=\"quantity_measureId\" class=\"form-control  input-xs\">";
            newTr+= "			<option value=\"104\">KB</option>";
            newTr+= "			<option value=\"105\">MB</option>";
            newTr+= "			<option value=\"106\">GB</option>";
            newTr+= "    </select>";
            newTr+= "   	<select class=\"form-control input-xs\"   id=\"quantity_unlimitamount\"   name=\"quantity_unlimitamount\"  style=\"display:none; width:204px;\"  disabled=\"disabled\" >";
            newTr+= "		<option value=\"999999999999999999\"  title=\"unlimited\">unlimited</option>";
            newTr+= "	 </select>";
            newTr+= "</td>";
            newTr+= "<td><a class='btn default btn-xs'  onclick=\"$(this).closest('tr').remove()\">Delete </a></td></tr>";
            tableObj.append(newTr);
        });
    }
    
    var handleUserStateJstree = function(selector, formName) {
        var thisForm = $(selector).closest("form[id=" + formName + "]");
        var itemNamesObj = $(thisForm).find("input[id=userStateNames]");
        var itemIdsObj = $(thisForm).find("#userStateIds");
        $(thisForm).find("div[id=userStateJstreeDiv]").hide();
        $(thisForm).find("div[id=loadingImg_tree]").show();
        $.ajax({
            async: true,
            type: "POST",
            url: APP_PATH + "/pardOfferPricePlan/getUserStateListTree.shtml",
            data: {
                itemIds: itemIdsObj.val()
            },
            success: function(data) {
                $(thisForm).find("div[id=userStateJstreeDiv]").show();
                $(thisForm).find("div[id=loadingImg_tree]").hide();
                if (data.code == "0000") {
                    var tree = $(selector).jstree({
                        'plugins': ["checkbox", "types"],
                        'core': {
                            'data': data.desc
                        },
                        "types": {
                            "default": {
                                "icon": "fa fa-folder icon-warning icon-lg"
                            },
                            "file": {
                                "icon": "fa fa-file icon-warning icon-lg"
                            }
                        }
                    });
                    $(selector).on('changed.jstree', function(e, data) {
                        var checkTextAry = [];
                        var checkIdAry = [];
                        for (i = 0, j = data.selected.length; i < j; i++) {
                            checkTextAry.push(data.instance.get_node(data.selected[i]).text);
                            checkIdAry.push(data.instance.get_node(data.selected[i]).id);
                        }
                        itemNamesObj.val(checkTextAry.join(","));
                        itemIdsObj.val(checkIdAry.join(","));
                    });
                } else {
                    toastr.error(data.desc);
                }
            },
            dataType: "json",
            error: function(xhr, textStatus, errorThrown) {}
        });
    }
    
    var handleChargeTableAdd = function(selector) {
        var tableObj = $(selector).find("table[id=ladderFeeTab]");
        $(selector).find(".addBut").bind('click', function() {
            var len = $(selector).find("table[id=ladderFeeTab]>tbody>tr").length;
            var val = $(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val == "-1" || val == "") {
                toastr.error(_endVal);
                $(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            var num1 = parseInt($(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=startval]").val());
            var num2 = parseInt($(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").val());
            if (num1 >= num2) {
                toastr.error(_greater);
                $(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            var val = $(selector).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val == undefined) {
                val = 0;
            }
            newTr = "<tr><td>" + (len + 1) + "</td>" + "<td>" + "    <div class='input-group input-xs'>" + "          <input type='text' value='" + val + "'  id='startval' name='startval'  class='form-control' maxlength='9'  onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"/>" + "        <span class='input-group-addon'> To </span>" + "        <input type='text' value='-1'  id='endVal'  name='endVal'  class='form-control' maxlength='9'   onkeyup=\"this.value=this.value.match('^-?[0-9]*$')\" onafterpaste=\"this.value=this.value.match('^-?[0-9]*$')\">" + "    </div>" + " </td>" + " <td>" + "        <input type='text' value='0' id='rateVal' name='rateVal' placeholder='' class='form-control input-xs' maxlength='9'  onblur=\"regValue(this);\"  >" + " </td>" + " <td>" + " <input type='text'  value='0' id='baseVal' name='baseVal' placeholder='' class='form-control input-xs'  maxlength='9'  onblur=\"regValue(this);\" >" + " </td>" + "<td>" + " <a class='btn default btn-xs'  onclick=\"$(this).closest('tr').remove()\">Delete </a>" + "</td>" + "</tr>";
            tableObj.append(newTr);
        });
    }
    var handleBillDisChargeTableAddRow = function(selector) {
        var tableObj = null;
        var promTypeObj = $(selector).find("select[id=promType]");
        if (promTypeObj.val() == 1) {
            tableObj = $(selector).find("table[id=detailTab]");
        } else {
            tableObj = $(selector).find("table[id=detailTab1]");
        }
        $(selector).find(".addBut").bind('click', function() {
            var thisTable = $(this).closest("table");
            var len = $(thisTable).find("tbody>tr").length;
            var val = $(thisTable).find("tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val == "-1" || val == "") {
                toastr.error(_endVal);
                $(thisTable).find("tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            var num1 = parseInt($(thisTable).find("tbody>tr").eq(len - 1).find("input[id=startval]").val());
            var num2 = parseInt($(thisTable).find("tbody>tr").eq(len - 1).find("input[id=endVal]").val());
            if (num1 >= num2) {
                toastr.error(_greater);
                $(thisTable).find("tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                return;
            }
            var val = $(thisTable).find("tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val == undefined) {
                val = 0;
            }
            newTr = "<tr><td>" + (len + 1) + "</td>" + "<td  style='text-align:center;'>" + "    <div class='input-group input-xs'>" + "          <input type='text' value='" + val + "'  id='startval' name='startval'  class='form-control' maxlength='9' onclick='select()' onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"  style='width:100px'/>" + "         <span class='input-group-addon'>~</span>" + "       <input type='text' value='-1'  id='endVal'  name='endVal'  class='form-control' maxlength='9' onclick='select()'  onkeyup=\"this.value=this.value.match('^-?[0-9]*$')\" onafterpaste=\"this.value=this.value.match('^-?[0-9]*$')\"   style='width:100px'>" + "    </div>" + " </td>" + " <td  style='text-align:center;'>";
            if (promTypeObj.val() == 1) {
                newTr += " <div class='input-group input-xs' >" + "       <input type='text'  id='numerator' name='numerator'  value='100'  class='form-control'  maxlength='9' onclick='select()'  onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"   style='width:100px'/>" + "       <span class='input-group-addon'>/</span>" + "       <input type='text'  id='denominator' name='denominator'  value='100'  class='form-control'  maxlength='9'  onclick='select()'  onkeyup=\"this.value=this.value.replace(/\\D/g,\'\')\" onafterpaste=\"this.value=this.value.replace(/\\D/g,\'\')\"   style='width:100px'/>" + " </div>";
            } else {
                newTr += "  <input type='text'  value='0' id='baseVal' name='baseVal' placeholder='' class='form-control input-xs'  maxlength='9' onclick='select()'  onblur='regValue(this);'   style='width:100px'>";
            }
            newTr += " </td>" + "<td>" + "  <a class='btn default btn-xs' onclick=\"$(this).closest('tr').remove()\">Delete </a>" + "</td>" + "</tr>";
            thisTable.append(newTr);
        });
    }
    var handleDatePickers = function(selector, model) {
        var options = {};
        if (model == 'daily') {
            options = {
                autoclose: true,
                minViewMode: 'year',
                format: 'yyyy-mm-dd'
            }
        } else if (model == 'monthly') {
            options = {
                autoclose: true,
                minViewMode: 'months',
                format: 'yyyy-mm'
            }
        } else {
            options = {
                autoclose: true,
                minViewMode: 'year',
                format: 'yyyy-mm-dd'
            }
        }
        if (jQuery().datepicker) {
            $(selector).datepicker(options);
        }
    }
    var showZoneMap = function() {
        var xsize,
            ysize,
            href,
            $modal,
            img,
            realWidth,
            realHeight,
            $this = $('.showZone'),
            size = $this.size();
        xsize = $this.attr('data-width') + 'px';
        ysize = $this.attr('data-height') + 'px';
        href = $this.attr('data-href');
        //点击查询消息
        $('.showZone').bind('click', function() {
            $modal = $('#selectZoneModal2');
            $modal.css({
                'width': xsize,
                'height': ysize
            });
            $modal.load(href, '', function() {
                setTimeout(initialize_1, 1500);
                $('#selectZoneModal2').modal('show');
            });
        });
    }
    var initDefValue = function(selector) {
            var prodOfferName = $("#prodOfferName").val();
            var pricingNameObj = $(selector).find("input[id=pricingName]");
            if (pricingNameObj.val() == "") {
                var tabNum = selector.substring(selector.indexOf("_") + 1, selector.length);
                prodOfferName += "_" + _pricePlan + "_" + tabNum;
                pricingNameObj.val(prodOfferName);
            }
        }
        //定价计划基本信息输入验证
    var pricePlanFormValidate = function(selector) {
            var form = $(selector).find("form[id=pricePlanForm]");
            form.validate({
                errorElement: 'span', //default input error message container
                errorClass: 'help-block', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                rules: {
                    "pricingName": {
                        required: true
                    },
                    "jstreeTextString": {
                        required: true
                    },
                    "pricePlanLifeCycle.validUnit": {
                        required: true
                    },
                    "pricingPlan.billingPriority": {
                        required: true
                    }
                },
                messages: {
                    "pricingName": {
                        required: _priceName + _isRequired
                    },
                    "jstreeTextString": {
                        required: _priceObjectprod + _isRequired
                    },
                    "pricePlanLifeCycle.validUnit": {
                        required: _validUnit + _isRequired
                    },
                    "pricingPlan.billingPriority": {
                        required: _priority + _isRequired
                    }
                },
                invalidHandler: function(event, validator) { //display error alert on form submit                       
                    App.scrollTop();
                },
                highlight: function(element) { // hightlight error inputs
                    $(element).closest('.form-group').addClass('has-error'); // set error class to the control group 
                },
                success: function(label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                }
            });
        }
        //Charge Information 输入信息验证
    var tab2ChilFormValidate = function(selector, formName) {
        var form = $(selector).find("form[id='" + formName + "']");
        form.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                "priceName": {
                    required: true,
                    minlength: 4
                },
                "itemNames": {
                    required: true
                },
                "ratingUnitVal": {
                    required: true
                },
                "currencyUnitVal": {
                    required: true
                },
                "itemId": {
                    required: true //Recurring Charge --- Subject
                },
                "segId": {
                    required: true //Rating Discount  --- Time Range
                },
                "businessItem": {
                    required: true //One-Time Charge   --- Service Type
                },
                "oneTimeItem": {
                    required: true //One-Time Charge  --- Subject
                },
                "eligibleItem": {
                    required: true //Billing Discount   --- Reference Subject
                },
                "targetItem": {
                    required: true //Billing Discount   --- Contrast Subject
                },
                "maxiMum": {
                    required: true //Billing Discount   --- Max Discount Money
                },
                "freeresItem": {
                    required: true //Free Resource   --- Free Resource Item
                },
                "forwardCycle": {
                    required: true //Free Resource   --- Forward Cycle
                }
            },
            messages: {
                "priceName": {
                    required: _priceName + _isRequired
                },
                "itemNames": {
                    required: _priceObject + _isRequired
                },
                "ratingUnitVal": {
                    required: _priceUnit1 + _isRequired
                },
                "currencyUnitVal": {
                    required: _fee + _isRequired
                },
                "itemId": {
                    required: _priceSubject + _isRequired
                },
                "segId": {
                    required: _timeRange + _isRequired
                },
                "businessItem": {
                    required: _serviceType + _isRequired
                },
                "oneTimeItem": {
                    required: _priceSubject + _isRequired
                },
                "eligibleItem": {
                    required: _reference + _isRequired
                },
                "targetItem": {
                    required: _contrast + _isRequired
                },
                "maxiMum": {
                    required: _maxDiscount + _isRequired
                },
                "freeresItem": {
                    required: _freeresItem+ _isRequired
                },
                "forwardCycle": {
                    required: _forwardCycle+ _isRequired
                }
            },
            invalidHandler: function(event, validator) { //display error alert on form submit                       
                //App.scrollTop();
                $("html,body").animate({
                    scrollTop: $("#chargeInformation").offset().top
                }, 500)
            },
            highlight: function(element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group 
            },
            success: function(label) {
                label.closest('.form-group').removeClass('has-error');
                label.remove();
            }
        });
    }
    return {
        init: function(tab, id, priceType) {
            if (tab == "tab2") {
                addTabMenu(id);
                initDefValue(id);
                handleJstree(id);
                handleOffsetType(id); //控制offset type显示和隐藏
                handleEffectiveTime(id);
                handleDatePickers(id + ' .date-picker');
                handleSelectZoneModal(id);
                App.ajaxInit();
                showZoneMap();
                $('.dropdown-toggle2').dropdownHover();
                pricePlanFormValidate(id);
            } else {
                //tab=="tab2Chil"
                if (priceType == 0) {
                    //basic tariff :
                    handlePOJstree(id + ' .poShu', "basicTariffForm");
                    handleChargeTable(id);
                    handleChargeTableAdd(id);
                    handleRatingUnitType(id);
                    basicTariff_InitDefValue(id);
                    $('.dropdown-toggle2').dropdownHover();
                    tab2ChilFormValidate(id, "basicTariffForm");
                } else if (priceType == 7) {
                    //Recurring charge :
                    handleSelectPriceItemModal(id);
                    tab2ChilFormValidate(id, "recurringChargeForm");
                } else if (priceType == 5) {
                    //Rating Discount :
                    handleSelectTimeRangeModal(id);
                    ratingDiscount_InitDefValue(id);
                    handlePOJstree(id + ' .poShu', "ratingDiscountForm");
                    $('.dropdown-toggle2').dropdownHover();
                    tab2ChilFormValidate(id, "ratingDiscountForm");
                } else if (priceType == 9) {
                    //One-time charge :
                    handleSelectPriceItem4Modal(id);
                    handleSelectPriceItem3Modal(id);
                    tab2ChilFormValidate(id, "oneTimeChargeForm");
                } else if (priceType == 8) {
                    //Billing discount :
                    handleSelectPriceItem23Modal(id);
                    billingDiscount_InitDefValue(id);
                    handleBillDisChargeTableAddRow(id);
                    tab2ChilFormValidate(id, "billingDiscountForm");
                }else if (priceType == 3) {
                    //Free Resource :
                	freeResourceInit(id);
                	handleUserStateJstree(id + ' .poShu', "freeResourceForm");
                	handleSelectFreeResourceItemModal(id);
                    tab2ChilFormValidate(id, "freeResourceForm");
                    $('.dropdown-toggle2').dropdownHover();
                }
                handleDatePickers(id + ' .date-picker');
                //setDefValue(id, selector,draw);
                App.ajaxInit();
            }
        }
    }
}();
//Save Price Plan Base Information:
function saveOrUpdatePricePlan(savebut) {
	
        var thisForm = $(savebut).closest("form[id=pricePlanForm]");
        if ($(thisForm).find("select[id=vaildType]").val() == "11") {
            $(thisForm).find("select[id=validUnit]").val('');
        }
        //验证输入：
        if (thisForm.valid() == false) {
            return false;
        }
        var pricePlanSpecIdList = $(thisForm).find('#pricePlanAttrListDiv').find("input[id=pricePlanSpecId]");
        var defaultValList = $(thisForm).find('#pricePlanAttrListDiv').find("input[id=defaultVal],select[id=defaultVal]");
        var defaultValNameList = $(thisForm).find('#pricePlanAttrListDiv').find("input[id=defaultValName],select[id=defaultValName]");
        if (pricePlanSpecIdList != null && pricePlanSpecIdList != undefined && pricePlanSpecIdList.length > 0) {
            var ids = "";
            var vals = "";
            var names = "";
            var j = 0;
            for (var i = 0; i < pricePlanSpecIdList.length; i++) {
                var obj = pricePlanSpecIdList[i];
                var _val = "";
                var _name = "";
                if ($(obj).prop("checked")) {
                    if (ids == "") {
                        ids += $(obj).val();
                    } else {
                        ids += "," + $(obj).val();
                    }
                    var vObj = defaultValList[i];
                    if ($(vObj) != null && $(vObj) != undefined) {
                        _val = $(vObj).val();
                    }
                    if (j == 0) {
                        vals += _val
                    } else {
                        vals += "," + _val
                    }
                    var nObj = defaultValNameList[i];
                    if ($(nObj) != null && $(nObj) != undefined) {
                        _name = $(nObj).val();
                        if (_name == "") {
                            _name = "null"
                        }
                    }
                    if (j == 0) {
                        names += _name
                    } else {
                        names += "," + _name
                    }
                    j++;
                }
            }
            $(thisForm).find("input[id=pricePlanSpecIds]").val(ids);
            $(thisForm).find("input[id=defaultVals]").val(vals);
            $(thisForm).find("input[id=defaultValNames]").val(names);
        }
        //pricingTarget:
        var prodOfferId = $("#prodOfferId").val();
        var pricingTargetId = $(thisForm).find("input[id=pricingTargetId]").val();
        //pricingPlan:
        var pricingInfoId = $(thisForm).find("input[id=pricingInfoId]").val();
        var applicType = $(thisForm).find("input[id=applicType]").val();
        var pricingName = $(thisForm).find("input[id=pricingName]").val();
        var billingPriority = $(thisForm).find("select[id=billingPriority] option:selected").val();
        //pricePlanLifeCycle:
        var subEffectMode = $(thisForm).find("select[id=subEffectMode]").val();
        var subDelayUnit = $(thisForm).find("input[id=subDelayUnit]").val();
        var subDelayCycle = $(thisForm).find("#subDelayCycle").val();
        var validUnit = $(thisForm).find("input[id=validUnit]").val();
        var vaildType = $(thisForm).find("select[id=vaildType]").val();
        var fixedExpireDate = $(thisForm).find("input[id=fixedExpireDate]").val();
        //Price Plan Attribute:
        var pricePlanSpecIds = $(thisForm).find("#pricePlanSpecIds").val();
        var defaultVals = $(thisForm).find("#defaultVals").val();
        var defaultValNames = $(thisForm).find("#defaultValNames").val();
        //Price Object-Product:
        var relIds = $(thisForm).find("input[id=relIds]").val();
        $.ajax({
            type: "POST",
            async: true,
            url: APP_PATH + "/pardOfferPricePlan/savrOrUpdatePricePlan.shtml",
            dataType: 'json',
            //data:$("#pricePlanForm").serialize(),
            data: {
                prodOfferId: prodOfferId,
                pricingTargetId: pricingTargetId,
                pricingInfoId: pricingInfoId,
                applicType: applicType,
                pricingName: pricingName,
                billingPriority: billingPriority,
                subEffectMode: subEffectMode,
                subDelayUnit: subDelayUnit,
                subDelayCycle: subDelayCycle,
                validUnit: validUnit,
                vaildType: vaildType,
                fixedExpireDate: fixedExpireDate,
                pricePlanSpecIds: pricePlanSpecIds,
                defaultVals: defaultVals,
                defaultValNames: defaultValNames,
                relIds: relIds
            },
            success: function(msg) {
                //{"pricingTargetId":"100000051","priceInfoId":"100000080"}
                if (msg.priceInfoId != null && msg.priceInfoId != "" && msg.pricingTargetId != null && msg.pricingTargetId != "") {
                    $(thisForm).find("input[id=pricingInfoId]").val(msg.priceInfoId);
                    $(thisForm).find("input[id=pricingTargetId]").val(msg.pricingTargetId);
                    var thisPricePlanTab = $(savebut).closest(".price-plan-tab");
                    $(thisPricePlanTab).find("#chargeInformation").show();
                    toastr.success(_saveSuccess);
                } else {
                    toastr.error(_saveFail + ":<br/> " + msg.desc);
                }
            }
        });
        /*
    $.ajax({
        type: "POST",
        async:true,
        url: APP_PATH+"/pardOfferPricePlan/saveOrUpdatePricePlan.shtml",
        dataType:'json',
        data:thisForm.serialize(),
        success:function(msg){
            //{"pricingTargetId":"100000051","priceInfoId":"100000080","code":"0000","desc":"Success"}
            if(msg.code!=null && msg.code=="0000" && msg.priceId!=null && msg.priceId!=""){
                $(thisForm).find("#pricingInfoId").val(msg.priceInfoId);
                $(thisForm).find("#pricingTargetId").val(msg.pricingTargetId);
                var thisPricePlanTab = $(savebut).closest(".price-plan-tab");
                $(thisPricePlanTab).find("#chargeInformation").show();
                toastr.success('Save Success.');
            }else{
                toastr.error('Save Fail : '+msg.desc);
            }
        }
    });
    */
    }
    //Save Basic Tariff:
var inputV = "1";
var frist = "1";

function saveOrUpdateBasicTariff(savebut) {
        var thisForm = $(savebut).closest("form[id=basicTariffForm]");
        //验证输入：
        if (thisForm.valid() == false) {
            return false;
        }
        var begin = $(thisForm).find("input[id=formatEffDate]").val();
        var over = $(thisForm).find("input[id=formatExpDate]").val();
        var ass, aD, aS;
        var bss, bD, bS;
        ass = begin.split("-");
        aD = new Date(ass[0], ass[1] - 1, ass[2]);
        aS = aD.getTime();
        bss = over.split("-");
        bD = new Date(bss[0], bss[1] - 1, bss[2]);
        bS = bD.getTime();
        if (aS > bS) {
            toastr.error(_prodDateStartEnd);
            return false;
        }
        var detail = "";
        if (inputV == "1") {
            if (!($(thisForm).find("input[id=rateVal]").val() != "" && $(thisForm).find("input[id=baseVal]").val() != "")) {
                toastr.error(_simpleNotNull);
                return;
            }
            detail = $(thisForm).find("input[id=rateVal]").val() + "," + $(thisForm).find("input[id=baseVal]").val();
        } else if (inputV == "2") {
            var len = $(thisForm).find("table[id=ladderFeeTab]>tbody>tr").length;
            var val = $(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").val();
            if (val != "-1") {
                $(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
                toastr.error(_endWith1);
                return;
            }
            var ladderFee = "";
            for (var i = 0; i < len; i++) {
                if ((i != len - 1) && parseInt($(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")>td:eq(0)>input:eq(0)").val()) >= parseInt($(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + (i) + ")>td:eq(0)>input:eq(1)").val())) {
                    $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")>td:eq(0)>input:eq(1)").focus();
                    toastr.error(_greater);
                    return;
                }
                if (i + 1 < len && $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")>td:eq(0)>input:eq(1)").val() != $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + (i + 1) + ")>td:eq(0)>input:eq(0)").val()) {
                    toastr.error(_equal1 + (i + 2) + _equal2 + (i + 1));
                    $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + (i + 1) + ")>td:eq(0)>input:eq(0)").focus();
                    return;
                }
                if ($(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(i).find("input[id=baseVal]").val() == "") {
                    toastr.error(_notNullBase);
                    return;
                } else {
                    ladderFee += (i + 1) + "," + $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")").find("#startval").val() + "," + $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")").find("#endVal").val() + "," + $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")").find("#rateVal").val() + "," + $(thisForm).find("table[id=ladderFeeTab]>tbody>tr:eq(" + i + ")").find("#baseVal").val() + ";";
                }
                if ($(thisForm).find("table[id=ladderFeeTab]>tbody>tr").eq(i).find("input[id=rateVal]").val() == "") {
                    toastr.error(_notNullRateVal);
                    return;
                }
            }
            detail = ladderFee.substring(0, ladderFee.length - 1);
        }
        var baseItemTypeObj = $(thisForm).find("select[id=baseItemType]");
        var baseItemTypeText = $(baseItemTypeObj).find("option:selected").text();
        var ratingUnitTypeObj = $(thisForm).find("select[id=ratingUnitType]");
        var ratingUnitTypeText = $(ratingUnitTypeObj).find("option:selected").text();
        var currencyUnitTypeObj = $(thisForm).find("select[id=currencyUnitType]");
        var currencyUnitTypeText = $(currencyUnitTypeObj).find("option:selected").text();
        var other = baseItemTypeText + "," + ratingUnitTypeText + "," + currencyUnitTypeText + "," + inputV;
		var baseQosTypeObj=$(thisForm).find("select[id=baseQosType]");
		var baseQosTypeVal=$(baseQosTypeObj).find("option:selected").val();
         var qosVal=$(baseItemTypeObj).find("option:selected").val();
     	 if(qosVal=='53001'){
      		  $(thisForm).find("#baseQosTypeVal").val(baseQosTypeVal);
		}else{
			$(thisForm).find("#baseQosTypeVal").val("");
		}
        $(thisForm).find("#other").val(other);
        $(thisForm).find("#details").val(detail);
        $.ajax({
            type: "POST",
            async: true,
            url: APP_PATH + "/pardOfferPricePlan/saveOrUpdateBasicTariff.shtml",
            dataType: 'json',
            data: thisForm.serialize(),
            success: function(msg) {
                //{"priceId":"100000010","code":"0000","desc":"Success"}
                if (msg.code != null && msg.code == "0000" && msg.priceId != null && msg.priceId != "") {
                    $(thisForm).find("#priceId").val(msg.priceId);
                    $(thisForm).find("#actionType").val("update");
                    toastr.success(_saveSuccess);
                } else {
                    toastr.error(_saveFail + ":<br/> " + msg.desc);
                }
            }
        });
    }
    //Save Recurring Charge:
function saveOrUpdateRecurringFee(savebut) {
        var thisForm = $(savebut).closest("form[id=recurringChargeForm]");
        //验证输入：
        if (thisForm.valid() == false) {
            return false;
        }
        var other = $(thisForm).find("#currencyUnitType").find("option:selected").text();
        $(thisForm).find("#other").val(other);
        var prodOfferId = $("#prodOfferId").val();
        $(thisForm).find("#offerId").val(prodOfferId);
        $.ajax({
            type: "POST",
            async: true,
            url: APP_PATH + "/pardOfferPricePlan/saveOrUpdateRecurringFee.shtml",
            dataType: 'json',
            data: thisForm.serialize(),
            success: function(msg) {
                //{"priceId":"100000010","code":"0000","desc":"Success"}
                if (msg.code != null && msg.code == "0000" && msg.priceId != null && msg.priceId != "") {
                    $(thisForm).find("#priceId").val(msg.priceId);
                    $(thisForm).find("#actionType").val("update");
                    toastr.success(_saveSuccess);
                } else {
                    toastr.error(_saveFail + ":<br/> " + msg.desc);
                }
            }
        });
    }
    //Save Rating Discount:
function saveOrUpdateRatingDiscount(savebut) {
        var thisForm = $(savebut).closest("form[id=ratingDiscountForm]");
        //验证输入：
        if (thisForm.valid() == false) {
            return false;
        }
        var begin = $(thisForm).find("#formatEffDate").val();
        var over = $(thisForm).find("#formatExpDate").val();
        var ass, aD, aS;
        var bss, bD, bS;
        ass = begin.split("-");
        aD = new Date(ass[0], ass[1] - 1, ass[2]);
        aS = aD.getTime();
        bss = over.split("-");
        bD = new Date(bss[0], bss[1] - 1, bss[2]);
        bS = bD.getTime();
        if (aS > bS) {
            toastr.error(_prodDateStartEnd);
            return false;
        }
        var discountType = $(thisForm).find("#discountType").val();
        if ("0" == discountType) {
            //Percentage:
            $(thisForm).find("#maxiMum").val($(thisForm).find("#percentyTabMaxiMum").val());
            $(thisForm).find("#currencyUnitType").val($(thisForm).find("#percentyTabCurrencyUnitType").val());
            $(thisForm).find("#numberator").val($(thisForm).find("#percentyTabNumberator").val());
            $(thisForm).find("#demominator").val($(thisForm).find("#percentyTabDemominator").val());
        } else if ("1" == discountType) {
            //Fixed charge:
            $(thisForm).find("#maxiMum").val($(thisForm).find("#fixedTabMaxiMum").val());
            $(thisForm).find("#currencyUnitType").val($(thisForm).find("#fixedTabCurrencyUnitType").val());
            $(thisForm).find("#numberator").val(1);
            $(thisForm).find("#demominator").val(1);
        } else if ("2" == discountType) {
            //Free:
            $(thisForm).find("#maxiMum").val(0);
            $(thisForm).find("#currencyUnitType").val($(thisForm).find("#fixedTabCurrencyUnitType").val());
            $(thisForm).find("#numberator").val(0);
            $(thisForm).find("#demominator").val(100);
        }
        $.ajax({
            type: "POST",
            async: true,
            url: APP_PATH + "/pardOfferPricePlan/saveOrUpdateRatingDiscount.shtml",
            dataType: 'json',
            data: thisForm.serialize(),
            success: function(msg) {
                //{"priceId":"100000010","code":"0000","desc":"Success"}
                if (msg.code != null && msg.code == "0000" && msg.priceId != null && msg.priceId != "") {
                    $(thisForm).find("#priceId").val(msg.priceId);
                    $(thisForm).find("#actionType").val("update");
                    toastr.success(_saveSuccess);
                } else {
                    toastr.error(_saveFail + ":<br/> " + msg.desc);
                }
            }
        });
    }
    //Save     One-Time Charge:
function saveOrUpdateOneTimeFee(savebut) {
        var thisForm = $(savebut).closest("form[id=oneTimeChargeForm]");
        //验证输入：
        if (thisForm.valid() == false) {
            return false;
        }
        var other = $(thisForm).find("#currencyUnitType").find("option:selected").text();
        $(thisForm).find("#other").val(other);
        var prodOfferId = $("#prodOfferId").val();
        $(thisForm).find("#offerId").val(prodOfferId);
        $.ajax({
            type: "POST",
            async: false,
            url: APP_PATH + "/pardOfferPricePlan/saveOrUpdateOneTimeFee.shtml",
            dataType: 'json',
            data: thisForm.serialize(),
            success: function(msg) {
                //{"priceId":"100000010","code":"0000","desc":"Success"}
                if (msg.code != null && msg.code == "0000" && msg.priceId != null && msg.priceId != "") {
                    $(thisForm).find("#priceId").val(msg.priceId);
                    $(thisForm).find("#actionType").val("update");
                    toastr.success(_saveSuccess);
                } else {
                    toastr.error(_saveFail + ":<br/> " + msg.desc);
                }
            }
        });
    }
    //Save Billing Discount:
function saveOrUpdateBillingDiscount(savebut) {
    var thisForm = $(savebut).closest("form[id=billingDiscountForm]");
    //验证输入：
    if (thisForm.valid() == false) {
        return false;
    }
    var begin = $(thisForm).find("#formatEffDate").val();
    var over = $(thisForm).find("#formatExpDate").val();
    
    var prodOfferId = $("#prodOfferId").val();
    $(thisForm).find("#offerId").val(prodOfferId);
    
    var ass, aD, aS;
    var bss, bD, bS;
    ass = begin.split("-");
    aD = new Date(ass[0], ass[1] - 1, ass[2]);
    aS = aD.getTime();
    bss = over.split("-");
    bD = new Date(bss[0], bss[1] - 1, bss[2]);
    bS = bD.getTime();
    if (aS > bS) {
        toastr.error(_prodDateStartEnd);
        return false;
    }
    var retMsg = "";
    var tabId = "#detailTab1";
    var promTypeVal = $(thisForm).find("select[id=promType]").val();
    if (promTypeVal == 1) {
        tabId = "#detailTab";
    }
    var len = $(thisForm).find(tabId + ">tbody>tr").length;
    var val = $(thisForm).find(tabId + ">tbody>tr").eq(len - 1).find("input[id=endVal]").val();
    if (val != "-1") {
        toastr.error(_endWith1);
        return;
    }
    for (var i = 0; i < len; i++) {
        if ((i != len - 1) && parseInt($(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=startval]").val()) >= parseInt($(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=endVal]").val())) {
            toastr.error(_greater); //结束值必须大于开始值
            $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=endVal]").focus();
            return;
        }
        if (i + 1 < len && $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=endVal]").val() != $(thisForm).find(tabId + ">tbody>tr").eq(i + 1).find("input[id=startval]").val()) {
        	toastr.error(_equal1+(i+2)+" "+_equal2+(i+1));
            $(thisForm).find(tabId + ">tbody>tr").eq(i + 1).find("input[id=startval]").focus();
            return;
        } else {
            retMsg += (i + 1) + "," + $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=startval]").val() + "," + $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=endVal]").val() + ",";
            if (promTypeVal == 1) {
                var n = $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=numerator]").val();
                var d = $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=denominator]").val();
                if (n == "") {
                    toastr.error(_nNotNull);
                    $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=numerator]").focus();
                    return;
                }
                if (d == "") {
                    toastr.error(_dNotNull);
                    $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=denominator]").focus();
                    return;
                }
                if (d == "0") {
                    toastr.error(_endWith0d);
                    $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=denominator]").focus();
                    return;
                }
                if (parseInt(n) > parseInt(d)) {
                    toastr.error(_nLessThand);
                    $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=denominator]").focus();
                    return;
                }
                retMsg += n + "," + d + ";";
            } else {
                if ($(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=baseVal]") == "") {
                    toastr.error(_notNull1);
                    $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=baseVal]").focus();
                    return;
                }
                retMsg += $(thisForm).find(tabId + ">tbody>tr").eq(i).find("input[id=baseVal]").val() + ";";
            }
        }
    }
    retMsg = retMsg.substring(0, retMsg.length - 1);
    $(thisForm).find("#details").val(retMsg);
    $.ajax({
        type: "POST",
        async: false,
        url: APP_PATH + "/pardOfferPricePlan/saveOrUpdateBillingDiscount.shtml",
        dataType: 'json',
        data: thisForm.serialize(),
        success: function(msg) {
            //{"priceId":"100000010","code":"0000","desc":"Success"}
            if (msg.code != null && msg.code == "0000" && msg.priceId != null && msg.priceId != "") {
                $(thisForm).find("#priceId").val(msg.priceId);
                $(thisForm).find("#actionType").val("update");
                toastr.success(_saveSuccess);
            } else {
                toastr.error(_saveFail + ":<br/> " + msg.desc);
            }
        }
    });
}
    //Save Free Resource:
function saveOrUpdateFreeResource(savebut) {
        var thisForm = $(savebut).closest("form[id=freeResourceForm]");
        var allowanceType=thisForm.find("#allowanceTypeDiv input[name=allowanceType]:checked").val();
        if(allowanceType==3){
      	  var tableObj = thisForm.find("#cycleRangeTable");
            var len = tableObj.find("tbody>tr").length;
      	  if(len==1){
          	  toastr.error("Cycle Range must add  multiple rows.");
                return;
            }
      	  var firstLineStartValue=tableObj.find("tbody>tr").eq(0).find("input[id=startval]").val();
            if(firstLineStartValue!='1'){
          	  toastr.error("Cycle Range start value of row 1 must be 1.");
                return;
            }
        }
        //验证输入：
        if (thisForm.valid() == false) {
            return false;
        }
        //验证Effective Date:
        var begin = $(thisForm).find("#formatEffDate").val();
        var over = $(thisForm).find("#formatExpDate").val();
        var ass, aD, aS;
        var bss, bD, bS;
        ass = begin.split("-");
        aD = new Date(ass[0], ass[1] - 1, ass[2]);
        aS = aD.getTime();
        bss = over.split("-");
        bD = new Date(bss[0], bss[1] - 1, bss[2]);
        bS = bD.getTime();
        if (aS > bS) {
            toastr.error(_prodDateStartEnd);
            return false;
        }

        var detail="";
        var ladderFee = "";
        var allowanceType = $(thisForm).find("input[name=allowanceType]:checked").val();  
        if(allowanceType==1){
        	//Specific Amount:
            var amount = $(thisForm).find("#amount").val();
            if(amount==""){
            	$(thisForm).find("#amount").focus();
            	toastr.error("Free Source Amount is required.");
	            return;
            }
            var measureId = $(thisForm).find("#measureId").val();
            if(measureId==""){
            	$(thisForm).find("#measureId").focus();
            	toastr.error("Free Source Amount Unit is required.");
	            return;
            }
            ladderFee +=  "0,-1,"+amount+","+measureId+";";
        }else if(allowanceType==2){
        	//Unlimited:
            ladderFee +=  "0,-1,999999999999999999,104;";
        }else if(allowanceType==3){
	        //Designated Amount:
	        var len = $(thisForm).find("table[id=cycleRangeTable]>tbody>tr").length;
	        var val = $(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").val();
	        if (val != "-1") {
	            $(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
	            toastr.error(_endWith1);
	            return;
	        }
	        for (var i = 0; i < len; i++) {
	            if ((i != len - 1) && parseInt($(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=startval]").val()) >= parseInt($(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=endVal]").val())) {
	            	$(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(len - 1).find("input[id=endVal]").focus();
	                toastr.error(_greater);
	                return;
	            }
	            if (i + 1 < len && $(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=endVal]").val() != $(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i+1).find("input[id=startval]").val()) {
	                toastr.error(_equal1 + (i + 2)  + " " + _equal2 + (i + 1));
	                $(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i+1).find("input[id=startval]").focus();
	                return;
	            }
	            if ($(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=unlimited]").is(':checked')== false) {
	            	if ($(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=quantity_amount]").val() == "") {
	            		$(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=quantity_amount]").focus();
	                    toastr.error("Quantity  is required.");
	                    return;
	                }
	            }
	            if ($(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=unlimited]").is(':checked')== true) {
	            	$(thisForm).find("table[id=cycleRangeTable]>tbody>tr").eq(i).find("input[id=quantity_amount]").val("999999999999999999"); 
	            }
	            ladderFee += $(thisForm).find("table[id=cycleRangeTable]>tbody>tr:eq(" + i + ")").find("#startval").val() + "," + $(thisForm).find("table[id=cycleRangeTable]>tbody>tr:eq(" + i + ")").find("#endVal").val() + "," + $(thisForm).find("table[id=cycleRangeTable]>tbody>tr:eq(" + i + ")").find("#quantity_amount").val() + "," + $(thisForm).find("table[id=cycleRangeTable]>tbody>tr:eq(" + i + ")").find("#quantity_measureId").val() + ";";
	        }
        }
        detail = ladderFee.substring(0, ladderFee.length - 1);
        $(thisForm).find("#details").val(detail);
        
        $.ajax({
            type: "POST",
            async: true,
            url: APP_PATH + "/pardOfferPricePlan/saveOrUpdateFreeResource.shtml",
            dataType: 'json',
            data: thisForm.serialize(),
            success: function(msg) {
                //{"priceId":"100000010","code":"0000","desc":"Success"}
                if (msg.code != null && msg.code == "0000" && msg.priceId != null && msg.priceId != "") {
                    $(thisForm).find("#priceId").val(msg.priceId);
                    $(thisForm).find("#actionType").val("update");
                    toastr.success(_saveSuccess);
                } else {
                    toastr.error(_saveFail + ":<br/> " + msg.desc);
                }
            }
        });
    }

var loadEx_tab2 = function(id, _url) {
    var target = $(id);
    var isLoad = target.attr("isLoad");
    if (isLoad == '0') {
        target.load(_url, function() {
            target.attr({
                "isLoad": "1"
            });
            PricePlanAdd.init("tab2", id, "");
            $(id + ' ul[data-set="addTarget"]').find('a').each(function(index) {
                $(this).attr('data-target', id + '_' + index);
            })
        });
    }
}
var loadEx_tab2Chil = function(id, priceType, _url) {
    var target = $(id);
    var isLoad = target.attr("isLoad");
    if (isLoad == '0') {
        target.load(_url, function() {
            target.attr({
                "isLoad": "1"
            });
            //PricePlanAdd.tab2Chil_init(id,priceType);
            PricePlanAdd.init("tab2Chil", id, priceType);
        });
    }
}

function unlimitedIsSelect(selObj){
    var thisTr = $(selObj).closest("tr");
	//var tr = selObj.parentElement.parentElement;
	var quantityTd = thisTr.find("td").eq(3);
    if($(selObj).is(':checked') == false ){
    	quantityTd.find("#quantity_amount").show();
    	quantityTd.find("#quantity_measureId").show();
    	quantityTd.find("#quantity_unlimitamount").hide();
    	quantityTd.find("#quantity_amount").show("");
    	if(quantityTd.find("#quantity_amount").val()=="999999999999999999"){
    		quantityTd.find("#quantity_amount").val("");
    	}
	 }else{
		quantityTd.find("#quantity_amount").hide();
		quantityTd.find("#quantity_measureId").hide();
		quantityTd.find("#quantity_unlimitamount").show();
	 }
}



var ctrQos=function(obj){
 		var qosVal=$(obj).find("option:selected").val();
 		if(qosVal=='53001'){
     		$("#qosDiv").show();
 		}else{
     	   $("#qosDiv").hide();
 	}
}